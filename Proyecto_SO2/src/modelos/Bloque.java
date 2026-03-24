package modelos;

public class Bloque {
    private int id;              // El número de la caja (ej. Bloque 0, Bloque 1...)
    private boolean ocupado;     // ¿Hay algo adentro?
    private String color;        // Para pintarlo en la Interfaz Gráfica
    
    // Al nacer, una caja está vacía y sin color
    public Bloque(int id) {
        this.id = id;
        this.ocupado = false;
        this.color = "#FFFFFF"; // Código de color blanco (vacío)
    }

    // --- HERRAMIENTAS (Getters y Setters) ---
    public int getId() { return id; }
    
    public boolean isOcupado() { return ocupado; }
    public void setOcupado(boolean ocupado) { this.ocupado = ocupado; }
    
    public String getColor() { return color; }
    public void setColor(String color) { this.color = color; }
}