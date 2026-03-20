package modelos;

public class Sesion {
    // Estas variables estáticas se pueden leer desde cualquier parte del programa
    public static String usuarioActual = "Admin"; // Por defecto arranca el jefe
    public static String rolActual = "Administrador"; // Puede ser "Administrador" o "Usuario"

    // Método para cambiar de usuario desde la interfaz gráfica
    public static void cambiarUsuario(String nuevoUsuario, String nuevoRol) {
        usuarioActual = nuevoUsuario;
        rolActual = nuevoRol;
    }

    // El portero: Verifica si el usuario actual tiene permiso de tocar un archivo
    public static boolean tienePermisoDeEscritura(String dueñoDelArchivo) {
        // Si soy admin, puedo hacer lo que quiera
        if (rolActual.equals("Administrador")) {
            return true;
        }
        // Si soy usuario normal, solo puedo tocar lo que es mío
        return usuarioActual.equals(dueñoDelArchivo);
    }
}