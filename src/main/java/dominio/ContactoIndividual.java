package dominio;


public class ContactoIndividual extends Contacto {
	//El codigo ya le viene de la clase Contacto
	private String telefono;
	private Usuario usuarioCreador;  //Para identificar en la base de datos los contactos que pertenecen a un usuario en concreto.	
	
	public ContactoIndividual(String nombre, String telefono, Usuario usuarioCreador) {
		super(nombre);
		this.telefono = telefono;
		this.usuarioCreador = usuarioCreador;
	}

	public String getTelefono() {
		return telefono;
	}

	/*public void setTelefono(String telefono) {
		this.telefono = telefono;
	}*/

	public Usuario getUsuarioCreador() {
		return usuarioCreador;
	}

	/*public void setUsuarioCreador(Usuario usuarioCreador) {
		this.usuarioCreador = usuarioCreador;
	}*/
	
	
	
/* Metodo que comprobaria si un contaco en especifivo pertenece a la lista de contactos de alguien. (no se si hace falta)
 * public boolean isAgregado() {
		return !nombre.isEmpty();
 */
	
}
