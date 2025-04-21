package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionAdapter;

public class Ventana extends JFrame {
    private Point initialClick;

    public Ventana(){
        setTitle("BusGPS");
        setSize(1080,720);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setIconImage(Imagen.imagenDepurada("busIcon.png","busIcon.png"));
        decorarBarradeTítulo();

        // CARGA LA PANTALLA DE INICIO"
        mostrarPantallaPrincipal();
        this.setVisible(true);

    }

    public void mostrarPantallaPrincipal(){
        new PantallaPrincipal(this);
    }

    public void decorarBarradeTítulo(){
        this.setUndecorated(true);

        JPanel titleBar = new JPanel(new BorderLayout());
        titleBar.setBackground(new Color(45, 45, 45));
        titleBar.setPreferredSize(new Dimension(getWidth(), 35));

        // Botones
        JPanel buttons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 5, 3));
        buttons.setOpaque(false);

        JButton btnMin = new JButton("_");
        JButton btnClose = new JButton(Imagen.resizedImageIconDepurada("X.png","X.png",30,30));

        styleButton(btnMin);
        styleButton(btnClose);

        btnMin.addActionListener(e -> setState(Frame.ICONIFIED));
        btnClose.addActionListener(e -> dispose());

        buttons.add(btnMin);
        buttons.add(btnClose);
        titleBar.add(buttons, BorderLayout.EAST);

        // Drag para mover ventana
        titleBar.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent e) {
                initialClick = e.getPoint();
            }
        });

        titleBar.addMouseMotionListener(new MouseMotionAdapter() {
            public void mouseDragged(MouseEvent e) {
                int thisX = getLocation().x;
                int thisY = getLocation().y;

                int xMoved = e.getX() - initialClick.x;
                int yMoved = e.getY() - initialClick.y;

                setLocation(thisX + xMoved, thisY + yMoved);
            }
        });

        add(titleBar, BorderLayout.NORTH);
    }

    private void styleButton(JButton btn) {
        btn.setFocusPainted(false);
        btn.setBorderPainted(false);
        btn.setForeground(Color.WHITE);
        btn.setBackground(new Color(80, 80, 80));
        btn.setPreferredSize(new Dimension(40, 24));
        btn.setFont(new Font("Dialog", Font.BOLD, 12));
    }


    }


}
