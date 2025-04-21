package depurar;

import javax.swing.*;

import java.awt.*;
import java.net.URL;

public class Imagen {

    public static ImageIcon imageIconDepurada(String nombreArchivo, String rutaImagen){
        URL ruta = Imagen.class.getClassLoader().getResource(rutaImagen);

        if (ruta == null) {
            System.err.println("❌ No se pudo encontrar "+ nombreArchivo+ " en: " + ruta);
        } else {
            System.out.println("✅" + nombreArchivo+" encontrado en: " + ruta.getPath());
        }
        ImageIcon imagen = new ImageIcon(ruta);
        return imagen;
    }
    public static Image imagenDepurada(String nombreArchivo, String rutaImagen) {
        URL ruta = Imagen.class.getClassLoader().getResource(rutaImagen);

        if (ruta == null) {
            System.err.println("❌ No se pudo encontrar " + nombreArchivo + " en: " + rutaImagen);
            return null; // Retorna null si la imagen no se encuentra
        } else {
            System.out.println("✅ " + nombreArchivo + " encontrado en: " + ruta.getPath());
        }

        return new ImageIcon(ruta).getImage(); // Devuelve directamente el objeto Image
    }

    public static ImageIcon resizedImagenDepurada(String nombreArchivo, String rutaImagen, int ancho, int alto){
        URL ruta = Imagen.class.getClassLoader().getResource(rutaImagen);

        if (ruta == null) {
            System.err.println("❌ No se pudo encontrar "+ nombreArchivo+ " en: " + ruta);
        } else {
            System.out.println("✅" + nombreArchivo+" encontrado en: " + ruta.getPath());
        }
        ImageIcon imagen = new ImageIcon(ruta);
        Image imagenRedimensionada= imagen.getImage().getScaledInstance(ancho, alto,Image.SCALE_DEFAULT);
        ImageIcon imagenFinal = new ImageIcon(imagenRedimensionada);

        return imagenFinal ;
    }
    public static ImageIcon resizedImageIconDepurada(String nombreArchivo, String rutaImagen, int ancho, int alto){
        URL ruta = Imagen.class.getClassLoader().getResource(rutaImagen);

        if (ruta == null) {
            System.err.println("❌ No se pudo encontrar "+ nombreArchivo+ " en: " + ruta);
        } else {
            System.out.println("✅" + nombreArchivo+" encontrado en: " + ruta.getPath());
        }
        ImageIcon imagen = new ImageIcon(ruta);

        return imagen ;
    }

}
