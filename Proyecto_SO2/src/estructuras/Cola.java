package estructuras;

public class Cola<T> {
    private Nodo<T> frente; // El primero en la fila (el que será atendido)
    private Nodo<T> finalCola;  // El último en la fila (el recién llegado)
    private int tamaño;

    public Cola() {
        this.frente = null;
        this.finalCola = null;
        this.tamaño = 0;
    }

    public boolean estaVacia() {
        return frente == null;
    }

    public int getTamaño() {
        return tamaño;
    }

    // Método para Encolar (Hacer la fila)
    public synchronized void encolar(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            frente = nuevoNodo;
            finalCola = nuevoNodo;
        } else {
            finalCola.setSiguiente(nuevoNodo); // El último actual apunta al nuevo
            finalCola = nuevoNodo;             // El nuevo se convierte en el último
        }
        tamaño++;
    }

    // Método para Desencolar (Atender al primero y sacarlo de la fila)
    public synchronized T desencolar() {
        if (estaVacia()) {
            return null;
        }
        T datoAtendido = frente.getDato();
        frente = frente.getSiguiente(); // El segundo en la fila ahora es el primero
        
        if (frente == null) {
            finalCola = null; // Si se vació la cola, el final también es nulo
        }
        tamaño--;
        return datoAtendido;
    }
    
    // Ver quién es el primero sin sacarlo de la fila
    public T verFrente() {
        return estaVacia() ? null : frente.getDato();
    }
    // Para poder ver quién es el primero de la fila desde afuera
    public Nodo<T> getFrente() { return frente; }
}