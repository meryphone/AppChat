package vista;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.UIManager;

import dominio.Grupo;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;

public class SeleccionarGrupo extends JDialog {

    private static final long serialVersionUID = 1L;
    private final JPanel contentPanel = new JPanel();
    private JButton cancelButton;
    private JButton okButton;
    private JComboBox<String> comboBox;
    private String grupoSeleccionado;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        try {
            SeleccionarGrupo dialog = new SeleccionarGrupo(null, List.of("Grupo 1", "Grupo 2", "Grupo 3"));
            dialog.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
            dialog.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Create the dialog.
     */
    public SeleccionarGrupo(java.awt.Frame owner, List<String> nombresGrupos) {
        super(owner, "", true);
        setBounds(100, 100, 413, 205);
        getContentPane().setBackground(UIManager.getColor("List.dropCellBackground"));
        getContentPane().setLayout(new BorderLayout());
        contentPanel.setBackground(UIManager.getColor("List.dropCellBackground"));
        contentPanel.setBorder(null);
        getContentPane().add(contentPanel, BorderLayout.CENTER);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        
        // Panel superior
        JPanel panelTitulo = new JPanel();
        panelTitulo.setBackground(UIManager.getColor("List.dropCellBackground"));
        getContentPane().add(panelTitulo, BorderLayout.NORTH);
        panelTitulo.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        JLabel lblSeleccioneElGrupo = new JLabel("Seleccione el grupo a modificar");
        lblSeleccioneElGrupo.setFont(new Font("Dialog", Font.BOLD, 14));
        panelTitulo.add(lblSeleccioneElGrupo);

        // Panel del comboBox
        JPanel panelComboBox = new JPanel();
        panelComboBox.setBackground(UIManager.getColor("List.dropCellBackground"));
        contentPanel.add(panelComboBox);
        comboBox = new JComboBox<>(nombresGrupos.toArray(new String[0]));
        comboBox.setPreferredSize(new Dimension(250, 26));
        panelComboBox.add(comboBox);

        // Panel inferior (botones y decoración)
        JPanel buttonPane = new JPanel();
        buttonPane.setBackground(UIManager.getColor("List.dropCellBackground"));
        getContentPane().add(buttonPane, BorderLayout.SOUTH);
        buttonPane.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

        okButton = new JButton("Aceptar");
        okButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                grupoSeleccionado = (String) comboBox.getSelectedItem(); // Guardar selección
                dispose(); // Cerrar el diálogo
            }
        });
        okButton.setActionCommand("OK");
        buttonPane.add(okButton);

        Component horizontalGlue1 = Box.createHorizontalGlue();
        horizontalGlue1.setPreferredSize(new Dimension(80, 0));
        buttonPane.add(horizontalGlue1);

        JLabel labelIcono = new JLabel("");
        labelIcono.setIcon(new ImageIcon(SeleccionarGrupo.class.getResource("/resources/discusionPequeño.png")));
        buttonPane.add(labelIcono);

        Component horizontalGlue2 = Box.createHorizontalGlue();
        horizontalGlue2.setPreferredSize(new Dimension(80, 0));
        buttonPane.add(horizontalGlue2);

        cancelButton = new JButton("Cancelar");
        cancelButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                grupoSeleccionado = null; // No se selecciona nada
                dispose(); // Cerrar el diálogo
            }
        });
        cancelButton.setActionCommand("Cancel");
        buttonPane.add(cancelButton);
    }

    /**
     * Obtiene el nombre del grupo seleccionado.
     * 
     * @return Nombre del grupo seleccionado o null si no se seleccionó ninguno.
     */
    public String getGrupoSeleccionado() {
        return grupoSeleccionado;
    }

    /**
     * Método para mostrar el diálogo y obtener el grupo seleccionado.
     * 
     * @param owner        La ventana principal (puede ser null).
     * @param nombresGrupos Lista de nombres de grupos para mostrar en el ComboBox.
     * @return El nombre del grupo seleccionado o null si se cancela.
     */
    public static String mostrarDialogo(java.awt.Frame owner, List<String> nombresGrupos) {
        SeleccionarGrupo dialog = new SeleccionarGrupo(owner, nombresGrupos);
        dialog.setVisible(true);
        return dialog.getGrupoSeleccionado();
    }
}
