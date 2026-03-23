package controladores; 

import estructuras.ListaEnlazada; 

public class PlanificadorDisco extends Thread {

    // --- 1. VARIABLES DEL PLANIFICADOR ---
    private String politicaActual = "FIFO";
    private int posicionCabezal = 0;
    
    private final ListaEnlazada<Integer> colaDeSolicitudes; 
    private boolean moviendoHaciaArriba = true;       
    private boolean sistemaEncendido = true;          

    // --- CONSTRUCTOR ---
    public PlanificadorDisco() {
        this.colaDeSolicitudes = new ListaEnlazada<>();
    }

    // --- 2. MÉTODOS GET Y SET ---
    public void setPosicionCabezal(int nuevaPosicion) {
        this.posicionCabezal = nuevaPosicion;
    }

    public int getPosicionCabezal() {
        return this.posicionCabezal;
    }

    public void setPolitica(String nuevaPolitica) {
        this.politicaActual = nuevaPolitica;
    }

    // --- NUEVO MÉTODO PARA RECIBIR ÓRDENES ---
    public synchronized void agregarSolicitud(int bloqueDestino) {
        this.colaDeSolicitudes.agregarAlFinal(bloqueDestino);
    }

    // --- 3. MOTOR DEL PLANIFICADOR ---
    @Override
    public void run() {
        System.out.println("🚀 Motor del Planificador iniciado y esperando órdenes...");
        
        while (sistemaEncendido) {
            int siguienteDestino = -1;
            int indiceAEliminar = -1;

            synchronized (this) {
                if (!colaDeSolicitudes.estaVacia()) {
                    int tamaño = colaDeSolicitudes.getTamaño();
                    
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
                                        minDistanciaScan = dist;
                                        siguienteDestino = pos;
                                        indiceAEliminar = i;
                                    }
                                } else if (!moviendoHaciaArriba && pos <= posicionCabezal) {
                                    int dist = posicionCabezal - pos;
                                    if (dist < minDistanciaScan) {
                                        minDistanciaScan = dist;
                                        siguienteDestino = pos;
                                        indiceAEliminar = i;
                                    }
                                }
                            }
                            
                            if (siguienteDestino == -1) {
                                moviendoHaciaArriba = !moviendoHaciaArriba;
                            }
                        }

                        case "C-SCAN" -> {
                            int minDistanciaCscan = Integer.MAX_VALUE;
                            for (int i = 0; i < tamaño; i++) {
                                int pos = colaDeSolicitudes.obtener(i);
                                if (pos >= posicionCabezal) {
                                    int dist = pos - posicionCabezal;
                                    if (dist < minDistanciaCscan) {
                                        minDistanciaCscan = dist;
                                        siguienteDestino = pos;
                                        indiceAEliminar = i;
                                    }
                                }
                            }
                            
                            if (siguienteDestino == -1) {
                                int minPosicion = Integer.MAX_VALUE;
                                for (int i = 0; i < tamaño; i++) {
                                    int pos = colaDeSolicitudes.obtener(i);
                                    if (pos < minPosicion) {
                                        minPosicion = pos;
                                        siguienteDestino = pos;
                                        indiceAEliminar = i;
                                    }
                                }
                            }
                        }
                    }
                    
                    if (siguienteDestino != -1 && indiceAEliminar != -1) {
                        colaDeSolicitudes.eliminarEnIndice(indiceAEliminar);
                    }
                }
            } 

            if (siguienteDestino != -1) {
                System.out.println("💽 [" + politicaActual + "] Moviendo cabezal de " + posicionCabezal + " a " + siguienteDestino);
                posicionCabezal = siguienteDestino; 
                
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) { }
            } else {
                try {
                    Thread.sleep(1000); 
                } catch (InterruptedException e) { }
            }
        }
    }
    
    public void apagarMotor() {
        this.sistemaEncendido = false;
    }
}