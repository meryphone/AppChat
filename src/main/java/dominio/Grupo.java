package dominio;

import java.util.LinkedList;
import java.util.List;

public class Grupo extends Contacto {
	private String imagen; //del grupo
	private List<ContactoIndividual> miembros;
	private Usuario propietario; //del grupo

	public Grupo(String nombre, Usuario propietario) {
		super(nombre);
		this.propietario = propietario;
		this.miembros = new LinkedList<ContactoIndividual>();
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

	/*public void setImagen(String imagen) {
		this.imagen = imagen;
	}

	public void setMiembros(List<Contacto> miembros) {
		this.miembros = miembros;
	}

	public void setPropietario(Usuario propietario) {
		this.propietario = propietario;
	}*/
	
	
	//ESTO VA EN EL CONTROLADORS
	/*public void agregarContacto(ContactoIndividual contacto) {
        if (!miembros.contains(contacto)) {
            miembros.add(contacto);
        }
    }

    public void eliminarContacto(Contacto contacto) {
        miembros.remove(contacto);
    }*/
	
    /*public void enviarMensaje(String texto, Usuario emisor) {
        for (Contacto contacto : miembros) {
            Mensaje mensaje = new Mensaje(texto, emisor);
            contacto.recibirMensaje(mensaje); // Suponiendo que Contacto tiene recibirMensaje
        }
    }*/
	
}
