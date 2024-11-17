package persistencia.interfaces;

import java.util.List;
import dominio.Grupo;
import excepciones.ExcepcionDAO;

public interface IAdaptadorGrupoDAO {
	
	public void registrarGrupo(Grupo nuevoGrupo) throws ExcepcionDAO;
	public Grupo recuperarGrupo(int codigo) throws  ExcepcionDAO;
	public void modificarGrupo(Grupo grupoModificar) throws ExcepcionDAO;
	public List<Grupo> recuperarTodosGrupos() throws ExcepcionDAO;

}
