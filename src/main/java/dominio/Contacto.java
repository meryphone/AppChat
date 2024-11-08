package dominio;

import java.util.LinkedList;
import java.util.List;

public class Contacto {

	private String nombre;
	private List<Mensaje> mensajes;
	private int identificadorBD;
	
	public Contacto(String nombre) {
		this.nombre = nombre;
		 this.mensajes = new LinkedList<>();
	}

	//Comprobar si hacen falta los setters
	//Si ninguna clase los utiliza hay que quitarlos
	protected String getNombre() {
		return nombre;
	}

	protected void setNombre(String nombre) {
		this.nombre = nombre;
	}

	protected List<Mensaje> getMensajes() {
		return new LinkedList<Mensaje>(mensajes);
	}

	protected void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	protected int getIdentificadorBD() {
		return identificadorBD;
	}

	protected void setIdentificadorBD(int identificadorBD) {
		this.identificadorBD = identificadorBD;
	}
}
