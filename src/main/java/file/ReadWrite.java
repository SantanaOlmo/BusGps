package file;

import logica.GPSData;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadWrite {

    private static final String RUTA = "gps_data.csv";

    public static void escribirDatosGPS(ArrayList<GPSData> listaDatos) {
        boolean archivoExiste = new File(RUTA).exists();
        boolean escribirCabecera = true;

        try {
            if (archivoExiste) {
                escribirCabecera = Files.size(Paths.get(RUTA)) == 0;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA, true))) {
                if (escribirCabecera) {
                    writer.write("busId,timestamp,latitude,longitude,speed\n");
                }

                for (GPSData data : listaDatos) {
                    writer.write(String.format("%s,%s,%.6f,%.6f,%.2f\n",
                            data.getBusId(),
                            data.getTimestamp(),
                            data.getLatitude(),
                            data.getLongitude(),
                            data.getSpeed()));
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public static ArrayList<GPSData> leerDatosGPS() throws IOException {
        ArrayList<GPSData> datos = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(RUTA))) {
            String linea;
            reader.readLine(); // Saltar cabecera
            while ((linea = reader.readLine()) != null) {
                String[] partes = linea.split(",");
                if (partes.length == 5) {
                    String busId = partes[0];
                    String timestamp = partes[1];
                    double latitude = Double.parseDouble(partes[2]);
                    double longitude = Double.parseDouble(partes[3]);
                    double speed = Double.parseDouble(partes[4]);
                    datos.add(new GPSData(busId, timestamp, latitude, longitude, speed));
                }
            }
        }

        return datos;
    }


    public static void archivar() throws IOException {
        File archivoOriginal = new File(RUTA);
        if (!archivoOriginal.exists()) {
            System.out.println("El archivo GPS no existe y no puede archivarse.");
            return;
        }

        File carpetaArchivados = new File("archivados");
        if (!carpetaArchivados.exists()) {
            carpetaArchivados.mkdir(); // Crea la carpeta si no existe
        }

        // Obtener fecha y hora actual para nombre único
        java.time.format.DateTimeFormatter formatter = java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        String fechaHora = java.time.LocalDateTime.now().format(formatter);

        // Crear nombre del archivo archivado
        String nombreArchivado = "gps_data_" + fechaHora + ".csv";
        File archivoDestino = new File(carpetaArchivados, nombreArchivado);

        // Mover el archivo
        Files.move(archivoOriginal.toPath(), archivoDestino.toPath());

        // Crear nuevo archivo gps_data.csv vacío con cabecera
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA))) {
            writer.write("busId,timestamp,latitude,longitude,speed\n");
        }

        System.out.println("Archivo archivado como: " + archivoDestino.getPath());
        System.out.println("Nuevo archivo gps_data.csv creado.");
    }


    public static void guardarLoteGPS(ArrayList<GPSData> datos) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA))) {
            writer.write("busId,timestamp,latitude,longitude,speed\n"); // Cabecera
            for (GPSData d : datos) {
                writer.write(String.format("%s,%s,%.6f,%.6f,%.2f\n",
                        d.getBusId(), d.getTimestamp(), d.getLatitude(), d.getLongitude(), d.getSpeed()));
            }
        }
    }

    public static void verificarYArchivarSiEsOtroDia() throws IOException {
        File archivoOriginal = new File(RUTA);

        if (!archivoOriginal.exists()) {
            return; // No hay nada que verificar ni archivar
        }

        // Obtener fecha de última modificación del archivo
        long ultimaModificacion = archivoOriginal.lastModified();
        java.time.LocalDate fechaArchivo =
                java.time.Instant.ofEpochMilli(ultimaModificacion)
                        .atZone(java.time.ZoneId.systemDefault())
                        .toLocalDate();

        // Obtener fecha actual
        java.time.LocalDate fechaActual = java.time.LocalDate.now();

        // Comparar fechas
        if (!fechaArchivo.equals(fechaActual)) {
            archivar(); // Si el archivo es de otro día, archivarlo
        }
    }

}

