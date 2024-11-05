package persistencia;

import tds.driver.ServicioPersistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
	
	private static ServicioPersistencia servicioPersistencia;
	
	public TDSFactoriaDAO() {
	}

	@Override
	public UsuarioDAO getUsuarioDAO() {
		return UsuarioDAO.geInstanc();
	}

	@Override
	public MensajeDAO getContactoDAO() {
		return MensajeDAO.getInstance();
	}

	@Override
	public ContactoIndividualDAO getProductoDAO() {
		return ContactoIndividualDAO.getInstance();
	}

	@Override
	public GrupoDAO getClienteDAO() {
		return GrupoDAO.getInstance();
	}

}fF