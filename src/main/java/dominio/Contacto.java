package dominio;

public class Contacto {

	private String nombre;
	private String apellidos;
	private int telefono;
	
	public Contacto(String _nombre, String _apellidos,  int _telefono) {
	 nombre =_nombre;
	 apellidos = _apellidos;
	 telefono = _telefono;
	}
	
	public String toString () {
		return nombre + " " + apellidos;
	}

	public String getNombre() {
		return nombre;
	}

	public String getApellidos() {
		return apellidos;
	}

	public int getTelefono() {
		return telefono;
	}

	public boolean isAgregado() {

		if (nombre == "") {
			return false;
		} else {
			return true;
		}
	}

}
