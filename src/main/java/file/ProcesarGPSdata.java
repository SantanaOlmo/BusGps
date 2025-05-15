package file;

import logica.GPSData;
import logica.Parada;

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

        Date fechaInicio = null;
        Date fechaFin = null;
        try {
            fechaInicio = sdf.parse(horaInicio);
            fechaFin = sdf.parse(horaFin);
        } catch (ParseException e) {
            System.err.println("Error al parsear las fechas: " + e.getMessage());
        }

        for (GPSData gpsData : datosGPS) {
            try {
                Date timestamp = sdf.parse(gpsData.getTimestamp());
                if (gpsData.getBusId().equals(busId) &&
                        timestamp.after(fechaInicio) && timestamp.before(fechaFin)) {
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

        if (gpsData.getLatitude() < -90 || gpsData.getLatitude() > 90) {
            System.err.println("Latitud fuera de rango: " + gpsData.getLatitude());
            isValid = false;
        }
        if (gpsData.getLongitude() < -180 || gpsData.getLongitude() > 180) {
            System.err.println("Longitud fuera de rango: " + gpsData.getLongitude());
            isValid = false;
        }
        if (gpsData.getSpeed() < 0) {
            System.err.println("Velocidad negativa: " + gpsData.getSpeed());
            isValid = false;
        }

        return isValid;
    }

    // Método para calcular la velocidad media por autobús
    public static double calcularVelocidadMedia(ArrayList<GPSData> datosGPS){
        if (datosGPS == null || datosGPS.isEmpty()) {
            return 0;
        }

        double totalVelocidad = 0;
        int contador = 0;

        for (GPSData dato : datosGPS) {
            totalVelocidad += dato.getSpeed();
            contador++;
        }

        return totalVelocidad / contador;
    }

    public static class ResultadoParadas {
        private Map<String, Integer> conteoParadas;
        private ArrayList<Parada> paradas;

        public ResultadoParadas(Map<String, Integer> conteoParadas, ArrayList<Parada> paradas) {
            this.conteoParadas = conteoParadas;
            this.paradas = paradas;
        }

        public Map<String, Integer> getConteoParadas() {
            return conteoParadas;
        }

        public ArrayList<Parada> getParadas() {
            return paradas;
        }
    }

    // Detecta paradas y retorna tanto conteo como detalles de paradas
    public static ResultadoParadas detectarParadas(ArrayList<GPSData> datos) {
        Map<String, Integer> paradasPorBus = new HashMap<>();
        ArrayList<Parada> listaParadas = new ArrayList<>();

        for (GPSData gpsData : datos) {
            if (gpsData.getSpeed() == 0) {
                String busId = gpsData.getBusId();
                paradasPorBus.put(busId, paradasPorBus.getOrDefault(busId, 0) + 1);

                Parada p = new Parada(busId, gpsData.getTimestamp(), gpsData.getLatitude(), gpsData.getLongitude());
                listaParadas.add(p);
            }
        }

        return new ResultadoParadas(paradasPorBus, listaParadas);
    }

}
