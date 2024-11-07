package persistencia;

import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import beans.Entidad;
import beans.Propiedad;
import dominio.ContactoIndividual;
import dominio.Usuario;
import dominio.excepciones.ExcepcionDAO;
import persistencia.interfaces.IAdaptadorUsuarioDAO;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;
import tds.driver.JPARMI.ServicioPersistenciaProxyJPARMI;

public class AdaptadorUsuarioDAO implements IAdaptadorUsuarioDAO{
	
	public static ServicioPersistencia servicioPersistencia;
	private static AdaptadorUsuarioDAO adaptadorUsuario;
	
	
	 // Patron Singletons
	public static AdaptadorUsuarioDAO getInstance() {
		if(adaptadorUsuario == null) adaptadorUsuario = new AdaptadorUsuarioDAO();					
		return adaptadorUsuario;
	}
	
	private AdaptadorUsuarioDAO() {
		servicioPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void registrarUsuario(Usuario nuevoUsuario) throws ExcepcionDAO{
		
		Entidad eUsuario = null;
	
		try {
			eUsuario = servicioPersistencia.recuperarEntidad(nuevoUsuario.getCodigo());
		} catch (NullPointerException e) {}
		if (eUsuario != null) throw new ExcepcionDAO("El usuario ya existía en la base de datos"); // A lo mejor cambiar por return;

		// registrar primero los atributos que son objetos
		// registrar lista de contactos
		
		AdaptadorContactoDAO adaptadorContacto= TDSFactoriaDAO.getInstance().getContactoDAO(); // Así o adaptadorContacto.getInstance;
																									     
		for (ContactoIndividual contacto : nuevoUsuario.getListaContactos())
			adaptadorContacto.registrarContacto(contacto);
		
		// Crear entidad usuario
		eUsuario = new Entidad();

		eUsuario.setNombre("Usuario");
		eUsuario.setPropiedades(new ArrayList<Propiedad>(
				Arrays.asList(new Propiedad("nombreCompleto", nuevoUsuario.getNombreCompleto()),
						new Propiedad("movil", nuevoUsuario.getMovil()),
						new Propiedad("contrasena", nuevoUsuario.getContrasena()),
						new Propiedad("email", nuevoUsuario.getEmail()),
						new Propiedad("fechaNacimiento", nuevoUsuario.getFechaNacimiento().isEmpty() ?  null : String.valueOf(nuevoUsuario.getFechaNacimiento().get())),
						new Propiedad("imagenPerfil", nuevoUsuario.getPathImagen().equals(Usuario.IMAGEN_POR_DEFECTO) ? Usuario.IMAGEN_POR_DEFECTO : nuevoUsuario.getPathImagen()),
						new Propiedad("premium", String.valueOf(nuevoUsuario.isPremium())),
						new Propiedad("mensajeSaludo", nuevoUsuario.getMensajeSaludo().isEmpty() ? null : nuevoUsuario.getMensajeSaludo().get()),
						new Propiedad ("listaContacto", nuevoUsuario.getCodigosContactos(nuevoUsuario.getListaContactos()))				
				)));
		// registrar entidad venta
		eUsuario = servicioPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		nuevoUsuario.setCodigo(eUsuario.getId());
		
	}

	@Override
	public void modificarUsuario(Usuario usuarioModificar) {
	    // Recuperar la entidad del usuario desde el servicio de persistencia
	    Entidad eUsuario = servicioPersistencia.recuperarEntidad(usuarioModificar.getCodigo());
	    
	    // Iterar sobre las propiedades de la entidad para actualizar cada una.
	    for (Propiedad prop : eUsuario.getPropiedades()) {
	        
	        if (prop.getNombre().equals("nombreCompleto")) {
	            prop.setValor(usuarioModificar.getNombreCompleto());
	        }
	        else if (prop.getNombre().equals("movil")) {
	            prop.setValor(usuarioModificar.getMovil());
	        }
	        else if (prop.getNombre().equals("contrasena")) {
	            prop.setValor(usuarioModificar.getContrasena());
	        }
	        else if (prop.getNombre().equals("email")) {
	            prop.setValor(usuarioModificar.getEmail());
	        }
	        else if (prop.getNombre().equals("fechaNacimiento")) {
	            prop.setValor(usuarioModificar.getFechaNacimiento().isEmpty() ? null : String.valueOf(usuarioModificar.getFechaNacimiento().get()));
	        }
	        else if (prop.getNombre().equals("imagenPerfil")) {
	            prop.setValor(usuarioModificar.getPathImagen().equals(Usuario.IMAGEN_POR_DEFECTO) ? Usuario.IMAGEN_POR_DEFECTO : usuarioModificar.getPathImagen());
	        }
	        else if (prop.getNombre().equals("premium")) {
	            prop.setValor(String.valueOf(usuarioModificar.isPremium()));
	        }
	        else if (prop.getNombre().equals("mensajeSaludo")) {
	            prop.setValor(usuarioModificar.getMensajeSaludo().isEmpty() ? null : usuarioModificar.getMensajeSaludo().get());
	        }
	        else if (prop.getNombre().equals("listaContacto")) {
	            prop.setValor(usuarioModificar.getCodigosContactos(usuarioModificar.getListaContactos()));
	        }
	        
	        // Modificar la propiedad en la base de datos
	        servicioPersistencia.modificarPropiedad(prop);
	    }
	}


	@SuppressWarnings("null")
	@Override
	public Usuario recuperarUsuario(int codigo) {
	    // Recuperar la entidad del usuario desde el servicio de persistencia
	    Entidad eUsuario = servicioPersistencia.recuperarEntidad(codigo);
	    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	    // Crear el objeto Usuario
	    Usuario usuario = null;

	    // Recuperar y asignar cada propiedad del usuario
	    usuario.setCodigo(codigo);
	    usuario.setNombreCompleto(servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "nombreCompleto"));
	    usuario.setMovil(servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "movil"));
	    usuario.setContrasena(servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "contrasena"));
	    usuario.setEmail(servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "email"));
	    
	    // Recuperar y asignar la fecha de nacimiento
	    Optional.ofNullable(servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento"))
	        .filter(fechaNacimientoStr -> !fechaNacimientoStr.isEmpty())
	        .ifPresent(fechaNacimientoStr -> {
	            try {
	                Date fechaNacimiento = (Date) dateFormat.parse(fechaNacimientoStr);
	                usuario.setFechaNacimiento(fechaNacimiento);
	            } catch (ParseException e) {
	                e.printStackTrace();
	            }
	        });

	    // Recuperar y asignar la imagen de perfil
	    usuario.setPathImagen(servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "imagenPerfil"));
	    
	    // Recuperar y asignar el estado de la suscripción Premium
	    usuario.setPremium(Boolean.parseBoolean(servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "premium")));

	    // Recuperar y asignar el mensaje de saludo
	    Optional.ofNullable(servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "mensajeSaludo"))
	        .filter(mensajeSaludo -> !mensajeSaludo.isEmpty())
	        .ifPresent(usuario::setMensajeSaludo);

	    // Recuperar y asignar la lista de contactos
	    String listaContacto = servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "listaContacto");
	    if (listaContacto != null && !listaContacto.isEmpty()) {
	        List<ContactoIndividual> contactos = usuario.getContactosDesdeCodigos(listaContacto);
	        usuario.setListaContactos(contactos);
	    }

	    return usuario;
	}


	@Override
	public List<Usuario> recuperarUsuarios() {
		// TODO Auto-generated method stub
		return null;
	}

}
