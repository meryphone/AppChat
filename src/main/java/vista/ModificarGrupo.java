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
import dominio.Grupo;
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
import java.util.List;

public class ModificarGrupo extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private Controlador controlador = Controlador.getInstance();
	private JList<ContactoIndividual> listaContactos = new JList<>();
	private JList<ContactoIndividual> listaContactosGrupo = new JList<>();
	private Principal principal;
	private String grupoAmodificar;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ModificarGrupo frame = new ModificarGrupo(null, null);
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
	public ModificarGrupo(Principal principal, String grupoAmodificar) {
		this.principal = principal;
		this.grupoAmodificar = grupoAmodificar;
		setBackground(SystemColor.window);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 716, 499);
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
		
		listaContactos = new JList<>();
	    listaContactos.setCellRenderer(new ContactoIndividualCellRenderer());
	    scrollPane.setViewportView(listaContactos);
				
		JPanel abajoIzq = new JPanel();
		abajoIzq.setBackground(UIManager.getColor("List.dropCellBackground"));
		izq.add(abajoIzq, BorderLayout.SOUTH);
	    
		JPanel centro = new JPanel();
		centro.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.add(centro);
		centro.setLayout(new BoxLayout(centro, BoxLayout.Y_AXIS));
		
		DefaultListModel<ContactoIndividual> miembrosGrupo = actulizarListaMiembrosGrupo();
		listaContactosGrupo.setModel(miembrosGrupo);
		
		actualizarListaContactos();
		
		JButton IzqDer = new JButton("--->");
		centro.add(IzqDer);
		IzqDer.addActionListener(ev -> {
			miembrosGrupo.addElement(listaContactos.getSelectedValue());
			listaContactosGrupo.setModel(miembrosGrupo);
		});
		
		Component glue = Box.createGlue();
		glue.setMaximumSize(new Dimension(20, 20));
		centro.add(glue);
		
		JButton DerIzq = new JButton("<---");
		centro.add(DerIzq);
		DerIzq.addActionListener(ev -> {
			miembrosGrupo.removeElement(listaContactosGrupo.getSelectedValue());
			listaContactosGrupo.setModel(miembrosGrupo);
		});
		
		JPanel der = new JPanel();
		der.setBackground(UIManager.getColor("List.dropCellBackground"));
		contentPane.add(der);
		der.setLayout(new BorderLayout(0, 0));
		
		JPanel abajoDer = new JPanel();
		abajoDer.setBackground(UIManager.getColor("List.dropCellBackground"));
		der.add(abajoDer, BorderLayout.SOUTH);
		
		JButton cancelar = new JButton("Cancelar\n");
		abajoDer.add(cancelar);
		cancelar.addActionListener(ev -> {
			dispose();
		});
		
		JButton confirmar = new JButton("Confirmar cambios");
		abajoIzq.add(confirmar);
		confirmar.addActionListener(ev -> {
				
			if(controlador.modificarGrupo(grupoAmodificar, miembrosGrupo)) {
				MensajeAdvertencia.mostrarConfirmacion("Se ha modificado el grupo correctamente", principal);
			}else{
				MensajeAdvertencia.mostrarError("No se ha podido modificar el grupo", principal);
			}
			
		});
		
		JScrollPane scrollPane_1 = new JScrollPane();
		der.add(scrollPane_1, BorderLayout.CENTER);
		
		JPanel grupos = new JPanel();
		scrollPane_1.setViewportView(grupos);
		grupos.setLayout(new BorderLayout(0, 0));
		
		JScrollPane scrollPane_2 = new JScrollPane();
		grupos.add(scrollPane_2);
		
		listaContactosGrupo.setCellRenderer(new ContactoIndividualCellRenderer());
		scrollPane_2.setViewportView(listaContactosGrupo);
		
	}
	
	private void actualizarListaContactos() {
	    List<ContactoIndividual> contactos = controlador.obtenerContactos();
	    
	    DefaultListModel<ContactoIndividual> modelo = new DefaultListModel<>();
	    for (ContactoIndividual contacto : contactos) {
	        modelo.addElement(contacto);
	    }
	    listaContactos.setModel(modelo);
	}
	
	private DefaultListModel<ContactoIndividual> actulizarListaMiembrosGrupo() {
		 Grupo grupo = controlador.getGrupoPorNombre(grupoAmodificar);
		 List<ContactoIndividual> contactos = grupo.getMiembros();
		 
		 DefaultListModel<ContactoIndividual> modelo = new DefaultListModel<>();
		    for (ContactoIndividual contacto : contactos) {
		        modelo.addElement(contacto);
		    }		    
		    return modelo;
	}

}
