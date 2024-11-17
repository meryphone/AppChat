package persistencia;
// COMENTAR

public class TDSFactoriaDAO extends FactoriaDAO {
	
	
	public TDSFactoriaDAO() {
		
	}

	@Override
	public AdaptadorUsuarioDAO getUsuarioDAO() {
		return AdaptadorUsuarioDAO.getInstance();
	}

	@Override
	public AdaptadorMensajeDAO getMensajeDAO() {
		return AdaptadorMensajeDAO.getInstance();
	}

	@Override
	public AdaptadorContactoDAO getContactoDAO() {
		return AdaptadorContactoDAO.getInstance();
	}

	@Override
	public AdaptadorGrupoDAO getGrupoDAO() {
		return AdaptadorGrupoDAO.getInstance();
	}

}