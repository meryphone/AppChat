package dominio;

import java.util.HashMap;
import java.util.Optional;

public class RepositorioUsuarios {
	
	private HashMap<String, Usuario> usuariosRegistrados;	// Mapa que relaciona el télefono con su usuario correspondiente.
	private static RepositorioUsuarios repositorioUsuarios;

		
	public RepositorioUsuarios() {
		usuariosRegistrados = new HashMap<String, Usuario>();
		// Inicializar repositorio con recuperarRepoUsuario)definir aquí)
	}
	
	// Patrón Singleton 
	
	public static RepositorioUsuarios getInstance() {
		  if (repositorioUsuarios == null) { 
	            repositorioUsuarios = new RepositorioUsuarios();
	        }
	        return repositorioUsuarios; 
	}
	
	/**
	 * Añade a la lista de usuarios registrados el usuario pasado como párametro.
	 * @param nuevoUsuario
	 */
	
	public void anadirUsuario(Usuario nuevoUsuario) {
		usuariosRegistrados.put(nuevoUsuario.getMovil(), nuevoUsuario);
	}
	
	/**
	 * Elimina de la lista de usuarios registrados al usuario pasado como parámetro.
	 * @param usuarioEliminar
	 */
	
	public void eliminarUsuario(Usuario usuarioEliminar) {
		usuariosRegistrados.remove(usuarioEliminar.getMovil());
	}
	
	/**
	 * Devuelve un Optional que contiene, si se encuentra en la lista de usuarios, el usuario cuyo teléfono se pasa como parámetro.
	 * @param tlf
	 * @return
	 */
	
	public Optional<Usuario> getUsuarioPorTelefono(String tlf) {
		return Optional.ofNullable(usuariosRegistrados.get(tlf));
	}
	
	
	

}
