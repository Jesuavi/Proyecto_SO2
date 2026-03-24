package modelos;

import java.awt.Color;
import java.util.Random;

public class DiscoVirtual {
    
    private int capacidadTotal;
    private int bloquesLibresCount;
    private int[] tablaAsignacion;
    private java.awt.Color[] colorBloques;

    // Constructor
    public DiscoVirtual(int capacidadTotal) {
        this.capacidadTotal = capacidadTotal;
        this.bloquesLibresCount = capacidadTotal;
        this.tablaAsignacion = new int[capacidadTotal];
        this.colorBloques = new java.awt.Color[capacidadTotal];

        // Inicializamos todo libre (-2)
        for (int i = 0; i < capacidadTotal; i++) {
            tablaAsignacion[i] = -2;
            colorBloques[i] = null;
        }
    }

    // Getters
    public int getCapacidadTotal() { return capacidadTotal; }
    public int getBloquesLibresCount() { return bloquesLibresCount; }
    public int[] getTablaAsignacion() { return tablaAsignacion; }
    public java.awt.Color[] getColorBloques() { return colorBloques; }

    // Generar color aleatorio
    public java.awt.Color generarColorAleatorio() {
        int r = (int) (Math.random() * 180);
        int g = (int) (Math.random() * 180);
        int b = (int) (Math.random() * 180);
        return new java.awt.Color(r, g, b);
    }

    // Asignar espacio con color
    public int asignarEspacio(int cantidadBloquesPedida, java.awt.Color colorArchivo) {
        if (cantidadBloquesPedida > bloquesLibresCount) {
            return -1; 
        }

        int primerBloque = -1;
        int bloqueAnterior = -1;
        int bloquesAsignados = 0;

        for (int i = 0; i < capacidadTotal; i++) {
            if (tablaAsignacion[i] == -2) { 
                if (primerBloque == -1) {
                    primerBloque = i;
                }

                if (bloqueAnterior != -1) {
                    tablaAsignacion[bloqueAnterior] = i; 
                }

                // Asignamos el color único del archivo
                colorBloques[i] = colorArchivo; 
                
                bloqueAnterior = i;
                bloquesAsignados++;

                if (bloquesAsignados == cantidadBloquesPedida) {
                    tablaAsignacion[i] = -1; 
                    bloquesLibresCount -= cantidadBloquesPedida;
                    break;
                }
            }
        }
        return primerBloque;
    }

    // Liberar espacio
    public void liberarEspacio(int direccionInicial) {
        int bloqueActual = direccionInicial;
        while (bloqueActual != -1) {
            int siguiente = tablaAsignacion[bloqueActual];
            tablaAsignacion[bloqueActual] = -2;
            colorBloques[bloqueActual] = null;
            bloquesLibresCount++;
            bloqueActual = siguiente;
        }
    }

    // --- MÉTODOS DE RECUPERACIÓN Y JOURNALING ---
    public void sumarBloquesLibres(int cantidad) {
        this.bloquesLibresCount += cantidad;
        if (this.bloquesLibresCount > this.capacidadTotal) {
            this.bloquesLibresCount = this.capacidadTotal;
        }
    }

    public void restarBloquesLibres(int cantidad) {
        this.bloquesLibresCount -= cantidad;
        if (this.bloquesLibresCount < 0) {
            this.bloquesLibresCount = 0;
        }
    }
}
