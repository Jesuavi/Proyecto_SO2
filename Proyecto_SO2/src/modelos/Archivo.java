package modelos;
import estructuras.ListaEnlazada; // Importamos la herramienta que hicimos antes

public class Archivo {
    private String nombre;
    private int tamañoEnBloques; // Cuántas cajas necesita
    private String dueño;        // Quién lo creó (Admin o un Usuario normal)
    private String color;        // El color que lo representará en la pantalla
    
    // ¡Ojo aquí! Usamos nuestra propia Lista Enlazada para guardar los bloques
    private ListaEnlazada<Bloque> bloquesAsignados; 

    // Variables para el control de Concurrencia (Sprint 3)
    private int lectoresActivos;
    private boolean escribiendo;

    public Archivo(String nombre, int tamañoEnBloques, String dueño, String color) {
        this.nombre = nombre;
        this.tamañoEnBloques = tamañoEnBloques;
        this.dueño = dueño;
        this.color = color;
        this.bloquesAsignados = new ListaEnlazada<>(); // Iniciamos nuestro tren vacío
        this.lectoresActivos = 0;
        this.escribiendo = false;
    }

    // Getters básicos (Añade los demás setters y getters según necesites)
    public String getNombre() { return nombre; }
    public int getTamaño() { return tamañoEnBloques; }
    public String getDueño() { return dueño; }
    public ListaEnlazada<Bloque> getBloquesAsignados() { return bloquesAsignados; }
}