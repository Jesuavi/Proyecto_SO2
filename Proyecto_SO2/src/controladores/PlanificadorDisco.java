package controladores; 

import estructuras.ListaEnlazada; 

public class PlanificadorDisco { // OJO: Ya no dice "extends Thread"

    // --- 1. VARIABLES DEL PLANIFICADOR ---
    private String politicaActual = "FIFO";
    private int posicionCabezal = 0;
    
    private ListaEnlazada<Integer> colaDeSolicitudes; 
    private boolean moviendoHaciaArriba = true;        

    // --- CONSTRUCTOR ---
    public PlanificadorDisco() {
        this.colaDeSolicitudes = new ListaEnlazada<>();
    }

    // --- 2. MÉTODOS GET Y SET ---
    public void setPosicionCabezal(int nuevaPosicion) { this.posicionCabezal = nuevaPosicion; }
    public int getPosicionCabezal() { return this.posicionCabezal; }
    public void setPolitica(String nuevaPolitica) { this.politicaActual = nuevaPolitica; }
    
    public void agregarSolicitud(int bloqueDestino) {
        this.colaDeSolicitudes.agregarAlFinal(bloqueDestino);
    }

    // Método vital para que no se acumulen bloques de la simulación anterior
    public void limpiarCola() {
        this.colaDeSolicitudes = new ListaEnlazada<>();
    }

    // --- 3. EL NUEVO MOTOR (PASO A PASO) ---
    // En lugar de hacer todo de golpe, este método resuelve 1 solo bloque cada vez que la tabla se lo pide
    public int procesarSiguiente() {
        if (colaDeSolicitudes.estaVacia()) {
            return posicionCabezal;
        }

        int tamaño = colaDeSolicitudes.getTamaño();
        int siguienteDestino = -1;
        int indiceAEliminar = -1;

        switch (politicaActual) {
            case "FIFO" -> {
                siguienteDestino = colaDeSolicitudes.obtener(0);
                indiceAEliminar = 0;
            }

            case "SSTF" -> {
                int minDistancia = Integer.MAX_VALUE;
                for (int i = 0; i < tamaño; i++) {
                    int destinoCandidato = colaDeSolicitudes.obtener(i);
                    int distancia = Math.abs(destinoCandidato - posicionCabezal);
                    if (distancia < minDistancia) {
                        minDistancia = distancia;
                        siguienteDestino = destinoCandidato;
                        indiceAEliminar = i;
                    }
                }
            }

            case "SCAN" -> {
                int minDistanciaScan = Integer.MAX_VALUE;
                for (int i = 0; i < tamaño; i++) {
                    int pos = colaDeSolicitudes.obtener(i);
                    if (moviendoHaciaArriba && pos >= posicionCabezal) {
                        int dist = pos - posicionCabezal;
                        if (dist < minDistanciaScan) {
                            minDistanciaScan = dist; siguienteDestino = pos; indiceAEliminar = i;
                        }
                    } else if (!moviendoHaciaArriba && pos <= posicionCabezal) {
                        int dist = posicionCabezal - pos;
                        if (dist < minDistanciaScan) {
                            minDistanciaScan = dist; siguienteDestino = pos; indiceAEliminar = i;
                        }
                    }
                }
                
                // Si no hay más bloques en esta dirección, cambia de sentido y vuelve a buscar
                if (siguienteDestino == -1) {
                    moviendoHaciaArriba = !moviendoHaciaArriba;
                    return procesarSiguiente(); 
                }
            }

            case "C-SCAN" -> {
                int minDistanciaCscan = Integer.MAX_VALUE;
                for (int i = 0; i < tamaño; i++) {
                    int pos = colaDeSolicitudes.obtener(i);
                    if (pos >= posicionCabezal) {
                        int dist = pos - posicionCabezal;
                        if (dist < minDistanciaCscan) {
                            minDistanciaCscan = dist; siguienteDestino = pos; indiceAEliminar = i;
                        }
                    }
                }
                // Si llegó al final, vuelve al inicio (0) y busca el más cercano
                if (siguienteDestino == -1) {
                    int minPosicion = Integer.MAX_VALUE;
                    for (int i = 0; i < tamaño; i++) {
                        int pos = colaDeSolicitudes.obtener(i);
                        if (pos < minPosicion) {
                            minPosicion = pos; siguienteDestino = pos; indiceAEliminar = i;
                        }
                    }
                }
            }
        }

        // Actualizar el cabezal y borrar la solicitud atendida
        if (siguienteDestino != -1 && indiceAEliminar != -1) {
            colaDeSolicitudes.eliminarEnIndice(indiceAEliminar);
            posicionCabezal = siguienteDestino;
        }

        return posicionCabezal;
    }
    public estructuras.ListaEnlazada<Integer> getColaSolicitudes() {
    return this.colaDeSolicitudes;
}
    // Método para establecer la dirección
public void setDireccion(boolean haciaArriba) {
    this.moviendoHaciaArriba = haciaArriba;
}

// Método para obtener la dirección actual
public boolean isMoviendoHaciaArriba() {
    return moviendoHaciaArriba;
}
}