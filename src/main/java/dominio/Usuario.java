package dominio;

import java.net.URL;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import javax.swing.ImageIcon;

public class Usuario {

	public static final ImageIcon IMAGEN_POR_DEFECTO;
	
	private int identificadorBD;
	private String nombreCompleto;
	private String movil;
	private String contrasena;
	private String email;
	private Optional<Date> fechaNacimiento;
	private ImageIcon imagenPerfil;
	private boolean premium = false;
	private Optional<String> mensajeSaludo;
	private List<ContactoIndividual> listaContactos;

	static {
		ImageIcon imagenPorDefecto;
		try {
			URL url = Usuario.class.getResource("/resources/nueva_cuenta(1).png");
			if (url != null) {
				imagenPorDefecto = new ImageIcon(url);
			} else {
				System.err.println("No se encontró la imagen predeterminada. Usando icono vacío.");
				imagenPorDefecto = new ImageIcon(); // Icono vacío si no se encuentra la imagen
			}
		} catch (Exception e) {
			System.err.println("Error al cargar la imagen predeterminada. Usando icono vacío.");
			imagenPorDefecto = new ImageIcon(); // Icono vacío en caso de error
		}
		
		IMAGEN_POR_DEFECTO = imagenPorDefecto;
	}

	public Usuario(String nombreCompleto, String movil, String contrasena, String email) {

		this.nombreCompleto = nombreCompleto;
		this.movil = movil;
		this.contrasena = contrasena;
		this.email = email;
		fechaNacimiento = Optional.empty();
		mensajeSaludo = Optional.empty();
		this.imagenPerfil = IMAGEN_POR_DEFECTO;
		this.listaContactos = new LinkedList<ContactoIndividual>();

	}
	
	/**
	 * Si existe el contacto cuyo teléfono es el pasado como parámetro, se devuelve un Optional que contiene dicho contacto, sino se 
	 * devuelve un Optional vacio.
	 * @param tlf
	 * @return
	 */
	
	public Optional<ContactoIndividual> getContactoPorTelefono(String tlf) {
		for(ContactoIndividual contacto : listaContactos) {
			if(contacto.getTelefono().equals(tlf)) return Optional.of(contacto);
		}
		
		return Optional.empty();
	}
	
	/**
	 * Agrega un contacto pasado como parametro la lista de contactos del usuario.
	 * @param nuevoContacto
	 */
	
	public void anadirContacto(ContactoIndividual nuevoContacto) {
		listaContactos.add(nuevoContacto);
	}
	
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

	public ImageIcon getImagenPerfil() {
		return imagenPerfil;
	}

	public void setImagenPerfil(ImageIcon imagenPerfil) {
		this.imagenPerfil = imagenPerfil;
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

}
