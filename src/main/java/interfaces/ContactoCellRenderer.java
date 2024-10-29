package interfaces;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import dominio.Contacto;

public class ContactoCellRenderer extends JPanel implements ListCellRenderer<Contacto> {
	
	private JLabel nombre;
	private JLabel imagen;
	
	public ContactoCellRenderer(){
		nombre = new JLabel();
		imagen = new JLabel();
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
        add(imagen); 
        add(nombre); 
				
	}	

	@Override
	public Component getListCellRendererComponent(JList<? extends Contacto> list, Contacto contacto, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		nombre.setText(contacto.toString());		
		imagen.setIcon(new ImageIcon(Principal.class.getResource("/resources/usuario(1).png")));
		
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
