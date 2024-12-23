package dominio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;



public class Usuario {

	public static final String IMAGEN_POR_DEFECTO = "/resources/nueva_cuenta(1).png"; // CREAR CLASE CON TODAS LAS CONSTANTES???
	
	private int codigo;
	private  String nombreCompleto;
	private String movil;
	private  String contrasena;
	private  String email;
	private Optional<Date> fechaNacimiento;
	private String pathImagen;
	private boolean premium = false;
	private Optional<String> mensajeSaludo;
	private List<ContactoIndividual> listaContactos;
	
	/**
	 * Constructor de un contacto.
	 * @param nombreCompleto
	 * @param movil
	 * @param contrasena
	 * @param email
	 */

	public Usuario(String nombreCompleto, String movil, String contrasena, String email) {
		
		this.codigo = 0; // Cuando se crea un usuario nuevo en el sistema y todavía no está en perisistencia codigo = 0
		this.nombreCompleto = nombreCompleto;
		this.movil = movil;
		this.contrasena = contrasena;
		this.email = email;
		fechaNacimiento = Optional.empty();
		mensajeSaludo = Optional.empty();
		this.pathImagen = IMAGEN_POR_DEFECTO;
		this.listaContactos = new ArrayList<ContactoIndividual>();

	}
	
	/**
	 * Constructor para inizializar sin parámetros.
	 */
	public Usuario() {
	    this.nombreCompleto = "";
	    this.movil = "";
	    this.contrasena = "";
	    this.email = "";
	    this.listaContactos = new ArrayList<>(); 
	}


	/**
	 * Si existe el contacto cuyo teléfono es el pasado como parámetro, se devuelve un Optional que contiene dicho contacto, sino se 
	 * devuelve un Optional vacio.
	 * @param tlf
	 * @return
	 */
	
	public Optional<ContactoIndividual> getContactoPorTelefono(String tlf) {
		
		if(listaContactos.isEmpty()) return Optional.empty();
		
		for(ContactoIndividual contacto : listaContactos) {
			if(tlf.equals(contacto.getTelefono())) return Optional.of(contacto);
		}
		
		return Optional.empty();
	}
	
	
	public List<ContactoIndividual> getListaContactos() {
		return  listaContactos;
	}

	public void setListaContactos(List<ContactoIndividual> listaContactos) {
		this.listaContactos = listaContactos;
	}	

	//Comprobar lo de los setters, si no son utilizados hay que quitarlos
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

}
