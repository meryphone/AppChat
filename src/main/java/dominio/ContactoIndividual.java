package dominio;

public class ContactoIndividual extends Contacto {
<<<<<<< HEAD
    
    private String telefono;
    private Usuario usuarioCreador; // Para identificar en la base de datos los contactos que pertenecen a un usuario en concreto.
=======
	//El codigo ya le viene de la clase Contacto
	private String telefono;
	private Usuario usuarioCreador;  //Para identificar en la base de datos los contactos que pertenecen a un usuario en concreto.	
	
	public ContactoIndividual(String nombre, String telefono, Usuario usuarioCreador) {
		super(nombre);
		this.telefono = telefono;
		this.usuarioCreador = usuarioCreador;
	}
>>>>>>> eba

    public ContactoIndividual(String nombre, String telefono, Usuario usuarioCreador) {
        super(nombre);
        this.telefono = telefono;
        this.usuarioCreador = usuarioCreador;
    }

<<<<<<< HEAD
    /**
     * Contructor sin parametros para inicilizar un ContactoIndividual y aplicar métodos set.
     */
    public ContactoIndividual() {
        super();
    }
    
    // Métodos específicos de la clase
    public String getTelefono() {
        return telefono;
    }
=======
	/*public void setTelefono(String telefono) {
		this.telefono = telefono;
	}*/
>>>>>>> eba

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

<<<<<<< HEAD
    public Usuario getUsuarioCreador() {
        return usuarioCreador;
    }

    public void setUsuarioCreador(Usuario usuarioCreador) {
        this.usuarioCreador = usuarioCreador;
    }

    // Método específico para verificar si el contacto pertenece a una lista de un usuario (si fuera necesario)
    public boolean perteneceAUsuario(Usuario usuario) {
        return this.usuarioCreador.equals(usuario);
    }
=======
	/*public void setUsuarioCreador(Usuario usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}*/
	
	
	
/* Metodo que comprobaria si un contaco en especifivo pertenece a la lista de contactos de alguien. (no se si hace falta)
 * public boolean isAgregado() {
		return !nombre.isEmpty();
 */
	
>>>>>>> eba
}
