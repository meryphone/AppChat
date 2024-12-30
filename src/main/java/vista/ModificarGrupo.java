package vista;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.SystemColor;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.border.LineBorder;
import javax.swing.border.TitledBorder;

import controlador.Controlador;
import dominio.ContactoIndividual;
import dominio.Grupo;

public class ModificarGrupo extends JFrame {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private Controlador controlador = Controlador.getInstance();
    private Principal principal;
    private String grupoAmodificar;

    private JPanel center;
    private JPanel izq;
    private JPanel flechas;
    private JPanel der;
    private JPanel buttom;

    private JList<ContactoIndividual> listaContactos = new JList<>();
    private JList<ContactoIndividual> listaContactosGrupo = new JList<>();
    private JPanel relleno;
    private Component verticalGlue;
    private Component rigidArea;
    private Component horizontalGlue;
    private Component horizontalGlue_1;
    private Component horizontalGlue_2;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        // Inicio de la aplicación ModificarGrupoRegistro para depuración
        EventQueue.invokeLater(() -> {
            try {
                ModificarGrupo frame = new ModificarGrupo(null, "Grupo de ejemplo");
                frame.setVisible(true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Create the frame.
     */
    public ModificarGrupo(Principal principal, String grupoAmodificar) {
        this.principal = principal;
        this.grupoAmodificar = grupoAmodificar;

        setBackground(SystemColor.window);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setBounds(100, 100, 716, 499);
        contentPane = new JPanel();
        contentPane.setBackground(UIManager.getColor("List.dropCellBackground"));
        contentPane.setBorder(new TitledBorder(new LineBorder(new Color(25, 25, 112), 2), "Modificar Grupo", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(51, 51, 51)));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));

        // Center Panel
        center = new JPanel();
        center.setBackground(UIManager.getColor("List.dropCellBackground"));
        contentPane.add(center, BorderLayout.CENTER);
        center.setLayout(new BoxLayout(center, BoxLayout.X_AXIS));

        // Panel izquierdo
        izq = new JPanel();
        izq.setPreferredSize(new Dimension(250, 400));
        izq.setLayout(new BorderLayout(0, 0));
        center.add(izq);

        JScrollPane scrollPaneIzq = new JScrollPane();
        scrollPaneIzq.setPreferredSize(new Dimension(250,400));
        scrollPaneIzq.setPreferredSize(new Dimension(250,400));
        scrollPaneIzq.setPreferredSize(new Dimension(250,400));

        izq.add(scrollPaneIzq, BorderLayout.CENTER);
        listaContactos.setCellRenderer(new ContactoIndividualCellRenderer());
        scrollPaneIzq.setViewportView(listaContactos);

        // Panel de las flechas
        flechas = new JPanel();
        flechas.setBackground(UIManager.getColor("List.dropCellBackground"));
        flechas.setLayout(new BoxLayout(flechas, BoxLayout.Y_AXIS));
        center.add(flechas);

        JButton izqDer = new JButton("--->");
        flechas.add(izqDer);
        izqDer.addActionListener(ev -> {
            DefaultListModel<ContactoIndividual> modeloGrupo = (DefaultListModel<ContactoIndividual>) listaContactosGrupo.getModel();
            // Obtener el modelo de listaContactosGrupoRegistro para depuración
            modeloGrupo.addElement(listaContactos.getSelectedValue());
            listaContactosGrupo.setModel(modeloGrupo);
        });
        
        verticalGlue = Box.createVerticalGlue();
        verticalGlue.setMaximumSize(new Dimension(0, 10));
        flechas.add(verticalGlue);

        JButton derIzq = new JButton("<---");
        flechas.add(derIzq);
        derIzq.addActionListener(ev -> {
            DefaultListModel<ContactoIndividual> modeloGrupo = (DefaultListModel<ContactoIndividual>) listaContactosGrupo.getModel();
            modeloGrupo.removeElement(listaContactosGrupo.getSelectedValue());
            listaContactosGrupo.setModel(modeloGrupo);
        });

        // Right Panel
        der = new JPanel();
        der.setPreferredSize(new Dimension(250, 400));
        der.setLayout(new BorderLayout(0, 0));
        center.add(der);

        JScrollPane scrollPaneDer = new JScrollPane();
        scrollPaneDer.setPreferredSize(new Dimension(250,400));
        scrollPaneDer.setPreferredSize(new Dimension(250,400));
        scrollPaneDer.setPreferredSize(new Dimension(250,400));
        der.add(scrollPaneDer, BorderLayout.CENTER);
        listaContactosGrupo.setCellRenderer(new ContactoIndividualCellRenderer());
        scrollPaneDer.setViewportView(listaContactosGrupo);

        // Bottom Panel
        buttom = new JPanel();
        buttom.setBackground(UIManager.getColor("List.dropCellBackground"));
        buttom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        contentPane.add(buttom, BorderLayout.SOUTH);
        
        horizontalGlue_2 = Box.createHorizontalGlue();
        horizontalGlue_2.setPreferredSize(new Dimension(60, 0));
        buttom.add(horizontalGlue_2);

        JButton confirmar = new JButton("Confirmar cambios");
        buttom.add(confirmar);
        confirmar.addActionListener(ev -> {
            DefaultListModel<ContactoIndividual> modeloGrupo = (DefaultListModel<ContactoIndividual>) listaContactosGrupo.getModel();
            if (controlador.modificarGrupo(grupoAmodificar, modeloGrupo)) {
                // Confirmación de modificación del grupoRegistro para depuración
                MensajeAdvertencia.mostrarConfirmacion("Se ha modificado el grupo correctamente", principal);
                dispose();
            } else {
                MensajeAdvertencia.mostrarError("No se ha podido modificar el grupo", principal);
            }
        });
        
        rigidArea = Box.createRigidArea(new Dimension(20, 20));
        rigidArea.setPreferredSize(new Dimension(200, 20));
        buttom.add(rigidArea);
        
        horizontalGlue_1 = Box.createHorizontalGlue();
        horizontalGlue_1.setPreferredSize(new Dimension(70, 0));
        buttom.add(horizontalGlue_1);

        JButton cancelar = new JButton("Cancelar");
        buttom.add(cancelar);
        
        horizontalGlue = Box.createHorizontalGlue();
        horizontalGlue.setPreferredSize(new Dimension(90, 0));
        buttom.add(horizontalGlue);
        cancelar.addActionListener(ev -> dispose());

        // Initial Data Loading
        actualizarListaContactos();
        listaContactosGrupo.setModel(actulizarListaMiembrosGrupo());
        
        relleno = new JPanel();
        relleno.setBackground(UIManager.getColor("List.dropCellBackground"));
        contentPane.add(relleno, BorderLayout.NORTH);
    }

    private void actualizarListaContactos() {
        List<ContactoIndividual> contactos = controlador.obtenerContactos();
        // Obtener la lista de contactos del controladorRegistro para depuración
        DefaultListModel<ContactoIndividual> modelo = new DefaultListModel<>();
        for (ContactoIndividual contacto : contactos) {
            modelo.addElement(contacto);
        }
        listaContactos.setModel(modelo);
    }

    private DefaultListModel<ContactoIndividual> actulizarListaMiembrosGrupo() {
        Grupo grupo = controlador.getGrupoPorNombre(grupoAmodificar);
        List<ContactoIndividual> contactos = grupo.getMiembros();
        // Obtener los miembros actuales del grupoRegistro para depuración
        DefaultListModel<ContactoIndividual> modelo = new DefaultListModel<>();
        for (ContactoIndividual contacto : contactos) {
            modelo.addElement(contacto);
        }
        return modelo;
    }
}
