package vista;

import java.awt.Component;

import javax.swing.JOptionPane;

public class MensajeAdvertencia {
	
	public static void mostrarError(String mensaje, Component parentComponent) {
		 JOptionPane.showMessageDialog(parentComponent, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	} 
	
	public static void mostrarConfirmacion(String mensaje, Component parentComponent) {
		JOptionPane.showMessageDialog(parentComponent, mensaje, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
	}

}
