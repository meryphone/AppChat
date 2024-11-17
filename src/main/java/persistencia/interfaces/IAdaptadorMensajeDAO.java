package persistencia.interfaces;

import java.util.List;
import dominio.Mensaje;

public interface IAdaptadorMensajeDAO {
	
	public Mensaje recuperarMensaje(int codigo);
	public void registrarMensaje(Mensaje mensaje);
	public List<Mensaje> recuperarTodosMensajes();
		

}
