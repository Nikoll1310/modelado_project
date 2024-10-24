
// initial code
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SimulacionTanque extends JFrame {
    private JPanel panelTanque;
    private JButton botonValvula;
    private JLabel etiquetaEstado;
    private boolean valvulaAbierta;
    private int nivelAgua;
    private Timer timer;
    private boolean mitadAlcanzada;

    // Relación entre nivel de agua (en pixeles) y metros
    private final int ALTURA_TANQUE_PIXELES = 200; // 1 metro en la simulación
    private final double MAX_METROS = 1.0; // 1 metro
    private final double MEDIO_METRO = 0.5; // 0.5 metros
    private final int PIXELES_POR_METRO = ALTURA_TANQUE_PIXELES;

    public SimulacionTanque() {
        setTitle("Simulación de llenado de un tanque");
        setSize(300, 450);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        panelTanque = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Dibujar el contorno del tanque
                g.setColor(Color.BLACK);
                g.drawRect(100, 50, 100, ALTURA_TANQUE_PIXELES);
                
                // Llenar el tanque según el nivel de agua
                g.setColor(Color.BLUE);
                g.fillRect(100, 250 - nivelAgua, 100, nivelAgua);

                // Mostrar el nivel en metros
                g.setColor(Color.BLACK);
                g.drawString(String.format("Nivel de agua: %.2f metros", nivelAgua / (double) PIXELES_POR_METRO), 100, 30);
            }
        };
        panelTanque.setPreferredSize(new Dimension(300, 300));
        add(panelTanque, BorderLayout.CENTER);

        // Crear el botón de la válvula
        botonValvula = new JButton("Abrir Válvula");
        botonValvula.setBackground(Color.RED);
        botonValvula.setForeground(Color.WHITE);
        botonValvula.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Cambiar el estado de la válvula
                valvulaAbierta = !valvulaAbierta;
                if (valvulaAbierta) {
                    botonValvula.setText("Cerrar Válvula");
                    botonValvula.setBackground(Color.GREEN);
                    iniciarLlenado();
                } else {
                    botonValvula.setText("Abrir Válvula");
                    botonValvula.setBackground(Color.RED);
                    detenerLlenado();
                }
            }
        });
        add(botonValvula, BorderLayout.SOUTH);

        // Crear la etiqueta para el estado del llenado
        etiquetaEstado = new JLabel("Estado: Válvula cerrada.");
        etiquetaEstado.setHorizontalAlignment(SwingConstants.CENTER);
        add(etiquetaEstado, BorderLayout.NORTH);

        nivelAgua = 0;
        valvulaAbierta = false;
        mitadAlcanzada = false; // Indicador para saber si ha llegado a la mitad
    }

    private void iniciarLlenado() {
        // Crear un Timer para simular el llenado del tanque
        timer = new Timer(100, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double nivelMetros = nivelAgua / (double) PIXELES_POR_METRO;

                if (nivelMetros < MEDIO_METRO) {
                    // Llenar al 100% de velocidad hasta 0.5 metros
                    nivelAgua += 5;
                    etiquetaEstado.setText("Estado: Llenando... a velocidad completa.");
                } else if (nivelMetros >= MEDIO_METRO && !mitadAlcanzada) {
                    // Notificar que llegó a la mitad
                    mitadAlcanzada = true;
                    etiquetaEstado.setText("Estado: El tanque está al 50%. Llenando más lento.");
                } else if (nivelMetros >= MEDIO_METRO && nivelMetros < MAX_METROS) {
                    // Llenar al 50% de velocidad después de 0.5 metros
                    nivelAgua += 2; // Llenado más lento (50%)
                }

                panelTanque.repaint();

                if (nivelMetros >= MAX_METROS) {
                    detenerLlenado();
                    etiquetaEstado.setText("Estado: El tanque está lleno al 100%. Reiniciando...");
                    reiniciarSimulacion();
                }
            }
        });
        timer.start();
    }

    private void detenerLlenado() {
        if (timer != null) {
            timer.stop();
        }
    }

    private void reiniciarSimulacion() {
        Timer reiniciarTimer = new Timer(2000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                nivelAgua = 0;
                mitadAlcanzada = false;
                botonValvula.setText("Abrir Válvula");
                botonValvula.setBackground(Color.RED);
                valvulaAbierta = false;
                panelTanque.repaint();
                etiquetaEstado.setText("Estado: Válvula cerrada.");
            }
        });
        reiniciarTimer.setRepeats(false);
        reiniciarTimer.start();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new SimulacionTanque().setVisible(true);
            }
        });
    }
}