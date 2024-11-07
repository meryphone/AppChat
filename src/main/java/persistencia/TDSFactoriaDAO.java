package persistencia;

import persistencia.interfaces.*;
import tds.driver.ServicioPersistencia;

public class TDSFactoriaDAO extends FactoriaDAO {
	
	
	public TDSFactoriaDAO() {
		
	}

	@Override
	public AdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioDAO.getInstance();
	}

	@Override
	public AdaptadorMensajeDAO getMensajeDAO() {
		return AdaptadorMensaje.getInstance();
	}

	@Override
	public AdaptadorContactoDAO getContactoDAO() {
		return AdaptadorContactoDAO.getInstance();
	}

	@Override
	public AdaptadorGrupoDAO getGrupoDAO() {
		return AdaptadorGrupo.getInstance();
	}

}