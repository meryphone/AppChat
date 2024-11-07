package dominio.excepciones;

@SuppressWarnings("serial")
public class ExcepcionDAO extends Exception {

	public ExcepcionDAO(String mensaje) {
		super(mensaje);
	}
}
