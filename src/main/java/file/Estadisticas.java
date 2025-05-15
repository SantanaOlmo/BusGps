package file;

import logica.GPSData;

import java.util.ArrayList;
import java.util.Map;

import static file.ProcesarGPSdata.calcularVelocidadMedia;
import static file.ProcesarGPSdata.detectarParadas;

public class Estadisticas {
    public static String mostrarEstadisticas(ArrayList<GPSData> datos) {
        StringBuilder sb = new StringBuilder();

        // Calcular la velocidad media por autobús
        double velocidadMedia = calcularVelocidadMedia(datos);
        sb.append("Velocidad media total: ").append(String.format("%.2f", velocidadMedia)).append(" km/h\n\n");

        // Detectar y contar las paradas
        Map<String, Integer> paradas = detectarParadas(datos);

        // Mostrar las paradas por cada autobús
        sb.append("Número de paradas por autobús:\n");
        for (Map.Entry<String, Integer> entry : paradas.entrySet()) {
            sb.append("Autobús ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" paradas\n");
        }

        return sb.toString();
    }

}
