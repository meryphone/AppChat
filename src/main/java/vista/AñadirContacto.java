package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;

import controlador.Controlador;
import excepciones.ExcepcionContacto;

import java.awt.BorderLayout;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Dimension;
import javax.swing.JButton;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class AñadirContacto extends JFrame implements MensajeAdvertencia {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField textTlf;
	private JTextField textNombre;
	private Controlador controlador = Controlador.getInstance();
	private Principal principal;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AñadirContacto frame = new AñadirContacto(null);
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
	public AñadirContacto(Principal principal) {
		this.principal = principal;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 492, 258);
		setResizable(false); // Evita que la ventana se pueda redimensionar
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));

		JPanel abajo = new JPanel();
		abajo.setBorder(new EmptyBorder(26, 0, 0, 0));
		contentPane.add(abajo, BorderLayout.SOUTH);
		FlowLayout fl_abajo = new FlowLayout(FlowLayout.RIGHT, 5, 5);
		fl_abajo.setAlignOnBaseline(true);
		abajo.setLayout(fl_abajo);

		JPopupMenu menuGrupos = new JPopupMenu();

        JMenuItem crearGrupo = new JMenuItem("Crear grupo");
       crearGrupo.addActionListener(ev -> {
    	   new ModificarGrupo(principal);
    	   dispose();
       });
       
        menuGrupos.add(crearGrupo);

        JMenuItem modificarGrupo = new JMenuItem("Modificar grupo");
        modificarGrupo.addActionListener(ev -> {
     	   ModificarGrupo ModificradorGrupo = new ModificarGrupo(principal);
     	   
     	   dispose();
        });
        menuGrupos.add(modificarGrupo);

        JMenuItem eliminarGrupo = new JMenuItem("Eliminar grupo");
        eliminarGrupo.setEnabled(false); 
        menuGrupos.add(eliminarGrupo);

        // Botón para gestionar grupos
        JButton btnGestionGrupos = new JButton("Gestionar grupos");
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

		abajo.add(btnGestionGrupos);

		Component rigidArea_7 = Box.createRigidArea(new Dimension(20, 20));
		abajo.add(rigidArea_7);

		Component rigidArea_6 = Box.createRigidArea(new Dimension(20, 20));
		abajo.add(rigidArea_6);

		Component rigidArea_5 = Box.createRigidArea(new Dimension(20, 20));
		abajo.add(rigidArea_5);

		Component rigidArea_4 = Box.createRigidArea(new Dimension(20, 20));
		abajo.add(rigidArea_4);

		Component rigidArea_3 = Box.createRigidArea(new Dimension(20, 20));
		abajo.add(rigidArea_3);

		Component rigidArea = Box.createRigidArea(new Dimension(20, 20));
		abajo.add(rigidArea);

		JButton aceptar = new JButton("Aceptar");
		aceptar.setHorizontalAlignment(SwingConstants.LEADING);
		abajo.add(aceptar);

		// PREGUNTAR SI ESTÁ BIEN QUE LA VISTA ACCEDA A LAS EXCEPCIONES
		aceptar.addActionListener(ev -> {
			try {
				boolean agregarContacto = controlador.agregarContacto(textTlf.getText(), textNombre.getText());
				if (agregarContacto) {
					mostrarConfirmacion("Contacto añadido correctamente", contentPane);
					ModificarGrupo contactos = new ModificarGrupo(principal);
					contactos.setVisible(true);
					dispose();
				}
			} catch (ExcepcionContacto e) {
				mostrarError(e.getMessage(), contentPane);
				e.printStackTrace();
			}
		});

		JButton cancelar = new JButton("Cancelar");
		cancelar.setHorizontalAlignment(SwingConstants.LEADING);
		abajo.add(cancelar);

		cancelar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				ModificarGrupo contactos = new ModificarGrupo(principal);
				contactos.setVisible(true);
				dispose(); // cierra la ventana actual
			}
		});

		JPanel centro = new JPanel();
		contentPane.add(centro, BorderLayout.CENTER);
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));

		JPanel mensaje_alerta = new JPanel();
		centro.add(mensaje_alerta);
		mensaje_alerta.setLayout(new BoxLayout(mensaje_alerta, BoxLayout.X_AXIS));

		JLabel lblNewLabel_3 = new JLabel("");
		lblNewLabel_3.setIcon(new ImageIcon(AñadirContacto.class.getResource("/resources/alerta24x24.png")));
		mensaje_alerta.add(lblNewLabel_3);

		JLabel lblNewLabel_2 = new JLabel("   Introduzca el nombre del contacto y su teléfono");
		lblNewLabel_2.setFont(new Font("Corbel", Font.BOLD, 14));
		mensaje_alerta.add(lblNewLabel_2);

		Component rigidArea_1 = Box.createRigidArea(new Dimension(20, 20));
		centro.add(rigidArea_1);

		JPanel nombre = new JPanel();
		nombre.setMaximumSize(new Dimension(32767, 0));
		centro.add(nombre);
		nombre.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel = new JLabel("Nombre:");
		lblNewLabel.setPreferredSize(new Dimension(67, 17));
		lblNewLabel.setFont(new Font("Corbel", Font.BOLD, 13));
		nombre.add(lblNewLabel);

		textNombre = new JTextField();
		nombre.add(textNombre);
		textNombre.setColumns(10);

		JPanel teléfono = new JPanel();
		centro.add(teléfono);
		teléfono.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));

		JLabel lblNewLabel_1 = new JLabel("Teléfono:");
		lblNewLabel_1.setFont(new Font("Corbel", Font.BOLD, 13));
		teléfono.add(lblNewLabel_1);

		textTlf = new JTextField();
		teléfono.add(textTlf);
		textTlf.setColumns(10);

		Component rigidArea_2 = Box.createRigidArea(new Dimension(20, 20));
		contentPane.add(rigidArea_2, BorderLayout.NORTH);
	}

	@Override
	public void mostrarError(String mensaje, Component parentComponent) {
		JOptionPane.showMessageDialog(parentComponent, mensaje, "Error", JOptionPane.ERROR_MESSAGE);
	}

	@Override
	public void mostrarConfirmacion(String mensaje, Component parentComponent) {
		JOptionPane.showMessageDialog(parentComponent, mensaje, "Confirmación", JOptionPane.INFORMATION_MESSAGE);
	}
}
