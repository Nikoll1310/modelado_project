package main.java.view;
import javax.imageio.ImageIO;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;


public class TanqueView extends JFrame {
    private JPanel panelTanque;
    private JButton botonValvula;

    private JButton botonLlenadoManual;

    private JButton botonLlenadoAutomatico;

    private JLabel etiquetaEstado;
    private int nivelAgua = 0; // Nivel de agua en píxeles (0 a 200)


    public TanqueView() {
        setTitle("Simulación de llenado de un tanque");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        Image house = loadImage("src/main/resources/house.png");
        Image platfform = loadImage("src/main/resources/platfform.png");
        Image sun = loadImage("src/main/resources/sun.png");
        Image cloud1 = loadImage("src/main/resources/cloud.png");
        Image cloud2 = loadImage("src/main/resources/cloud.png");

        panelTanque = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(Color.BLACK);
                g.drawRect(150, 100, 100, 200); // Contorno del tanque

                // Dibujar el agua dentro del tanque según el nivel de agua actual
                g.setColor(Color.BLUE);
                g.fillRect(150, 300 - nivelAgua, 100, nivelAgua); // Llenado progresivo desde la parte inferior

                // Dibujar la manguera de entrada
                g.setColor(Color.GRAY);
                g.fillRect(50, 220, 100, 10); // Manguera de entrada

                g.setColor(Color.GRAY);
                g.fillRect(50, 230, 10, 220); // Manguera de entrada

                // Dibujar la manguera de salida
                g.setColor(Color.GRAY);
                g.fillRect(251, 292, 100, 10); // Manguera de salida

                // Dibujar la imagen de la casita al lado del tanque
                if (house != null) {
                    g.drawImage(house, 300, 200, 200, 250, this); // Ajusta la posición y el tamaño según necesites
                }
                if(platfform != null){
                    g.drawImage(platfform, 149, 300, 100, 150, this);
                }
                if(sun != null){
                    g.drawImage(sun, 0, 0, 100, 100, this);
                }
                if(cloud1 != null){
                    g.drawImage(cloud1, 250, 20, 100, 100, this);
                }
                if(cloud2 != null){
                    g.drawImage(cloud2, 350, 20, 100, 100, this);
                }
            }
        };
        panelTanque.setPreferredSize(new Dimension(400, 400));
        panelTanque.setLayout(null); // Establecer layout a null para posicionamiento manual
        add(panelTanque, BorderLayout.CENTER);

        // Botón de abrir y cerrar válvula
        botonValvula = new JButton("cerrada");
        botonValvula.setBackground(Color.RED);
        botonValvula.setForeground(Color.WHITE);
        botonValvula.setBounds(15, 400, 80, 30); // Establecer posición y tamaño (x, y, width, height)
        panelTanque.add(botonValvula); // Agregar el botón al panel del tanque

        // Panel de control
        JPanel panelControl = new JPanel();
        panelControl.setLayout(new FlowLayout());

        // Botón de llenado manual
        botonLlenadoManual = new JButton("Llenado Manual");
        panelControl.add(botonLlenadoManual);

        // Botón de llenado automático
        botonLlenadoAutomatico = new JButton("Llenado Automático");
        panelControl.add(botonLlenadoAutomatico);

        add(panelControl, BorderLayout.SOUTH);

        // Etiqueta de estado
        etiquetaEstado = new JLabel("Estado: Válvula cerrada.");
        etiquetaEstado.setHorizontalAlignment(SwingConstants.CENTER);
        add(etiquetaEstado, BorderLayout.NORTH);
    }

    /**
     * Actualiza el nivel de agua del tanque y repinta el panel para reflejar el cambio.
     *
     * @param nivelAgua El nivel de agua en píxeles (0 a 200).
     */
    public void actualizarTanque(int nivelAgua) {
        this.nivelAgua = nivelAgua; // Actualiza el nivel de agua
        panelTanque.repaint(); // Redibuja el tanque con el nuevo nivel de agua
    }


    private Image loadImage(String path) {
        return Toolkit.getDefaultToolkit().getImage(path);
    }


    public JButton getBotonValvula() {
        return botonValvula;
    }

    public JLabel getEtiquetaEstado() {
        return etiquetaEstado;
    }

    public JButton getBotonLlenadoManual() {
        return botonLlenadoManual;
    }

    public JButton getBotonLlenadoAutomatico() {
        return botonLlenadoAutomatico;
    }
}
