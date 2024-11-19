package persistencia;
//COMENTAR
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import beans.Entidad;
import beans.Propiedad;
import dominio.Grupo;
import excepciones.ExcepcionDAO;
import excepciones.ExcepcionRegistroDuplicado;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorGrupoDAO {
	
	public static AdaptadorGrupoDAO adaptadorGrupo;
	private ServicioPersistencia servicioPersistencia;
	AdaptadorUsuarioDAO adaptadorUsuario = null;
	AdaptadorContactoDAO adaptadorContacto = null;

	public static AdaptadorGrupoDAO getInstance() {
		if (adaptadorGrupo == null) {
			adaptadorGrupo = new AdaptadorGrupoDAO();
		}
		return adaptadorGrupo;
	}

	private AdaptadorGrupoDAO() {
		servicioPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
		
		try {
			adaptadorContacto = TDSFactoriaDAO.getInstance().getContactoDAO();
			adaptadorUsuario = TDSFactoriaDAO.getInstance().getUsuarioDAO();
		} catch (ExcepcionDAO e) {
			e.printStackTrace();
		}
	
	}
	
	/**
	 * Metodo para registrar un grupo en la base de datos.
	 * @param nuevoGrupo
	 * @throws ExcepcionRegistroDuplicado
	 */
	
	public void registrarGrupo(Grupo nuevoGrupo) throws ExcepcionRegistroDuplicado {
		Entidad eGrupo = new Entidad();		
		boolean noRegistrar = false;

		try {
			eGrupo = servicioPersistencia.recuperarEntidad(nuevoGrupo.getCodigo());
		} catch (NullPointerException e) {
			noRegistrar = true;
			e.printStackTrace();
		}
		
		if (noRegistrar) throw new ExcepcionRegistroDuplicado("Entidad ya registrada");
		 
		// Obtenemos las instancias de los adaptadores de contacto y usuario para 
		// poder utilizar las funciones auxiliares definidas en estos
			
		
		// Establecemos propiedades de la entidad grupo		
		eGrupo.setNombre("Grupo");
		eGrupo.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("nombre", nuevoGrupo.getNombre()),
				new Propiedad("mensajes", PersistenciaUtils.obtenerCodigosMensajes(nuevoGrupo.getMensajes())),
				new Propiedad("miembros", PersistenciaUtils.getCodigosContactos(nuevoGrupo.getMiembros())),
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
	 * @throws ExcepcionRegistroDuplicado
	 */
	
	public Grupo recuperarGrupo(int codigo) {
		
		// Si la entidad esta en el pool la devuelve directamente
		if(PoolDAO.getInstance().contiene(codigo)) return (Grupo) PoolDAO.getInstance().getObjeto(codigo);
		
		Entidad eGrupo = servicioPersistencia.recuperarEntidad(codigo);
		
		Grupo grupo = new Grupo();
		

		
		// Establecemos el resto de propiedades
		grupo.setCodigo(codigo);
		grupo.setNombre(servicioPersistencia.recuperarPropiedadEntidad(eGrupo, "nombre"));
		grupo.setImagen(servicioPersistencia.recuperarPropiedadEntidad(eGrupo, "imagen"));
		grupo.setPropietario(adaptadorUsuario.recuperarUsuario(Integer.parseInt(servicioPersistencia.recuperarPropiedadEntidad(eGrupo, "propietario"))));
		grupo.setMensajes(PersistenciaUtils.obtenerMensajesDesdeCódigos(servicioPersistencia.recuperarPropiedadEntidad(eGrupo, "mensajes")));
		grupo.setMiembros(PersistenciaUtils.getListaContactosDesdeCodigos(servicioPersistencia.recuperarPropiedadEntidad(eGrupo, "miembros")));
		
		return grupo;
	}
	
	/**
	 * Metodo para actualizar las propiedades de un grupo guardado en la base de datos
	 * @param grupoModificar
	 * @throws ExcepcionRegistroDuplicado
	 */
	
	public void modificarGrupo(Grupo grupoModificar) {
	    // Recuperar la entidad del grupo desde el servicio de persistencia
	    Entidad eGrupo = servicioPersistencia.recuperarEntidad(grupoModificar.getCodigo());
	    
	    for (Propiedad prop : eGrupo.getPropiedades()) {
	        if (prop.getNombre().equals("imagen")) {
	            prop.setValor(grupoModificar.getImagen());
	        } else if (prop.getNombre().equals("miembros")) {
	            prop.setValor(PersistenciaUtils.getCodigosContactos(grupoModificar.getMiembros()));
	        } else if (prop.getNombre().equals("propietario")) {
	            prop.setValor(String.valueOf(grupoModificar.getPropietario().getCodigo()));
	        } else if (prop.getNombre().equals("nombre")) {
	            prop.setValor(grupoModificar.getNombre());
	        } else if (prop.getNombre().equals("mensajes")) {
	            prop.setValor(PersistenciaUtils.obtenerCodigosMensajes(grupoModificar.getMensajes()));
	        }

	        // Modificar la propiedad en la base de datos
	        servicioPersistencia.modificarPropiedad(prop);
	    }
	}
	
	/**
	 * Permite recuperar todos los grupos de la capa de persistencia
	 * @return
	 * @throws ExcepcionRegistroDuplicado
	 */
	
	public List<Grupo> recuperarTodosGrupos() {
		List<Grupo> grupos = new ArrayList<>();
		List<Entidad> eGrupos = servicioPersistencia.recuperarEntidades("Grupo");

		for (Entidad eGrupo : eGrupos) {
			grupos.add(recuperarGrupo(eGrupo.getId()));
		}
		
		return grupos;
	}
	
	
}
