package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;

import file.Estadisticas;
import file.GenerarDatosGPS;
import file.ProcesarGPSdata;
import file.ReadWrite;
import logica.Menu;

public class VentanaGPS extends JFrame {

    public VentanaGPS() {
        setTitle("Sistema de Seguimiento GPS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        JLabel titulo = new JLabel("Selecciona una opción del sistema GPS:", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 16));
        add(titulo, BorderLayout.NORTH);

        JPanel panelBotones = new JPanel(new GridLayout(0, 1, 10, 10));

        // Actualización de las llamadas a métodos en los botones
        panelBotones.add(crearBoton("Generar datos GPS", e -> {
            try {
                ReadWrite.archivar();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }

            GenerarDatosGPS.generarDatos();
        })); // Método generacion de datos GPS
        panelBotones.add(crearBoton("Cargar CSV", e -> {
            Menu.cargarCSV(); // Método cargarCSV actualizado
        }));
        /*panelBotones.add(crearBoton("Filtrar por autobús y hora", e -> Menu.filtrarDatos())); // Método filtrarDatos*/
        panelBotones.add(crearBoton("Calcular velocidad media", e -> {
            double velocidadMedia = 0; // Obtener datos y calcular velocidad media
            velocidadMedia = ProcesarGPSdata.calcularVelocidadMedia(Menu.cargarCSV());
            JOptionPane.showMessageDialog(this, "Velocidad media: " + velocidadMedia + " km/h");
        }));
        panelBotones.add(crearBoton("Detectar paradas", e -> {
            // Se muestra las paradas detectadas
            JOptionPane.showMessageDialog(this, "Paradas detectadas: " + ProcesarGPSdata.detectarParadas(Menu.cargarCSV()));
        }));
        panelBotones.add(crearBoton("Mostrar estadísticas", e -> {
            String estadisticas = Estadisticas.mostrarEstadisticas(Menu.cargarCSV());
            JOptionPane.showMessageDialog(this, estadisticas, "Estadísticas de GPS", JOptionPane.INFORMATION_MESSAGE);
        }));


        add(panelBotones, BorderLayout.CENTER);
        setVisible(true);
    }

    private JButton crearBoton(String texto, ActionListener action) {
        JButton boton = new JButton(texto);
        boton.addActionListener(action);
        return boton;
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaGPS::new);
    }
}
