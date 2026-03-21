package modelos;

public class Proceso {
    private static int contadorGlobal = 1; // Para que cada proceso tenga un número único automáticamente
    
    private int id;
    private String tipoOperacion; // Puede ser: "CREAR", "LEER", "ACTUALIZAR", "ELIMINAR"
    private Archivo archivoObjetivo; // El archivo con el que vamos a trabajar
    private int bloquesRequeridos;   // Cuánto espacio necesita (solo útil al CREAR)
    private String estado;           // "NUEVO", "LISTO", "EJECUTANDO", "BLOQUEADO", "TERMINADO"
    private int posicion; // El bloque del disco al que quiere ir este proceso
    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }

    // Constructor: Así nace un ticket de pedido
    public Proceso(String tipoOperacion, Archivo archivoObjetivo, int bloquesRequeridos) {
        this.id = contadorGlobal++; // Le asignamos un ID y sumamos 1 para el siguiente
        this.tipoOperacion = tipoOperacion;
        this.archivoObjetivo = archivoObjetivo;
        this.bloquesRequeridos = bloquesRequeridos;
        this.estado = "NUEVO"; // Todo proceso nace en estado NUEVO
    }

    // --- HERRAMIENTAS (Getters y Setters) ---
    public int getId() { return id; }
    
    public String getTipoOperacion() { return tipoOperacion; }
    
    public Archivo getArchivoObjetivo() { return archivoObjetivo; }
    
    public int getBloquesRequeridos() { return bloquesRequeridos; }
    
    public String getEstado() { return estado; }
    
    // Este método es vital, lo usaremos para mover el ticket de un estado a otro
    public void setEstado(String nuevoEstado) { 
        this.estado = nuevoEstado; 
    }
    
    // Para imprimir la información fácilmente en la interfaz más adelante
    @Override
    public String toString() {
        return "Proceso " + id + " [" + tipoOperacion + " - " + estado + "]";
    }
}