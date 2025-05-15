package file;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import logica.GPSData;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class JsonManager {

    private static final String JSON_DIR = "busesJson";

    // Clase auxiliar para estructura JSON (puedes ponerla en su propio archivo si quieres)
    public static class BusStatus {
        private String busId;
        private double latitude;
        private double longitude;
        private String timestamp;

        public BusStatus(String busId, double latitude, double longitude, String timestamp) {
            this.busId = busId;
            this.latitude = latitude;
            this.longitude = longitude;
            this.timestamp = timestamp;
        }

        // Getters (si usas Gson no es obligatorio pero ayuda)
        public String getBusId() { return busId; }
        public double getLatitude() { return latitude; }
        public double getLongitude() { return longitude; }
        public String getTimestamp() { return timestamp; }
    }

    // Actualiza los JSONs con la última posición conocida de cada bus
    public static void actualizarJsonUltimaPosicion(ArrayList<GPSData> datosGPS) {
        File carpeta = new File(JSON_DIR);
        if (!carpeta.exists()) {
            carpeta.mkdir();
        }

        // Agrupar por busId y obtener el último registro (por timestamp)
        Map<String, GPSData> ultimoRegistroPorBus = datosGPS.stream()
                .collect(Collectors.groupingBy(GPSData::getBusId,
                        Collectors.collectingAndThen(
                                Collectors.maxBy(Comparator.comparing(GPSData::getTimestamp)),
                                opt -> opt.orElse(null)
                        )));

        Gson gson = new GsonBuilder().setPrettyPrinting().create();

        for (Map.Entry<String, GPSData> entry : ultimoRegistroPorBus.entrySet()) {
            GPSData ultimo = entry.getValue();
            if (ultimo == null) continue; // por si acaso

            BusStatus status = new BusStatus(
                    ultimo.getBusId(),
                    ultimo.getLatitude(),
                    ultimo.getLongitude(),
                    ultimo.getTimestamp()
            );

            File archivoJson = new File(carpeta, ultimo.getBusId() + "_status.json");

            try (FileWriter writer = new FileWriter(archivoJson)) {
                gson.toJson(status, writer);
                System.out.println("JSON actualizado: " + archivoJson.getPath());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}

