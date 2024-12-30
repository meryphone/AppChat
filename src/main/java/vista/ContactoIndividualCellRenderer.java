package vista;

import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListCellRenderer;
import javax.swing.border.Border;

import dominio.Contacto;
import dominio.ContactoIndividual;
import dominio.Grupo;

import java.awt.*;
import java.io.File;
import java.io.IOException;

@SuppressWarnings("serial")
public class ContactoIndividualCellRenderer extends JPanel implements ListCellRenderer<Contacto> {
    private static final Border SELECCIONADO = BorderFactory.createLineBorder(Color.BLUE, 2);
    private static final Border NO_SELECCIONADO = BorderFactory.createEmptyBorder(2, 2, 2, 2);

    private JLabel lblImagen;
    private JLabel lblNombre;
    private JLabel lblTelefono;
    private JLabel lblSaludo;

    public ContactoIndividualCellRenderer() {
        setLayout(new BorderLayout(10, 10)); // Espaciado entre imagen y texto

        lblImagen = new JLabel();
        lblNombre = new JLabel();
        lblTelefono = new JLabel();
        lblSaludo = new JLabel();

        lblNombre.setFont(new Font("Arial", Font.BOLD, 14));
        lblTelefono.setFont(new Font("Arial", Font.PLAIN, 12));
        lblSaludo.setFont(new Font("Arial", Font.ITALIC, 12));

        JPanel panelTexto = new JPanel(new GridLayout(3, 1)); // Para organizar los textos verticalmente
        panelTexto.setOpaque(false); // Hace transparente el panel para usar el fondo de la celda
        panelTexto.add(lblNombre);
        panelTexto.add(lblTelefono);
        panelTexto.add(lblSaludo);

        add(lblImagen, BorderLayout.WEST);  // Imagen a la izquierda
        add(panelTexto, BorderLayout.CENTER);  // Texto a la derecha
    }

    @Override
    public Component getListCellRendererComponent(JList<? extends Contacto> listacontactos, Contacto contacto, int index,
                                                  boolean isSelected, boolean cellHasFocus) {

        // Configuración de datos comunes
        lblNombre.setText(contacto.getNombre());

        if (contacto instanceof ContactoIndividual) {
            ContactoIndividual ci = (ContactoIndividual) contacto;

            // Configuración de la imagen
            configurarImagen(ci.getUsuario().getPathImagen());

            // Configuración del texto
            lblTelefono.setText("Tel: " + ci.getUsuario().getMovil());
            lblSaludo.setText(ci.getUsuario().getMensajeSaludo().orElse(""));
        } else if (contacto instanceof Grupo) {
            Grupo grupo = (Grupo) contacto;

            // Configuración de la imagen
            configurarImagen(grupo.getImagen());

            // Construir cadena de nombres de miembros
            StringBuilder cadenaMiembros = new StringBuilder();
            for (ContactoIndividual miembro : grupo.getMiembros()) {
                cadenaMiembros.append(miembro.getNombre()).append(", ");
            }
            if (cadenaMiembros.length() > 2) {
                cadenaMiembros.setLength(cadenaMiembros.length() - 2); // Quitar la última coma y espacio
            }

            // Configuración del texto
            lblTelefono.setText(cadenaMiembros.toString());
            lblSaludo.setText(""); // No hay saludo para grupos
        }

        // Configuración de selección
        if (isSelected) {
            setBorder(SELECCIONADO);
            setBackground(listacontactos.getSelectionBackground());
            setForeground(listacontactos.getSelectionForeground());
        } else {
            setBorder(NO_SELECCIONADO);
            setBackground(Color.WHITE); // Fondo blanco
            setForeground(listacontactos.getForeground());
        }

        // Asegurarse de que todos los componentes internos tengan el mismo fondo
        lblImagen.setOpaque(true);
        lblImagen.setBackground(getBackground());
        lblNombre.setOpaque(true);
        lblNombre.setBackground(getBackground());
        lblTelefono.setOpaque(true);
        lblTelefono.setBackground(getBackground());
        lblSaludo.setOpaque(true);
        lblSaludo.setBackground(getBackground());

        setOpaque(true); // Fondo visible
        return this;
    }

    // Método auxiliar para configurar imágenes
    private void configurarImagen(String rutaImagen) {
        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            try {
                Image imagenOriginal = ImageIO.read(new File(rutaImagen));
                int anchoDeseado = 50;
                int altoDeseado = 50;
                Image imagenEscalada = imagenOriginal.getScaledInstance(anchoDeseado, altoDeseado, Image.SCALE_SMOOTH);
                lblImagen.setIcon(new ImageIcon(imagenEscalada));
            } catch (IOException e) {
                System.err.println("Error al cargar la imagen: " + rutaImagen);
                lblImagen.setIcon(null); // Imagen no disponible
            }
        } else {
            lblImagen.setIcon(null); // Sin imagen
        }
    }    
}
