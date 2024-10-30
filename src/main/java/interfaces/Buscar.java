package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.GridBagLayout;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.ImageIcon;
import java.awt.Dimension;
import java.awt.Component;
import java.awt.FlowLayout;

public class Buscar extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtTexto;
	private JTextField txtTelefono;
	private JTextField txtContacto;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Buscar frame = new Buscar();
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
	public Buscar() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 645, 509);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.Y_AXIS));
		
		JPanel arriba = new JPanel();
		contentPane.add(arriba);
		
		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Buscar.class.getResource("/resources/archivo-de-busqueda(2).png")));
		arriba.add(lblNewLabel);
		
		JPanel centro = new JPanel();
		centro.setBorder(new TitledBorder(new LineBorder(new Color(25, 25, 112), 2), "Buscar", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		contentPane.add(centro);
		centro.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = new JPanel();
		centro.add(panel);
		GridBagLayout gbl_panel = new GridBagLayout();
		gbl_panel.columnWidths = new int[]{114, 114, 114, 82, 0, 0, 0, 0};
		gbl_panel.rowHeights = new int[]{25, 0, 0, 0, 0};
		gbl_panel.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gbl_panel.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		panel.setLayout(gbl_panel);
		
		JLabel label_2 = new JLabel("");
		label_2.setIcon(new ImageIcon(Buscar.class.getResource("/resources/comentario(1).png")));
		GridBagConstraints gbc_label_2 = new GridBagConstraints();
		gbc_label_2.insets = new Insets(0, 0, 5, 5);
		gbc_label_2.anchor = GridBagConstraints.EAST;
		gbc_label_2.gridx = 0;
		gbc_label_2.gridy = 1;
		panel.add(label_2, gbc_label_2);
		
		txtTexto = new JTextField();
		GridBagConstraints gbc_txtTexto = new GridBagConstraints();
		gbc_txtTexto.fill = GridBagConstraints.BOTH;
		gbc_txtTexto.gridwidth = 4;
		gbc_txtTexto.insets = new Insets(0, 0, 5, 5);
		gbc_txtTexto.gridx = 1;
		gbc_txtTexto.gridy = 1;
		panel.add(txtTexto, gbc_txtTexto);
		txtTexto.setMinimumSize(new Dimension(10, 19));
		txtTexto.setText("texto");
		txtTexto.setColumns(10);
		
		JLabel label_1 = new JLabel("");
		label_1.setIcon(new ImageIcon(Buscar.class.getResource("/resources/llamada-telefonica(1).png")));
		GridBagConstraints gbc_label_1 = new GridBagConstraints();
		gbc_label_1.insets = new Insets(0, 0, 5, 5);
		gbc_label_1.anchor = GridBagConstraints.EAST;
		gbc_label_1.gridx = 0;
		gbc_label_1.gridy = 2;
		panel.add(label_1, gbc_label_1);
		
		txtTelefono = new JTextField();
		GridBagConstraints gbc_txtTelefono = new GridBagConstraints();
		gbc_txtTelefono.fill = GridBagConstraints.VERTICAL;
		gbc_txtTelefono.anchor = GridBagConstraints.WEST;
		gbc_txtTelefono.insets = new Insets(0, 0, 5, 5);
		gbc_txtTelefono.gridx = 1;
		gbc_txtTelefono.gridy = 2;
		panel.add(txtTelefono, gbc_txtTelefono);
		txtTelefono.setText("telefono");
		txtTelefono.setColumns(10);
		
		JLabel label = new JLabel("");
		label.setIcon(new ImageIcon(Buscar.class.getResource("/resources/buscar-perfil(3).png")));
		GridBagConstraints gbc_label = new GridBagConstraints();
		gbc_label.insets = new Insets(0, 0, 5, 5);
		gbc_label.anchor = GridBagConstraints.EAST;
		gbc_label.gridx = 2;
		gbc_label.gridy = 2;
		panel.add(label, gbc_label);
		
		txtContacto = new JTextField();
		GridBagConstraints gbc_txtContacto = new GridBagConstraints();
		gbc_txtContacto.fill = GridBagConstraints.VERTICAL;
		gbc_txtContacto.gridwidth = 2;
		gbc_txtContacto.anchor = GridBagConstraints.WEST;
		gbc_txtContacto.insets = new Insets(0, 0, 5, 5);
		gbc_txtContacto.gridx = 3;
		gbc_txtContacto.gridy = 2;
		panel.add(txtContacto, gbc_txtContacto);
		txtContacto.setText("contacto");
		txtContacto.setColumns(10);
		
		JButton btnNewButton = new JButton("Buscar");
		GridBagConstraints gbc_btnNewButton = new GridBagConstraints();
		gbc_btnNewButton.insets = new Insets(0, 0, 5, 0);
		gbc_btnNewButton.anchor = GridBagConstraints.NORTHWEST;
		gbc_btnNewButton.gridx = 6;
		gbc_btnNewButton.gridy = 2;
		panel.add(btnNewButton, gbc_btnNewButton);
		
		JPanel abajo = new JPanel();
		contentPane.add(abajo);
		abajo.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		abajo.add(scrollPane);
		
		JScrollBar scrollBar = new JScrollBar();
		scrollPane.setRowHeaderView(scrollBar);
		
		JList list = new JList();
		scrollPane.setViewportView(list);
	}

}
