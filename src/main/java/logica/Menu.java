package logica;

import file.ReadWrite;
import javax.swing.*;
import java.io.IOException;
import java.util.*;


public class Menu {

    public static ArrayList<GPSData> datosGPS = new ArrayList<>();

    // Método para cargar el archivo CSV y almacenar los datos en datosGPS
    public static ArrayList<GPSData> cargarCSV() {
        ArrayList<GPSData> datosGPS = new ArrayList<>();
        try {
            datosGPS = ReadWrite.leerDatosGPS(); // ✅ Usa el método que devuelve objetos GPSData
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
