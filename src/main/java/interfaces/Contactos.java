package interfaces;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.border.TitledBorder;

import dominio.Contacto;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.SystemColor;

public class Contactos extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Contactos frame = new Contactos();
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
	public Contactos() {
		setBackground(SystemColor.window);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 665, 422);
		contentPane = new JPanel();
		contentPane.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.setForeground(new Color(0, 0, 0));
		contentPane.setBorder(new TitledBorder(new LineBorder(new Color(25, 25, 112), 2), "Lista contactos", TitledBorder.LEADING, TitledBorder.TOP, null, null));

		setContentPane(contentPane);
		contentPane.setLayout(new BoxLayout(contentPane, BoxLayout.X_AXIS));
		
		JPanel izq = new JPanel();
		izq.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.add(izq);
		izq.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane = new JScrollPane();
		izq.add(scrollPane, BorderLayout.CENTER);
		
		// Para probar Jlist
				DefaultListModel<Contacto> modelo = new DefaultListModel<>();
				modelo.addElement(new Contacto("Jose", "López", 123));
				modelo.addElement(new Contacto("Ana", "Jover", 321));
				modelo.addElement(new Contacto("María", "Sánchez", 456));
				JList<Contacto> listaContactos = new JList<Contacto>(modelo);
				listaContactos.setCellRenderer(new ContactoCellRenderer());
				/////////////////////////////////////////////////////////////
		
		scrollPane.setViewportView(listaContactos);
				
		JPanel abajoIzq = new JPanel();
		abajoIzq.setBackground(UIManager.getColor("List.dropCellBackground"));
		izq.add(abajoIzq, BorderLayout.SOUTH);
		
		JButton AñadirContacto = new JButton("Añadir contacto");
		abajoIzq.add(AñadirContacto);
		
		JPanel centro = new JPanel();
		centro.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.add(centro);
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		
		JButton IzqDer = new JButton("--->");
		centro.add(IzqDer);
		
		Component glue = Box.createGlue();
		glue.setMaximumSize(new Dimension(20, 20));
		centro.add(glue);
		
		JButton button = new JButton("<---");
		centro.add(button);
		
		JPanel der = new JPanel();
		der.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.add(der);
		der.setLayout(new BorderLayout(0, 0));
		
		JPanel abajoDer = new JPanel();
		abajoDer.setBackground(UIManager.getColor("List.dropCellBackground"));
		der.add(abajoDer, BorderLayout.SOUTH);
		
		JButton btnAadirGrupo = new JButton("Añadir grupo");
		abajoDer.add(btnAadirGrupo);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		der.add(scrollPane_1, BorderLayout.CENTER);
		
		// Para probar Jlist
		DefaultListModel<Contacto> modelo1 = new DefaultListModel<>();
		modelo1.addElement(new Contacto("Jose", "López", 123));
		modelo1.addElement(new Contacto("Ana", "Jover", 321));
		modelo1.addElement(new Contacto("María", "Sánchez", 456));
		JList<Contacto> listaContactos1 = new JList<Contacto>(modelo1);
		listaContactos1.setCellRenderer(new ContactoCellRenderer());
		/////////////////////////////////////////////////////////////
		
		scrollPane_1.setViewportView(listaContactos1);
		
	}

}
