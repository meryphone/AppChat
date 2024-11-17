package persistencia;
// COMENTAR
import java.util.HashMap;

/*Esta clase implementa un pool para los adaptadores que lo necesiten*/

public class PoolDAO {
	private static PoolDAO pool ;
	private HashMap<Integer,Object> poolDAO;

	private PoolDAO() {
		poolDAO = new HashMap<Integer, Object>();
	}

	public static PoolDAO getInstance() {
		if (pool == null)
			pool = new PoolDAO();
		return pool;

	}
	
	//Devuelve null si no encuentra el objeto.
	public Object getObjeto(int id) {
		return poolDAO.get(id);
	} 

	public void addObjeto(int id, Object objeto) {
		poolDAO.put(id, objeto);
	}

	public boolean contiene(int id) {
		return poolDAO.containsKey(id);
	}

}