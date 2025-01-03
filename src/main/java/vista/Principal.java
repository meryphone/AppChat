package vista;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;
import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import controlador.Controlador;
import dominio.Contacto;
import tds.BubbleText;

/**
 * Clase Principal representa la ventana principal de la aplicación.
 * Permite gestionar contactos, grupos y funcionalidades premium, además de enviar mensajes.
 */
public class Principal extends JFrame {

    private static final long serialVersionUID = 1L;

    // Componentes principales de la ventana
    private JPanel contentPane;
    private JLabel lblUsuario; 
    private JList<Contacto> listaContactos; 
    private Controlador controlador = Controlador.getInstance(); 
    private double precioPremium = -1; 
    private Principal principal = this; 
    private JTextField textField; 

    /**
     * Método principal para ejecutar la ventana.
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
     * Constructor de la clase.
     * Configura la ventana principal y sus componentes.
     */
    public Principal() {
    	
        // Configuración de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 934, 512);

        // Configuración del panel principal
        contentPane = new JPanel();
        contentPane.setBackground(UIManager.getColor("CheckBoxMenuItem.acceleratorForeground"));
        contentPane.setPreferredSize(new Dimension(20, 20));
        contentPane.setSize(new Dimension(800, 800));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Configuración del panel superior con botones y opciones de usuario
        configurarPanelSuperior();

        // Configuración del área central (contactos y chat)
        configurarPanelCentral();
    }

    /**
     * Configura el panel superior con botones y opciones de usuario.
     */
    private void configurarPanelSuperior() {
        JPanel arriba = new JPanel();
        arriba.setBorder(new BevelBorder(BevelBorder.RAISED, null, null, null, null));
        contentPane.add(arriba, BorderLayout.NORTH);
        arriba.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        // Botón para gestionar grupos
        JButton btnGestionGrupos = new JButton("Gestionar Grupos");
        btnGestionGrupos.setIcon(new ImageIcon(Principal.class.getResource("/resources/citizen_8382930(1).png")));
        arriba.add(btnGestionGrupos);

        // Menú contextual para gestionar grupos
        JPopupMenu menuGrupos = new JPopupMenu();
        JMenuItem crearGrupo = new JMenuItem("Crear grupo");
        crearGrupo.addActionListener(ev -> {
            CreacionGrupos creador = new CreacionGrupos(principal);
            creador.setVisible(true);
        });
        menuGrupos.add(crearGrupo);

        JMenuItem modificarGrupo = new JMenuItem("Modificar grupo");
        modificarGrupo.addActionListener(ev -> {
            String grupoSeleccionado = SeleccionarGrupo.mostrarDialogo(null, controlador.obtenerNombresGruposUsuario());
            if (grupoSeleccionado != null) {
                ModificarGrupo modificador = new ModificarGrupo(principal, grupoSeleccionado);
                modificador.setVisible(true);
                actualizarListaContactos();
            }
        });
        menuGrupos.add(modificarGrupo);

        JMenuItem eliminarGrupo = new JMenuItem("Eliminar grupo");
        eliminarGrupo.setEnabled(false);
        menuGrupos.add(eliminarGrupo);

        btnGestionGrupos.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menuGrupos.show(e.getComponent(), e.getX(), e.getY());
                }
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (e.isPopupTrigger()) {
                    menuGrupos.show(e.getComponent(), e.getX(), e.getY());
                }
            }
        });

        // Botón para exportar contactos en formato PDF
        JButton exportar = new JButton("");
        exportar.setIcon(new ImageIcon(Principal.class.getResource("/resources/document_9890148(1).png")));
        arriba.add(exportar);
        exportar.addActionListener(ev -> {
            if (controlador.isUsuarioPremium()) {
                String[] contactoYrutaSeleccionado = SeleccionarContacto.mostrarDialogo(principal, controlador.obtenerNombresContactos());
                if (controlador.exportarPDF(contactoYrutaSeleccionado[1], contactoYrutaSeleccionado[0])) {
                    MensajeAdvertencia.mostrarConfirmacion("Se ha exportado correctamente el documento", principal);
                } else {
                    MensajeAdvertencia.mostrarError("No se ha podido exportar el documento", principal);
                }
            } else {
                MensajeAdvertencia.mostrarError("Esta funcionalidad solo está disponible para usuarios premium", principal);
            }
        });

        // Botón para buscar contactos
        JButton buscar = new JButton("");
        buscar.setIcon(new ImageIcon(Principal.class.getResource("/resources/buscar(1).png")));
        arriba.add(buscar);
        buscar.addActionListener(ev -> {
            Buscar buscador = new Buscar();
            buscador.setVisible(true);
        });

        // Botón para agregar contactos
        JButton btnContactos = new JButton("Agregar contacto");
        btnContactos.setIcon(new ImageIcon(Principal.class.getResource("/resources/libreta-de-contactos.png")));
        arriba.add(btnContactos);
        btnContactos.addActionListener(ev -> {
            AñadirContacto contactos = new AñadirContacto(this);
            contactos.setVisible(true);
            actualizarListaContactos();
        });

        // Botón para gestionar la versión premium
        JButton btnPremium = new JButton("PREMIUM");
        btnPremium.setIcon(new ImageIcon(Principal.class.getResource("/resources/dolar(2)(1).png")));
        arriba.add(btnPremium);
        configurarBotonPremium(btnPremium);

        // Etiqueta del usuario con imagen de perfil
        ImageIcon imagenPerfil = hacerCircularYRedimensionar(controlador.getImagenUsuario(), 24, 24);
        lblUsuario = new JLabel(controlador.getNombreUsuario());
        lblUsuario.setIcon(imagenPerfil);
        lblUsuario.setIconTextGap(10);
        lblUsuario.setHorizontalTextPosition(SwingConstants.RIGHT);
        lblUsuario.setVerticalTextPosition(SwingConstants.CENTER);
        arriba.add(lblUsuario);
        lblUsuario.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                cambiarImagenPerfil();
            }
        });
    }

    /**
     * Configura el botón de la versión premium con acciones específicas.
     */
    private void configurarBotonPremium(JButton btnPremium) {
        btnPremium.addActionListener(ev -> {
            if (controlador.isUsuarioPremium()) {
                MensajeAdvertencia.mostrarConfirmacion("El usuario ya ha obtenido la versión premium.", contentPane);
            } else {
                precioPremium = controlador.setPremium();
                String precioAtexto = String.format("%.2f", precioPremium);
                if (precioPremium > 0) {
                    MensajeAdvertencia.mostrarConfirmacion(
                            "Enhorabuena, has obtenido la versión premium con un precio de: " + precioAtexto,
                            contentPane);
                    btnPremium.setIcon(new ImageIcon(Principal.class.getResource("/resources/cheque(1).png")));
                } else {
                    MensajeAdvertencia.mostrarError("No se ha podido obtener la versión premium", contentPane);
                }
            }
        });

        if (controlador.isUsuarioPremium()) {
            btnPremium.setIcon(new ImageIcon(Principal.class.getResource("/resources/cheque(1).png")));
        }
    }

    /**
     * Configura el panel central que incluye la lista de contactos y el área de chat.
     */
    private void configurarPanelCentral() {
        JPanel centro = new JPanel();
        contentPane.add(centro, BorderLayout.CENTER);
        centro.setLayout(new BorderLayout(0, 0));

        // Panel izquierdo con la lista de contactos
        JPanel izq = new JPanel();
        Dimension panelSize = new Dimension(250, 0);
        izq.setPreferredSize(panelSize);
        izq.setBorder(new TitledBorder(new LineBorder(new Color(99, 130, 191), 2), "Mensajes", TitledBorder.LEADING,
                TitledBorder.TOP, null, new Color(51, 51, 51)));
        izq.setBackground(UIManager.getColor("List.dropCellBackground"));
        centro.add(izq, BorderLayout.WEST);
        izq.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane = new JScrollPane();
        izq.add(scrollPane, BorderLayout.CENTER);

        listaContactos = new JList<>();
        listaContactos.setCellRenderer(new ContactoCellRenderer());
        scrollPane.setViewportView(listaContactos);

        actualizarListaContactos();

        // Panel derecho con el área de chat
        JPanel der = new JPanel();
        der.setPreferredSize(panelSize);
        der.setBorder(new TitledBorder(new LineBorder(new Color(99, 130, 191), 2), "Chat", TitledBorder.LEADING,
                TitledBorder.TOP, null, new Color(51, 51, 51)));
        der.setBackground(UIManager.getColor("Tree.dropCellBackground"));
        centro.add(der, BorderLayout.CENTER);
        der.setLayout(new BorderLayout(0, 0));

        JScrollPane scrollPane_1 = new JScrollPane();
        der.add(scrollPane_1, BorderLayout.CENTER);

        JPanel chat = new JPanel();
        scrollPane_1.setViewportView(chat);
        chat.setLayout(new BoxLayout(chat, BoxLayout.Y_AXIS));
        chat.setBackground(UIManager.getColor("Tree.dropCellBackground"));

        configurarPanelEnviarMensaje(chat, der);
    }

    /**
     * Configura el panel para enviar mensajes en el área de chat.
     * 
     * @param chat Panel de chat.
     * @param der  Panel derecho que contiene el chat.
     */
    private void configurarPanelEnviarMensaje(JPanel chat, JPanel der) {
        JPanel enviarMensaje = new JPanel(new BorderLayout());
        der.add(enviarMensaje, BorderLayout.SOUTH);

        JPopupMenu menuEmoticonos = new JPopupMenu();
        for (int i = 0; i < 8; i++) {
            final int index = i;
            JLabel emoticonoLabel = new JLabel(BubbleText.getEmoji(index));
            menuEmoticonos.add(emoticonoLabel);

            emoticonoLabel.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    BubbleText burbujaEmoticono = new BubbleText(chat, index, Color.GREEN,
                            controlador.getNombreUsuario(), BubbleText.SENT, 18);
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

        btnEmoticono.addActionListener(e -> menuEmoticonos.show(btnEmoticono, btnEmoticono.getWidth() / 2,
                btnEmoticono.getHeight() / 2));

        textField = new JTextField();
        enviarMensaje.add(textField, BorderLayout.CENTER);

        JButton btnEnviar = new JButton("");
        btnEnviar.setIcon(new ImageIcon(Principal.class.getResource("/resources/enviar-mensaje(1).png")));
        enviarMensaje.add(btnEnviar, BorderLayout.EAST);

        btnEnviar.addActionListener(ev -> {
            String mensajeTexto = textField.getText();

            if (!mensajeTexto.isEmpty()) {
                BubbleText burbuja = new BubbleText(chat, mensajeTexto, Color.GREEN,
                        controlador.getNombreUsuario(), BubbleText.SENT);
                chat.add(burbuja);

                chat.revalidate();
                chat.repaint();

                JScrollBar verticalScrollBar = ((JScrollPane) chat.getParent().getParent()).getVerticalScrollBar();
                verticalScrollBar.setValue(verticalScrollBar.getMaximum());

                textField.setText("");
            }
        });
    }

    /**
     * Redimensiona y convierte una imagen en circular.
     * 
     * @param pathImagen Ruta de la imagen.
     * @param ancho      Ancho deseado.
     * @param alto       Alto deseado.
     * @return Icono de imagen circular.
     */
    private ImageIcon hacerCircularYRedimensionar(String pathImagen, int ancho, int alto) {
        if (pathImagen == null || pathImagen.isEmpty()) {
            throw new IllegalArgumentException("El path de la imagen no puede ser nulo o vacío.");
        }

        ImageIcon iconoOriginal = new ImageIcon(pathImagen);
        if (iconoOriginal.getImage() == null) {
            throw new IllegalArgumentException("No se pudo cargar la imagen desde el path especificado: " + pathImagen);
        }

        Image imagenEscalada = iconoOriginal.getImage().getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

        BufferedImage imagenCircular = new BufferedImage(ancho, alto, BufferedImage.TYPE_INT_ARGB);
        Graphics2D graficos = imagenCircular.createGraphics();

        Ellipse2D.Double forma = new Ellipse2D.Double(0, 0, ancho, alto);
        graficos.setClip(forma);
        graficos.drawImage(imagenEscalada, 0, 0, ancho, alto, null);

        graficos.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        graficos.dispose();

        return new ImageIcon(imagenCircular);
    }

    /**
     * Cambia la imagen de perfil del usuario.
     */
    private void cambiarImagenPerfil() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Selecciona una nueva imagen de perfil");

        int resultado = fileChooser.showOpenDialog(this);
        if (resultado == JFileChooser.APPROVE_OPTION) {
            File archivoSeleccionado = fileChooser.getSelectedFile();
            try {
                BufferedImage imagenOriginal = ImageIO.read(archivoSeleccionado);
                if (imagenOriginal == null) {
                    throw new Exception("El archivo seleccionado no es una imagen válida.");
                }

                String pathImagen = archivoSeleccionado.getAbsolutePath();

                controlador.setImagenPerfilUsuario(pathImagen);

                ImageIcon imagenCircular = hacerCircularYRedimensionar(pathImagen, 24, 24);

                lblUsuario.setIcon(imagenCircular);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Actualiza la lista de contactos mostrada en la ventana.
     */
    public void actualizarListaContactos() {
        List<Contacto> contactos = controlador.obtenerContactosYgrupos();

        DefaultListModel<Contacto> modelo = new DefaultListModel<>();
        for (Contacto contacto : contactos) {
            modelo.addElement(contacto);
        }
        listaContactos.setModel(modelo);
    }
}
