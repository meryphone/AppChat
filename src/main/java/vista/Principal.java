package vista;

import java.awt.EventQueue;
import java.awt.Graphics2D;
import dominio.ContactoIndividual;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.GridBagConstraints;
import java.awt.Dimension;
import javax.swing.border.BevelBorder;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.JButton;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;

import controlador.Controlador;
import dominio.Contacto;
import tds.BubbleText;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JLabel;
import javax.swing.JList;

import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;
import javax.swing.JScrollPane;
import javax.swing.JScrollBar;
import java.awt.FlowLayout;
import javax.swing.JTextField;
import javax.swing.ListModel;
import javax.swing.SwingConstants;

public class Principal extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
    private JLabel lblUsuario;  
    private JTextField textField;
    private JList<Contacto> listaContactos;
    private Controlador controlador = Controlador.getInstance();
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            try {
                Principal frame = new Principal();
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
	}

	/**
	 * Create the frame.
	 */
	public Principal() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 839, 512);
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
		arriba.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		//AQUI CREO QUE VAMOS A TENER QUE UTILIZAR  SEGURAMENTE UN ITEMLISTENER
		JComboBox<Object> comboBox = new JComboBox<Object>();
		comboBox.setMaximumRowCount(20);
		comboBox.setBackground(UIManager.getColor("Button.light"));
		comboBox.setModel(new DefaultComboBoxModel<Object>(new String[] { "Contactos", "Teléfono" }));
		arriba.add(comboBox);

		JButton enviar = new JButton("");
		enviar.setIcon(new ImageIcon(Principal.class.getResource("/resources/enviar(2)(3).png")));
		arriba.add(enviar);

		JButton buscar = new JButton("");
		buscar.setIcon(new ImageIcon(Principal.class.getResource("/resources/buscar(1).png")));
		arriba.add(buscar);
		buscar.addActionListener(ev -> {
			Buscar buscador = new Buscar();
			buscador.setVisible(true);
		});

		JLabel lblNewLabel = new JLabel("");
		lblNewLabel.setIcon(new ImageIcon(Principal.class.getResource("/resources/Appchatlogominecraft(3).png")));
		arriba.add(lblNewLabel);

		JButton btnContactos = new JButton("Agregar contacto");
		btnContactos.setIcon(new ImageIcon(Principal.class.getResource("/resources/libreta-de-contactos.png")));
		arriba.add(btnContactos);
		btnContactos.addActionListener(ev -> {
			AñadirContacto contactos = new AñadirContacto(this);
			contactos.setVisible(true);
			actualizarListaContactos();

		});

		JButton btnPremium = new JButton("PREMIUM");
		btnPremium.setIcon(new ImageIcon(Principal.class.getResource("/resources/dolar(2)(1).png")));
		arriba.add(btnPremium);
		
		ImageIcon imagenPerfil = hacerCircularYRedimensionar(controlador.getImagenUsuario(),24,24);
		 lblUsuario = new JLabel(controlador.getNombreUsuario()); 
		    lblUsuario.setIcon(new ImageIcon(Principal.class.getResource("/resources/usuario(1).png")));
		    lblUsuario.setIconTextGap(10); 
		    lblUsuario.setHorizontalTextPosition(SwingConstants.RIGHT); 
		    lblUsuario.setVerticalTextPosition(SwingConstants.CENTER); 
		    arriba.add(lblUsuario); 
		
		//MouseListener para cambiar la imagen de perfil al hacer clic
        lblUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cambiarImagenPerfil();
            }
        });
        
		Component verticalGlue = Box.createVerticalGlue();
		arriba.add(verticalGlue);
		
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

		listaContactos = new JList<Contacto>();
		listaContactos.setCellRenderer(new ContactoIndividualCellRenderer());
		scrollPane.setViewportView(listaContactos);

		   
		actualizarListaContactos();


		JPanel der = new JPanel();
		der.setMinimumSize(new Dimension(200, 200));
		der.setBorder(new TitledBorder(new LineBorder(new Color(99, 130, 191), 2), "Chat", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));
		
		der.setBackground(UIManager.getColor("Tree.dropCellBackground"));
		der.setMaximumSize(getMaximumSize()); 
		centro.add(der);
		der.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBackground(new Color(255, 255, 255));
		der.add(scrollPane_1, BorderLayout.CENTER);
		
		JPanel chat = new JPanel();
		scrollPane_1.setViewportView(chat);
		chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS)); // Para apilar las burbujas verticalmente
		chat.setBackground(UIManager.getColor("Tree.dropCellBackground")); 
		
		JPanel enviarMensaje = new JPanel(new BorderLayout()); 
		der.add(enviarMensaje, BorderLayout.SOUTH);

		//Para crear el menú de emoticonos
		JPopupMenu menuEmoticonos = new JPopupMenu();
		for (int i = 0; i < 8; i++) { 
		    final int index = i; 
		    
		    JLabel emoticonoLabel = new JLabel(BubbleText.getEmoji(index)); 
		    menuEmoticonos.add(emoticonoLabel);

		    emoticonoLabel.addMouseListener(new MouseAdapter() {
		        @Override
		        public void mouseClicked(MouseEvent e) {
		        	
		            BubbleText burbujaEmoticono = new BubbleText(chat, index, Color.GREEN, controlador.getNombreUsuario(), BubbleText.SENT,18); 
		            chat.add(burbujaEmoticono);
		            chat.revalidate();
		            chat.repaint();
		            menuEmoticonos.setVisible(false); 
		        }
		    });
		}

		
		JButton btnEmoticono = new JButton("");
		btnEmoticono.setIcon(new ImageIcon(Principal.class.getResource("/resources/contento(1).png")));
		enviarMensaje.add(btnEmoticono, BorderLayout.WEST); 

		// Mostrar el menú desplegable al hacer clic en el botón de emoticono
		btnEmoticono.addActionListener(e -> menuEmoticonos.show(btnEmoticono, btnEmoticono.getWidth() / 2, btnEmoticono.getHeight() / 2));
		enviarMensaje.add(btnEmoticono, BorderLayout.WEST);
		
		textField = new JTextField();
		enviarMensaje.add(textField, BorderLayout.CENTER); 

		JButton btnEnviar = new JButton("");
		btnEnviar.setIcon(new ImageIcon(Principal.class.getResource("/resources/enviar-mensaje(1).png")));
		enviarMensaje.add(btnEnviar, BorderLayout.EAST); 
		
		//Para enviar mensaje en un bubbleText
		btnEnviar.addActionListener(ev -> {
		    String mensajeTexto = textField.getText();
		    		    
		    if (!mensajeTexto.isEmpty()) {
		    	
		        BubbleText burbuja = new BubbleText(chat, mensajeTexto, Color.GREEN, controlador.getNombreUsuario(), BubbleText.SENT);
		        chat.add(burbuja);

		        //Para que se actualice la interfaz
		        chat.revalidate();
		        chat.repaint();

		        JScrollBar verticalScrollBar = scrollPane_1.getVerticalScrollBar();
		        verticalScrollBar.setValue(verticalScrollBar.getMaximum());

		        //Para limpiar el campo de texto después de enviar
		        textField.setText("");
		    }
		});

	}
	
	/**
	 * Método para poner un marco de foto circular.
	 * 
	 * @param imagen
	 * @return imagenCircular
	 */

	private ImageIcon hacerCircularYRedimensionar(String pathImagen, int ancho, int alto) {
	    // Validar el path
	    if (pathImagen == null || pathImagen.isEmpty()) {
	        throw new IllegalArgumentException("El path de la imagen no puede ser nulo o vacío.");
	    }

	    // Cargar la imagen desde el path
	    ImageIcon iconoOriginal = new ImageIcon(pathImagen);
	    if (iconoOriginal.getImage() == null) {
	        throw new IllegalArgumentException("No se pudo cargar la imagen desde el path especificado: " + pathImagen);
	    }

	    // Escalar la imagen al tamaño especificado
	    Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

	    // Crear un BufferedImage circular
	    BufferedImage imagenCircular = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
	    Graphics2D graficos = imagenCircular.createGraphics();

	    // Dibujar la forma circular
	    Ellipse2D.Double forma = new Ellipse2D.Double(0, 0, ancho, alto);
	    graficos.setClip(forma);
	    graficos.drawImage(imagenEscalada, 0, 0, ancho, alto, null);

	    // Aplicar antialiasing para bordes suaves
	    graficos.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

	    graficos.dispose();

	    // Devolver el resultado como ImageIcon
	    return new ImageIcon(imagenCircular);
	}


	
	/**
	 * Método para cambiar la foto de perfil.
	 * 
	 * @return void
	 */
	private void cambiarImagenPerfil() {
	    JFileChooser fileChooser = new JFileChooser();
	    fileChooser.setDialogTitle("Selecciona una nueva imagen de perfil");

	    int resultado = fileChooser.showOpenDialog(this);
	    if (resultado == JFileChooser.APPROVE_OPTION) {
	        File archivoSeleccionado = fileChooser.getSelectedFile();
	        try {
	            // Validar que el archivo seleccionado es una imagen
	            BufferedImage imagenOriginal = ImageIO.read(archivoSeleccionado);
	            if (imagenOriginal == null) {
	                throw new Exception("El archivo seleccionado no es una imagen válida.");
	            }

	            // Obtener el path del archivo seleccionado
	            String pathImagen = archivoSeleccionado.getAbsolutePath();
	            
	            // Estabezco la nueva foto de perfil al usuario
	            controlador.setImagenPerfilUsuario(pathImagen);
	            
	            // Crear un ImageIcon circular desde el path
	            ImageIcon imagenCircular = hacerCircularYRedimensionar(pathImagen,24,24);

	            // Actualizar la imagen en lblUsuario
	            lblUsuario.setIcon(imagenCircular);

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	    }
	}

	
	
	
	/**
	 * Método para actualizar la lista de contactos
	 * 
	 * @return void
	 */
	
	public void actualizarListaContactos() {
	    // Obtener la lista de contactos desde el controlador
	    List<Contacto> contactos = controlador.obtenerContactosYgrupos();
	    
	    // Crear un modelo para el JList y añadir los contactos
	    DefaultListModel<Contacto> modelo = new DefaultListModel<Contacto>();
	    for (Contacto contacto : contactos) {
	        modelo.addElement(contacto);
	    }
	    listaContactos.setModel(modelo);
	}
	
}
