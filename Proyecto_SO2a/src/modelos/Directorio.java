package modelos;
import estructuras.ListaEnlazada;

public class Directorio {
    private String nombre;
    private String dueño;
    
    // Una carpeta puede tener archivos adentro...
    private ListaEnlazada<Archivo> archivos;
    // ...¡Y también otras carpetas (subcarpetas)!
    private ListaEnlazada<Directorio> subdirectorios;

    public Directorio(String nombre, String dueño) {
        this.nombre = nombre;
        this.dueño = dueño;
        this.archivos = new ListaEnlazada<>();
        this.subdirectorios = new ListaEnlazada<>();
    }

    // Getters para acceder a su contenido
    public String getNombre() { return nombre; }
    public ListaEnlazada<Archivo> getArchivos() { return archivos; }
    public ListaEnlazada<Directorio> getSubdirectorios() { return subdirectorios; }
}