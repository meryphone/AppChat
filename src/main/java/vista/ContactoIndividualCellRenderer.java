package vista;

import java.awt.Component;
import java.awt.FlowLayout;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import dominio.ContactoIndividual;

@SuppressWarnings("serial")
public class ContactoIndividualCellRenderer extends JPanel implements ListCellRenderer<ContactoIndividual> {
	
	private JLabel nombre;
	private JLabel imagen;
	
	public ContactoIndividualCellRenderer(){
		nombre = new JLabel();
		imagen = new JLabel();
		
		setLayout(new FlowLayout(FlowLayout.LEFT));
		
        add(imagen); 
        add(nombre); 
				
	}	

	@Override
	public Component getListCellRendererComponent(JList<? extends ContactoIndividual> list, ContactoIndividual contacto, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		nombre.setText(contacto.getNombre());		
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
