package modelos;
import estructuras.ListaEnlazada;

public class DiscoVirtual {
    private ListaEnlazada<Bloque> bloques;
    private int capacidadTotal;
    private int bloquesLibres; // Llevamos la cuenta para no recorrer todo buscando espacio

    // Constructor: Le decimos de qué tamaño es el disco al crearlo
    public DiscoVirtual(int capacidadTotal) {
        this.capacidadTotal = capacidadTotal;
        this.bloquesLibres = capacidadTotal;
        this.bloques = new ListaEnlazada<>();
        
        // Fabricamos las cajas y las metemos en el disco
        for (int i = 0; i < capacidadTotal; i++) {
            bloques.agregarAlFinal(new Bloque(i));
        }
    }

    // Método vital: ¿Hay espacio suficiente para guardar un archivo nuevo?
    public boolean hayEspacioSuficiente(int bloquesNecesitados) {
        return bloquesLibres >= bloquesNecesitados;
    }

    // Getters
    public int getBloquesLibres() { return bloquesLibres; }
    public ListaEnlazada<Bloque> getBloques() { return bloques; }
    
    // Cuando ocupemos un bloque, restaremos a esta variable (lo haremos en el Sprint 3)
    public void restarBloquesLibres(int cantidad) {
        this.bloquesLibres -= cantidad;
    }
}