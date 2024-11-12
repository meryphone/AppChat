package controlador;

import java.util.Date;
import java.util.Optional;

import javax.swing.Icon;
import javax.swing.ImageIcon;

import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.RepositorioUsuarios;
import dominio.Usuario;
import dominio.excepciones.*;

public class Controlador {
	
	private Usuario usuarioActual;
	
	private static Controlador controlador;
	
	// Gestores necesarios para la lógica de la aplicación.
	private RepositorioUsuarios repositorioUsuarios;
	//private AdaptadorUsuario adaptadorUsuario;
	
	
	private Controlador() {
		repositorioUsuarios  = RepositorioUsuarios.getInstance();
		// AdaptadorUsuario adaptadorUsuario = AdaptadorUsuario.getInstance;
	}
	
	// Patrón Singleton
	
	public static Controlador getInstance() {
		if(controlador == null) {
			controlador = new Controlador();
		}
		return controlador;
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
	 */

	public boolean registrarUsuario(String nombre, String apellidos, String movil, String contrasena,
            String contrasenaRepe, String email, Date fechaNacimiento,
            String pathImagen, String mensajeSaludo) throws ExcepcionRegistro {
		
		
		// Si el telefono ya está registrado se lanza una excepción
		if(repositorioUsuarios.getUsuarioPorTelefono(movil).isEmpty()) {
			throw new ExcepcionRegistro("El teléfono ya está registrado");
		}
		
		// Validacion de requisitos
		validarCamposObligatorios(nombre, apellidos, movil, contrasena, contrasenaRepe, email);
		validarContrasenas(contrasena, contrasenaRepe);
		validarEmail(email);		

		Usuario usuario = new Usuario(nombre + " " + apellidos, movil, contrasena, email);
		
		configurarOpcionales(usuario, fechaNacimiento, pathImagen, mensajeSaludo);
		
		usuarioActual = usuario;
		repositorioUsuarios.anadirUsuario(usuario);
	 // adapatadorUsuario.registrarUsuario(usuario);
		
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

	    return repositorioUsuarios.getUsuarioPorTelefono(telefono)
	            .map(usuario -> {
	                if (usuario.getContrasena().equals(contrasena)) {	// Aplica la acción al contenido del Optional si este no está vacio.
	                    return true; 
	                } else {
	                    throw new ExcepcionLogin("La contraseña es incorrecta"); 
	                }
	            })
	            .orElseThrow(() -> new ExcepcionLogin("El teléfono no se encuentra registrado"));
	}
	
	/**
	 * Método para añadir un nuevo contacto al usuario cuyo teléfono se pasa como parámetro.
	 * @param tlf El número de teléfono del contacto
	 * @param nombreContacto El nombre personalizado para el contacto
	 * @return true si el contacto se agregó correctamente
	 * @throws ExcepcionContacto si el contacto ya existe o el usuario no está registrado
	 */
	public boolean agregarContacto(String tlf, String nombreContacto) throws ExcepcionContacto {
		
	    if(!repositorioUsuarios.getUsuarioPorTelefono(tlf).isEmpty()){ // Si existe el contacto.
	    	
	    	 usuarioActual.getContactoPorTelefono(tlf).
	    	 ifPresent(contacto -> {
	    		 try {
	    			 throw new ExcepcionContacto("El contacto ya está agregado.");
	    		 }catch(ExcepcionContacto e) {
	    		        throw new RuntimeException(e); // Envolver en RuntimeException
	    		 }
             });
	    	 
	    	 usuarioActual.anadirContacto( new ContactoIndividual(nombreContacto,tlf,usuarioActual));
	    	 // adaptadorCOntacto.nañadorCOnatcto
	    	 
	    }else {
	    	throw new ExcepcionContacto("El usuario no existe");
	    }
	    
		return true;
	           
	}


	
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
