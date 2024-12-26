package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.swing.DefaultListModel;

import persistencia.*;
import persistencia.interfaces.IAdaptadorContactoDAO;
import persistencia.interfaces.IAdaptadorGrupoDAO;
import persistencia.interfaces.IAdaptadorMensajeDAO;
import persistencia.interfaces.IAdaptadorUsuarioDAO;
import dominio.*;
import excepciones.*;

public class Controlador {
	
	private Usuario usuarioActual;
	
	private static Controlador controlador;
	
	// Gestores necesarios para la lógica de la aplicación.
	private RepositorioUsuarios repositorioUsuarios;
	private IAdaptadorUsuarioDAO adaptadorUsuario;
	private IAdaptadorContactoDAO adaptadorContacto;
	private IAdaptadorMensajeDAO adaptadorMensaje;
	private IAdaptadorGrupoDAO adaptadorGrupo;
	
	// Implementación del patron Singleton	
	public static Controlador getInstance() {
		if(controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
	}
		
	private Controlador() {
		repositorioUsuarios  = RepositorioUsuarios.getInstance();
		inicializarAdaptadores();
	}
	
	private void inicializarAdaptadores() {
		FactoriaDAO factoria = null;
		try {
			factoria = FactoriaDAO.getInstance(FactoriaDAO.DAO_TDS);
		} catch (ExcepcionDAO e) {
			e.printStackTrace();
		}

		adaptadorGrupo = factoria.getGrupoDAO();
		adaptadorContacto = factoria.getContactoDAO();
		adaptadorMensaje = factoria.getMensajeDAO();
		adaptadorUsuario = factoria.getUsuarioDAO();
	}
	
	/**
	 * Registra al usuario en la aplicacion si los parametros cumplen los requisitos.
	 * 
	 * @param nombre
	 * @param apellidos
	 * @param movil
	 * @param contrasena
	 * @param contrasenaRepe
	 * @param email
	 * @param fechaNacimiento
	 * @param pathImagen
	 * @param mensajeSaludo
	 * @return
	 * @throws ExcepcionRegistro
	 * @throws ExcepcionDAO 
	 */

	public boolean registrarUsuario(String nombre, String apellidos, String movil, String contrasena,
            String contrasenaRepe, String email, Date fechaNacimiento,
            String pathImagen, String mensajeSaludo) throws ExcepcionRegistro {
		
		
		// Si el telefono ya está registrado se lanza una excepción
		if(!repositorioUsuarios.getUsuarioPorTelefono(movil).isEmpty()) {
			throw new ExcepcionRegistro("El teléfono ya está registrado");
		}
		
		// Validacion de requisitos
		validarCamposObligatorios(nombre, apellidos, movil, contrasena, contrasenaRepe, email);
		validarContrasenas(contrasena, contrasenaRepe);
		validarEmail(email);		

		Usuario usuario = new Usuario(nombre + " " + apellidos, movil, contrasena, email);
		usuarioActual = usuario;
		
		configurarOpcionales(usuario, fechaNacimiento, pathImagen, mensajeSaludo);
		repositorioUsuarios.anadirUsuario(usuario);
		
	    try {
			adaptadorUsuario.registrarUsuario(usuario);
		} catch (ExcepcionRegistroDuplicado e) {
			e.printStackTrace();
			throw new ExcepcionRegistro("Usuario ya registrado");
		}
		
		return true;
	}
	
	/**
	 * Permite al usuario loguarse en la aplicación si sus credenciales son correctas.
	 * @param telefono
	 * @param contrasena
	 * @return
	 * @throws ExcepcionLogin
	 */
	
	public boolean loguearUsuario(String telefono, String contrasena) throws ExcepcionLogin {
	    
		repositorioUsuarios.getUsuarioPorTelefono(telefono)
	    .ifPresentOrElse(
	        u -> usuarioActual = u, // Si el usuario existe, asignarlo
	        () -> { throw new ExcepcionLogin("El teléfono no está registrado."); } // Si no, lanzar la excepción
	    );
	        	    
	
	    if (!usuarioActual.getContrasena().equals(contrasena)) {
	        throw new ExcepcionLogin("La contraseña es incorrecta.");
	    }
	    
	    return true;
	}

	
	/**
	 * Método para añadir un nuevo contacto al usuario cuyo teléfono se pasa como parámetro.
	 * @param tlf El número de teléfono del contacto
	 * @param nombreContacto El nombre personalizado para el contacto
	 * @return true si el contacto se agregó correctamente
	 * @throws ExcepcionContacto si el contacto ya existe o el usuario no está registrado
	 * @throws ExcepcionDAO 
	 */
	public boolean agregarContacto(String tlf, String nombreContacto) throws ExcepcionAgregarContacto {
	    // Verifica si hay un usuario autenticado
	    if (usuarioActual == null) {
	        throw new ExcepcionAgregarContacto("No hay un usuario actual autenticado.");
	    }

	    // Verifica si el contacto ya está en la lista de contactos del usuario actual
	    if (usuarioActual.getContactoPorTelefono(tlf).isPresent()) {
	        throw new ExcepcionAgregarContacto("El contacto ya está agregado.");
	    }

	    // Si no existe, crea y registra el nuevo contacto
	    ContactoIndividual nuevoContacto = new ContactoIndividual(nombreContacto, tlf);
	    usuarioActual.getListaContactos().add(nuevoContacto);

	    try {
	        // Registra el contacto en el adaptador de contactos
	        adaptadorContacto.registrarContacto(nuevoContacto);
	        // Actualiza el usuario con el nuevo contacto en el adaptador de usuarios
	        adaptadorUsuario.modificarUsuario(usuarioActual);
	    } catch (ExcepcionRegistroDuplicado e) {
	    	e.printStackTrace();
	        throw new ExcepcionAgregarContacto("Error al registrar el contacto: contacto duplicado.");
	    }

	    return true;
	}
	
	public boolean crearGrupo(String nombreGrupo, DefaultListModel<ContactoIndividual> listaMiembros) throws ExcepcionCrearGrupo {
		
		List<ContactoIndividual> miembrosGrupo = new ArrayList<ContactoIndividual>();
		
		  for (int i = 0; i < listaMiembros.getSize(); i++) {
	            miembrosGrupo.add(listaMiembros.getElementAt(i));
	        }
		  
		  Grupo grupoNuevo = new Grupo(nombreGrupo, usuarioActual, miembrosGrupo);
		  usuarioActual.addGrupo(grupoNuevo);
		  
		  
		  try {
			adaptadorGrupo.registrarGrupo(grupoNuevo);
			adaptadorUsuario.modificarUsuario(usuarioActual);
		} catch (ExcepcionRegistroDuplicado e) {
			e.printStackTrace();
			throw new ExcepcionCrearGrupo("El grupo ya ha sido registrado"); 
		}
		  
		return true;
	}
	
	/*
	public void enviarMensaje(String texto, Usuario emisor) {
        for (Contacto contacto : miembros) {
            Mensaje mensaje = new Mensaje(texto, emisor);
            contacto.recibirMensaje(mensaje); // Suponiendo que Contacto tiene recibirMensaje
        }
    }
    */
    public List<ContactoIndividual> obtenerContactos() {
	    if (usuarioActual != null) {
	        return usuarioActual.getListaContactos(); 
	    }
	    return new ArrayList<>(); 
    }
		
	
	public String getImagenUsuario() {
		return usuarioActual.getPathImagen();
	}
	
	public String getNombreUsuario() {
		return usuarioActual.getNombreCompleto();
	}
	
	// -------- Funciones auxiliares ----------
	
	/**
	 * Encargado de validar que todos los campos obligatorios no esten vacíos.
	 * @param nombre
	 * @param apellidos
	 * @param movil
	 * @param contrasena
	 * @param contrasenaRepe
	 * @param email
	 * @throws ExcepcionRegistro
	 */

	private void validarCamposObligatorios(String nombre, String apellidos, String movil,
                   String contrasena, String contrasenaRepe, String email) throws ExcepcionRegistro {
		if (nombre.isEmpty() || apellidos.isEmpty() || movil.isEmpty() || contrasena.isEmpty()
				|| contrasenaRepe.isEmpty() || email.isEmpty()) {
			
			throw new ExcepcionRegistro("Rellene los campos obligatorios");
			
		}
	}
	
	/**
	 * Valida que las passwords introducidas por el usuario coincidan. 
	 * @param contrasena
	 * @param contrasenaRepe
	 * @throws ExcepcionRegistro
	 */

	private void validarContrasenas(String contrasena, String contrasenaRepe) throws ExcepcionRegistro {
		if (!contrasena.equals(contrasenaRepe)) {
			throw new ExcepcionRegistro("Las contraseñas no son iguales");
		}
	}
	
	/**
	 * Verifica que el email introducido por el usuario sigue un formato adecuado.
	 * @param email
	 * @throws ExcepcionRegistro
	 */
	private void validarEmail(String email) throws ExcepcionRegistro {
	    if (!email.matches("^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")) {
	        throw new ExcepcionRegistro("El email no es válido.");
	    }
	}
	
	/**
	 * Establece el valor de los campos opcionales del registro al usuario pasado como parámetro.
	 * @param usuario
	 * @param fechaNacimiento
	 * @param pathImagen
	 * @param mensajeSaludo
	 */
	
	private void configurarOpcionales(Usuario usuario, Date fechaNacimiento, String pathImagen, String mensajeSaludo) {
	    // Uso de Optional para manejar valores opcionales de manera segura
	    Optional.ofNullable(fechaNacimiento).ifPresent(usuario::setFechaNacimiento);
	    Optional.ofNullable(mensajeSaludo).filter(s -> !s.isEmpty()).ifPresent(usuario::setMensajeSaludo);
	    Optional.ofNullable(pathImagen)
	            .filter(icon -> !icon.equals(Usuario.IMAGEN_POR_DEFECTO))
	            .ifPresent(icon -> usuario.setPathImagen(icon));
	}
	

}
