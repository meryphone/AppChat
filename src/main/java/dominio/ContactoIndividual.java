package dominio;

public class ContactoIndividual extends Contacto {
    
    private String telefono;
   

    public ContactoIndividual(String nombre, String telefono) {
        super(nombre);
        this.telefono = telefono;
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
	
	
}
