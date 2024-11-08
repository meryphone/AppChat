package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.ListCellRenderer;

import dominio.Mensaje;

public class BusquedaCellRenderer extends JPanel implements ListCellRenderer<Mensaje>{
	private static final long serialVersionUID = 1L;
	private JLabel emisor;
	private JLabel receptor;
	private JTextArea mensaje; //no se si ponerlo como JTextArea
	
	public BusquedaCellRenderer(){
		emisor = new JLabel();
		receptor = new JLabel();
		
		mensaje = new JTextArea();
		mensaje.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
		
		setLayout(new BorderLayout(5,5));
		setBackground(new Color(173, 216, 230)); // Fondo azul claro
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        JPanel panelSuperior = new JPanel(new GridLayout(1, 2, 5, 5));
        panelSuperior.setOpaque(false); //para el fondo azul claro

        panelSuperior.add(emisor); 
        panelSuperior.add(receptor); 
		
        JPanel panelMensaje = new JPanel(new BorderLayout());
        panelMensaje.add(mensaje, BorderLayout.CENTER);
        
        add(panelSuperior, BorderLayout.NORTH);
        add(panelMensaje, BorderLayout.CENTER);
	}	
	
	public Component getListCellRendererComponent(JList<? extends Mensaje> list, Mensaje mensaje, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		emisor.setText(mensaje.getEmisor());		
		receptor.setText(mensaje.getReceptor());	
		this.mensaje.setText(mensaje.getTexto());
		
		if (isSelected) {
			setBackground(list.getSelectionBackground());
			setForeground(list.getSelectionForeground());
		} else {
			setBackground(list.getBackground());
			setForeground(list.getForeground());
		}
		
		return this;
	}

}
