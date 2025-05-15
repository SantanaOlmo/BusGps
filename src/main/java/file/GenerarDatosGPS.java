package file;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

public class GenerarDatosGPS {
    // Método para generar los datos GPS de prueba
    public static void generarDatos() {
        String[] buses = {"BUS01", "BUS02", "BUS03"};
        Random rand = new Random();
        LocalDateTime inicio = LocalDateTime.now().withSecond(0).withNano(0);
        DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

        // Escribir cabecera solo si el archivo está vacío
        ReadWrite.escribirDatosGPS("busId", "timestamp", 0.0, 0.0, 0.0);  // Simula cabecera (puedes mejorar esto si lo prefieres)

        for (String busId : buses) {
            double lat = 40.4168 + rand.nextDouble() * 0.01;
            double lon = -3.7038 + rand.nextDouble() * 0.01;

            for (int i = 0; i < 60; i++) {
                double speed = rand.nextDouble() * 60;
                lat += (rand.nextDouble() - 0.5) * 0.001;
                lon += (rand.nextDouble() - 0.5) * 0.001;
                String timestamp = inicio.plusMinutes(i).format(formatter);

                // Usar tu clase para escribir el CSV
                ReadWrite.escribirDatosGPS(busId, timestamp, lat, lon, speed);
            }
        }

        System.out.println("Datos GPS generados correctamente en gps_data.csv");
    }
}

