package dominio;
import java.util.ArrayList;
import java.util.List;

public abstract class Contacto {
	private String nombre;
	private List<Mensaje> mensajes;
	private int codigo;
	
	public Contacto(String nombre) {
		this.nombre = nombre;
		 this.mensajes = new ArrayList<Mensaje>();
	}


    public Contacto() {
        this.mensajes = new ArrayList<>();
    }
    
    
    public String getNombre() {
        return nombre;
    }

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


    public List<Mensaje> getMensajes() {			
        return new ArrayList<Mensaje>(mensajes);
    }

	public void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}

	public int getCodigo() {
		return codigo;
	}

	public void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
