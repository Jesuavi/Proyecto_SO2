package estructuras;

// Nuestro "Tren" personalizado, capaz de llevar cualquier tipo de cargamento <T>
public class ListaEnlazada<T> {
    
    private Nodo<T> cabeza; // El primer elemento (la locomotora)
    private int tamaño;     // Llevamos la cuenta para no tener que contar los vagones uno por uno

    // Al construir la lista, nace vacía
    public ListaEnlazada() {
        this.cabeza = null;
        this.tamaño = 0;
    }

    // --- HERRAMIENTAS BÁSICAS ---
    
    public boolean estaVacia() {
        return cabeza == null;
    }

    public int getTamaño() {
        return tamaño;
    }

    // Método para agregar un vagón al final (Vital para la asignación encadenada)
    public void agregarAlFinal(T dato) {
        Nodo<T> nuevoNodo = new Nodo<>(dato);
        if (estaVacia()) {
            cabeza = nuevoNodo; // Si no hay tren, este vagón es la locomotora
        } else {
            Nodo<T> actual = cabeza;
            // Caminamos hasta el último vagón
            while (actual.getSiguiente() != null) {
                actual = actual.getSiguiente();
            }
            // Enganchamos el nuevo vagón al final
            actual.setSiguiente(nuevoNodo);
        }
        tamaño++; // El tren creció
    }

    // Método para saber qué pasajero/cargamento está en un vagón específico (índice)
    public T obtener(int indice) {
        if (indice < 0 || indice >= tamaño) {
            return null; // Índice inválido, no existe ese vagón
        }
        Nodo<T> actual = cabeza;
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        return actual.getDato(); // Devolvemos lo que hay adentro
    }

    // --- LAS HERRAMIENTAS NUEVAS DEL SPRINT 4 ---

    // Método para reemplazar el cargamento de un vagón (Útil para ordenar usando Burbuja)
    public void setDatoEnIndice(int indice, T nuevoDato) {
        if (indice < 0 || indice >= tamaño) {
            return; // Índice inválido, no hacemos nada
        }
        
        Nodo<T> actual = cabeza;
        // Caminamos hasta el vagón indicado
        for (int i = 0; i < indice; i++) {
            actual = actual.getSiguiente();
        }
        // Le cambiamos el dato a ese vagón
        actual.setDato(nuevoDato);
    }

    // Método para desenganchar (eliminar) un vagón sabiendo su posición
    public void eliminarEnIndice(int indice) {
        // Validamos que el índice exista y que el tren no esté vacío
        if (indice < 0 || indice >= tamaño || estaVacia()) {
            return; 
        }

        // Caso 1: Queremos eliminar la locomotora (el primer vagón)
        if (indice == 0) {
            cabeza = cabeza.getSiguiente(); // El segundo vagón ahora es la locomotora
        } 
        // Caso 2: Queremos eliminar un vagón del medio o del final
        else {
            Nodo<T> actual = cabeza;
            
            // Caminamos hasta el vagón JUSTO ANTES del que queremos eliminar
            for (int i = 0; i < indice - 1; i++) {
                actual = actual.getSiguiente();
            }
            
            // El vagón que queremos eliminar es el que le sigue al 'actual'
            Nodo<T> vagonAEliminar = actual.getSiguiente();
            
            // Desenganchamos el vagón: conectamos el actual con el que le sigue al eliminado
            actual.setSiguiente(vagonAEliminar.getSiguiente());
        }
        
        tamaño--; // Como sacamos un vagón, el tren es más pequeño
    }
    // Para poder recorrer el tren desde afuera
    public Nodo<T> getCabeza() { return cabeza; }
}