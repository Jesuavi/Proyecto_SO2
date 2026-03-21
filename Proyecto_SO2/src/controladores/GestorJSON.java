package controladores;

// Importamos la librería externa que instalaste (La Excavadora)
import org.json.JSONObject; 

// Importamos las herramientas de Java para leer y escribir archivos
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.FileWriter;

public class GestorJSON {

    // --- 1. LEER ARCHIVO JSON (Desempacar) ---
    // Recibe la ruta de un archivo (.json) y devuelve un Diccionario Inteligente listo para usar
    public static JSONObject leerArchivoJSON(String rutaArchivo) {
        try {
            // 1. Leemos todo el texto del archivo de un solo golpe
            String contenidoTexto = new String(Files.readAllBytes(Paths.get(rutaArchivo)));
            
            // 2. Metemos ese texto en el "Diccionario Inteligente" (JSONObject)
            JSONObject diccionarioJson = new JSONObject(contenidoTexto);
            
            return diccionarioJson; // Devolvemos el diccionario lleno de datos
            
        } catch (Exception e) {
            System.out.println("❌ Error al leer el archivo JSON: " + e.getMessage());
            return null; // Si algo sale mal, devolvemos 'nada'
        }
    }

    // --- 2. GUARDAR ARCHIVO JSON (Empacar) ---
    // Recibe un Diccionario Inteligente y una ruta, y crea un archivo .json físico en tu computadora
    public static boolean guardarArchivoJSON(JSONObject diccionario, String rutaDestino) {
        try {
            // 1. Creamos un escritor de archivos apuntando a la ruta que nos dieron
            FileWriter escritor = new FileWriter(rutaDestino);
            
            // 2. Escribimos el JSON en el archivo. 
            // El '4' significa que le pondrá 4 espacios de sangría para que sea fácil de leer por humanos.
            escritor.write(diccionario.toString(4));
            
            // 3. Cerramos el archivo (¡MUY IMPORTANTE para que se guarde correctamente!)
            escritor.close(); 
            
            return true; // Avisamos que todo salió bien
            
        } catch (Exception e) {
            System.out.println("❌ Error al guardar el archivo JSON: " + e.getMessage());
            return false; // Avisamos que hubo un error
        }
    }
}