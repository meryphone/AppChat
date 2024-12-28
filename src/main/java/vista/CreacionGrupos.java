package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.border.TitledBorder;
import controlador.Controlador;
import dominio.ContactoIndividual;
import excepciones.ExcepcionCrearGrupo;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.UIManager;
import java.awt.Component;

import javax.imageio.ImageIO;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;

public class CreacionGrupos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controlador controlador = Controlador.getInstance();
	private Principal principal;
	private JPanel center;
	private JPanel top;
	private JLabel lblNombreDelGrupo;
	private JTextField nombreGrupo;
	private JPanel izq;
	private JPanel flechas;
	private JPanel der;
	private JPanel buttom;
	private JScrollPane scrollPane;
	private JList<ContactoIndividual> listaContactos = new JList<>();
	private JScrollPane scrollPane_1;
	private JList<ContactoIndividual> listaMiembrosGrupo = new JList<>() ;
	private JButton btnCrearGrupo;
	private JButton izqDer;
	private JButton derIzq;
	private Component verticalGlue;
	private JButton btnCancelar;
	private Component rigidArea;
	private JLabel lblFotoDePerfil;
	private JLabel icono;
	private Component horizontalGlue_3;
	private Component horizontalGlue;
	private String pathImagen;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					CreacionGrupos frame = new CreacionGrupos(null);
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
	public CreacionGrupos(Principal principal) {
		this.principal = principal;
		setBackground(SystemColor.window);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 716, 499);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new TitledBorder(new LineBorder(new Color(25, 25, 112), 2), "Creaci\u00F3n de grupos", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		center = new JPanel();
		center.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.add(center, BorderLayout.CENTER);
		center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));
		
		izq = new JPanel();
		center.add(izq);
		izq.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		izq.add(scrollPane, BorderLayout.CENTER);
		
		scrollPane.setViewportView(listaContactos);
		
		flechas = new JPanel();
		flechas.setBackground(UIManager.getColor("TabbedPane.contentAreaColor"));
		center.add(flechas);
		flechas.setLayout(new BoxLayout(flechas, BoxLayout.Y_AXIS));
		
		// Lista que almacene los contactos seleccionados para el grupo
		DefaultListModel<ContactoIndividual> miembrosGrupo = new DefaultListModel<>();
		listaContactos.setCellRenderer(new ContactoIndividualCellRenderer());
		listaMiembrosGrupo.setCellRenderer(new ContactoIndividualCellRenderer());
		
		derIzq = new JButton("---->");
		flechas.add(derIzq);
		derIzq.addActionListener(ev -> {			
			miembrosGrupo.addElement(listaContactos.getSelectedValue());
			listaMiembrosGrupo.setModel(miembrosGrupo);
		});
		
		verticalGlue = Box.createVerticalGlue();
		verticalGlue.setPreferredSize(new Dimension(0, 10));
		verticalGlue.setMaximumSize(new Dimension(0, 20));
		flechas.add(verticalGlue);
		
		izqDer = new JButton("<----");
		flechas.add(izqDer);
		izqDer.addActionListener(ev -> {			
			miembrosGrupo.removeElement(listaMiembrosGrupo.getSelectedValue());
			listaMiembrosGrupo.setModel(miembrosGrupo);
		});
		
		der = new JPanel();
		center.add(der);
		der.setLayout(new BorderLayout(0, 0));
		
		scrollPane_1 = new JScrollPane();
		der.add(scrollPane_1, BorderLayout.CENTER);
		
		scrollPane_1.setViewportView(listaMiembrosGrupo);
		
		top = new JPanel();
		top.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.add(top, BorderLayout.NORTH);
		
		lblNombreDelGrupo = new JLabel("Nombre del Grupo: ");
		top.add(lblNombreDelGrupo);
		
		nombreGrupo = new JTextField();
		top.add(nombreGrupo);
		nombreGrupo.setColumns(10);
		
		horizontalGlue_3 = Box.createHorizontalGlue();
		horizontalGlue_3.setPreferredSize(new Dimension(200, 0));
		top.add(horizontalGlue_3);
		
		lblFotoDePerfil = new JLabel("Foto de perfil: ");
		top.add(lblFotoDePerfil);
		
		icono = new JLabel("");
		icono.setIcon(new ImageIcon(CreacionGrupos.class.getResource("/resources/anadir-foto(1).png")));
		top.add(icono);
		
		horizontalGlue = Box.createHorizontalGlue();
		horizontalGlue.setPreferredSize(new Dimension(50, 0));
		top.add(horizontalGlue);
		
		//MouseListener para cambiar la imagen de perfil al hacer clic
        icono.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
               String pathImagen = cambiarImagenPerfil();
            }
        });
		
		buttom = new JPanel();
		buttom.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.add(buttom, BorderLayout.SOUTH);
		buttom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		actualizarListaContactos();
		
		btnCrearGrupo = new JButton("Crear grupo");
		btnCrearGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(miembrosGrupo.isEmpty() || miembrosGrupo.size() < 2) {
					MensajeAdvertencia.mostrarError("Seleccione un número válido de participantes", contentPane);
				}else {
					
					try {
						if(controlador.crearGrupo(nombreGrupo.getText(),pathImagen, miembrosGrupo)) {
							MensajeAdvertencia.mostrarConfirmacion("El grupo se ha creado correctamente", contentPane);
							dispose();
						}
					} catch (ExcepcionCrearGrupo e1) {
						e1.printStackTrace();
						MensajeAdvertencia.mostrarError(e1.getMessage(), contentPane);
					}
				}
				
			}
		});
		buttom.add(btnCrearGrupo);
		
		  addWindowListener(new WindowAdapter() {
	            @Override
	            public void windowClosed(WindowEvent e) {
	                if (principal != null) {
	                    principal.actualizarListaContactos(); // Actualiza la lista en Principal al cerrar Contactos
	                }
	            }
	        });
		
		rigidArea = Box.createRigidArea(new Dimension(20, 20));
		rigidArea.setPreferredSize(new Dimension(300, 20));
		rigidArea.setMaximumSize(new Dimension(50, 20));
		buttom.add(rigidArea);
		
		btnCancelar = new JButton("Cancelar");
		buttom.add(btnCancelar);
		
	}
	
	private void actualizarListaContactos() {
	    List<ContactoIndividual> contactos = controlador.obtenerContactos();
	    
	    DefaultListModel<ContactoIndividual> modelo = new DefaultListModel<>();
	    for (ContactoIndividual contacto : contactos) {
	        modelo.addElement(contacto);
	    }
	    listaContactos.setModel(modelo);
	}
	
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
	
	private String cambiarImagenPerfil() {
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

	            // Crear un ImageIcon circular desde el path
	            ImageIcon imagenCircular = hacerCircularYRedimensionar(pathImagen,24,24);
	            
	            // Actualizar la imagen en lblUsuario
	            icono.setIcon(imagenCircular);
	            
	            return pathImagen;

	        } catch (Exception e) {
	            e.printStackTrace();
	        }
	        
	    }
		return null;
	}

}
