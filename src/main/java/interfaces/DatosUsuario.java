package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JLabel;
import java.awt.GridBagConstraints;
import javax.swing.JTextField;
import java.awt.Insets;
import javax.swing.JPasswordField;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JTextArea;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Font;
import javax.swing.border.EmptyBorder;
import javax.swing.UIManager;
import com.toedter.calendar.JDateChooser;

public class DatosUsuario extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textField;
	private JTextField textField_1;
	private JLabel lblTelfono;
	private JTextField textField_2;
	private JLabel lblContrasea;
	private JPasswordField passwordField;
	private JLabel lblRepitaContrasea;
	private JPasswordField passwordField_1;
	private JLabel lblNewLabel;
	private JLabel lblNewLabel_1;
	private JTextArea textArea;
	private JButton btnNewButton;
	private JButton btnNewButton_1;
	private JLabel lblNewLabel_2;
	private JLabel iconoFoto;
	private JLabel label;
	private JLabel label_1;
	private JDateChooser dateChooser;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					DatosUsuario frame = new DatosUsuario();
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
	public DatosUsuario() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 940, 486);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.setSize(new Dimension(800, 800));
		contentPane.setPreferredSize(new Dimension(15, 15));
		contentPane.setBorder(new EmptyBorder(20, 20, 20, 20));

		setContentPane(contentPane);
		GridBagLayout gbl_contentPane = new GridBagLayout();
		gbl_contentPane.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.rowHeights = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gbl_contentPane.columnWeights = new double[]{0.0, 0.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_contentPane.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		contentPane.setLayout(gbl_contentPane);
		
		label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(DatosUsuario.class.getResource("/resources/REGISTRO.png")));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.anchor = GridBagConstraints.WEST;
		gbc_label_1.gridwidth = 4;
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.gridx = 5;
		gbc_label_1.gridy = 0;
		contentPane.add(label_1, gbc_label_1);
		
		label = new JLabel("");
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.gridx = 7;
		gbc_label.gridy = 1;
		contentPane.add(label, gbc_label);
		
		JLabel lblNombre = new JLabel("Nombre:");
		lblNombre.setFont(new Font("Courier 10 Pitch", Font.BOLD, 14));
		GridBagConstraints gbc_lblNombre = new GridBagConstraints();
		gbc_lblNombre.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblNombre.insets = new Insets(0, 0, 5, 5);
		gbc_lblNombre.gridx = 1;
		gbc_lblNombre.gridy = 2;
		contentPane.add(lblNombre, gbc_lblNombre);
		
		textField = new JTextField();
		GridBagConstraints gbc_textField = new GridBagConstraints();
		gbc_textField.gridwidth = 16;
		gbc_textField.insets = new Insets(0, 0, 5, 5);
		gbc_textField.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField.gridx = 2;
		gbc_textField.gridy = 2;
		contentPane.add(textField, gbc_textField);
		textField.setColumns(10);
		
		JLabel lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setFont(new Font("Courier 10 Pitch", Font.BOLD, 14));
		GridBagConstraints gbc_lblApellidos = new GridBagConstraints();
		gbc_lblApellidos.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblApellidos.insets = new Insets(0, 0, 5, 5);
		gbc_lblApellidos.gridx = 1;
		gbc_lblApellidos.gridy = 3;
		contentPane.add(lblApellidos, gbc_lblApellidos);
		
		textField_1 = new JTextField();
		GridBagConstraints gbc_textField_1 = new GridBagConstraints();
		gbc_textField_1.gridwidth = 16;
		gbc_textField_1.insets = new Insets(0, 0, 5, 5);
		gbc_textField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_1.gridx = 2;
		gbc_textField_1.gridy = 3;
		contentPane.add(textField_1, gbc_textField_1);
		textField_1.setColumns(10);
		
		lblTelfono = new JLabel("Teléfono:");
		lblTelfono.setFont(new Font("Courier 10 Pitch", Font.BOLD, 14));
		GridBagConstraints gbc_lblTelfono = new GridBagConstraints();
		gbc_lblTelfono.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblTelfono.insets = new Insets(0, 0, 5, 5);
		gbc_lblTelfono.gridx = 1;
		gbc_lblTelfono.gridy = 4;
		contentPane.add(lblTelfono, gbc_lblTelfono);
		
		textField_2 = new JTextField();
		GridBagConstraints gbc_textField_2 = new GridBagConstraints();
		gbc_textField_2.gridwidth = 3;
		gbc_textField_2.insets = new Insets(0, 0, 5, 5);
		gbc_textField_2.fill = GridBagConstraints.HORIZONTAL;
		gbc_textField_2.gridx = 2;
		gbc_textField_2.gridy = 4;
		contentPane.add(textField_2, gbc_textField_2);
		textField_2.setColumns(10);
		
		lblContrasea = new JLabel("Contraseña:");
		lblContrasea.setFont(new Font("Courier 10 Pitch", Font.BOLD, 14));
		GridBagConstraints gbc_lblContrasea = new GridBagConstraints();
		gbc_lblContrasea.fill = GridBagConstraints.HORIZONTAL;
		gbc_lblContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblContrasea.gridx = 1;
		gbc_lblContrasea.gridy = 5;
		contentPane.add(lblContrasea, gbc_lblContrasea);
		
		passwordField = new JPasswordField();
		GridBagConstraints gbc_passwordField = new GridBagConstraints();
		gbc_passwordField.gridwidth = 3;
		gbc_passwordField.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField.gridx = 2;
		gbc_passwordField.gridy = 5;
		contentPane.add(passwordField, gbc_passwordField);
		
		lblRepitaContrasea = new JLabel("Repita contraseña:");
		lblRepitaContrasea.setFont(new Font("Courier 10 Pitch", Font.BOLD, 14));
		GridBagConstraints gbc_lblRepitaContrasea = new GridBagConstraints();
		gbc_lblRepitaContrasea.gridwidth = 2;
		gbc_lblRepitaContrasea.anchor = GridBagConstraints.WEST;
		gbc_lblRepitaContrasea.insets = new Insets(0, 0, 5, 5);
		gbc_lblRepitaContrasea.gridx = 7;
		gbc_lblRepitaContrasea.gridy = 5;
		contentPane.add(lblRepitaContrasea, gbc_lblRepitaContrasea);
		
		passwordField_1 = new JPasswordField();
		GridBagConstraints gbc_passwordField_1 = new GridBagConstraints();
		gbc_passwordField_1.fill = GridBagConstraints.HORIZONTAL;
		gbc_passwordField_1.gridwidth = 9;
		gbc_passwordField_1.insets = new Insets(0, 0, 5, 5);
		gbc_passwordField_1.gridx = 9;
		gbc_passwordField_1.gridy = 5;
		contentPane.add(passwordField_1, gbc_passwordField_1);
		
		lblNewLabel = new JLabel("Fecha:");
		lblNewLabel.setFont(new Font("Courier 10 Pitch", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.anchor = GridBagConstraints.EAST;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 1;
		gbc_lblNewLabel.gridy = 7;
		contentPane.add(lblNewLabel, gbc_lblNewLabel);
		
		dateChooser = new JDateChooser();
		GridBagConstraints gbc_dateChooser = new GridBagConstraints();
		gbc_dateChooser.gridwidth = 2;
		gbc_dateChooser.insets = new Insets(0, 0, 5, 5);
		gbc_dateChooser.fill = GridBagConstraints.BOTH;
		gbc_dateChooser.gridx = 2;
		gbc_dateChooser.gridy = 7;
		contentPane.add(dateChooser, gbc_dateChooser);
		
		lblNewLabel_2 = new JLabel("Foto:");
		lblNewLabel_2.setFont(new Font("Courier 10 Pitch", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_2 = new GridBagConstraints();
		gbc_lblNewLabel_2.anchor = GridBagConstraints.WEST;
		gbc_lblNewLabel_2.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_2.gridx = 7;
		gbc_lblNewLabel_2.gridy = 8;
		contentPane.add(lblNewLabel_2, gbc_lblNewLabel_2);
		
		lblNewLabel_1 = new JLabel("Biografía:");
		lblNewLabel_1.setFont(new Font("Courier 10 Pitch", Font.BOLD, 14));
		GridBagConstraints gbc_lblNewLabel_1 = new GridBagConstraints();
		gbc_lblNewLabel_1.anchor = GridBagConstraints.BELOW_BASELINE_LEADING;
		gbc_lblNewLabel_1.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel_1.gridx = 1;
		gbc_lblNewLabel_1.gridy = 9;
		contentPane.add(lblNewLabel_1, gbc_lblNewLabel_1);
		
		textArea = new JTextArea();
		textArea.setWrapStyleWord(true);
		textArea.setLineWrap(true);
		textArea.setPreferredSize(new Dimension(5, 5));
		GridBagConstraints gbc_textArea = new GridBagConstraints();
		gbc_textArea.gridheight = 2;
		gbc_textArea.gridwidth = 3;
		gbc_textArea.insets = new Insets(0, 0, 5, 5);
		gbc_textArea.fill = GridBagConstraints.BOTH;
		gbc_textArea.gridx = 2;
		gbc_textArea.gridy = 9;
		contentPane.add(textArea, gbc_textArea);
		
		iconoFoto = new JLabel("");
		iconoFoto.setIcon(new ImageIcon(DatosUsuario.class.getResource("/resources/nueva-cuenta (1).png")));
		GridBagConstraints gbc_iconoFoto = new GridBagConstraints();
		gbc_iconoFoto.gridwidth = 5;
		gbc_iconoFoto.insets = new Insets(0, 0, 5, 5);
		gbc_iconoFoto.gridx = 9;
		gbc_iconoFoto.gridy = 9;
		contentPane.add(iconoFoto, gbc_iconoFoto);
		
		btnNewButton = new JButton("Cancelar");
		btnNewButton.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 12));
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton.gridx = 2;
		gbc_btnNewButton.gridy = 11;
		contentPane.add(btnNewButton, gbc_btnNewButton);
		
		btnNewButton_1 = new JButton("Aceptar");
		btnNewButton_1.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 12));
		btnNewButton_1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});
		GridBagConstraints gbc_btnNewButton_1 = new GridBagConstraints();
		gbc_btnNewButton_1.anchor = GridBagConstraints.EAST;
		gbc_btnNewButton_1.insets = new Insets(0, 0, 0, 5);
		gbc_btnNewButton_1.gridx = 4;
		gbc_btnNewButton_1.gridy = 11;
		contentPane.add(btnNewButton_1, gbc_btnNewButton_1);
	}

}
