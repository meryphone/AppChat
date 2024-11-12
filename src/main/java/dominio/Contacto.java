package dominio;

import java.util.ArrayList;
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

	/*protected void setNombre(String nombre) {
		this.nombre = nombre;
	}*/


    public List<Mensaje> getMensajes() {	// arraylist implementa list
        return new LinkedList<Mensaje>(mensajes);
    }

	/*protected void setMensajes(List<Mensaje> mensajes) {
		this.mensajes = mensajes;
	}*/

	protected int getCodigo() {
		return codigo;
	}

	protected void setCodigo(int codigo) {
		this.codigo = codigo;
	}

}
