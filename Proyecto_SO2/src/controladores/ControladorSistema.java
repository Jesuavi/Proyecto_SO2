package controladores;

import estructuras.Cola;
import estructuras.ListaEnlazada;
import estructuras.Nodo;
import modelos.Archivo;
import modelos.Proceso;
import modelos.RegistroJournal;
import modelos.Sesion;
import modelos.DiscoVirtual;
import org.json.JSONObject;
import org.json.JSONArray;
import java.util.Iterator;

public class ControladorSistema {

    // --- VARIABLES DEL SISTEMA ---
    private final PlanificadorDisco ascensorista;
    private ListaEnlazada<Archivo> listaDeArchivos;
    private Cola<Proceso> colaDeProcesos;
    private ListaEnlazada<RegistroJournal> bitacora;
    
    // El botón de pánico que conectaremos a la interfaz gráfica
    public boolean simularFalloActivado = false;
    
    // Constructor
    public ControladorSistema(PlanificadorDisco planificador, ListaEnlazada<Archivo> lista, Cola<Proceso> cola) {
        this.ascensorista = planificador;
        this.listaDeArchivos = lista;
        this.colaDeProcesos = cola;
        this.bitacora = new ListaEnlazada<>();
    }

    // --- GETTERS ---
    public Cola<Proceso> getColaProcesos() {
        return this.colaDeProcesos;
    }

    public ListaEnlazada<Archivo> getListaArchivos() {
        return this.listaDeArchivos;
    }

    public ListaEnlazada<RegistroJournal> getBitacora() {
        return this.bitacora;
    }

    public PlanificadorDisco getPlanificador() {
        return this.ascensorista;
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
                    
                    Archivo nuevoArchivo = new Archivo(nombre, cantidadBloques, "Sistema", "#FFFFFF");
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
                    
                    Proceso nuevoProceso = new Proceso(operacion, null, 0);
                    nuevoProceso.setPosicion(posicionSolicitada);
                    
                    this.colaDeProcesos.encolar(nuevoProceso); 
                    
                    System.out.println("⚙️ Solicitud encolada: " + operacion + " en posición " + posicionSolicitada);
                }
            }

            // C. Extraer JOURNAL (Bitácora) para recuperar fallos
            if (datosJson.has("journal")) {
                JSONArray journalArray = datosJson.getJSONArray("journal");
                for (int i = 0; i < journalArray.length(); i++) {
                    JSONObject regJson = journalArray.getJSONObject(i);
                    
                    RegistroJournal reg = new RegistroJournal(
                        regJson.getString("tipo"),
                        regJson.getString("archivo"),
                        regJson.getInt("bloques")
                    );
                    reg.setEstado(regJson.getString("estado"));
                    
                    this.bitacora.agregarAlFinal(reg);
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
        if (!Sesion.rolActual.equals("Administrador")) {
            System.out.println("❌ ACCESO DENEGADO: Solo administrador puede guardar.");
            return false;
        }

        System.out.println("⏳ Guardando estado del sistema en JSON...");
        JSONObject estadoGeneral = new JSONObject();
        estadoGeneral.put("test_id", "Estado_Guardado");
        estadoGeneral.put("initial_head", this.ascensorista.getPosicionCabezal());
        
        // 1. Guardar la cola de procesos
        JSONArray procesosArrayJSON = new JSONArray();
        Nodo<Proceso> actualProceso = this.colaDeProcesos.getFrente();
        
        while (actualProceso != null) {
            JSONObject procJson = new JSONObject();
            procJson.put("pos", actualProceso.getDato().getPosicion());
            procJson.put("op", actualProceso.getDato().getTipoOperacion());
            if (actualProceso.getDato().getArchivoObjetivo() != null) {
                procJson.put("file", actualProceso.getDato().getArchivoObjetivo().getNombre());
            }
            procJson.put("estado", actualProceso.getDato().getEstado());
            procesosArrayJSON.put(procJson);
            actualProceso = actualProceso.getSiguiente();
        }
        estadoGeneral.put("requests", procesosArrayJSON);

        // 2. Guardar la lista de archivos
        JSONObject archivosDictJSON = new JSONObject();
        Nodo<Archivo> actualArchivo = this.listaDeArchivos.getCabeza();
        int posicionSimulada = 0;
        
        while (actualArchivo != null) {
            JSONObject archJson = new JSONObject();
            archJson.put("name", actualArchivo.getDato().getNombre());
            archJson.put("blocks", actualArchivo.getDato().getTamaño());
            archJson.put("owner", actualArchivo.getDato().getDueño());
            archivosDictJSON.put(String.valueOf(posicionSimulada), archJson);
            actualArchivo = actualArchivo.getSiguiente();
            posicionSimulada += 10;
        }
        estadoGeneral.put("system_files", archivosDictJSON);

        // 3. Guardar el journal (bitácora)
        JSONArray journalArrayJSON = new JSONArray();
        for (int i = 0; i < this.bitacora.getTamaño(); i++) {
            RegistroJournal reg = this.bitacora.obtener(i);
            JSONObject regJson = new JSONObject();
            regJson.put("tipo", reg.getTipoOperacion());
            regJson.put("archivo", reg.getNombreArchivo());
            regJson.put("bloques", reg.getBloquesInvolucrados());
            regJson.put("estado", reg.getEstado());
            journalArrayJSON.put(regJson);
        }
        estadoGeneral.put("journal", journalArrayJSON);

        // Guardar en archivo
        boolean exito = GestorJSON.guardarArchivoJSON(estadoGeneral, rutaDestino);
        if (exito) {
            System.out.println("✅ Estado guardado exitosamente en: " + rutaDestino);
        }
        return exito;
    }

    // --- 5. CARGAR ESTADO GUARDADO ---
    public boolean cargarEstadoSistema(String rutaArchivo) {
        if (!Sesion.rolActual.equals("Administrador")) {
            System.out.println("❌ ACCESO DENEGADO: Solo administrador puede cargar.");
            return false;
        }

        System.out.println("⏳ Cargando estado del sistema desde JSON...");
        JSONObject datosJson = GestorJSON.leerArchivoJSON(rutaArchivo);
        
        if (datosJson != null) {
            // Limpiar estado actual
            this.listaDeArchivos = new ListaEnlazada<Archivo>();
            this.colaDeProcesos = new Cola<Proceso>();
            this.bitacora = new ListaEnlazada<RegistroJournal>();
            
            // Configurar cabezal inicial
            if (datosJson.has("initial_head")) {
                int cabezalInicial = datosJson.getInt("initial_head");
                configurarCabezalInicial(cabezalInicial);
                System.out.println("📍 Cabezal configurado en: " + cabezalInicial);
            }

            // A. Cargar SYSTEM_FILES (Archivos)
            if (datosJson.has("system_files")) {
                JSONObject archivosJson = datosJson.getJSONObject("system_files");
                Iterator<String> llaves = archivosJson.keys();
                
                while(llaves.hasNext()) {
                    String llavePosicion = llaves.next(); 
                    JSONObject archivoUnico = archivosJson.getJSONObject(llavePosicion);
                    
                    String nombre = archivoUnico.getString("name");
                    int cantidadBloques = archivoUnico.getInt("blocks");
                    String dueno = archivoUnico.has("owner") ? archivoUnico.getString("owner") : "Sistema";
                    
                    Archivo nuevoArchivo = new Archivo(nombre, cantidadBloques, dueno, "#FFFFFF");
                    this.listaDeArchivos.agregarAlFinal(nuevoArchivo); 
                    
                    System.out.println("📦 Archivo cargado: " + nombre + " (" + cantidadBloques + " bloques)");
                }
            }

            // B. Cargar REQUESTS (Procesos)
            if (datosJson.has("requests")) {
                JSONArray listaProcesosJSON = datosJson.getJSONArray("requests");
                
                for (int i = 0; i < listaProcesosJSON.length(); i++) {
                    JSONObject procesoUnico = listaProcesosJSON.getJSONObject(i);
                    
                    int posicionSolicitada = procesoUnico.getInt("pos");
                    String operacion = procesoUnico.getString("op");
                    String estado = procesoUnico.has("estado") ? procesoUnico.getString("estado") : "NUEVO";
                    
                    Archivo archivoObjetivo = null;
                    if (procesoUnico.has("file")) {
                        String nombreArchivo = procesoUnico.getString("file");
                        archivoObjetivo = buscarArchivoPorNombre(nombreArchivo);
                    }
                    
                    Proceso nuevoProceso = new Proceso(operacion, archivoObjetivo, 0);
                    nuevoProceso.setPosicion(posicionSolicitada);
                    nuevoProceso.setEstado(estado);
                    
                    this.colaDeProcesos.encolar(nuevoProceso); 
                    
                    System.out.println("⚙️ Proceso cargado: " + operacion + " en posición " + posicionSolicitada);
                }
            }

            // C. Cargar JOURNAL
            if (datosJson.has("journal")) {
                JSONArray journalArray = datosJson.getJSONArray("journal");
                for (int i = 0; i < journalArray.length(); i++) {
                    JSONObject regJson = journalArray.getJSONObject(i);
                    
                    RegistroJournal reg = new RegistroJournal(
                        regJson.getString("tipo"),
                        regJson.getString("archivo"),
                        regJson.getInt("bloques")
                    );
                    reg.setEstado(regJson.getString("estado"));
                    
                    this.bitacora.agregarAlFinal(reg);
                }
                System.out.println("📔 Journal cargado. Entradas: " + journalArray.length());
            }

            System.out.println("✅ Estado cargado exitosamente.");
            return true;
        }
        return false;
    }

    // --- 6. BUSCAR ARCHIVO POR NOMBRE ---
    public Archivo buscarArchivoPorNombre(String nombre) {
        Nodo<Archivo> actual = this.listaDeArchivos.getCabeza();
        
        while (actual != null) {
            if (actual.getDato().getNombre().equals(nombre)) {
                return actual.getDato();
            }
            actual = actual.getSiguiente();
        }
        return null;
    }

    // --- 7. MÉTODOS DEL JOURNAL (TOLERANCIA A FALLOS) ---
    public RegistroJournal registrarInicioOperacion(String tipo, String nombreArchivo, int bloques) {
        RegistroJournal nuevoRegistro = new RegistroJournal(tipo, nombreArchivo, bloques);
        this.bitacora.agregarAlFinal(nuevoRegistro);
        System.out.println("📝 JOURNAL: Anotado [" + tipo + "] de " + nombreArchivo + " como PENDIENTE.");
        return nuevoRegistro;
    }
    
    public void confirmarOperacion(RegistroJournal registro) {
        registro.setEstado("CONFIRMADA");
        System.out.println("✅ JOURNAL: Operación de " + registro.getNombreArchivo() + " CONFIRMADA.");
    }
    
    public void recuperarSistema(DiscoVirtual disco) {
        System.out.println("🔍 Iniciando recuperación del sistema (Leyendo Journal)...");
        boolean huboFallos = false;
        
        for (int i = 0; i < bitacora.getTamaño(); i++) {
            RegistroJournal registro = bitacora.obtener(i);
            
            if (registro.getEstado().equals("PENDIENTE")) {
                huboFallos = true;
                System.out.println("⚠️ ERROR DETECTADO: Fallo en " + registro.getTipoOperacion() + " de " + registro.getNombreArchivo());
                
                if (registro.getTipoOperacion().equals("CREAR")) {
                    System.out.println("⏪ DESHACIENDO: Devolviendo " + registro.getBloquesInvolucrados() + " bloques al disco...");
                    disco.sumarBloquesLibres(registro.getBloquesInvolucrados());
                } else if (registro.getTipoOperacion().equals("ELIMINAR")) {
                    System.out.println("⏪ DESHACIENDO: Volviendo a reservar " + registro.getBloquesInvolucrados() + " bloques...");
                    disco.restarBloquesLibres(registro.getBloquesInvolucrados());
                }
                
                registro.setEstado("ABORTADA (Recuperada)");
            }
        }
        
        if (!huboFallos) {
            System.out.println("✅ El Journal está limpio. No hay operaciones a medias.");
        } else {
            System.out.println("🛡️ Recuperación finalizada. El disco está seguro.");
        }
    }

    // --- 8. MÉTODO PARA OBTENER RUTA DEL ARCHIVO DE ESTADO ---
    public String getRutaArchivoEstado() {
        return "estado_sistema.json";
    }
}