package persistencia.interfaces;

import dominio.ContactoIndividual;
import excepciones.ExcepcionDAO;

public interface IAdaptadorContactoDAO {
	
	public ContactoIndividual recuperarContacto(int codigo);
	public void registrarContacto(ContactoIndividual nuevoContacto) throws ExcepcionDAO;

}
