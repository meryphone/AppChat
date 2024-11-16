package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.BoxLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import java.awt.FlowLayout;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.ImageIcon;

public class AlertaNoExisteContacto extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					AlertaNoExisteContacto frame = new AlertaNoExisteContacto();
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
	public AlertaNoExisteContacto() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 398, 201);
	    setResizable(false);             // Evita que la ventana se pueda redimensionar
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(contentPane);
		contentPane.setLayout(new BorderLayout(0, 0));
		
		JPanel abajo = new JPanel();
		FlowLayout flowLayout = (FlowLayout) abajo.getLayout();
		flowLayout.setAlignment(FlowLayout.RIGHT);
		contentPane.add(abajo, BorderLayout.SOUTH);
		
		JButton btnNewButton = new JButton("Aceptar");
		abajo.add(btnNewButton);
		
		JPanel centro = new JPanel();
		contentPane.add(centro, BorderLayout.CENTER);
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		
		Component verticalStrut_2 = Box.createVerticalStrut(20);
		centro.add(verticalStrut_2);
		
		Component verticalStrut_1 = Box.createVerticalStrut(20);
		centro.add(verticalStrut_1);
		
		Component verticalStrut = Box.createVerticalStrut(20);
		centro.add(verticalStrut);
		
		JPanel panel = new JPanel();
		centro.add(panel);
		
		JLabel lblNewLabel_1 = new JLabel("");
		lblNewLabel_1.setIcon(new ImageIcon(AlertaNoExisteContacto.class.getResource("/resources/alerta24x24.png")));
		panel.add(lblNewLabel_1);
		
		JLabel lblNewLabel = new JLabel("El teléfono indicado no existe");
		panel.add(lblNewLabel);
	}

}
