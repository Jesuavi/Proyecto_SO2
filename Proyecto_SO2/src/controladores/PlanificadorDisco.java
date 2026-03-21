package controladores; // Asegúrate de que este sea el nombre correcto de tu carpeta

import estructuras.ListaEnlazada; // Importamos tu estructura


public class PlanificadorDisco extends Thread {

    // --- 1. VARIABLES DEL PLANIFICADOR ---
    private String politicaActual = "FIFO";
    private int posicionCabezal = 0;
    
    // Tus variables originales de la captura
    private ListaEnlazada<Integer> colaDeSolicitudes; 
    private boolean moviendoHaciaArriba = true;       
    private boolean sistemaEncendido = true;          

    // --- CONSTRUCTOR ---
    public PlanificadorDisco() {
        // Inicializamos la lista para que no dé errores de "NullPointer"
        this.colaDeSolicitudes = new ListaEnlazada<>();
    }

    // --- 2. MÉTODOS GET Y SET (Para hablar con el Controlador) ---
    public void setPosicionCabezal(int nuevaPosicion) {
        this.posicionCabezal = nuevaPosicion;
    }

    public int getPosicionCabezal() {
        return this.posicionCabezal;
    }

    public void setPolitica(String nuevaPolitica) {
        this.politicaActual = nuevaPolitica;
    }

    // --- 3. MOTOR DEL PLANIFICADOR (HILO EN SEGUNDO PLANO) ---
    // Como pusiste "extends Thread", Java te obliga a tener este método run()
    @Override
    public void run() {
        System.out.println("🚀 Motor del Planificador iniciado y esperando órdenes...");
        
        while (sistemaEncendido) {
            // Más adelante aquí pondremos la magia matemática de FIFO, SSTF y SCAN
            
            try {
                // Hacemos que el hilo duerma 1 segundo para que no consuma toda la RAM de tu PC
                Thread.sleep(1000); 
            } catch (InterruptedException e) {
                System.out.println("❌ Error en el motor del planificador: " + e.getMessage());
            }
        }
    }
    
    // Método de seguridad para apagar el motor si cerramos el programa
    public void apagarMotor() {
        this.sistemaEncendido = false;
    }
}