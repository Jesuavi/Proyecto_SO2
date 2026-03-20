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
    
    // --- MÉTODOS DE CONCURRENCIA (SPRINT 3) ---

    // 1. Un proceso pide permiso para LEER (Lock Compartido)
    public boolean adquirirLockLectura() {
        // Si alguien está escribiendo (modificando), no podemos leer. ¡Nos bloqueamos!
        if (escribiendo == true) {
            return false; // Permiso denegado
        }
        // Si nadie está escribiendo, ¡somos bienvenidos! (Incluso si hay otros leyendo)
        lectoresActivos++;
        return true; // Permiso concedido
    }

    // 2. El proceso termina de leer y suelta el archivo
    public void liberarLockLectura() {
        if (lectoresActivos > 0) {
            lectoresActivos--;
        }
    }

    // 3. Un proceso pide permiso para ESCRIBIR/MODIFICAR (Lock Exclusivo)
    public boolean adquirirLockEscritura() {
        // Si hay gente leyendo o alguien más está escribiendo, no podemos tocarlo. ¡Nos bloqueamos!
        if (lectoresActivos > 0 || escribiendo == true) {
            return false; // Permiso denegado
        }
        // Si está completamente libre, le ponemos el candado rojo
        escribiendo = true;
        return true; // Permiso concedido
    }

    // 4. El proceso termina de modificar y quita el candado rojo
    public void liberarLockEscritura() {
        escribiendo = false;
    }
    
    // Métodos extra para saber el estado actual del archivo
    public boolean isEscribiendo() { return escribiendo; }
    public int getLectoresActivos() { return lectoresActivos; }
}