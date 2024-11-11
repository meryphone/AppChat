package persistencia;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.List;
import dominio.Mensaje;
import beans.Entidad;
import beans.Propiedad;
import dominio.ContactoIndividual;
import dominio.Usuario;
import excepciones.ExcepcionDAO;
import excepciones.MensajesError;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorContactoDAO {

	public static AdaptadorContactoDAO adaptadorContacto;
	private ServicioPersistencia servicioPersistencia;

	public static AdaptadorContactoDAO getInstance() {
		if (adaptadorContacto == null) {
			adaptadorContacto = new AdaptadorContactoDAO();
		}
		return adaptadorContacto;
	}

	private AdaptadorContactoDAO() {
		servicioPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public void registrarContacto(ContactoIndividual nuevoContacto) throws ExcepcionDAO {
	    
	    Entidad eContacto = null;
	    boolean existeEntidad = false;
	    
	    try {
	        // Intentar recuperar la entidad del contacto a partir del código
	        eContacto = servicioPersistencia.recuperarEntidad(nuevoContacto.getCodigo());
	    } catch (NullPointerException e) {
	        existeEntidad = true; // La entidad no existe, se puede registrar
	    }
	    
	    // Si la entidad ya existe, lanzar excepción
	    if (existeEntidad) {
	        throw new ExcepcionDAO(MensajesError.ENTIDAD_YA_REGISTRADA.getMensaje());
	    }

	    // Crear la entidad para el nuevo contacto
	    eContacto = new Entidad();
	    eContacto.setNombre("ContactoIndividual");

	    // Crear la lista de propiedades, incluyendo heredadas y específicas de ContactoIndividual
	    eContacto.setPropiedades(new ArrayList<Propiedad>(
	            Arrays.asList(
	                // Propiedades heredadas de Contacto
	                new Propiedad("nombre", nuevoContacto.getNombre()),
	                new Propiedad("mensajes", obtenerCodigosMensajes(nuevoContacto.getMensajes())),
	                
	                // Propiedades específicas de ContactoIndividual
	                new Propiedad("telefono", nuevoContacto.getTelefono()),
	                new Propiedad("usuarioCreador", String.valueOf(nuevoContacto.getUsuarioCreador().getCodigo()))
	            )
	    ));

	    // Registrar la entidad en el servicio de persistencia
	    eContacto = servicioPersistencia.registrarEntidad(eContacto);
	    
	    // Asignar el identificador único generado por el servicio de persistencia al contacto
	    nuevoContacto.setCodigo(eContacto.getId());
	    
	    
	}

	
	
	public ContactoIndividual recuperarContacto(int codigo) throws ExcepcionDAO {
		
		ContactoIndividual contacto = new ContactoIndividual();
		Entidad eContacto = servicioPersistencia.recuperarEntidad(codigo);
		AdaptadorUsuarioDAO adaptadorUsuario = TDSFactoriaDAO.getInstance().getUsuarioDAO();
		
		
		// setear mas propiedades
		contacto.setCodigo(codigo);
		// NO SE contacto.setMensajes((servicioPersistencia.recuperarMensajes(eContacto)));
		contacto.setTelefono(servicioPersistencia.recuperarPropiedadEntidad(eContacto, "telefono"));
		contacto.setUsuarioCreador(adaptadorUsuario.recuperarUsuario(Integer.parseInt(servicioPersistencia.recuperarPropiedadEntidad(eContacto,"usuarioCreador"))));
		
		
		return contacto ;
		
	}
	
	
	
	// -------Funciones auxiliares----------
	
	private String obtenerCodigosMensajes(List<Mensaje> mensajes) {
		
		String idMensajes = "";
		
		for(Mensaje mensaje : mensajes) {
			idMensajes += mensaje.getCodigo() + " ";
		}
		
		return idMensajes.trim();
	}
	
	private List<Mensaje> obtenerMensajesDesdeCódigos(String codigosMensajes){
		
		List<Mensaje> listaMensajes = new LinkedList<Mensaje>(); 
		StringTokenizer srtTok = new StringTokenizer(codigosMensajes, " ");
		AdaptadorMensaje adaptadorMensaje = TDSFactoriaDAO.getInstance().getMensajeDAO();
		
		while(srtTok.hasMoreTokens()) {
			listaMensajes.add(adaptadorMensaje.recuperarMensaje(Integer.parseInt(srtTok.nextToken())));
		}
		
	}

	
	
	

}
