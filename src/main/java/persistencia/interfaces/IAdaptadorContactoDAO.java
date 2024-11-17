package persistencia.interfaces;

import java.util.List;

import dominio.ContactoIndividual;
import excepciones.ExcepcionDAO;

public interface IAdaptadorContactoDAO {
	
	public void registrarContacto(ContactoIndividual nuevoContacto) throws ExcepcionDAO;
	public ContactoIndividual recuperarContacto(int codigo) throws ExcepcionDAO;
	public List<ContactoIndividual> recuperarTodosContactos() throws ExcepcionDAO;
}
