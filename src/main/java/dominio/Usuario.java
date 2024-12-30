package dominio;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import javax.swing.ImageIcon;

public class Usuario {

    private static final double PRECIO_PREMIUM = 15.20;
    public static final String IMAGEN_POR_DEFECTO = "/resources/nueva_cuenta(1).png";

    private int codigo;
    private String nombreCompleto;
    private String movil;
    private String contrasena;
    private String email;
    private Optional<Date> fechaNacimiento;
    private String pathImagen;
    private boolean premium = false;
    private Optional<String> mensajeSaludo;
    private List<ContactoIndividual> listaContactos;
    private List<Grupo> grupos;
    private Optional<Descuento> descuento;
    private LocalDate fechaRegistro;

    /**
     * Constructor de un usuario.
     *
     * @param nombreCompleto Nombre completo del usuario.
     * @param movil          Teléfono del usuario.
     * @param contrasena      Contraseña del usuario.
     * @param email          Email del usuario.
     */
    public Usuario(String nombreCompleto, String movil, String contrasena, String email) {
        this.codigo = 0; // Codigo inicial, no persistido
        this.nombreCompleto = nombreCompleto;
        this.movil = movil;
        this.contrasena = contrasena;
        this.email = email;
        this.fechaNacimiento = Optional.empty();
        this.mensajeSaludo = Optional.empty();
        this.pathImagen = IMAGEN_POR_DEFECTO;
        this.listaContactos = new ArrayList<>();
        this.grupos = new ArrayList<>();
        this.fechaRegistro = LocalDate.now();
        this.descuento = Optional.empty();
    }

    /**
     * Constructor por defecto.
     */
    public Usuario() {
        this.nombreCompleto = "";
        this.movil = "";
        this.contrasena = "";
        this.email = "";
        this.fechaNacimiento = Optional.empty();
        this.pathImagen = IMAGEN_POR_DEFECTO;
        this.listaContactos = new ArrayList<>();
        this.grupos = new ArrayList<>();
        this.fechaRegistro = LocalDate.now();
        this.descuento = Optional.empty();
    }

    /**
     * Busca un contacto por teléfono.
     *
     * @param tlf Teléfono del contacto.
     * @return Optional con el contacto encontrado o vacío si no se encuentra.
     */
    public Optional<ContactoIndividual> getContactoPorTelefono(String tlf) {
        return listaContactos.stream()
                .filter(contacto -> tlf.equals(contacto.getTelefono()))
                .findFirst();
    }

    /**
     * Obtiene el número de mensajes enviados durante el último mes.
     *
     * @return Número de mensajes enviados.
     */
    public int getNumMensajesUltimoMes() {
        LocalDate fechaActual = LocalDate.now();
        LocalDate primerDiaUltimoMes = fechaActual.minusMonths(1).withDayOfMonth(1);
        LocalDate ultimoDiaUltimoMes = primerDiaUltimoMes.withDayOfMonth(primerDiaUltimoMes.lengthOfMonth());

        return (int) listaContactos.stream()
                .flatMap(contacto -> contacto.getMensajes().stream())
                .filter(mensaje -> mensaje.getTipo() == 0)
                .filter(mensaje -> {
                    LocalDate fechaMensaje = mensaje.getFechaYhora().toLocalDate();
                    return !fechaMensaje.isBefore(primerDiaUltimoMes) && !fechaMensaje.isAfter(ultimoDiaUltimoMes);
                })
                .count();
    }

    /**
     * Obtiene un grupo por su nombre.
     *
     * @param nombre Nombre del grupo.
     * @return El grupo encontrado o un nuevo grupo si no existe.
     */
    public Grupo getGrupoPorNombre(String nombre) {
        return grupos.stream()
                .filter(grupo -> grupo.getNombre().equals(nombre))
                .findFirst()
                .orElse(new Grupo());
    }

    /**
     * Busca un contacto por su nombre.
     *
     * @param nombreContacto Nombre del contacto.
     * @return Optional con el contacto encontrado o vacío si no se encuentra.
     */
    public Optional<ContactoIndividual> getContactoPorNombre(String nombreContacto) {
        return listaContactos.stream()
                .filter(contacto -> contacto.getNombre().equals(nombreContacto))
                .findFirst();
    }

    /**
     * Obtiene los nombres de los grupos del usuario.
     *
     * @return Lista de nombres de los grupos.
     */
    public List<String> getNombresGrupos() {
        return grupos.stream()
                .map(Grupo::getNombre)
                .collect(Collectors.toList());
    }

    /**
     * Obtiene los grupos que tienen al contacto especificado como miembro.
     *
     * @param contacto Contacto a buscar en los grupos.
     * @return Conjunto de grupos que contienen al contacto.
     */
    public Set<Grupo> getGruposConContacto(ContactoIndividual contacto) {
        return grupos.stream()
                .filter(grupo -> grupo.poseeMiembro(contacto.getUsuario()))
                .collect(Collectors.toSet());
    }

    /**
     * Calcula el precio de la suscripción premium considerando el descuento si existe.
     *
     * @return Precio final.
     */
    public double getPrecio() {
        return descuento.map(d -> d.getDescuento(PRECIO_PREMIUM)).orElse(PRECIO_PREMIUM);
    }

    /**
     * Devuelve un icono desde el path de la imagen.
     *
     * @return Icono de la imagen.
     */
    public ImageIcon getIconoDesdePath() {
        if (pathImagen == null || pathImagen.isEmpty()) {
            throw new IllegalArgumentException("El path de la imagen no está definido.");
        }
        File archivoImagen = new File(pathImagen);
        if (!archivoImagen.exists()) {
            throw new IllegalArgumentException("El archivo de imagen no existe: " + pathImagen);
        }
        return new ImageIcon(pathImagen);
    }

    // Getters y setters

    public List<ContactoIndividual> getListaContactos() {
        return listaContactos;
    }

    public void setListaContactos(List<ContactoIndividual> listaContactos) {
        this.listaContactos = listaContactos;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public String getMovil() {
        return movil;
    }

    public void setMovil(String movil) {
        this.movil = movil;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Optional<Date> getFechaNacimiento() {
        return fechaNacimiento;
    }

    public void setFechaNacimiento(Date fechaNacimiento) {
        this.fechaNacimiento = Optional.of(fechaNacimiento);
    }

    public String getPathImagen() {
        return pathImagen;
    }

    public void setPathImagen(String pathImagen) {
        this.pathImagen = pathImagen;
    }

    public boolean isPremium() {
        return premium;
    }

    public void setPremium(boolean premium) {
        this.premium = premium;
    }

    public Optional<String> getMensajeSaludo() {
        return mensajeSaludo;
    }

    public void setMensajeSaludo(String mensajeSaludo) {
        this.mensajeSaludo = Optional.of(mensajeSaludo);
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public List<Grupo> getGrupos() {
        return grupos;
    }

    public void setGrupos(List<Grupo> grupos) {
        this.grupos = grupos;
    }

    public void addGrupo(Grupo grupo) {
        grupos.add(grupo);
    }

    public Optional<Descuento> getDescuento() {
        return descuento;
    }

    public void setDescuento(Descuento descuento) {
        this.descuento = Optional.ofNullable(descuento);
    }

    public LocalDate getFechaRegistro() {
        return fechaRegistro;
    }

    public void setFechaRegistro(LocalDate fechaRegistro) {
        this.fechaRegistro = fechaRegistro;
    }
}
