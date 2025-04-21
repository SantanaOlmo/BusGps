package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;

public class Desplegable extends JPanel {
        private int arcWidth = 30;
        private int arcHeight = 30;

    public Desplegable(){
        setSize(400,720);
        this.setOpaque(false); // Importante para transparencia


    }
    //CHATGPT:
        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getBackground());
            g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), arcWidth, arcHeight));
            //PARA HACER QUE EL JPANEL SEA REDONDEADO
            g2.dispose();
            super.paintComponent(g);
        }

}
