package main.java.model;

public class TanqueModel {
    private final int ALTURA_TANQUE_PIXELES = 200;
    private final double MAX_METROS = 1.0;
    private final double MEDIO_METRO = 0.5;
    private final int PIXELES_POR_METRO = ALTURA_TANQUE_PIXELES;
    
    private int nivelAgua;
    private boolean valvulaAbierta;
    private boolean mitadAlcanzada;

    public TanqueModel() {
        nivelAgua = 0;
        valvulaAbierta = false;
        mitadAlcanzada = false;
    }

    public int getNivelAgua() {
        return nivelAgua;
    }

    public void setNivelAgua(int nivel) {
        nivelAgua = nivel;
    }

    public boolean isValvulaAbierta() {
        return valvulaAbierta;
    }

    public void setValvulaAbierta(boolean estado) {
        valvulaAbierta = estado;
    }

    public boolean isMitadAlcanzada() {
        return mitadAlcanzada;
    }

    public void setMitadAlcanzada(boolean estado) {
        mitadAlcanzada = estado;
    }

    public double getNivelMetros() {
        return nivelAgua / (double) PIXELES_POR_METRO;
    }

    public int getIncrementoLlenado() {
        if (getNivelMetros() < MEDIO_METRO) return 5; // Velocidad completa
        if (getNivelMetros() >= MEDIO_METRO && getNivelMetros() < MAX_METROS) return 1; // Velocidad reducida
        return 0;
    }

    public boolean isTanqueLleno() {
        return getNivelMetros() >= MAX_METROS;
    }

    public void reiniciarTanque() {
        nivelAgua = 0;
        mitadAlcanzada = false;
        valvulaAbierta = false;
    }
}
