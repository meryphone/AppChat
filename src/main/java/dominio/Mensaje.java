package dominio;

import java.time.LocalDateTime;

public class Mensaje {
	private final String texto;
	private int codigo;
	private final LocalDateTime fecha;
	
	//preguntar a María si le parece bien ponerle un receptor y un emisor
	//mirar si es mejor poner Contacto o Usuario
	private Usuario emisor; //persona que ha mandado el mensaje
	private Usuario receptor; //persona que recibe el mensaje
	
	public Mensaje(String texto, Usuario emisor, Usuario receptor) {
		this.texto = texto;
		this.emisor = emisor;
		this.receptor=receptor;
		fecha=LocalDateTime.now();
	}

	public String getTexto() {
		return texto;
	}

	public int getCodigo() {
		return codigo;
	}
	
	public Usuario getEmisor() {
		return emisor;
	}
	
	public Usuario getReceptor() {
		return receptor;
	}
	
	public LocalDateTime getFecha() {
		return fecha;
	}
	
	public void setCodigo(int codigo) {
	this.codigo = codigo;
	}

	/*public void setEmisor(Contacto emisor) {
		this.emisor = emisor;
	}
	
	public void setReceptor(Contacto receptor){
		this.receptor = receptor;
	}
	
	/*public void setTexto(String texto) {
		this.texto = texto;
	}*/
}
