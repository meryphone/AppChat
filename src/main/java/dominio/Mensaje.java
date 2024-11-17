package dominio;


import java.time.LocalDateTime;

public class Mensaje implements Comparable<Mensaje> {
	
	private String texto;
	private int codigo;
	private  LocalDateTime fechaYhora;	
	private int emoticono;
	private int tipo; 
	private Usuario emisor; 
	private Contacto receptor;
	
	public Mensaje(String texto, Usuario emisor, Contacto receptor) {
		this.texto = texto;
		this.emisor = emisor;
		this.receptor=receptor;
		fechaYhora=LocalDateTime.now();
	}
	
	
	
	public int getTipo() {
		return tipo;
	}

	public void setTipo(int tipo) {
		this.tipo = tipo;
	}

	public Mensaje() {
	}
	
	public int getEmoticono() {
		return emoticono;
	}
	
	public void setEmoticono(int emoticono) {
		 this.emoticono = emoticono;
	}

	public String getTexto() {
		return texto;
	}
	
	public void setTexto(String texto) {
		this.texto = texto;
	}

	public int getCodigo() {
		return codigo;
	}
	
	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}
	
	public Usuario getEmisor() {
		return emisor;
	}
	
	public void setEmisor(Usuario emisor) {
		this.emisor = emisor;
	}
	
	public Contacto getReceptor() {
		return receptor;
	}
	
	public void setReceptor(Contacto receptor){
		this.receptor = receptor;
	}
	
	public LocalDateTime getFechaYhora() {
		return fechaYhora;
	}	
	
	public void setFechaYhora(LocalDateTime fechaYhora) {
		this.fechaYhora = fechaYhora;
	}
	
	@Override
	public int compareTo(Mensaje m) {
		return fechaYhora.compareTo(m.getFechaYhora());
	}

	
	
}
