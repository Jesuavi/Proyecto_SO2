package controladores;

import estructuras.Cola;
import estructuras.ListaEnlazada;
import estructuras.Nodo;
import modelos.Archivo;
import modelos.Proceso;
import modelos.Sesion;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Iterator;

public class ControladorSistema {

    // --- VARIABLES DEL SISTEMA ---
    private PlanificadorDisco ascensorista; 
    private ListaEnlazada<Archivo> listaDeArchivos;  
    private Cola<Proceso> colaDeProcesos;            

    // Constructor
    public ControladorSistema(PlanificadorDisco planificador, ListaEnlazada<Archivo> lista, Cola<Proceso> cola) {
        this.ascensorista = planificador;
        this.listaDeArchivos = lista;
        this.colaDeProcesos = cola;
    }

    // --- 1. CONFIGURACIÓN DEL CABEZAL ---
    public boolean configurarCabezalInicial(int bloqueInicio) {
        if (!Sesion.rolActual.equals("Administrador")) return false;
        this.ascensorista.setPosicionCabezal(bloqueInicio);
        System.out.println("📍 Cabezal movido a la posición: " + bloqueInicio);
        return true;
    }

    // --- 2. CAMBIAR POLÍTICA ---
    public boolean cambiarPoliticaPlanificacion(String nuevaPolitica) {
        if (!Sesion.rolActual.equals("Administrador")) return false;
        this.ascensorista.setPolitica(nuevaPolitica);
        System.out.println("🎛️ Política cambiada a: " + nuevaPolitica);
        return true;
    }

    // --- 3. CARGAR CASO DE PRUEBA (Lectura) ---
    public boolean cargarCasoDePrueba(String rutaArchivoJSON) {
        if (!Sesion.rolActual.equals("Administrador")) {
            System.out.println("❌ ACCESO DENEGADO.");
            return false;
        }

        System.out.println("⏳ Leyendo archivo JSON de prueba...");
        JSONObject datosJson = GestorJSON.leerArchivoJSON(rutaArchivoJSON);
        
        if (datosJson != null) {
            
            if (datosJson.has("initial_head")) {
                configurarCabezalInicial(datosJson.getInt("initial_head")); 
            }

            // A. Extraer SYSTEM_FILES (Archivos)
            if (datosJson.has("system_files")) {
                JSONObject archivosJson = datosJson.getJSONObject("system_files");
                Iterator<String> llaves = archivosJson.keys();
                
                while(llaves.hasNext()) {
                    String llavePosicion = llaves.next(); 
                    JSONObject archivoUnico = archivosJson.getJSONObject(llavePosicion);
                    
                    String nombre = archivoUnico.getString("name");
                    int cantidadBloques = archivoUnico.getInt("blocks");
                    
                    // Usamos tu constructor exacto (nombre, tamaño, dueño, color)
                    Archivo nuevoArchivo = new Archivo(nombre, cantidadBloques, "Sistema", "#FFFFFF");
                    
                    // Usamos tu método exacto: agregarAlFinal
                    this.listaDeArchivos.agregarAlFinal(nuevoArchivo); 
                    
                    System.out.println("📦 Archivo cargado: " + nombre);
                }
            }

            // B. Extraer REQUESTS (Procesos)
            if (datosJson.has("requests")) {
                JSONArray listaProcesosJSON = datosJson.getJSONArray("requests");
                
                for (int i = 0; i < listaProcesosJSON.length(); i++) {
                    JSONObject procesoUnico = listaProcesosJSON.getJSONObject(i);
                    
                    int posicionSolicitada = procesoUnico.getInt("pos");
                    String operacion = procesoUnico.getString("op");
                    
                    // Usamos tu constructor exacto (operacion, archivoObjetivo, bloquesRequeridos)
                    Proceso nuevoProceso = new Proceso(operacion, null, 0);
                    nuevoProceso.setPosicion(posicionSolicitada); // Guardamos la posición que agregamos en el Paso 1
                    
                    // Usamos tu método exacto: encolar
                    this.colaDeProcesos.encolar(nuevoProceso); 
                    
                    System.out.println("⚙️ Solicitud encolada: " + operacion + " en posición " + posicionSolicitada);
                }
            }

            System.out.println("✅ Todos los datos fueron extraídos con éxito.");
            return true;
        }
        return false;
    }

    // --- 4. GUARDAR ESTADO ACTUAL (Escritura) ---
    public boolean guardarEstadoSistema(String rutaDestino) {
        if (!Sesion.rolActual.equals("Administrador")) return false;

        System.out.println("⏳ Empacando el estado del sistema en JSON...");
        JSONObject estadoGeneral = new JSONObject();
        estadoGeneral.put("test_id", "Guardado_Manual");
        estadoGeneral.put("initial_head", this.ascensorista.getPosicionCabezal());
        
        // Empacar la Cola de Procesos usando tu clase Nodo<T>
        JSONArray procesosArrayJSON = new JSONArray();
        Nodo<Proceso> actualProceso = this.colaDeProcesos.getFrente();
        
        while (actualProceso != null) {
            JSONObject procJson = new JSONObject();
            procJson.put("pos", actualProceso.getDato().getPosicion());
            procJson.put("op", actualProceso.getDato().getTipoOperacion());
            procesosArrayJSON.put(procJson);
            actualProceso = actualProceso.getSiguiente();
        }
        estadoGeneral.put("requests", procesosArrayJSON);

        // Empacar la Lista de Archivos
        JSONObject archivosDictJSON = new JSONObject();
        Nodo<Archivo> actualArchivo = this.listaDeArchivos.getCabeza();
        int posicionSimulada = 0; 
        
        while (actualArchivo != null) {
            JSONObject archJson = new JSONObject();
            archJson.put("name", actualArchivo.getDato().getNombre());
            archJson.put("blocks", actualArchivo.getDato().getTamaño());
            archivosDictJSON.put(String.valueOf(posicionSimulada), archJson);
            actualArchivo = actualArchivo.getSiguiente();
            posicionSimulada += 10; // Simulamos llaves separadas para los archivos
        }
        estadoGeneral.put("system_files", archivosDictJSON);

        // Mandar a guardar
        boolean exito = GestorJSON.guardarArchivoJSON(estadoGeneral, rutaDestino);
        if (exito) System.out.println("✅ Estado guardado exitosamente.");
        return exito;
    }
}