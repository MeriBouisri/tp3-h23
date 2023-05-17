package controller;

import model.FunctionalModel;
import model.GestionInventions;
import utils.Updateable;
import view.gui.FunctionalView;
import view.gui.InterfaceDemarrage;

/**
 * La classe {@code Controller} permet de lier le modèle et le view
 */
public class Controller implements Updateable {
    private FunctionalModel<GestionInventions> funModel;
    private FunctionalView<GestionInventions> funView;
    
    /**
     * Bind le view au model.
     * @param view L'interface graphique de l'application
     * @param model Le modele de donnees de l'application
     */
    public Controller(InterfaceDemarrage view, GestionInventions model) {
        funModel = new FunctionalModel<GestionInventions>(GestionInventions.class);
        funView = new FunctionalView<GestionInventions>(view, funModel, model);
    }
    
    @Override
    public void update() {
        this.funView.updateModels();
    }
}
