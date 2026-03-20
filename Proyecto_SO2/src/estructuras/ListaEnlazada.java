package estructuras;

public class ListaEnlazada<T> {
    private Nodo<T> cabeza; // El primer elemento (la locomotora)
    private int tamaño;     // Llevamos la cuenta para no tener que contar uno por uno

    public ListaEnlazada() {
        this.cabeza = null;
        this.tamaño = 0;
    }

    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamaño() {
        return tamaño;
    }

    // Método para agregar al final (Vital para la asignación encadenada)
    public void agregarAlFinal(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevoNodo;
        } else {
            Nodo<T> actual = cabeza;
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            actual.setSiguiente(nuevoNodo);
        }
        tamaño++;
    }

    // Método para obtener un elemento por su posición (índice)
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) {
            return null; // Índice inválido
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato();
    }
    
    // Método para limpiar la lista (Útil al eliminar archivos)
    public void vaciar() {
        cabeza = null;
        tamaño = 0;
    }
}