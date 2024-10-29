package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.Font;

public class AlertaAñadirContacto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlertaAñadirContacto frame = new AlertaAñadirContacto();
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
	public AlertaAñadirContacto() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 492, 258);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel abajo = new JPanel();
		abajo.setBorder(new EmptyBorder(26, 0, 0, 0));
		contentPane.add(abajo, BorderLayout.SOUTH);
		FlowLayout fl_abajo = new FlowLayout(FlowLayout.RIGHT, 5, 5);
		fl_abajo.setAlignOnBaseline(true);
		abajo.setLayout(fl_abajo);
		
		JButton btnNewButton = new JButton("Aceptar");
		btnNewButton.setHorizontalAlignment(SwingConstants.LEADING);
		abajo.add(btnNewButton);
		
		JButton btnNewButton_1 = new JButton("Cancelar");
		btnNewButton_1.setHorizontalAlignment(SwingConstants.LEADING);
		abajo.add(btnNewButton_1);
		
		JPanel centro = new JPanel();
		contentPane.add(centro, BorderLayout.CENTER);
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		
		JPanel mensaje_alerta = new JPanel();
		centro.add(mensaje_alerta);
		mensaje_alerta.setLayout(new BoxLayout(mensaje_alerta, BoxLayout.X_AXIS));
		
		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(AlertaAñadirContacto.class.getResource("/resources/alerta24x24.png")));
		mensaje_alerta.add(lblNewLabel_3);
		
		JLabel lblNewLabel_2 = new JLabel("   Introduzca el nombre del contacto y su teléfono");
		lblNewLabel_2.setFont(new Font("Corbel", Font.BOLD, 14));
		mensaje_alerta.add(lblNewLabel_2);
		
		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		centro.add(rigidArea_1);
		
		JPanel nombre = new JPanel();
		nombre.setMaximumSize(new Dimension(32767, 0));
		centro.add(nombre);
		nombre.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setPreferredSize(new Dimension(54, 17));
		lblNewLabel.setFont(new Font("Corbel", Font.BOLD, 13));
		nombre.add(lblNewLabel);
		
		textField_1 = new JTextField();
		nombre.add(textField_1);
		textField_1.setColumns(10);
		
		JPanel teléfono = new JPanel();
		centro.add(teléfono);
		teléfono.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		JLabel lblNewLabel_1 = new JLabel("Teléfono:");
		lblNewLabel_1.setFont(new Font("Corbel", Font.BOLD, 13));
		teléfono.add(lblNewLabel_1);
		
		textField = new JTextField();
		teléfono.add(textField);
		textField.setColumns(10);
		
		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		contentPane.add(rigidArea_2, BorderLayout.NORTH);
	}

}
