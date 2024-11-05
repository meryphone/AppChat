package dominio;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

public abstract class Contacto {

	private String nombre;
	private List<Mensaje> mensajes;
	private int identificadorBD;
	
	public Contacto(String nombre) {
		this.nombre = nombre;
		 this.mensajes = new LinkedList();
	}

	protected String getNombre() {
		return nombre;
	}

	protected void setNombre(String nombre) {
		this.nombre = nombre;
	}

	protected List<Mensaje> getMensajes() {
		return mensajes;
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
