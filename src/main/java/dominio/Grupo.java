package dominio;

import java.util.LinkedList;
import java.util.List;

import umu.tds.apps.AppChat.User;

public class Grupo extends Contacto {
	
	public static final String IMAGEN_POR_DEFECTO_GRUPO = "/resources/personas(2)(1)(1).png";
	
	private String imagen;
	private List<ContactoIndividual> miembros;
	private Usuario propietario; 

	public Grupo(String nombre, Usuario propietario, List<ContactoIndividual> listaMiembros) {
		super(nombre);
		this.propietario = propietario;
		this.miembros = listaMiembros;
		imagen = IMAGEN_POR_DEFECTO_GRUPO;
	}
	
	public Grupo(String nombre, String pathImagen, Usuario propietario, List<ContactoIndividual> listaMiembros) {
		super(nombre);
		this.propietario = propietario;
		this.miembros = listaMiembros;
		imagen = pathImagen;
	}
	
	public Grupo() {
		this.miembros = new LinkedList<ContactoIndividual>();
		imagen = IMAGEN_POR_DEFECTO_GRUPO;
	}


	public List<ContactoIndividual> getMiembros() {
		return new LinkedList<ContactoIndividual>(miembros);
	}

	public Usuario getPropietario() {
		return propietario;
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
    
    public String getImagen() {
		return imagen;
	}


	public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public boolean poseeMiembro(Usuario usuario) {
		return miembros.stream().anyMatch(c -> c.getUsuario().equals(usuario));
	}
	

}
