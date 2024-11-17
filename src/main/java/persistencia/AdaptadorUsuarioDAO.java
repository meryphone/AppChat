package persistencia;
// COMENTAR
import java.sql.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.StringTokenizer;

import beans.Entidad;
import beans.Propiedad;
import dominio.ContactoIndividual;
import dominio.Usuario;
import excepciones.ExcepcionDAO;
import persistencia.interfaces.IAdaptadorUsuarioDAO;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorUsuarioDAO implements IAdaptadorUsuarioDAO {

	public static ServicioPersistencia servicioPersistencia;
	private static AdaptadorUsuarioDAO adaptadorUsuario;

	// Patron Singletons
	public static AdaptadorUsuarioDAO getInstance() {
		if (adaptadorUsuario == null)
			adaptadorUsuario = new AdaptadorUsuarioDAO();
		return adaptadorUsuario;
	}

	private AdaptadorUsuarioDAO() {
		servicioPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}

	@Override
	public void registrarUsuario(Usuario nuevoUsuario) throws ExcepcionDAO {

		Entidad eUsuario = null;
		 boolean noRegistrar = true;

	  		try {
	  			eUsuario = servicioPersistencia.recuperarEntidad(nuevoUsuario.getCodigo());
	  		} catch (NullPointerException e) {
	  			noRegistrar = false;
	  			e.printStackTrace();
	  		}
	  		
	  		if (noRegistrar) throw new ExcepcionDAO("Entidad ya registrada");


		// registrar primero los atributos que son objetos
		// registrar lista de contactos
		AdaptadorContactoDAO adaptadorContacto = TDSFactoriaDAO.getInstance().getContactoDAO();
		
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
						new Propiedad("fechaNacimiento",nuevoUsuario.getFechaNacimiento().isEmpty() ? null : String.valueOf(nuevoUsuario.getFechaNacimiento().get())),
						new Propiedad("imagenPerfil", nuevoUsuario.getPathImagen().equals(Usuario.IMAGEN_POR_DEFECTO) ? Usuario.IMAGEN_POR_DEFECTO : nuevoUsuario.getPathImagen()),
						new Propiedad("premium", String.valueOf(nuevoUsuario.isPremium())),
						new Propiedad("mensajeSaludo", nuevoUsuario.getMensajeSaludo().isEmpty() ? null : nuevoUsuario.getMensajeSaludo().get()),
						new Propiedad("listaContacto", getCodigosContactos(nuevoUsuario.getListaContactos())))));
		// registrar entidad venta
		eUsuario = servicioPersistencia.registrarEntidad(eUsuario);
		// asignar identificador unico
		// Se aprovecha el que genera el servicio de persistencia
		nuevoUsuario.setCodigo(eUsuario.getId());
		
		// Guardamos en el Pool
	    PoolDAO.getInstance().addObjeto(nuevoUsuario.getCodigo(), nuevoUsuario);

	}
	
	/**
	 * Itera sobre las propiedades de la entidad (desactualizada) para actualizar cada 
	 * una con los valores del usuario pasado como parametro.
	 * @param usuarioModificar
	 */

	@Override
	public void modificarUsuario(Usuario usuarioModificar) {
		// Recuperar la entidad del usuario desde el servicio de persistencia
		Entidad eUsuario = servicioPersistencia.recuperarEntidad(usuarioModificar.getCodigo());
		
		for (Propiedad prop : eUsuario.getPropiedades()) {

			if (prop.getNombre().equals("nombreCompleto")) {
				prop.setValor(usuarioModificar.getNombreCompleto());
			} else if (prop.getNombre().equals("movil")) {
				prop.setValor(usuarioModificar.getMovil());
			} else if (prop.getNombre().equals("contrasena")) {
				prop.setValor(usuarioModificar.getContrasena());
			} else if (prop.getNombre().equals("email")) {
				prop.setValor(usuarioModificar.getEmail());
			} else if (prop.getNombre().equals("fechaNacimiento")) {
				prop.setValor(usuarioModificar.getFechaNacimiento().isEmpty() ? null
						: String.valueOf(usuarioModificar.getFechaNacimiento().get()));
			} else if (prop.getNombre().equals("imagenPerfil")) {
				prop.setValor(
						usuarioModificar.getPathImagen().equals(Usuario.IMAGEN_POR_DEFECTO) ? Usuario.IMAGEN_POR_DEFECTO
								: usuarioModificar.getPathImagen());
			} else if (prop.getNombre().equals("premium")) {
				prop.setValor(String.valueOf(usuarioModificar.isPremium()));
			} else if (prop.getNombre().equals("mensajeSaludo")) {
				prop.setValor(usuarioModificar.getMensajeSaludo().isEmpty() ? null
						: usuarioModificar.getMensajeSaludo().get());
			} else if (prop.getNombre().equals("listaContacto")) {
				prop.setValor(getCodigosContactos(usuarioModificar.getListaContactos()));
			}

			// Modificar la propiedad en la base de datos
			servicioPersistencia.modificarPropiedad(prop);
		}
	}

	@Override
	public Usuario recuperarUsuario(int codigo) throws ExcepcionDAO {
		
		// Si la entidad esta en el pool la devuelve directamente
		if (PoolDAO.getInstance().contiene(codigo)) return (Usuario) PoolDAO.getInstance().getObjeto(codigo);
		
		// Recuperar la entidad del usuario desde el servicio de persistencia
		Entidad eUsuario = servicioPersistencia.recuperarEntidad(codigo);
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

		// Crear el objeto Usuario
		Usuario usuario = new Usuario();

		// Recuperar y asignar cada propiedad del usuario
		usuario.setCodigo(codigo);
		usuario.setNombreCompleto(servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "nombreCompleto"));
		usuario.setMovil(servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "movil"));
		usuario.setContrasena(servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "contrasena"));
		usuario.setEmail(servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "email"));

		// Recuperar y asignar la fecha de nacimiento
		Optional.ofNullable(servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "fechaNacimiento"))
				.filter(fechaNacimientoStr -> !fechaNacimientoStr.isEmpty()).ifPresent(fechaNacimientoStr -> {
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
				.filter(mensajeSaludo -> !mensajeSaludo.isEmpty()).ifPresent(usuario::setMensajeSaludo);

		// Recuperar y asignar la lista de contactos
		String listaContacto = servicioPersistencia.recuperarPropiedadEntidad(eUsuario, "listaContacto");
		if (listaContacto != null && !listaContacto.isEmpty()) {
			List<ContactoIndividual> contactos = getListaContactosDesdeCodigos(listaContacto);
			usuario.setListaContactos(contactos);
		}

		return usuario;
	}

	@Override
	public List<Usuario> recuperarUsuarios() throws ExcepcionDAO {

		List<Usuario> usuarios = new LinkedList<Usuario>();
		List<Entidad> eUsuarios = servicioPersistencia.recuperarEntidades("Usuario");

		for (Entidad eUsuario : eUsuarios) {
			usuarios.add(recuperarUsuario(eUsuario.getId()));
		}
		return usuarios;
	}

	// -----------Funciones auxiliares-----------------------

	String getCodigosContactos(List<ContactoIndividual> listaContactos) {

		String codigosContacto = "";

		for (ContactoIndividual contacto : listaContactos) {
			codigosContacto += contacto.getCodigo();
		}

		return codigosContacto.trim();
	}

	List<ContactoIndividual> getListaContactosDesdeCodigos(String codigos) throws ExcepcionDAO {
		List<ContactoIndividual> listaContactos = new LinkedList<ContactoIndividual>();

		StringTokenizer strTok = new StringTokenizer(codigos, " ");
		AdaptadorContactoDAO adaptadorContacto = TDSFactoriaDAO.getInstance().getContactoDAO();
		while (strTok.hasMoreTokens()) {
			listaContactos.add(adaptadorContacto.recuperarContacto(Integer.parseInt(strTok.nextToken())));
		}

		return listaContactos;

	}

}
