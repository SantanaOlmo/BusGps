package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;

import file.*;
import logica.GPSData;
import logica.Menu;
import logica.Parada;

import static logica.Menu.datosGPS;

public class VentanaGPS extends JFrame {

    public VentanaGPS() {
        setTitle("Sistema de Seguimiento GPS");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(1080, 720);
        setLocationRelativeTo(null);

        FondoPanel fondo = new FondoPanel();
        setContentPane(fondo); // Establecer como fondo
        fondo.setLayout(new GridBagLayout());

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = GridBagConstraints.RELATIVE;
        gbc.insets = new Insets(15, 15, 15, 15);

        JPanel panelBotones = new JPanel();
        panelBotones.setOpaque(false); // Para que el fondo se vea
        panelBotones.setLayout(new BoxLayout(panelBotones, BoxLayout.Y_AXIS));

        // Añadir botones con separación entre ellos
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(crearBoton("Generar datos GPS", e -> {
            try {
                ReadWrite.verificarYArchivarSiEsOtroDia();
                GenerarDatosGPS.generarDatos();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        }));

        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(crearBoton("Cargar CSV", e -> Menu.cargarCSV()));

        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(crearBoton("Calcular velocidad media", e -> {

            double velocidadMedia = ProcesarGPSdata.calcularVelocidadMedia(Menu.cargarCSV());
            JOptionPane.showMessageDialog(this, "Velocidad media: " + velocidadMedia + " km/h");

        }));

        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(crearBoton("Detectar paradas", e -> {
            ArrayList<GPSData> datos = Menu.cargarCSV();
            ProcesarGPSdata.ResultadoParadas resultado = ProcesarGPSdata.detectarParadas(datos);

            StringBuilder mensaje = new StringBuilder();
            mensaje.append("Paradas detectadas por bus:\n");
            for (var entry : resultado.getConteoParadas().entrySet()) {
                mensaje.append(String.format("Bus %s: %d paradas\n", entry.getKey(), entry.getValue()));
            }
            mensaje.append("\nDetalles de las paradas:\n");
            for (Parada parada : resultado.getParadas()) {
                mensaje.append(parada.toString()).append("\n");
            }

            JOptionPane.showMessageDialog(null, mensaje.toString(), "Paradas detectadas", JOptionPane.INFORMATION_MESSAGE);
        }));

        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(crearBoton("Mostrar estadísticas", e -> {
            String estadisticas = Estadisticas.mostrarEstadisticas(Menu.cargarCSV());
            JOptionPane.showMessageDialog(this, estadisticas, "Estadísticas de GPS", JOptionPane.INFORMATION_MESSAGE);
        }));

        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(crearBoton("Actualizar Json",e -> {
            JsonManager.actualizarJsonUltimaPosicion(Menu.cargarCSV());
        }));
        panelBotones.add(Box.createVerticalStrut(10));
        panelBotones.add(crearBoton("Mostrar archivos Json",e -> {
            JsonViewer.mostrarContenidoJsons();

        }));

        fondo.add(panelBotones, gbc);
        setVisible(true);
    }


    private JButton crearBoton(String texto, ActionListener action) {
        JButton boton = new JButton(texto);
        boton.setAlignmentX(Component.CENTER_ALIGNMENT);
        boton.setPreferredSize(new Dimension(300, 40));
        boton.setMaximumSize(new Dimension(300, 40));

        // 🎨 Estilo personalizado
        boton.setBackground(new Color(0x6fc0f4)); // Fondo azul claro
        boton.setForeground(Color.WHITE);         // Texto blanco
        boton.setFocusPainted(false);
        boton.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Padding interno
        boton.setFont(new Font("Arial", Font.BOLD, 14));

        // 🔵 Bordes redondeados con UI personalizada
        boton.setContentAreaFilled(false);
        boton.setOpaque(true);

        boton.addActionListener(action);
        return boton;
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(VentanaGPS::new);
    }
}
