package vista;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.border.TitledBorder;
import controlador.Controlador;
import dominio.ContactoIndividual;
import excepciones.ExcepcionCrearGrupo;

import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JScrollPane;
import java.awt.BorderLayout;
import javax.swing.JButton;
import javax.swing.UIManager;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.FlowLayout;

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
		
		izqDer = new JButton("<----");
		flechas.add(izqDer);
		izqDer.addActionListener(ev -> {			
			miembrosGrupo.removeElement(listaMiembrosGrupo.getSelectedValue());
			listaMiembrosGrupo.setModel(miembrosGrupo);
		});
		
		verticalGlue = Box.createVerticalGlue();
		verticalGlue.setMaximumSize(new Dimension(0, 20));
		flechas.add(verticalGlue);
		
		derIzq = new JButton("---->");
		flechas.add(derIzq);
		derIzq.addActionListener(ev -> {			
			miembrosGrupo.addElement(listaContactos.getSelectedValue());
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
		
		buttom = new JPanel();
		buttom.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.add(buttom, BorderLayout.SOUTH);
		buttom.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		actualizarListaContactos();
		
		btnCrearGrupo = new JButton("Crear grupo");
		btnCrearGrupo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				
				if(miembrosGrupo.isEmpty() || miembrosGrupo.size() < 2) {
					MensajeAdvertencia.mostrarError("Seleccione un número válido de participantes", principal);
				}else {
					
					try {
						if(controlador.crearGrupo(nombreGrupo.getText(), miembrosGrupo)) {
							MensajeAdvertencia.mostrarConfirmacion("El grupo se ha creado correctamente", principal);
							dispose();
						}
					} catch (ExcepcionCrearGrupo e1) {
						e1.printStackTrace();
						MensajeAdvertencia.mostrarError(e1.getMessage(), principal);
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
	

}
