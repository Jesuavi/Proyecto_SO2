package modelos;

public class Proceso {
    private static int contadorGlobal = 1;
    
    private int id;
    private String tipoOperacion; // CREAR, LEER, ACTUALIZAR, ELIMINAR
    private Archivo archivoObjetivo;
    private int bloquesRequeridos;
    private String estado; // NUEVO, LISTO, EJECUTANDO, BLOQUEADO, TERMINADO
    private int posicion;
    private String dueño; // Administrador o Usuario
    private long tiempoCreacion;
    private long tiempoEjecucion;

    public Proceso(String tipoOperacion, Archivo archivoObjetivo, int bloquesRequeridos) {
        this.id = contadorGlobal++;
        this.tipoOperacion = tipoOperacion;
        this.archivoObjetivo = archivoObjetivo;
        this.bloquesRequeridos = bloquesRequeridos;
        this.estado = "NUEVO";
        this.posicion = 0;
        this.dueño = Sesion.usuarioActual;
        this.tiempoCreacion = System.currentTimeMillis();
        this.tiempoEjecucion = 0;
    }

    // Getters y Setters
    public int getId() { return id; }
    public String getTipoOperacion() { return tipoOperacion; }
    public Archivo getArchivoObjetivo() { return archivoObjetivo; }
    public int getBloquesRequeridos() { return bloquesRequeridos; }
    public String getEstado() { return estado; }
    public void setEstado(String nuevoEstado) { this.estado = nuevoEstado; }
    public int getPosicion() { return posicion; }
    public void setPosicion(int posicion) { this.posicion = posicion; }
    public String getDueño() { return dueño; }
    public long getTiempoCreacion() { return tiempoCreacion; }
    
    public void iniciarEjecucion() {
        this.tiempoEjecucion = System.currentTimeMillis();
        this.estado = "EJECUTANDO";
    }
    
    public long getTiempoEjecucionTotal() {
        if (tiempoEjecucion == 0) return 0;
        return System.currentTimeMillis() - tiempoEjecucion;
    }
    
    @Override
    public String toString() {
        return "P" + id + " [" + tipoOperacion + "] " + 
               (archivoObjetivo != null ? archivoObjetivo.getNombre() : "?") + 
               " - " + dueño;
    }
}