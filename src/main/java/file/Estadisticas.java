package file;

import logica.GPSData;

import java.util.ArrayList;
import java.util.Map;

import static file.ProcesarGPSdata.calcularVelocidadMedia;
import static file.ProcesarGPSdata.detectarParadas;

public class Estadisticas {
    // Muestra estadísticas incluyendo velocidad media y paradas detectadas
    public static String mostrarEstadisticas(ArrayList<GPSData> datos) {
        StringBuilder sb = new StringBuilder();

        double velocidadMedia = calcularVelocidadMedia(datos);
        sb.append("Velocidad media total: ").append(String.format("%.2f", velocidadMedia)).append(" km/h\n\n");

        ProcesarGPSdata.ResultadoParadas resultado = detectarParadas(datos);
        Map<String, Integer> paradas = resultado.getConteoParadas();

        sb.append("Número de paradas por autobús:\n");
        for (Map.Entry<String, Integer> entry : paradas.entrySet()) {
            sb.append("Autobús ").append(entry.getKey()).append(": ").append(entry.getValue()).append(" paradas\n");
        }

        return sb.toString();
    }


}
