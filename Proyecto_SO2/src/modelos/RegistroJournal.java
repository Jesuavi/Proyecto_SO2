package modelos;

public class RegistroJournal {
    private static int contadorId = 1;
    
    private final int id;
    private final String tipoOperacion; // Ej: "CREAR", "ELIMINAR"
    private final String nombreArchivo;
    private final int bloquesInvolucrados;
    private String estado;        // "PENDIENTE" o "CONFIRMADA" o "ABORTADA"

    public RegistroJournal(String tipoOperacion, String nombreArchivo, int bloquesInvolucrados) {
        this.id = contadorId++;
        this.tipoOperacion = tipoOperacion;
        this.nombreArchivo = nombreArchivo;
        this.bloquesInvolucrados = bloquesInvolucrados;
        this.estado = "PENDIENTE"; // Todo empieza como pendiente
    }

    // --- GETTERS Y SETTERS ---
    public int getId() { return id; }
    public String getTipoOperacion() { return tipoOperacion; }
    public String getNombreArchivo() { return nombreArchivo; }
    public int getBloquesInvolucrados() { return bloquesInvolucrados; }
    public String getEstado() { return estado; }
    
    public void setEstado(String estado) { this.estado = estado; }
    
    @Override
    public String toString() {
        return "[" + estado + "] " + tipoOperacion + " -> " + nombreArchivo;
    }
}
