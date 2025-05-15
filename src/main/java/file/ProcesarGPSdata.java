package file;

import logica.GPSData;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static logica.Menu.*;

public class ProcesarGPSdata {
    // Método para procesar datos y filtrar por autobús y rango horario
    public static ArrayList<GPSData> procesarDatos(String busId, String horaInicio, String horaFin) throws IOException {
        ArrayList<GPSData> datosFiltrados = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

        // Convertir las horas de inicio y fin a objetos Date para compararlos
        Date fechaInicio = null;
        Date fechaFin = null;
        try {
            fechaInicio = sdf.parse(horaInicio);
            fechaFin = sdf.parse(horaFin);
        } catch (ParseException e) {
            System.err.println("Error al parsear las fechas: " + e.getMessage());
        }

        // Filtrar los datos por busId y rango de hora
        for (GPSData gpsData : datosGPS) {
            // Verificar si el busId coincide y si el timestamp está dentro del rango de horas
            try {
                Date timestamp = sdf.parse(gpsData.getTimestamp());
                if (gpsData.getBusId().equals(busId) &&
                        timestamp.after(fechaInicio) && timestamp.before(fechaFin)) {
                    // Validar la coherencia de los datos (coordenadas y velocidad)
                    if (isValidGPSData(gpsData)) {
                        datosFiltrados.add(gpsData);
                    }
                }
            } catch (ParseException e) {
                System.err.println("Error al procesar el timestamp: " + e.getMessage());
            }
        }

        return datosFiltrados;
    }

    private static boolean isValidGPSData(GPSData gpsData) {
        boolean isValid = true;

        // Validar latitud y longitud
        if (gpsData.getLatitude() < -90 || gpsData.getLatitude() > 90) {
            System.err.println("Latitud fuera de rango: " + gpsData.getLatitude());
            isValid = false;
        }
        if (gpsData.getLongitude() < -180 || gpsData.getLongitude() > 180) {
            System.err.println("Longitud fuera de rango: " + gpsData.getLongitude());
            isValid = false;
        }

        // Validar velocidad (no puede ser negativa)
        if (gpsData.getSpeed() < 0) {
            System.err.println("Velocidad negativa: " + gpsData.getSpeed());
            isValid = false;
        }

        // Si los datos no son válidos, retornar false
        return isValid;
    }

    // Método para calcular la velocidad media por autobús
    public static double calcularVelocidadMedia(ArrayList<GPSData> datosGPS) {
        if (datosGPS == null || datosGPS.isEmpty()) {
            return 0;
        }

        double totalVelocidad = 0;
        int contador = 0;

        for (GPSData dato : datosGPS) {
            totalVelocidad += dato.getSpeed();  // Sumar la velocidad
            contador++;
        }

        return totalVelocidad / contador;  // Calcular la velocidad media
    }

    // Método para detectar paradas y contar el número de paradas por autobús
    public static Map<String, Integer> detectarParadas(ArrayList<GPSData> datos) {
        Map<String, Integer> paradasPorBus = new HashMap<>();

        // Recorrer los datos y contar las paradas (velocidad = 0)
        for (GPSData gpsData : datos) {
            if (gpsData.getSpeed() == 0) {  // Si la velocidad es 0, es una parada
                String busId = gpsData.getBusId();
                paradasPorBus.put(busId, paradasPorBus.getOrDefault(busId, 0) + 1);
            }
        }

        return paradasPorBus;
    }
}
