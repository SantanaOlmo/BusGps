package file;

import com.google.gson.Gson;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;

public class JsonViewer {

    private static final String JSON_DIR = "busesJson";

    public static void mostrarContenidoJsons() {
        File carpeta = new File(JSON_DIR);

        if (!carpeta.exists() || !carpeta.isDirectory()) {
            JOptionPane.showMessageDialog(null, "La carpeta de JSONs no existe.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        File[] archivos = carpeta.listFiles((dir, name) -> name.endsWith(".json"));

        if (archivos == null || archivos.length == 0) {
            JOptionPane.showMessageDialog(null, "No se encontraron archivos JSON.", "Información", JOptionPane.INFORMATION_MESSAGE);
            return;
        }

        StringBuilder contenido = new StringBuilder();
        Gson gson = new Gson();

        for (File archivo : archivos) {
            try (FileReader reader = new FileReader(archivo)) {
                Object jsonObj = gson.fromJson(reader, Object.class);
                String jsonPretty = gson.toJson(jsonObj);
                contenido.append("Archivo: ").append(archivo.getName()).append("\n")
                        .append(jsonPretty).append("\n\n");
            } catch (IOException e) {
                contenido.append("Error al leer ").append(archivo.getName()).append(": ").append(e.getMessage()).append("\n");
            }
        }

        // Mostrar el contenido en un cuadro de diálogo (acotado por longitud)
        if (contenido.length() > 3000) {
            contenido.setLength(3000);
            contenido.append("\n... (contenido truncado)");
        }

        JOptionPane.showMessageDialog(null, contenido.toString(), "Contenido de los JSON", JOptionPane.INFORMATION_MESSAGE);
    }
}
