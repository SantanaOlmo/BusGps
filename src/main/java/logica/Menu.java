package logica;

import file.ReadWrite;
import javax.swing.*;
import java.io.IOException;
import java.util.*;


public class Menu {

    public static ArrayList<GPSData> datosGPS = new ArrayList<>();

    // Método para cargar el archivo CSV y almacenar los datos en datosGPS
    public static ArrayList<GPSData> cargarCSV() {
        try {
            // Se lee el archivo usando la ruta definida en ReadWrite.RUTA
            ArrayList<String[]> datos = ReadWrite.leerDatosGPS();
            datosGPS.clear();  // Limpiar la lista antes de agregar nuevos datos

            for (String[] fila : datos) {
                String busId = fila[0];
                String timestamp = fila[1];
                double latitude = Double.parseDouble(fila[2]);
                double longitude = Double.parseDouble(fila[3]);
                double speed = Double.parseDouble(fila[4]);
                datosGPS.add(new GPSData(busId, timestamp, latitude, longitude, speed));
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error al leer el archivo CSV: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        return datosGPS;
    }


    // Método para obtener los datos cargados
    public static ArrayList<GPSData> getDatosGPS() {
        return datosGPS;
    }


}
