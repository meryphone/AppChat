package persistencia;
//COMENTAR
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import beans.Entidad;
import beans.Propiedad;
import dominio.Grupo;
import excepciones.ExcepcionDAO;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorGrupoDAO {
	
	public static AdaptadorGrupoDAO adaptadorGrupo;
	private ServicioPersistencia servicioPersistencia;

	public static AdaptadorGrupoDAO getInstance() {
		if (adaptadorGrupo == null) {
			adaptadorGrupo = new AdaptadorGrupoDAO();
		}
		return adaptadorGrupo;
	}

	private AdaptadorGrupoDAO() {
		servicioPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	/**
	 * Metodo para registrar un grupo en la base de datos.
	 * @param nuevoGrupo
	 * @throws ExcepcionDAO
	 */
	
	public void registrarGrupo(Grupo nuevoGrupo) throws ExcepcionDAO {
		Entidad eGrupo = new Entidad();		
		boolean noRegistrar = true;

		try {
			eGrupo = servicioPersistencia.recuperarEntidad(nuevoGrupo.getCodigo());
		} catch (NullPointerException e) {
			noRegistrar = false;
			e.printStackTrace();
		}
		
		if (noRegistrar) throw new ExcepcionDAO("Entidad ya registrada");
		
		// Obtenemos las instancias de los adaptadores de contacto y usuario para 
		// poder utilizar las funciones auxiliares definidas en estos
		AdaptadorContactoDAO adaptadorContactoDAO = TDSFactoriaDAO.getInstance().getContactoDAO();
		AdaptadorUsuarioDAO adaptadorUsuarioDAO = TDSFactoriaDAO.getInstance().getUsuarioDAO();	
		
		// Establecemos propiedades de la entidad grupo		
		eGrupo.setNombre("Grupo");
		eGrupo.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", nuevoGrupo.getNombre()),
				new Propiedad("mensajes", adaptadorContactoDAO.obtenerCodigosMensajes(nuevoGrupo.getMensajes())),
				new Propiedad("miembros", adaptadorUsuarioDAO.getCodigosContactos(nuevoGrupo.getMiembros())),
				new Propiedad("propietario", String.valueOf(nuevoGrupo.getPropietario().getCodigo())), 
				new Propiedad("imagen", nuevoGrupo.getImagen()))));
		
		 // Registrar la entidad en el servicio de persistencia
	    eGrupo = servicioPersistencia.registrarEntidad(eGrupo);
	    
	    // Asignar el identificador único generado por el servicio de persistencia al contacto
	    nuevoGrupo.setCodigo(eGrupo.getId());
	    
	    // Guardamos en el Pool
	    PoolDAO.getInstance().addObjeto(nuevoGrupo.getCodigo(), nuevoGrupo);
				
	}
	
	/**
	 * Metodo para recuperar de la base de datos al grupo cuyo codigo se pasa como parámetro
	 * @param codigo
	 * @return 
	 * @throws ExcepcionDAO
	 */
	
	public Grupo recuperarGrupo(int codigo) throws  ExcepcionDAO {
		
		// Si la entidad esta en el pool la devuelve directamente
		if(PoolDAO.getInstance().contiene(codigo)) return (Grupo) PoolDAO.getInstance().getObjeto(codigo);
		
		Entidad eGrupo = servicioPersistencia.recuperarEntidad(codigo);
		
		Grupo grupo = new Grupo();
		
		// Obtenemos los adaptadores necesarios para poder aplicar los metodos definidos en estos
		AdaptadorUsuarioDAO adaptadorUsuario = TDSFactoriaDAO.getInstance().getUsuarioDAO();
		AdaptadorContactoDAO adaptadorContacto = TDSFactoriaDAO.getInstance().getContactoDAO();
		
		// Establecemos el resto de propiedades
		grupo.setCodigo(codigo);
		grupo.setNombre(servicioPersistencia.recuperarPropiedadEntidad(eGrupo, "nombre"));
		grupo.setImagen(servicioPersistencia.recuperarPropiedadEntidad(eGrupo, "imagen"));
		grupo.setPropietario(adaptadorUsuario.recuperarUsuario(Integer.parseInt(servicioPersistencia.recuperarPropiedadEntidad(eGrupo, "propietario"))));
		grupo.setMensajes(adaptadorContacto.obtenerMensajesDesdeCódigos(servicioPersistencia.recuperarPropiedadEntidad(eGrupo, "mensajes")));
		grupo.setMiembros(adaptadorUsuario.getListaContactosDesdeCodigos(servicioPersistencia.recuperarPropiedadEntidad(eGrupo, "miembros")));
		
		return grupo;
	}
	
	/**
	 * Metodo para actualizar las propiedades de un grupo guardado en la base de datos
	 * @param grupoModificar
	 * @throws ExcepcionDAO
	 */
	
	public void modificarGrupo(Grupo grupoModificar) throws ExcepcionDAO {
	    // Recuperar la entidad del grupo desde el servicio de persistencia
	    Entidad eGrupo = servicioPersistencia.recuperarEntidad(grupoModificar.getCodigo());
	    
		// Obtenemos los adaptadores necesarios para poder aplicar los metodos definidos en estos
		AdaptadorUsuarioDAO adaptadorUsuario = TDSFactoriaDAO.getInstance().getUsuarioDAO();
		AdaptadorContactoDAO adaptadorContacto = TDSFactoriaDAO.getInstance().getContactoDAO();

	    for (Propiedad prop : eGrupo.getPropiedades()) {
	        if (prop.getNombre().equals("imagen")) {
	            prop.setValor(grupoModificar.getImagen());
	        } else if (prop.getNombre().equals("miembros")) {
	            prop.setValor(adaptadorUsuario.getCodigosContactos(grupoModificar.getMiembros()));
	        } else if (prop.getNombre().equals("propietario")) {
	            prop.setValor(String.valueOf(grupoModificar.getPropietario().getCodigo()));
	        } else if (prop.getNombre().equals("nombre")) {
	            prop.setValor(grupoModificar.getNombre());
	        } else if (prop.getNombre().equals("mensajes")) {
	            prop.setValor(adaptadorContacto.obtenerCodigosMensajes(grupoModificar.getMensajes()));
	        }

	        // Modificar la propiedad en la base de datos
	        servicioPersistencia.modificarPropiedad(prop);
	    }
	}
	
	/**
	 * Permite recuperar todos los grupos de la capa de persistencia
	 * @return
	 * @throws ExcepcionDAO
	 */
	
	public List<Grupo> recuperarTodosGrupos() throws ExcepcionDAO {
		List<Grupo> grupos = new LinkedList<>();
		List<Entidad> eGrupos = servicioPersistencia.recuperarEntidades("Grupo");

		for (Entidad eGrupo : eGrupos) {
			grupos.add(recuperarGrupo(eGrupo.getId()));
		}
		
		return grupos;
	}
	
	
}
