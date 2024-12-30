package controlador;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Supplier;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.pdf.PdfWriter;

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
	
	// Mapa con suppliers para crear los descuentos
	private static Map<String,Supplier<Descuento>> supplierDescuento = new HashMap<String, Supplier<Descuento>>();
	
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
		supplierDescuento.put("DescuentoPorMensajes", DescuentoMensajes::new);
		supplierDescuento.put("DescuentoFechaRegistro", DescuentoFechaRegistro::new);
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
		if(repositorioUsuarios.getUsuarioPorTelefono(movil).isPresent()) {
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
	 */
	public boolean agregarContacto(String tlf, String nombreContacto) throws ExcepcionAgregarContacto {
	    
	    // Verifica si el nombre del contacto ya está en uso
	    if(usuarioActual.getContactoPorNombre(nombreContacto).isPresent()) {
	        throw new ExcepcionAgregarContacto("El nombre del contacto ya está en uso");
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
	public boolean crearGrupo(String nombreGrupo, String imagenGrupo, DefaultListModel<ContactoIndividual> listaMiembros) throws ExcepcionCrearGrupo {
	    
		if(nombreGrupo.equals("")) {
			throw new ExcepcionCrearGrupo("Introduzca un nombre para grupo");
		}
		
	    // Convertir la lista de miembros del modelo en una lista de ContactoIndividual
	    List<ContactoIndividual> miembrosGrupo = new ArrayList<>();
	    for (int i = 0; i < listaMiembros.getSize(); i++) {
	        miembrosGrupo.add(listaMiembros.getElementAt(i));
	    }
	    
	    Grupo grupoNuevo;
	    
	    // Crear un nuevo grupo con los datos proporcionados
	    if(imagenGrupo != null) {
	    	grupoNuevo = new Grupo(nombreGrupo, imagenGrupo, usuarioActual, miembrosGrupo);
	    }else {
	    	 grupoNuevo = new Grupo(nombreGrupo, usuarioActual, miembrosGrupo);
	    }
	    
	    // Añadimos el grupo al usuarioActual
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
	
	/**
	 * Activa la suscripción premium para el usuario actual.
	 * 
	 * Aplica un descuento basado en el número de mensajes enviados 
	 * o en la fecha de registro, si corresponde. Finalmente, actualiza
	 * los datos del usuario en la persistencia.
	 * 
	 * @return El precio final de la suscripción premium, teniendo en cuenta el descuento aplicado.
	 */
	public double setPremium() {
		
		usuarioActual.setPremium(true);
		
		if(usuarioActual.getNumMensajesUltimoMes() >= 5) {		//CAMBIAR LUEGO POR NUM MAS GRANDE SI ESO			
			usuarioActual.setDescuento(supplierDescuento.get("DescuentoMensajes").get());
			adaptadorUsuario.modificarUsuario(usuarioActual);
		}else if(usuarioActual.getFechaRegistro().isAfter(LocalDate.of(2024, 12, 27))) { // Si se ha registrado despues del 27 de dicmiembre
			usuarioActual.setDescuento(supplierDescuento.get("DescuentoFechaRegistro").get());
			adaptadorUsuario.modificarUsuario(usuarioActual);

		}
				
		return usuarioActual.getPrecio();
	}
	
	/**
	 * Cancela la suscripción premium del usuario actual.
	 * 
	 * Desactiva el estado premium, elimina el descuento asociado 
	 * y actualiza los datos del usuario en la persistencia.
	 */
	public void cancelarPremium() {
		usuarioActual.setPremium(false);
		usuarioActual.setDescuento(null);
		adaptadorUsuario.modificarUsuario(usuarioActual);
	}
	
	/**
	 * Exporta los contactos del usuario actual a un archivo PDF en la ruta especificada.
	 * 
	 * @param ruta la ubicación donde se guardará el archivo PDF.
	 * @return Devuelve true si la exportación fue exitosa y false en caso de error.
	 */	
	public boolean exportarPDF(String ruta, String nombreContacto) {
	    try {
	        // Obtener el contacto seleccionado
	        Optional<ContactoIndividual> contactoOpt = usuarioActual.getContactoPorNombre(nombreContacto);
	        if (contactoOpt.isEmpty()) {
	            throw new IllegalArgumentException("El contacto indicado no existe.");
	        }
	        ContactoIndividual contactoSeleccionado = contactoOpt.get();

	        // Verificar si la ruta es un directorio
	        File rutaFile = new File(ruta);
	        if (rutaFile.isDirectory() || ruta.endsWith(File.separator)) {
	            ruta = new File(ruta, "Conversacion_" + contactoSeleccionado.getNombre().replaceAll("\\s+", "_") + ".pdf").getAbsolutePath();
	        }

	        // Crear el documento PDF
	        FileOutputStream archivo = new FileOutputStream(ruta);
	        Document documento = new Document();
	        PdfWriter.getInstance(documento, archivo);
	        documento.open();

	        // Agregar título
	        documento.add(new Paragraph("Conversación con: " + contactoSeleccionado.getNombre()));
	        documento.add(Chunk.NEWLINE);

	        // Recorrer los mensajes del contacto
	        List<Mensaje> mensajes = contactoSeleccionado.getMensajes();
	        mensajes.sort(Comparator.comparing(Mensaje::getFechaYhora)); // Ordenar por fecha y hora

	        for (Mensaje mensaje : mensajes) {
	            // Formatear el mensaje
	            String emisor = mensaje.getEmisor().getNombreCompleto();
	            String texto = mensaje.getTexto();
	            String fecha = mensaje.getFechaYhora().toString();

	            // Agregar el mensaje al documento
	            documento.add(new Paragraph(emisor + " (" + fecha + "):"));
	            documento.add(new Paragraph(texto));
	            documento.add(Chunk.NEWLINE);
	        }

	        documento.close();
	        return true;
	    } catch (DocumentException | IOException e) {
	        e.printStackTrace();
	        return false;
	    }
	}


	
	
	
	
	/**
	 * Obtiene un grupo del usuario actual por su nombre.
	 *
	 * @param nombreGrupo El nombre del grupo a buscar.
	 * @return El grupo correspondiente al nombre proporcionado, o un grupo vacío si no se encuentra.
	 */	
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
	
	public List<String> obtenerNombresContactos(){
		List<String> contactos = new ArrayList<String>();
		
		for(ContactoIndividual co : obtenerContactos()) {
			contactos.add(co.getNombre());
		}
		return contactos;
	}

	/**
	 * Obtiene la lista de contactos individuales asociados al usuario actual.
	 *
	 * @return Lista de ContactoIndividual del usuario actual.
	 */
	public List<ContactoIndividual> obtenerContactos() {
	    
	    if (usuarioActual != null) {
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
	
	/**
	 * Actualiza la imagen de perfil del usuario actual.
	 *
	 * @param pathImagen La ruta de la nueva imagen de perfil.
	 */
	public void setImagenPerfilUsuario(String pathImagen) {
	    usuarioActual.setPathImagen(pathImagen);
	}

	/**
	 * Verifica si el usuario actual tiene una suscripción premium.
	 *
	 * @return {@code true} si el usuario es premium, {@code false} en caso contrario.
	 */
	public boolean isUsuarioPremium() {
	    return usuarioActual.isPremium();
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
