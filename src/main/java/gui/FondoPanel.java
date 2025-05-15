package gui;

import javax.swing.*;
import java.awt.*;

public class FondoPanel extends JPanel {
    private Image imagen;

    public FondoPanel() {
        this.imagen = new ImageIcon(getClass().getResource("/fondoBus.png")).getImage();
        setLayout(new GridBagLayout()); // Establecer layout aquí mismo
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(1080, 720); // Para asegurar tamaño
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen != null) {
            g.drawImage(imagen, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
