package vista;

import java.awt.EventQueue;
import java.awt.Graphics2D;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.Insets;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;

import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Usuario;
import tds.BubbleText;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import javax.swing.JList;
import javax.swing.JOptionPane;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Principal frame = new Principal();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Método para poner un marco de foto circular.
	 * 
	 * @param imagen
	 * @return imagenCircular
	 */

	private Image imagenCircular(Image imagen) {

		BufferedImage imagenCircular = new BufferedImage(72, 72, BufferedImage.TYPE_4BYTE_ABGR);

		Graphics2D graficos = imagenCircular.createGraphics();
		Ellipse2D.Double forma = new Ellipse2D.Double(0, 0, 72, 72);
		graficos.setClip(forma);
		graficos.drawImage(imagen, 0, 0, 72, 72, null);
		graficos.dispose();

		return imagenCircular;

	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 776, 513);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
		contentPane.setPreferredSize(new Dimension(20, 20));
		contentPane.setSize(new Dimension(800, 800));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel arriba = new JPanel();
		arriba.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
		contentPane.add(arriba, BorderLayout.NORTH);
		GridBagLayout gbl_arriba = new GridBagLayout();
		gbl_arriba.columnWidths = new int[] { 164, 0, 0, 111, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
		gbl_arriba.rowHeights = new int[] { 23, 0, 0 };
		gbl_arriba.columnWeights = new double[] { 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
				Double.MIN_VALUE };
		gbl_arriba.rowWeights = new double[] { 0.0, 0.0, Double.MIN_VALUE };
		arriba.setLayout(gbl_arriba);

		JComboBox comboBox = new JComboBox();
		comboBox.setBackground(UIManager.getColor("Button.light"));
		comboBox.setModel(new DefaultComboBoxModel(new String[] { "Contactos", "Teléfono" }));
		GridBagConstraints gbc_comboBox = new GridBagConstraints();
		gbc_comboBox.insets = new Insets(0, 0, 5, 5);
		gbc_comboBox.fill = GridBagConstraints.HORIZONTAL;
		gbc_comboBox.gridx = 0;
		gbc_comboBox.gridy = 0;
		arriba.add(comboBox, gbc_comboBox);

		JButton atras = new JButton("");
		atras.setIcon(new ImageIcon(Principal.class.getResource("/resources/volver(1).png")));
		GridBagConstraints gbc_atras = new GridBagConstraints();
		gbc_atras.insets = new Insets(0, 0, 5, 5);
		gbc_atras.gridx = 1;
		gbc_atras.gridy = 0;
		arriba.add(atras, gbc_atras);

		/*
		 * atras.addActionListener(ev -> { dispose(); Login login = new Login();
		 * login.setVisible(true); });
		 */

		JButton buscar = new JButton("");
		buscar.setIcon(new ImageIcon(Principal.class.getResource("/resources/buscar(1).png")));
		GridBagConstraints gbc_buscar = new GridBagConstraints();
		gbc_buscar.insets = new Insets(0, 0, 5, 5);
		gbc_buscar.gridx = 2;
		gbc_buscar.gridy = 0;
		arriba.add(buscar, gbc_buscar);
		buscar.addActionListener(ev -> {
			Buscar buscador = new Buscar();
			buscador.setVisible(true);
		});

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/resources/Appchatlogominecraft(3).png")));
		GridBagConstraints gbc_lblNewLabel = new GridBagConstraints();
		gbc_lblNewLabel.gridwidth = 2;
		gbc_lblNewLabel.insets = new Insets(0, 0, 5, 5);
		gbc_lblNewLabel.gridx = 3;
		gbc_lblNewLabel.gridy = 0;
		arriba.add(lblNewLabel, gbc_lblNewLabel);

		JButton btnContactos = new JButton("Contactos");
		btnContactos.setIcon(new ImageIcon(Principal.class.getResource("/resources/libreta-de-contactos.png")));
		GridBagConstraints gbc_btnContactos = new GridBagConstraints();
		gbc_btnContactos.gridwidth = 2;
		gbc_btnContactos.insets = new Insets(0, 0, 5, 5);
		gbc_btnContactos.gridx = 5;
		gbc_btnContactos.gridy = 0;
		arriba.add(btnContactos, gbc_btnContactos);
		btnContactos.addActionListener(ev -> {
			Contactos contactos = new Contactos();
			contactos.setVisible(true);
		});

		JButton btnPremium = new JButton("PREMIUM");
		btnPremium.setIcon(new ImageIcon(Principal.class.getResource("/resources/dolar(2)(1).png")));
		GridBagConstraints gbc_btnPremium = new GridBagConstraints();
		gbc_btnPremium.gridwidth = 5;
		gbc_btnPremium.insets = new Insets(0, 0, 5, 5);
		gbc_btnPremium.gridx = 7;
		gbc_btnPremium.gridy = 0;
		arriba.add(btnPremium, gbc_btnPremium);

		JLabel lblUsuario = new JLabel("Usuario");
		lblUsuario.setIcon(new ImageIcon(Principal.class.getResource("/resources/usuario(1).png")));
		GridBagConstraints gbc_lblUsuario = new GridBagConstraints();
		gbc_lblUsuario.gridwidth = 2;
		gbc_lblUsuario.insets = new Insets(0, 0, 5, 0);
		gbc_lblUsuario.gridx = 12;
		gbc_lblUsuario.gridy = 0;
		arriba.add(lblUsuario, gbc_lblUsuario);

		Component verticalGlue = Box.createVerticalGlue();
		GridBagConstraints gbc_verticalGlue = new GridBagConstraints();
		gbc_verticalGlue.insets = new Insets(0, 0, 0, 5);
		gbc_verticalGlue.gridx = 0;
		gbc_verticalGlue.gridy = 1;
		arriba.add(verticalGlue, gbc_verticalGlue);

		JPanel centro = new JPanel();
		contentPane.add(centro, BorderLayout.CENTER);
		centro.setLayout(new BoxLayout(centro, BoxLayout.X_AXIS));

		JPanel izq = new JPanel();
		izq.setBorder(new TitledBorder(new LineBorder(new Color(99, 130, 191), 2), "Mensajes", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(51, 51, 51)));
		izq.setBackground(UIManager.getColor("List.dropCellBackground"));
		centro.add(izq);
		GridBagLayout gbl_izq = new GridBagLayout();
		gbl_izq.columnWidths = new int[] { 0, 0 };
		gbl_izq.rowHeights = new int[] { 0, 0 };
		gbl_izq.columnWeights = new double[] { 1.0, Double.MIN_VALUE };
		gbl_izq.rowWeights = new double[] { 1.0, Double.MIN_VALUE };
		izq.setLayout(gbl_izq);

		JScrollPane scrollPane = new JScrollPane();
		GridBagConstraints gbc_scrollPane = new GridBagConstraints();
		gbc_scrollPane.fill = GridBagConstraints.BOTH;
		gbc_scrollPane.gridx = 0;
		gbc_scrollPane.gridy = 0;
		izq.add(scrollPane, gbc_scrollPane);

		JScrollBar scrollBar = new JScrollBar();
		scrollPane.setRowHeaderView(scrollBar);

		// Para probar Jlist

	/*	DefaultListModel<Contacto> modelo = new DefaultListModel<>();
		modelo.addElement(new Contacto("Jose", "López", 123));
		modelo.addElement(new Contacto("Ana", "Jover", 321));
		modelo.addElement(new Contacto("María", "Sánchez", 456));*/
		/////////////////////////////////////////////////////////////

		//JList<Contacto> listaContactos = new JList<Contacto>(modelo);
		//scrollPane.setViewportView(listaContactos);
		//listaContactos.setCellRenderer(new ContactoCellRenderer());


		JPanel der = new JPanel();
		der.setBorder(new TitledBorder(new LineBorder(new Color(99, 130, 191), 2), "Chat con Blas04",
				TitledBorder.LEADING, TitledBorder.TOP, null, null));
		der.setBackground(UIManager.getColor("Tree.dropCellBackground"));
		der.setMaximumSize(getMaximumSize()); // Para que el panel ocupe todo el espacio disponible.
		centro.add(der);
		der.setLayout(new BoxLayout(der, BoxLayout.Y_AXIS));

		///// Prueba chat
		BubbleText burbuja = new BubbleText(der, "k pasa ermaniko", Color.GREEN, "elBridas", BubbleText.SENT);
		burbuja.setMaximumSize(new Dimension(Integer.MAX_VALUE, burbuja.getPreferredSize().height));
		der.add(burbuja);

		BubbleText burbuja2 = new BubbleText(der, "k kiereh tu", Color.LIGHT_GRAY, "Blas", BubbleText.RECEIVED);
		der.add(burbuja2);
		burbuja2.setMaximumSize(new Dimension(Integer.MAX_VALUE, burbuja2.getPreferredSize().height));
		der.add(burbuja2);

		///////////////////////////

	}

}
