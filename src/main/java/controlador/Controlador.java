package controlador;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
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

		Usuario usuario = new Usuario(nombre + apellidos, movil, contrasena, email);
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
	    
	    Optional<Usuario> usuarioContacto = repositorioUsuarios.getUsuarioPorTelefono(tlf);
	    
	    if(usuarioContacto.isEmpty()) {
	    	throw new ExcepcionAgregarContacto("El número de télefono indicado no corresponde con ningún usuario");
	    }

	    // Si no está ya agregado y corresponde con un usuario de la explicación, crea y registra el nuevo contacto
	    ContactoIndividual nuevoContacto = new ContactoIndividual(nombreContacto, tlf, usuarioContacto.get());
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
	
	/**
	 * Crea un nuevo grupo con un nombre dado y una lista de miembros.
	 *
	 * @param nombreGrupo     Nombre del grupo a crear.
	 * @param listaMiembros   Lista de miembros que formarán parte del grupo.
	 * @return true si el grupo se crea correctamente.
	 * @throws ExcepcionCrearGrupo si el grupo ya está registrado o se produce algún error durante su creación.
	 */
	public boolean crearGrupo(String nombreGrupo, DefaultListModel<ContactoIndividual> listaMiembros) throws ExcepcionCrearGrupo {
	    
	    // Convertir la lista de miembros del modelo en una lista de ContactoIndividual
	    List<ContactoIndividual> miembrosGrupo = new ArrayList<>();
	    for (int i = 0; i < listaMiembros.getSize(); i++) {
	        miembrosGrupo.add(listaMiembros.getElementAt(i));
	    }

	    // Crear un nuevo grupo con los datos proporcionados
	    Grupo grupoNuevo = new Grupo(nombreGrupo, usuarioActual, miembrosGrupo);
	    usuarioActual.addGrupo(grupoNuevo);

	    try {
	        adaptadorGrupo.registrarGrupo(grupoNuevo); // Registrar el grupo en la base de datos
	        adaptadorUsuario.modificarUsuario(usuarioActual); // Actualizar los datos del usuario
	    } catch (ExcepcionRegistroDuplicado e) {
	        e.printStackTrace();
	        throw new ExcepcionCrearGrupo("El grupo ya ha sido registrado");
	    }

	    return true;
	}

	/**
	 * Modifica un grupo existente actualizando su lista de miembros.
	 *
	 * @param nombreGrupo     Nombre del grupo a modificar.
	 * @param listaMiembros   Nueva lista de miembros para el grupo.
	 * @return true si el grupo se modifica correctamente.
	 */
	public boolean modificarGrupo(String nombreGrupo, DefaultListModel<ContactoIndividual> listaMiembros) {
	    
	    // Convertir la lista de miembros del modelo en una lista de ContactoIndividual
	    List<ContactoIndividual> miembrosGrupo = new ArrayList<>();
	    for (int i = 0; i < listaMiembros.getSize(); i++) {
	        miembrosGrupo.add(listaMiembros.getElementAt(i));
	    }

	    // Obtener el grupo existente y actualizar su lista de miembros
	    Grupo grupoAmodificar = usuarioActual.getGrupoPorNombre(nombreGrupo);
	    grupoAmodificar.setMiembros(miembrosGrupo);

	    // Guardar los cambios en la base de datos 
	    adaptadorGrupo.modificarGrupo(grupoAmodificar);
	    adaptadorUsuario.modificarUsuario(usuarioActual);

	    return true;
	}
	
	public Grupo getGrupoPorNombre(String nombreGrupo){
		return usuarioActual.getGrupoPorNombre(nombreGrupo);
	}

	/**
	 * Obtiene todos los contactos y grupos asociados al usuario actual.
	 *
	 * @return Lista de contactos y grupos del usuario actual.
	 */
	public List<Contacto> obtenerContactosYgrupos() {
	    
	    List<Contacto> contactosYgrupos = new ArrayList<>();

	    if (usuarioActual != null) {
	        // Agregar contactos y grupos del usuario actual a la lista
	        contactosYgrupos.addAll(usuarioActual.getListaContactos());
	        contactosYgrupos.addAll(usuarioActual.getGrupos());
	        return contactosYgrupos;
	    }

	    return new ArrayList<>(); // Retornar lista vacía si no hay usuario actual
	}

	/**
	 * Obtiene los nombres de los grupos asociados al usuario actual.
	 *
	 * @return Lista de nombres de grupos del usuario actual.
	 */
	public List<String> obtenerNombresGruposUsuario() {
	    return usuarioActual.getNombresGrupos();
	}

	/**
	 * Obtiene la lista de contactos individuales asociados al usuario actual.
	 *
	 * @return Lista de ContactoIndividual del usuario actual.
	 */
	public List<ContactoIndividual> obtenerContactos() {
	    
	    if (usuarioActual != null) {
	        // Retornar lista de contactos si el usuario actual existe
	        return usuarioActual.getListaContactos();
	    }

	    return new ArrayList<>(); // Retornar lista vacía si no hay usuario actual
	}

	/**
	 * Obtiene la ruta de la imagen del usuario actual.
	 *
	 * @return Ruta de la imagen del usuario actual.
	 */
	public String getImagenUsuario() {
	    return usuarioActual.getPathImagen();
	}

	/**
	 * Obtiene el nombre completo del usuario logueado.
	 *
	 * @return Nombre completo del usuario actual.
	 */
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
