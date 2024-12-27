package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import dominio.ContactoIndividual;

@SuppressWarnings("serial")
public class ContactoIndividualCellRenderer extends JPanel implements ListCellRenderer<ContactoIndividual> {
	private static final ImageIcon ICONO_CONTACTO = new ImageIcon(Principal.class.getResource("/resources/usuario(1).png"));
	private JLabel nombre;
	private JLabel imagen;
	private JLabel telefono;
	
	
	public ContactoIndividualCellRenderer(){
		 setLayout(new BorderLayout(10, 10)); // Espaciado horizontal y vertical entre componentes

	        // Crear componentes
	        imagen = new JLabel(ICONO_CONTACTO);
	        nombre = new JLabel();
	        telefono = new JLabel();

	        // Configurar estilos
	        nombre.setFont(nombre.getFont().deriveFont(14f)); // Nombre con fuente más grande
	        telefono.setFont(telefono.getFont().deriveFont(12f));
	        telefono.setForeground(Color.GRAY); // Teléfono en gris para menor peso visual

	        // Crear un panel para los textos (nombre y teléfono)
	        JPanel panelTextos = new JPanel();
	        panelTextos.setLayout(new BorderLayout());
	        panelTextos.add(nombre, BorderLayout.NORTH); // Nombre arriba
	        panelTextos.add(telefono, BorderLayout.SOUTH); // Teléfono abajo

	        // Añadir componentes al panel principal
	        add(imagen, BorderLayout.WEST); // Imagen a la izquierda
	        add(panelTextos, BorderLayout.CENTER); // Textos a la derecha de la imagen

	        // Configurar dimensiones mínimas para la imagen
	        imagen.setPreferredSize(new Dimension(50, 50)); // Tamaño 
				
	}	

	@Override
	public Component getListCellRendererComponent(JList<? extends ContactoIndividual> list, ContactoIndividual contacto, int index,
			boolean isSelected, boolean cellHasFocus) {
		
		nombre.setText(contacto.getNombre());		
		telefono.setText("Telf: " + contacto.getTelefono());
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
