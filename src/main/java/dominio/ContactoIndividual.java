package dominio;

import javax.swing.ImageIcon;

/**
 * Clase que represena un contacto individual
 */

public class ContactoIndividual extends Contacto {
	
    
    private String telefono;
    private Usuario usuario; // Usuario al que corresponde el contacto

    public ContactoIndividual(String nombre, String telefono, Usuario user) {
        super(nombre);
        this.telefono = telefono;
        usuario = user;
    }
    
    public ContactoIndividual() {
        super();
    }
    
    public String getTelefono() {
        return telefono;
    }
    
	public void setTelefono(String telefono) {
		this.telefono = telefono;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}
	
	public ImageIcon getImagenPerfil() {
		return usuario.getIconoDesdePath();
	}
	
	
}
