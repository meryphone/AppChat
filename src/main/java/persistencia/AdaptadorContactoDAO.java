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
import excepciones.ExcepcionDAO;
import excepciones.MensajesError;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

/**
 * Clase AdaptadorContactoDAO
 * 
 * Esta clase actúa como un puente entre los objetos de dominio ContactoIndividual
 * y su representación en la base de datos, utilizando un servicio de persistencia.
 * Implementa los métodos necesarios para registrar, recuperar y gestionar contactos
 * individuales, asegurando la consistencia entre el modelo de datos y el sistema persistente.
 */
public class AdaptadorContactoDAO {

    /**
     * Instancia única de la clase (implementación Singleton).
     */
    public static AdaptadorContactoDAO adaptadorContacto;

    /**
     * Servicio de persistencia utilizado para interactuar con la base de datos.
     */
    private ServicioPersistencia servicioPersistencia;

    /**
     * Obtiene la instancia única de la clase.
     * 
     * @return Instancia de AdaptadorContactoDAO.
     */
    public static AdaptadorContactoDAO getInstance() {
        if (adaptadorContacto == null) {
            adaptadorContacto = new AdaptadorContactoDAO();
        }
        return adaptadorContacto;
    }

    /**
     * Constructor privado para implementar el patrón Singleton.
     * Inicializa el servicio de persistencia.
     */
    private AdaptadorContactoDAO() {
        servicioPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
    }

    /**
     * Registra un nuevo contacto individual en la base de datos.
     * 
     * @param nuevoContacto ContactoIndividual a registrar.
     * @throws ExcepcionDAO Si el contacto ya está registrado.
     */
    public void registrarContacto(ContactoIndividual nuevoContacto) throws ExcepcionDAO {
        Entidad eContacto = null;
        boolean noRegistrar = true;

        try {
            eContacto = servicioPersistencia.recuperarEntidad(nuevoContacto.getCodigo());
        } catch (NullPointerException e) {
            noRegistrar = false;
            e.printStackTrace();
        }

        if (noRegistrar) throw new ExcepcionDAO("Entidad ya registrada");

        // Crear la entidad para el nuevo contacto
        eContacto = new Entidad();
        eContacto.setNombre("ContactoIndividual");

        // Crear la lista de propiedades, incluyendo heredadas y específicas de ContactoIndividual
        eContacto.setPropiedades(new ArrayList<Propiedad>(
                Arrays.asList(
                    new Propiedad("nombre", nuevoContacto.getNombre()),
                    new Propiedad("mensajes", obtenerCodigosMensajes(nuevoContacto.getMensajes())),
                    new Propiedad("telefono", nuevoContacto.getTelefono()),
                    new Propiedad("usuarioCreador", String.valueOf(nuevoContacto.getUsuarioCreador().getCodigo()))
                )
        ));

        // Registrar la entidad en el servicio de persistencia
        eContacto = servicioPersistencia.registrarEntidad(eContacto);

        // Asignar el identificador único generado por el servicio de persistencia al contacto
        nuevoContacto.setCodigo(eContacto.getId());

        // Guardamos en el Pool
        PoolDAO.getInstance().addObjeto(nuevoContacto.getCodigo(), nuevoContacto);
    }

    /**
     * Recupera un contacto individual desde la base de datos dado su código.
     * 
     * @param codigo Código del contacto individual.
     * @return ContactoIndividual correspondiente al código.
     * @throws ExcepcionDAO Si ocurre un error al recuperar el contacto.
     */
    public ContactoIndividual recuperarContacto(int codigo) throws ExcepcionDAO {
        // Si la entidad está en el pool, la devuelve directamente
        if (PoolDAO.getInstance().contiene(codigo)) return (ContactoIndividual) PoolDAO.getInstance().getObjeto(codigo);

        ContactoIndividual contacto = new ContactoIndividual();
        Entidad eContacto = servicioPersistencia.recuperarEntidad(codigo);
        AdaptadorUsuarioDAO adaptadorUsuario = TDSFactoriaDAO.getInstance().getUsuarioDAO();

        // Establecemos el resto de propiedades
        contacto.setCodigo(codigo);
        contacto.setMensajes(obtenerMensajesDesdeCódigos(servicioPersistencia.recuperarPropiedadEntidad(eContacto, "mensajes")));
        contacto.setTelefono(servicioPersistencia.recuperarPropiedadEntidad(eContacto, "telefono"));
        contacto.setUsuarioCreador(adaptadorUsuario.recuperarUsuario(Integer.parseInt(servicioPersistencia.recuperarPropiedadEntidad(eContacto, "usuarioCreador"))));

        return contacto;
    }

    /**
     * Recupera todos los contactos individuales registrados en la base de datos.
     * 
     * @return Lista de ContactoIndividual.
     * @throws ExcepcionDAO Si ocurre un error al recuperar los contactos.
     */
    public List<ContactoIndividual> recuperarTodosContactos() throws ExcepcionDAO {
        List<ContactoIndividual> contactos = new LinkedList<>();
        List<Entidad> eContacts = servicioPersistencia.recuperarEntidades("ContactoIndividual");

        for (Entidad eContact : eContacts) {
            contactos.add(recuperarContacto(eContact.getId()));
        }

        return contactos;
    }

    // --------Funciones auxiliares----------

    /**
     * Convierte una lista de mensajes en una cadena de códigos separados por espacios.
     * 
     * @param mensajes Lista de mensajes.
     * @return Cadena de códigos de los mensajes.
     */
    String obtenerCodigosMensajes(List<Mensaje> mensajes) {
        String idMensajes = "";

        for (Mensaje mensaje : mensajes) {
            idMensajes += mensaje.getCodigo() + " ";
        }

        return idMensajes.trim();
    }

    /**
     * Convierte una cadena de códigos de mensajes en una lista de mensajes.
     * 
     * @param codigosMensajes Cadena de códigos de mensajes.
     * @return Lista de mensajes.
     * @throws ExcepcionDAO Si ocurre un error al recuperar un mensaje.
     */
    List<Mensaje> obtenerMensajesDesdeCódigos(String codigosMensajes) throws ExcepcionDAO {
        List<Mensaje> listaMensajes = new LinkedList<>();
        StringTokenizer srtTok = new StringTokenizer(codigosMensajes, " ");
        AdaptadorMensajeDAO adaptadorMensaje = TDSFactoriaDAO.getInstance().getMensajeDAO();

        while (srtTok.hasMoreTokens()) {
            listaMensajes.add(adaptadorMensaje.recuperarMensaje(Integer.parseInt(srtTok.nextToken())));
        }

        return listaMensajes;
    }
}
