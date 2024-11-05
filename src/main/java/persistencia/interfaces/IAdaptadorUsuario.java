package persistencia.interfaces;

import java.util.List;
import dominio.Usuario;

/**
 * Interfaz que define los métodos esenciales para interactuar con la base de datos, en relación con los usuarios.
 */

public interface IAdaptadorUsuario {
	
	public void registrarUsuario(Usuario nuevoUsuario);
	public void actualizarUsuario(Usuario usuarioActualizar);
	public Usuario recuperarUsuario(String telefono);
	public List<Usuario> recuperarUsuarios();

}
