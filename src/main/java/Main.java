package main.java;

import main.java.model.TanqueModel;
import main.java.view.TanqueView;
import main.java.controller.TanqueController;

public class Main {
    public static void main(String[] args) {
        TanqueModel model = new TanqueModel();
        TanqueView view = new TanqueView();
        new TanqueController(model, view);
        view.setLocationRelativeTo(null);
        view.setVisible(true);
    }
}
