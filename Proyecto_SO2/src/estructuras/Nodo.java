package estructuras;

public class Nodo<T> {
    private T dato;           // El cargamento (puede ser un Archivo, un Proceso, etc.)
    private Nodo<T> siguiente; // El gancho hacia el próximo vagón

    public Nodo(T dato) {
        this.dato = dato;
        this.siguiente = null; // Al nacer, no está enganchado a nada
    }

    // Getters y Setters
    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo<T> getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo<T> siguiente) {
        this.siguiente = siguiente;
    }
}