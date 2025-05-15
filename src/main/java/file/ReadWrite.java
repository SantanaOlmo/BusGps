package file;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

public class ReadWrite {

    private static final String RUTA = "gps_data.csv";

    public static void escribirDatosGPS(String busId, String timestamp, double latitude, double longitude, double speed) {
        boolean archivoExiste = new File(RUTA).exists();
        boolean escribirCabecera = true;

        try {
            if (archivoExiste) {
                // Verifica si el archivo tiene contenido
                escribirCabecera = Files.size(Paths.get(RUTA)) == 0;
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(RUTA, true))) {
                if (escribirCabecera) {
                    writer.write("busId,timestamp,latitude,longitude,speed\n");
                }

                writer.write(String.format("%s,%s,%.6f,%.6f,%.2f\n",
                        busId, timestamp, latitude, longitude, speed));
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<String[]> leerDatosGPS() throws IOException {
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



}

