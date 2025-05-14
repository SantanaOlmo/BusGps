package file;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ReadWrite {

    private static final String RUTA = "gps_data.csv";

    public static void escribirDatoGPS(double latitud, double longitud, String timestamp) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA, true))) {
            String linea = latitud + "," + longitud + "," + timestamp;
            writer.write(linea);
            writer.newLine();
        }
    }

    public static List<String[]> leerDatosGPS() throws IOException {
        ArrayList<String[]> datos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 3) {
                    datos.add(partes);
                }
            }
        }

        return datos;
    }

    public static void borrarArchivoGPS() throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA))) {
            writer.write(""); // sobrescribe con cadena vacía
        }
    }
}

