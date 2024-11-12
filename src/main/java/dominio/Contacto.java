package dominio;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public abstract class Contacto {
    
    private String nombre;
    private List<Mensaje> mensajes;
    private int codigo;

    // Constructor con parámetro nombre
    public Contacto(String nombre) {
        this.nombre = nombre;
        this.mensajes = new LinkedList<>();
    }

    /**
     * Contructor sin parametros para inicilizar un ContactoIndividual y aplicar métodos set.
     */
    public Contacto() {
        this.mensajes = new LinkedList<>();
    }
    
    // Métodos comunes a todas las subclases
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public List<Mensaje> getMensajes() {	// arraylist implementa list
        return mensajes;
    }

    public void setMensajes(ArrayList<Mensaje> arrayList) { 
        this.mensajes = arrayList;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    // Método común para verificar si el contacto tiene mensajes
    public boolean tieneMensajes() {
        return !mensajes.isEmpty();
    }
}
