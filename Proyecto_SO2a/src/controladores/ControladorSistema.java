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
    private final PlanificadorDisco ascensorista; 
    private final ListaEnlazada<Archivo> listaDeArchivos;  
    private final Cola<Proceso> colaDeProcesos;            

    //  La libreta donde el gerente anotará todo antes de hacerlo:
    private final estructuras.ListaEnlazada<modelos.RegistroJournal> bitacora; 
    
    // El botón de pánico que conectaremos a la interfaz gráfica
    public boolean simularFalloActivado = false;
    
    // Constructor
    public ControladorSistema(PlanificadorDisco planificador, ListaEnlazada<Archivo> lista, Cola<Proceso> cola) {
        this.ascensorista = planificador;
        this.listaDeArchivos = lista;
        this.colaDeProcesos = cola;
        this.bitacora = new estructuras.ListaEnlazada<>();
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

            // C. Extraer JOURNAL (Bitácora) para recuperar fallos
            if (datosJson.has("journal")) {
                JSONArray journalArray = datosJson.getJSONArray("journal");
                for (int i = 0; i < journalArray.length(); i++) {
                    JSONObject regJson = journalArray.getJSONObject(i);
                    
                    // Reconstruimos el registro
                    modelos.RegistroJournal reg = new modelos.RegistroJournal(
                        regJson.getString("tipo"),
                        regJson.getString("archivo"),
                        regJson.getInt("bloques")
                    );
                    reg.setEstado(regJson.getString("estado")); // Le ponemos el estado en el que se quedó
                    
                    this.bitacora.agregarAlFinal(reg); // Lo volvemos a meter a la libreta
                }
                System.out.println("📔 Journal cargado desde el archivo. Entradas: " + journalArray.length());
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

        // ==========================================================
        // --- SPRINT 5 GUARDAR LA LIBRETA EN EL JSON ---
        // ==========================================================
        JSONArray journalArrayJSON = new JSONArray();
        for (int i = 0; i < this.bitacora.getTamaño(); i++) {
            modelos.RegistroJournal reg = this.bitacora.obtener(i);
            JSONObject regJson = new JSONObject();
            regJson.put("tipo", reg.getTipoOperacion());
            regJson.put("archivo", reg.getNombreArchivo());
            regJson.put("bloques", reg.getBloquesInvolucrados());
            regJson.put("estado", reg.getEstado());
            journalArrayJSON.put(regJson);
        }
        estadoGeneral.put("journal", journalArrayJSON);
        // ==========================================================

        // Mandar a guardar
        boolean exito = GestorJSON.guardarArchivoJSON(estadoGeneral, rutaDestino);
        if (exito) System.out.println("✅ Estado guardado exitosamente.");
        return exito;
    }
    
    // BÚSQUEDA DE ARCHIVOS 
    // Busca un archivo por su nombre dentro de la lista enlazada del sistema
    public Archivo buscarArchivoPorNombre(String nombre) {
        Nodo<Archivo> actual = this.listaDeArchivos.getCabeza();
        
        while (actual != null) {
            // Si el nombre del archivo actual coincide con el que buscamos...
            if (actual.getDato().getNombre().equals(nombre)) {
                return actual.getDato(); // ¡Lo encontramos! Lo devolvemos.
            }
            actual = actual.getSiguiente(); // Si no, pasamos al siguiente vagón
        }
        
        return null; // Si terminamos de recorrer el tren y no lo hallamos, devolvemos null
    }
    
    // ==========================================================
    // --- MÉTODOS DEL JOURNAL (TOLERANCIA A FALLOS) ---
    // ==========================================================

    // 1. Anota en la libreta ANTES de tocar el disco
    public modelos.RegistroJournal registrarInicioOperacion(String tipo, String nombreArchivo, int bloques) {
        // Fabricamos un nuevo renglón para la libreta
        modelos.RegistroJournal nuevoRegistro = new modelos.RegistroJournal(tipo, nombreArchivo, bloques);
        
        // Lo metemos al final de la libreta
        this.bitacora.agregarAlFinal(nuevoRegistro);
        
        System.out.println("📝 JOURNAL: Anotado [" + tipo + "] de " + nombreArchivo + " como PENDIENTE.");
        
        // Devolvemos el renglón para no perderlo de vista
        return nuevoRegistro;
    }
    
    // 2. Confirma en la libreta DESPUÉS de tocar el disco con éxito
    public void confirmarOperacion(modelos.RegistroJournal registro) {
        // Cambiamos el estado de "PENDIENTE" a "CONFIRMADA"
        registro.setEstado("CONFIRMADA");
        System.out.println("✅ JOURNAL: Operación de " + registro.getNombreArchivo() + " CONFIRMADA.");
    }
    
    // 3. El método salvavidas: Se ejecuta al encender el sistema
    public void recuperarSistema(modelos.DiscoVirtual disco) {
        System.out.println("🔍 Iniciando recuperación del sistema (Leyendo Journal)...");
        boolean huboFallos = false;
        
        // Recorremos la libreta desde el principio (índice 0) hasta el final
        for (int i = 0; i < bitacora.getTamaño(); i++) {
            modelos.RegistroJournal registro = bitacora.obtener(i);
            
            // Si encontramos algo que se quedó a medias ("PENDIENTE")
            if (registro.getEstado().equals("PENDIENTE")) {
                huboFallos = true; // Encendemos la alarma
                System.out.println("⚠️ ERROR DETECTADO: Fallo en " + registro.getTipoOperacion() + " de " + registro.getNombreArchivo());
                
                // CASO A: Se apagó mientras CREÁBAMOS. 
                // El disco ya había restado los bloques, así que se los SUMAMOS de regreso.
                if (registro.getTipoOperacion().equals("CREAR")) {
                    System.out.println("⏪ DESHACIENDO: Devolviendo " + registro.getBloquesInvolucrados() + " bloques al disco...");
                    disco.sumarBloquesLibres(registro.getBloquesInvolucrados());
                }
                
                // CASO B: Se apagó mientras ELIMINÁBAMOS.
                // El disco ya había sumado los bloques libres, así que se los RESTAMOS de regreso.
                else if (registro.getTipoOperacion().equals("ELIMINAR")) {
                    System.out.println("⏪ DESHACIENDO: Volviendo a reservar " + registro.getBloquesInvolucrados() + " bloques...");
                    disco.restarBloquesLibres(registro.getBloquesInvolucrados());
                }
                
                // Sellamos el registro para que mañana no vuelva a hacer esta corrección
                registro.setEstado("ABORTADA (Recuperada)");
            }
        }
        
        // Mensajes finales para el usuario
        if (!huboFallos) {
            System.out.println("✅ El Journal está limpio. No hay operaciones a medias.");
        } else {
            System.out.println("🛡️ Recuperación finalizada. El disco está seguro.");
        }
    }
}