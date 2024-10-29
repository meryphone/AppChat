package interfaces;

import java.awt.EventQueue;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class Login extends JFrame {
    
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
                	
                    Login frame = new Login();
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
    public Login() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 450, 300);
        // Crear el panel principal y establecer el color de fondo
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.setBackground(UIManager.getColor("List.dropCellBackground"));  // Color de fondo para toda la ventana
        contentPane.setOpaque(true);  // Asegurarse de que sea opaco para que el color se vea
        
        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(0, 0));
        
        JPanel sur = new JPanel();
        sur.setBorder(new EmptyBorder(5, 5, 5, 5));
        contentPane.add(sur, BorderLayout.SOUTH);
        sur.setLayout(new BoxLayout(sur, BoxLayout.X_AXIS));
        
        // Color de fondo del panel "sur"
        sur.setBackground(UIManager.getColor("List.dropCellBackground")); // Color específico para este panel
        sur.setOpaque(true);  // Aseguramos que el panel sea opaco
        
        JButton btnNewButton = new JButton("Cancelar");
        btnNewButton.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 11));
        sur.add(btnNewButton);
        
        btnNewButton.addActionListener(ev -> { 
        	dispose();
        });
        
        Component glue = Box.createGlue();
        glue.setBackground(UIManager.getColor("List.dropCellBackground"));
        sur.add(glue);
        
        JButton btnNewButton_1 = new JButton("Login");
        btnNewButton_1.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 11));
        sur.add(btnNewButton_1);
        
        btnNewButton_1.addActionListener(ev -> {
        	// Si el usuario está registrado se abrirá la ventana principal
        });
        
        Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
        sur.add(rigidArea);
        
        JButton btnNewButton_2 = new JButton("Registrarse");
        btnNewButton_2.setFont(new Font("DejaVu Sans Mono", Font.BOLD, 11));
        sur.add(btnNewButton_2);
        btnNewButton_2.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				dispose();
				DatosUsuario datos = new DatosUsuario();
				datos.setVisible(true);				
			}
        	
        });
        
        JPanel center = new JPanel();
        center.setForeground(new Color(0, 0, 0));
        center.setBorder(new TitledBorder(new LineBorder(new Color(25, 25, 112), 2), "Login", TitledBorder.LEADING, TitledBorder.TOP, null, new Color(0, 0, 0)));
        contentPane.add(center, BorderLayout.CENTER);
        center.setLayout(new BoxLayout(center, BoxLayout.Y_AXIS));

        // Color de fondo del panel "center"
        center.setBackground(UIManager.getColor("List.dropCellBackground"));  // Aquí aplicamos el color de fondo
        center.setOpaque(true);  // Aseguramos que el panel sea opaco
        
        Component verticalGlue = Box.createVerticalGlue();
        center.add(verticalGlue);
        
        JPanel icono_1 = new JPanel();
        icono_1.setMaximumSize(new Dimension(32767, 0));
        icono_1.setBackground(UIManager.getColor("List.dropCellBackground"));
        center.add(icono_1);
        
        JLabel icono = new JLabel("");
        icono_1.add(icono);
        icono.setIcon(new ImageIcon(Login.class.getResource("/resources/Appchatlogominecraft.png")));
        
        JPanel Telefono = new JPanel();
        Telefono.setBackground(UIManager.getColor("List.dropCellBackground"));
        Telefono.setMaximumSize(new Dimension(32767, 0));
        center.add(Telefono);
        
        JLabel lblTelefono = new JLabel("Teléfono:");
        lblTelefono.setForeground(new Color(0, 0, 0));
        lblTelefono.setFont(new Font("Courier", Font.BOLD, 16));
        lblTelefono.setBackground(new Color(255, 248, 220));
        lblTelefono.setPreferredSize(new Dimension(112, 14));
        lblTelefono.setHorizontalAlignment(SwingConstants.RIGHT);
        lblTelefono.setMinimumSize(new Dimension(100, 14));
        Telefono.add(lblTelefono);
        
        textField = new JTextField();
        Telefono.add(textField);
        textField.setColumns(10);
        
        JPanel Contraseña = new JPanel();
        Contraseña.setBackground(UIManager.getColor("List.dropCellBackground"));
        Contraseña.setMaximumSize(new Dimension(32767, 0));
        center.add(Contraseña);
        
        JLabel lblContrasea = new JLabel("Contraseña:");
        lblContrasea.setForeground(new Color(0, 0, 0));
        lblContrasea.setFont(new Font("Courier", Font.BOLD, 16));
        lblContrasea.setBackground(new Color(255, 248, 220));
        lblContrasea.setPreferredSize(new Dimension(112, 14));
        lblContrasea.setHorizontalAlignment(SwingConstants.RIGHT);
        Contraseña.add(lblContrasea);
        
        textField_1 = new JTextField();
        textField_1.setMinimumSize(new Dimension(20, 20));
        textField_1.setPreferredSize(new Dimension(20, 20));
        Contraseña.add(textField_1);
        textField_1.setColumns(10);
        
        Component verticalGlue_1 = Box.createVerticalGlue();
        center.add(verticalGlue_1);
    }
}
