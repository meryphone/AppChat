package persistencia;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import dominio.ContactoIndividual;
import dominio.Mensaje;
import excepciones.ExcepcionDAO;
import excepciones.ExcepcionRegistroDuplicado;

public class PersistenciaUtils {
	
	private static AdaptadorMensajeDAO adaptadorMensaje;  // Preguntar si esto es legal
	private static AdaptadorContactoDAO adaptadorContacto;
	
	static {
		try {
			adaptadorMensaje = TDSFactoriaDAO.getInstance().getMensajeDAO();
			adaptadorContacto = TDSFactoriaDAO.getInstance().getContactoDAO();
		} catch (ExcepcionDAO e) {
			e.printStackTrace();
		}
	}
	
	 /**
     * Convierte una cadena de códigos de mensajes en una lista de mensajes.
     * 
     * @param codigosMensajes Cadena de códigos de mensajes.
     * @return Lista de mensajes.
     * @throws ExcepcionDAO Si ocurre un error al recuperar un mensaje.
     */
	
	static List<Mensaje> obtenerMensajesDesdeCódigos(String codigosMensajes) {
	    List<Mensaje> listaMensajes = new ArrayList<>();
	    
	    // Validar si la cadena es nula o vacía
	    if (codigosMensajes == null || codigosMensajes.trim().isEmpty()) {
	        return listaMensajes; // Retornar lista vacía
	    }

	    StringTokenizer srtTok = new StringTokenizer(codigosMensajes, " ");

	    while (srtTok.hasMoreTokens()) {
	            int codigo = Integer.parseInt(srtTok.nextToken());
	            Mensaje mensaje = adaptadorMensaje.recuperarMensaje(codigo);
	            listaMensajes.add(mensaje);
	    }

	    return listaMensajes;
	}



    /**
     * Convierte una lista de mensajes en una cadena de códigos separados por espacios.
     * 
     * @param mensajes Lista de mensajes.
     * @return Cadena de códigos de los mensajes.
     */
    static String obtenerCodigosMensajes(List<Mensaje> mensajes) {
        String idMensajes = "";

        for (Mensaje mensaje : mensajes) {
            idMensajes += mensaje.getCodigo() + " ";
        }

        return idMensajes.trim();
    }
   	
   	/**
   	 * 
   	 * @param listaContactos
   	 * @return
   	 */
   
   
	 static String getCodigosContactos(List<ContactoIndividual> listaContactos) {

		String codigosContacto = "";

		for (ContactoIndividual contacto : listaContactos) {
			codigosContacto += contacto.getCodigo();
		}

		return codigosContacto.trim();
	}
	
	/**
	 * 
	 * @param codigos
	 * @return
	 * @throws ExcepcionRegistroDuplicado
	 */

	 static List<ContactoIndividual> getListaContactosDesdeCodigos(String codigos) {
		    List<ContactoIndividual> listaContactos = new ArrayList<>();

		    // Validar si la cadena es nula o vacía
		    if (codigos == null || codigos.trim().isEmpty()) {
		        return listaContactos; // Retornar lista vacía
		    }

		    StringTokenizer strTok = new StringTokenizer(codigos, " ");

		    while (strTok.hasMoreTokens()) {
		            int codigo = Integer.parseInt(strTok.nextToken());
		            ContactoIndividual contacto = adaptadorContacto.recuperarContacto(codigo);
		            listaContactos.add(contacto);		        
		    }

		    return listaContactos;
		}
   
   

}
