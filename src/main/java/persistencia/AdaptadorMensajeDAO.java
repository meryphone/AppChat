package persistencia;
// COMENTAR
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import beans.Entidad;
import beans.Propiedad;
import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;
import dominio.Mensaje;
import excepciones.ExcepcionDAO;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;


public class AdaptadorMensajeDAO {

	public static AdaptadorMensajeDAO adaptadorMensaje;
	private ServicioPersistencia servicioPersistencia;

	public static AdaptadorMensajeDAO getInstance() {
		if (adaptadorMensaje == null) {
			adaptadorMensaje = new AdaptadorMensajeDAO();
		}
		return adaptadorMensaje;
	}

	private AdaptadorMensajeDAO() {
		servicioPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public void registrarMensaje(Mensaje mensaje) throws ExcepcionDAO {
		 
	    Entidad eMensaje = null;
	    boolean noRegistrar = true;

		try {
			eMensaje = servicioPersistencia.recuperarEntidad(mensaje.getCodigo());
		} catch (NullPointerException e) {
			noRegistrar = false;
			e.printStackTrace();
		}
		
		if (noRegistrar) throw new ExcepcionDAO("Entidad ya registrada");
	    
	    eMensaje.setNombre("Mensaje");
	    
	    // Primero registramos entidades que son objetos
	    
	    TDSFactoriaDAO.getInstance().getUsuarioDAO().registrarUsuario(mensaje.getEmisor()); //NO SE SI QUITAR
	    registrarReceptorNoAgregado(mensaje.getReceptor());
	    
	    boolean grupo = false;
		if (mensaje.getReceptor() instanceof Grupo) {
			grupo = true;
		}

		eMensaje.setPropiedades(new ArrayList<Propiedad>(Arrays.asList(new Propiedad("texto", mensaje.getTexto()),
				new Propiedad("fechaYhora", mensaje.getFechaYhora().toString()),
				new Propiedad("emoticono", String.valueOf(mensaje.getEmoticono())),
				new Propiedad("receptor", String.valueOf(mensaje.getReceptor().getCodigo())),
				new Propiedad("toGrupo", String.valueOf(grupo)),
				new Propiedad("emisor", String.valueOf(mensaje.getEmisor().getCodigo())), 
				new Propiedad("tipo", String.valueOf(mensaje.getTipo())))));
		
		 // Registrar la entidad en el servicio de persistencia
	    eMensaje = servicioPersistencia.registrarEntidad(eMensaje);
	    
	    // Asignar el identificador único generado por el servicio de persistencia al mensaje
	    mensaje.setCodigo(eMensaje.getId());
	    
	 // Guardamos en el pool
	 PoolDAO.getInstance().addObjeto(mensaje.getCodigo(), mensaje);
	    		
	}
	
	public Mensaje recuperarMensaje(int codigo) throws ExcepcionDAO {
		
		// Si la entidad esta en el pool la devuelve directamente
		if (PoolDAO.getInstance().contiene(codigo)) return (Mensaje) PoolDAO.getInstance().getObjeto(codigo);
		
		Entidad eMensaje = servicioPersistencia.recuperarEntidad(codigo);
		
		// Crear el objeto Usuario
		 Mensaje mensaje = new Mensaje();
		
		 // Establecemos las propiedades al objeto mensaje
		 mensaje.setCodigo(codigo);
		 mensaje.setTexto(servicioPersistencia.recuperarPropiedadEntidad(eMensaje, "texto"));
		 mensaje.setEmoticono(Integer.parseInt(servicioPersistencia.recuperarPropiedadEntidad(eMensaje, "emoticono")));
		 mensaje.setFechaYhora(LocalDateTime.parse(servicioPersistencia.recuperarPropiedadEntidad(eMensaje, "fechaYhora")));
		 
		 // Recuperamos las propiedades que son objetos
		 AdaptadorUsuarioDAO adaptadorUsuario = TDSFactoriaDAO.getInstance().getUsuarioDAO();		 
		 mensaje.setEmisor(adaptadorUsuario.recuperarUsuario(Integer.parseInt(servicioPersistencia.recuperarPropiedadEntidad(eMensaje, "emisor"))));
		 
		 boolean toGrupo = Boolean.parseBoolean(servicioPersistencia.recuperarPropiedadEntidad(eMensaje, "toGrupo"));
		 
		 int codigoReceptor = Integer.parseInt(servicioPersistencia.recuperarPropiedadEntidad(eMensaje, "receptor"));
		 Contacto receptor;
		 
		 if(toGrupo) {
			 receptor = TDSFactoriaDAO.getInstance().getGrupoDAO().recuperarGrupo(codigoReceptor);
		}else {
			 receptor = TDSFactoriaDAO.getInstance().getContactoDAO().recuperarContacto(codigoReceptor);
		}
		
		 mensaje.setReceptor(receptor);
		
		 return mensaje;
		
	}
	
	public List<Mensaje> recuperarTodosMensajes() throws ExcepcionDAO {
		List<Mensaje> mensajes = new LinkedList<>();
		List<Entidad> eMensajes = servicioPersistencia.recuperarEntidades("Mensaje");

		for (Entidad eMensaje : eMensajes) {
			mensajes.add(recuperarMensaje(eMensaje.getId()));
		}
		return mensajes;
	}
	
	
	  //---------Funciones Auxiliares-------------
    
    /**
     * Si el receptor del mensaje no esta en la base de datos se registará, y si ya esta en la base de datos es porque
     * el receptor ya está agregado como contacto entonces devolverá una excepción.
     * @param contactoDesconocido
     * @throws ExcepcionDAO 
     */
    private void registrarReceptorNoAgregado(Contacto contactoDesconocido) throws ExcepcionDAO { // Para implementar la funcion de recibir desde un contacto no agregado.
    		
    	if(contactoDesconocido instanceof ContactoIndividual) {
    		AdaptadorContactoDAO.getInstance().registrarContacto( (ContactoIndividual) contactoDesconocido);
    	}
    	
    }
	
}
