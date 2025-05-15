package logica;

import file.ReadWrite;
import javax.swing.*;
import java.io.IOException;
import java.util.*;


public class Menu {

    public static ArrayList<GPSData> datosGPS = new ArrayList<>();

    public static ArrayList<GPSData> cargarCSV() {
        ArrayList<GPSData> datosGPS = new ArrayList<>();
        try {
            datosGPS = ReadWrite.leerDatosGPS(); // leemos y guardamos en el nuevo ArrayList

            // Construir texto para mostrar el contenido
            if (datosGPS.isEmpty()) {
                JOptionPane.showMessageDialog(null, "No se encontraron datos en el archivo CSV.", "Información", JOptionPane.INFORMATION_MESSAGE);
            } else {
                StringBuilder sb = new StringBuilder();
                for (GPSData dato : datosGPS) {
                    sb.append(String.format("Bus: %s, Timestamp: %s, Lat: %.6f, Lon: %.6f, Speed: %.2f\n",
                            dato.getBusId(), dato.getTimestamp(), dato.getLatitude(), dato.getLongitude(), dato.getSpeed()));
                    // Limitar el tamaño para no saturar el diálogo (opcional)
                    if (sb.length() > 1000) {
                        sb.append("... (más datos no mostrados)");
                        break;
                    }
                }
                // Mostrar el contenido leído
                JOptionPane.showMessageDialog(null, sb.toString(), "✅Datos leídos correctamente♥️🚌", JOptionPane.INFORMATION_MESSAGE);
            }

        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo CSV: " + e.getMessage(),
                    "❌ Error", JOptionPane.ERROR_MESSAGE);
        }
        return datosGPS;
    }




    // Método para obtener los datos cargados
    public static ArrayList<GPSData> getDatosGPS() {
        return datosGPS;
    }


}
