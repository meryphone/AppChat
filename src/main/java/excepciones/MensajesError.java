package excepciones;

public enum MensajesError {

	ENTIDAD_YA_REGISTRADA("La entidad ya ha sido registrada en la base de datos"),
	ENTIDAD_NO_ENCONTRADA("La entidad no se encontró en la base de datos"),
	ACCESO_DENEGADO("No tienes permisos para acceder a este recurso"),
	ERROR_DESCONOCIDO("Ha ocurrido un error desconocido");

	private final String mensaje;

	// Constructor para inicializar el mensaje de error
	MensajesError(String mensaje) {
		this.mensaje = mensaje;
	}

	// Método para obtener el mensaje asociado al enum
	public String getMensaje() {
		return mensaje;
	}
}
