package persistencia.interfaces;

import java.util.List;
import dominio.Usuario;
import excepciones.ExcepcionDAO;

/**
 * Interfaz que define los métodos esenciales para interactuar con la base de datos, en relación con los usuarios.
 */

public interface IAdaptadorUsuarioDAO {
	
	public void registrarUsuario(Usuario nuevoUsuario) throws ExcepcionDAO;
	public void modificarUsuario(Usuario usuarioActualizar);
	public Usuario recuperarUsuario(int codigo) throws ExcepcionDAO;
	public List<Usuario> recuperarUsuarios() throws ExcepcionDAO;

}
