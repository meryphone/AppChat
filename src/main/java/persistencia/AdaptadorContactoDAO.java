package persistencia;

import beans.Entidad;
import dominio.ContactoIndividual;
import dominio.excepciones.ExcepcionDAO;
import tds.driver.FactoriaServicioPersistencia;
import tds.driver.ServicioPersistencia;

public class AdaptadorContactoDAO {

	public static AdaptadorContactoDAO adaptadorContacto;
	private ServicioPersistencia servicioPersistencia;

	public static AdaptadorContactoDAO getInstance() {
		if (adaptadorContacto == null) {
			adaptadorContacto = new AdaptadorContactoDAO();
		}
		return adaptadorContacto;
	}

	private AdaptadorContactoDAO() {
		servicioPersistencia = FactoriaServicioPersistencia.getInstance().getServicioPersistencia();
	}
	
	public void registrarContacto(ContactoIndividual contacto) {
		// TODO Auto-generated method stub
		
	}
	
	
	public ContactoIndividual recuperarContacto(int codigo) throws ExcepcionDAO {
		
		ContactoIndividual contacto = null;
		Entidad eContacto = servicioPersistencia.recuperarEntidad(codigo);
		AdaptadorUsuarioDAO adaptadorUsuario = TDSFactoriaDAO.getInstance().getUsuarioDAO();
		
		contacto.setCodigo(codigo);
		contacto.setTelefono(servicioPersistencia.recuperarPropiedadEntidad(eContacto, "telefono"));
		contacto.setUsuarioCreador(adaptadorUsuario.recuperarUsuario(Integer.parseInt(servicioPersistencia.recuperarPropiedadEntidad(eContacto,"usuarioCreador"))));
		
		
		return null;
		
	}

	
	
	

}
