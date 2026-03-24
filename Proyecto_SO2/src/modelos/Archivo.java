package modelos;
import estructuras.ListaEnlazada;

public class Archivo {
    private String nombre;
    private int tamañoEnBloques;
    private String dueño;
    private String color;
    private ListaEnlazada<Bloque> bloquesAsignados;

    // Variables para control de concurrencia
    private int lectoresActivos;
    private boolean escribiendo;
    private String escritorActual; // Para saber quién tiene el lock exclusivo
    private ListaEnlazada<String> lectores; // Lista de quiénes están leyendo

    public Archivo(String nombre, int tamañoEnBloques, String dueño, String color) {
        this.nombre = nombre;
        this.tamañoEnBloques = tamañoEnBloques;
        this.dueño = dueño;
        this.color = color;
        this.bloquesAsignados = new ListaEnlazada<>();
        this.lectoresActivos = 0;
        this.escribiendo = false;
        this.escritorActual = null;
        this.lectores = new ListaEnlazada<>();
    }

    // Getters
    public String getNombre() { return nombre; }
    public int getTamaño() { return tamañoEnBloques; }
    public String getDueño() { return dueño; }
    public String getColor() { return color; }
    public ListaEnlazada<Bloque> getBloquesAsignados() { return bloquesAsignados; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    public void setTamaño(int tamaño) { this.tamañoEnBloques = tamaño; }

    // --- MÉTODOS DE CONCURRENCIA ---

    public synchronized boolean adquirirLockLectura(String procesoId) {
        // Si alguien está escribiendo, no podemos leer
        if (escribiendo) {
            return false;
        }
        // Si no hay escritor, podemos leer
        lectoresActivos++;
        lectores.agregarAlFinal(procesoId);
        return true;
    }

    public synchronized void liberarLockLectura(String procesoId) {
        if (lectoresActivos > 0) {
            lectoresActivos--;
            // Eliminar de la lista de lectores
            for (int i = 0; i < lectores.getTamaño(); i++) {
                if (lectores.obtener(i).equals(procesoId)) {
                    lectores.eliminarEnIndice(i);
                    break;
                }
            }
        }
    }

    public synchronized boolean adquirirLockEscritura(String procesoId) {
        // Si hay lectores activos o alguien está escribiendo, no podemos escribir
        if (lectoresActivos > 0 || escribiendo) {
            return false;
        }
        escribiendo = true;
        escritorActual = procesoId;
        return true;
    }

    public synchronized void liberarLockEscritura() {
        escribiendo = false;
        escritorActual = null;
    }

    public boolean isEscribiendo() { return escribiendo; }
    public int getLectoresActivos() { return lectoresActivos; }
    public String getEscritorActual() { return escritorActual; }
    
    public String getEstadoLock() {
        if (escribiendo) {
            return "🔒 EXCLUSIVO (Escritura por " + escritorActual + ")";
        } else if (lectoresActivos > 0) {
            return "🔓 COMPARTIDO (" + lectoresActivos + " lectores)";
        }
        return "✅ LIBRE";
    }
}