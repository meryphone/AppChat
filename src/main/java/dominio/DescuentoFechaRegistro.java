package dominio;

public class DescuentoFechaRegistro implements Descuento{

	@Override
	public double getDescuento(Double precioInicial) {
		return 0.6 * precioInicial;
	}

}
