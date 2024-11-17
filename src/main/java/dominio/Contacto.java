package dominio;
import java.util.LinkedList;
import java.util.List;

public abstract class Contacto {
	private String nombre;
	private List<Mensaje> mensajes;
	private int codigo;
	
	public Contacto(String nombre) {
		this.nombre = nombre;
		 this.mensajes = new LinkedList<Mensaje>();
	}

    /**
     * Contructor sin parametros para inicilizar un ContactoIndividual y aplicar métodos set.
     */
    public Contacto() {
        this.mensajes = new LinkedList<>();
    }
    
    // Métodos comunes a todas las subclases
    
    public String getNombre() {
        return nombre;
    }

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}


    public List<Mensaje> getMensajes() {			
        return new LinkedList<Mensaje>(mensajes);
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
