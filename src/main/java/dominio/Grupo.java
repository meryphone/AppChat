package dominio;

import java.util.LinkedList;
import java.util.List;

public class Grupo extends Contacto {
	
	private String imagen;
	private List<ContactoIndividual> miembros;
	private Usuario propietario; 

	public Grupo(String nombre, Usuario propietario) {
		super(nombre);
		this.propietario = propietario;
		this.miembros = new LinkedList<ContactoIndividual>();
	}
	
	public Grupo() {		
	}

	public String getImagen() {
		return imagen;
	}

	public List<ContactoIndividual> getMiembros() {
		return new LinkedList<ContactoIndividual>(miembros);
	}

	public Usuario getPropietario() {
		return propietario;
	}

	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public void setMiembros(List<ContactoIndividual> miembros) {
		this.miembros = miembros;
	}

	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}
	

	public void agregarContacto(ContactoIndividual contacto) {
        if (!miembros.contains(contacto)) {
            miembros.add(contacto);
        }
    }

    public void eliminarContacto(Contacto contacto) {
        miembros.remove(contacto);
    }
	

}
