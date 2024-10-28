package main.java.controller;

import main.java.model.TanqueModel;
import main.java.view.TanqueView;

import javax.swing.*;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TanqueController {
    private TanqueModel model;
    private TanqueView view;
    private Timer timer;


    public TanqueController(TanqueModel model, TanqueView view) {
        this.model = model;
        this.view = view;

        view.getBotonValvula().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                manejarValvulaManual();
            }
        });

        view.getBotonLlenadoManual().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                reiniciarLlenadoManual();
            }
        });

        view.getBotonLlenadoAutomatico().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                iniciarLlenadoAutomatico();
            }
        });
    }

    private void manejarValvulaManual() {
        model.setValvulaAbierta(!model.isValvulaAbierta());
        view.getBotonValvula().setText(model.isValvulaAbierta() ? "abierta" : "cerrada");
        view.getBotonValvula().setBackground(model.isValvulaAbierta() ? Color.GREEN : Color.RED);
        if (model.isValvulaAbierta()) iniciarLlenado();
        else detenerLlenado();
    }

    private void reiniciarLlenadoManual() {
        detenerLlenado(); // Asegura que el temporizador de llenado esté detenido
        model.reiniciarTanque(); // Restablece el nivel de agua y otras propiedades en el modelo
        view.actualizarTanque(model.getNivelAgua()); // Actualiza la vista con el nivel de agua restablecido
        view.getEtiquetaEstado().setText("Estado: Válvula cerrada. Listo para reiniciar."); // Actualiza el estado de la vista
        view.getBotonLlenadoAutomatico().setEnabled(false);
        manejarValvulaManual();

    }

    private void iniciarLlenado() {
        timer = new Timer(200, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                view.getBotonValvula().setEnabled(true);
                if (!model.isMitadAlcanzada() && model.getNivelMetros() >= 0.5) {
                    model.setMitadAlcanzada(true);
                    view.getEtiquetaEstado().setText("Estado: Tanque al 50%. Llenando más lento.");
                }

                model.setNivelAgua(model.getNivelAgua() + model.getIncrementoLlenado());
                view.getEtiquetaEstado().setText("Estado: Llenando tanque al " + (int) (model.getNivelMetros() * 100) + "%");
                view.actualizarTanque(model.getNivelAgua());

                if (model.isTanqueLleno()) {
                    detenerLlenado();
                    view.getEtiquetaEstado().setText("Estado: Tanque lleno al 100%.");
                    view.getBotonValvula().setText("cerrada");
                    view.getBotonValvula().setBackground(Color.RED);
                    view.getBotonLlenadoManual().setEnabled(true);
                    view.getBotonLlenadoAutomatico().setEnabled(true);
                    model.setValvulaAbierta(!model.isValvulaAbierta());
                    view.getBotonValvula().setEnabled(false);
                }
            }
        });
        timer.start();
    }

    private void iniciarLlenadoAutomatico() {
        model.reiniciarTanque();
        model.setValvulaAbierta(!model.isValvulaAbierta());
        view.getBotonLlenadoAutomatico().setEnabled(false);
        view.getBotonValvula().setEnabled(false);
        view.getBotonValvula().setText(model.isValvulaAbierta() ? "abierta" : "cerrada");
        view.getBotonValvula().setBackground(model.isValvulaAbierta() ? Color.GREEN : Color.RED);

        if (model.isValvulaAbierta()) {
            timer = new Timer(200, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    if (!model.isMitadAlcanzada() && model.getNivelMetros() >= 0.5) {
                        model.setMitadAlcanzada(true);
                        view.getEtiquetaEstado().setText("Estado: Tanque al 50%. Llenando más lento.");
                    }

                    model.setNivelAgua(model.getNivelAgua() + model.getIncrementoLlenado());
                    view.getEtiquetaEstado().setText("Estado: Llenando tanque al " + (int) (model.getNivelMetros() * 100) + "%");
                    view.actualizarTanque(model.getNivelAgua());

                    if (model.isMitadAlcanzada() && model.getNivelMetros() >= 0.6) {
                        model.setNivelAgua(100);
                        view.actualizarTanque(model.getNivelAgua());
                    }
                }
            });
            timer.start();
        }
        else detenerLlenado();
    }
    private void detenerLlenado() {
        if (timer != null) timer.stop();
    }




}
