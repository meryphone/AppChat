package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import dominio.ContactoIndividual;
import dominio.Grupo;
import excepciones.ExcepcionAgregarContacto;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import java.awt.Color;

public class AñadirContacto extends JFrame{

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textTlf;
	private JTextField textNombre;
	private Controlador controlador = Controlador.getInstance();
	private Principal principal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AñadirContacto frame = new AñadirContacto(null);
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public AñadirContacto(Principal principal) {
		this.principal = principal;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 516, 288);
		setResizable(false); // Evita que la ventana se pueda redimensionar
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.setBorder(new LineBorder(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"), 4));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel abajo = new JPanel();
		abajo.setBackground(UIManager.getColor("List.dropCellBackground"));
		abajo.setBorder(new EmptyBorder(26, 0, 0, 0));
		contentPane.add(abajo, BorderLayout.SOUTH);
		FlowLayout fl_abajo = new FlowLayout(FlowLayout.RIGHT, 5, 5);
		fl_abajo.setAlignOnBaseline(true);
		abajo.setLayout(fl_abajo);

		JButton aceptar = new JButton("Aceptar");
		aceptar.setHorizontalAlignment(SwingConstants.LEADING);
		abajo.add(aceptar);

		aceptar.addActionListener(ev -> {
			try {
				boolean agregarContacto = controlador.agregarContacto(textTlf.getText(), textNombre.getText());
				if (agregarContacto) {
					MensajeAdvertencia.mostrarConfirmacion("Contacto añadido correctamente", contentPane);					
					dispose();
					
				}
			} catch (ExcepcionAgregarContacto e) {
				MensajeAdvertencia.mostrarError(e.getMessage(), contentPane);
				e.printStackTrace();
			}
		});

		JButton cancelar = new JButton("Cancelar");
		cancelar.setHorizontalAlignment(SwingConstants.LEADING);
		abajo.add(cancelar);
			   
	    
	    //WindowListener para detectar el cierre de la ventana Contactos
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosed(WindowEvent e) {
                if (principal != null) {
                    principal.actualizarListaContactos(); // Actualiza la lista en Principal al cerrar la ventana para añadir contactos
                }
            }
        });

		cancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				dispose(); 
			}
		});

		JPanel centro = new JPanel();
		centro.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.add(centro, BorderLayout.CENTER);
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

		JPanel mensaje_alerta = new JPanel();
		mensaje_alerta.setBackground(UIManager.getColor("List.dropCellBackground"));
		centro.add(mensaje_alerta);
		mensaje_alerta.setLayout(new BoxLayout(mensaje_alerta, BoxLayout.X_AXIS));

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(AñadirContacto.class.getResource("/resources/alerta24x24.png")));
		mensaje_alerta.add(lblNewLabel_3);

		JLabel lblNewLabel_2 = new JLabel("   Introduzca el nombre del contacto y su teléfono");
		lblNewLabel_2.setBackground(UIManager.getColor("List.dropCellBackground"));
		lblNewLabel_2.setFont(new Font("Liberation Mono", Font.BOLD, 15));
		mensaje_alerta.add(lblNewLabel_2);

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		centro.add(rigidArea_1);
		
		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		centro.add(rigidArea);

		JPanel nombre = new JPanel();
		nombre.setBackground(UIManager.getColor("List.dropCellBackground"));
		nombre.setMaximumSize(new Dimension(32767, 0));
		centro.add(nombre);
		nombre.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setPreferredSize(new Dimension(67, 17));
		lblNewLabel.setFont(new Font("Liberation Mono", Font.BOLD, 15));
		nombre.add(lblNewLabel);

		textNombre = new JTextField();
		nombre.add(textNombre);
		textNombre.setColumns(10);

		JPanel teléfono = new JPanel();
		teléfono.setBackground(UIManager.getColor("List.dropCellBackground"));
		centro.add(teléfono);
		teléfono.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_1 = new JLabel("Teléfono:");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("Liberation Mono", Font.BOLD, 15));
		teléfono.add(lblNewLabel_1);

		textTlf = new JTextField();
		teléfono.add(textTlf);
		textTlf.setColumns(10);

		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		rigidArea_2.setPreferredSize(new Dimension(20, 30));
		contentPane.add(rigidArea_2, BorderLayout.NORTH);
	}
	
}
