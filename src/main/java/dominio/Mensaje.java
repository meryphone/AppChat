package dominio;


import java.time.LocalDateTime;

public class Mensaje {
	private final String texto;
	private int identificadorBD;
	private LocalDateTime hora;
	
	//preguntar a María si le parece bien ponerle un receptor y un emisor
	//mirar si es mejor poner Contacto o Usuario
	private Contacto emisor; //persona que ha mandado el mensaje
	private Contacto receptor; //persona que recibe el mensaje
	
	public Mensaje(String texto, Contacto emisor, Contacto receptor) {
		this.texto = texto;
		this.emisor = emisor;
		this.receptor=receptor;
		hora=LocalDateTime.now();
	}

	public String getTexto() {
		return texto;
	}

	public int getIdentificadorBD() {
		return identificadorBD;
	}
	
	public String getEmisor() {
		return emisor.getNombre();
	}
	
	public String getReceptor() {
		return receptor.getNombre();
	}
	
	public LocalDateTime getHora() {
		return hora;
	}
	
	public void setIdentificadorBD(int identificadorBD) {
	this.identificadorBD = identificadorBD;
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
