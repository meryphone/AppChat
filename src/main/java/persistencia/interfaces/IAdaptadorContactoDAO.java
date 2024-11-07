package persistencia.interfaces;

import dominio.ContactoIndividual;

public interface IAdaptadorContactoDAO {
	
	public ContactoIndividual recuperarContacto(int codigo);
	

}
