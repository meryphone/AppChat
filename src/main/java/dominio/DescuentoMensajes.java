package dominio;

public class DescuentoMensajes implements Descuento {

	@Override
	public double getDescuento(Double precioInicial) {
		return 0.8 * precioInicial;

	}
	
	

}
