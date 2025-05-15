package file;

import logica.GPSData;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GenerarDatosGPS {
    // Método para generar los datos GPS de prueba
    public static void generarDatos() {
        String[] buses = {"BUS01", "BUS02", "BUS03"};
        Random rand = new Random();
        LocalDateTime inicio = LocalDateTime.now().withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        ArrayList<GPSData> datosGenerados = new ArrayList<>();

        for (String busId : buses) {
            double lat = 40.4168 + rand.nextDouble() * 0.01;
            double lon = -3.7038 + rand.nextDouble() * 0.01;

            List<Integer> indicesParadas = new ArrayList<>();
            while (indicesParadas.size() < 3) {
                int idx = rand.nextInt(60);
                if (!indicesParadas.contains(idx)) {
                    indicesParadas.add(idx);
                }
            }

            for (int i = 0; i < 60; i++) {
                double speed = indicesParadas.contains(i) ? 0.0 : rand.nextDouble() * 60;
                lat += (rand.nextDouble() - 0.5) * 0.001;
                lon += (rand.nextDouble() - 0.5) * 0.001;
                String timestamp = inicio.plusMinutes(i).format(formatter);

                datosGenerados.add(new GPSData(busId, timestamp, lat, lon, speed));
            }
        }

        //  tras generar los datos los guardo en csv y memoria dinamica
        try {
            ReadWrite.guardarLoteGPS(datosGenerados);
            ReadWrite.escribirDatosGPS(datosGenerados);
            System.out.println("Datos GPS generados correctamente y almacenados.");
        } catch (IOException e) {
            System.err.println("Error al guardar los datos GPS: " + e.getMessage());
        }
    }

}

