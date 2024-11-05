package dominio;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RepositorioContactos {
	
	private Map<String, List<Contacto>> contactosUsuario;
	private static RepositorioContactos repositorioContactos;
	
	private RepositorioContactos() {
		contactosUsuario = new HashMap<String, List<Contacto>>();		
	}
	
	public static RepositorioContactos getInstance() {
		if(repositorioContactos == null) {
			repositorioContactos = new RepositorioContactos();
		}
		
		return repositorioContactos;
	}
		

}
