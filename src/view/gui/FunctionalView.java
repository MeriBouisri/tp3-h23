package view.gui;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.event.AncestorEvent;
import javax.swing.event.AncestorListener;
import javax.swing.event.ListDataListener;

import model.FunctionalModel;
import model.GestionInventions;
import model.categorie.NoSuchCategoryException;
import model.invention.NoSuchInventionException;
import utils.Listable;
import utils.Updateable;
import view.components.*;

/**
 * La classe {@code FunctionalView} permet de de creer une vue a partir du {@code FunctionalModel}.
 * Cette classe est donc le point intermediaire entre l'interface de demarrage et le controlleur.
 * 
 * NOTE : Cette classe est dans le meme package que {@code InterfaceDemarrage}, car il etait 
 * necessaire d'avoir acces aux composants de la vue pour pouvoir les configurer selon les besoins
 * de l'application, sans les restrictions d'encapsulation.
 */
public class FunctionalView<E extends Updateable> implements ActionListener {
    // 
    private E targetObject;
    private FunctionalModel<E> functionalModel;
    private InterfaceDemarrage gui;
    
    private ArrayList<FunctionalPanel<E>> functionalPanelList;


    public FunctionalView(InterfaceDemarrage gui, FunctionalModel<E> functionalModel, E targetObject) {
        this.gui = gui;
        this.functionalModel = functionalModel;
        this.targetObject = targetObject;
        this.functionalPanelList = new ArrayList<FunctionalPanel<E>>();
        
        this.initializeComboBoxModels();
        this.bindFunctionalMethods();
    }


    public void initializeComboBoxModels() {

		gui.boxCategoSuppression.setModel(new Listable() {
            public List<String> getElementNames() {
                return ((GestionInventions) targetObject)
                            .getCategoryTree().getElementNames();
            }
        });

        gui.boxCategoAjoutInv.setModel(new Listable() {
            public List<String> getElementNames() {
                return ((GestionInventions) targetObject)
                            .getCategoryTree().getElementNames();
            }
        });

        gui.boxInventionModif.setModel(new Listable() {
            public List<String> getElementNames() {
                return ((GestionInventions) targetObject)
                            .getCategoryTree().getAllInventions()
                            .getElementNames();
            }
        });

        gui.boxInventeurAffInventions.setModel(new Listable() {
            public List<String> getElementNames() {
                return Arrays.asList(((GestionInventions) targetObject)
                            .getCategoryTree().getAllInventions()
                            .getAllValuesForField("inventorName"));
            }
        });
        
        
            
	}

    /**
     * Cette methode permet de lier les methodes qui portent l'annotation {@code FunctionalMethod} aux boutons de la vue.
     * Ce sont donc ces methodes qui seront invoquees sur le {@code targetObject} lorsque les boutons seront cliques.
     * @see FunctionalMethod
     * @see FunctionalModel
     */
    public void bindFunctionalMethods() {
       this.retrieveFunctionalPanels();

        gui.addCategoryPane.setCallbackMethod("ADDER");
        gui.deleteCategoryPane.setCallbackMethod("REMOVER");
        gui.addInventionPane.setCallbackMethod("DEEP_ADDER");
        gui.undoActionPane.setCallbackMethod("UNDOER");
        gui.displayInformationPane.setCallbackMethod("DISPLAYER");
        gui.editInventionPane.setCallbackMethod("SETTER");
        gui.displayInventorPane.setCallbackMethod("INVENTOR_DISPLAYER");

        this.updateModels();
    }

    /**
     * Configuration automatique de tous les {@code FunctionalPanels} de l'interface.
     * Cette methode implique l'autoConfiguration des composants de chaque {@code FunctionalPanel} et
     * l'attribution du {@code functionalModel} et du {@code targetObject} a chaque {@code FunctionalPanel}.
     * 
     * Utiliser seulement apres l'initialisation de tous les {@code FunctionalPanels} et de leurs composants.
     * Noter que cette methode est a eviter si l'application utilise des {@code FunctionalModels} differents.
     * Dans ce cas, il faut configurer les {@code FunctionalPanels} ou redefinir cette methode.
     */
    public void retrieveFunctionalPanels() {
        Component[] componentArray = gui.frame.getContentPane().getComponents();

        for (Component component : componentArray) {
            if (component instanceof FunctionalPanel<?>) 
            	// Je ne met pas parametres generiques, car il est possible que l'objet sur lequel
            	// nous effections les operations n'est pas le meme que le type du FunctionalView?
            	this.functionalPanelList.add((FunctionalPanel<E>) component);	
        }
        
        this.setupFunctionalModels();
    }
    
    public void setupFunctionalModels() {
    	
    	this.functionalPanelList.forEach(panel -> {
    		panel.autoConfigureAllComponents();
    		panel.setFunctionalModel(this.functionalModel);
    		panel.setTargetObject(this.targetObject);
    		panel.setGlobalActionListener(this);
    	});
    	
    }

    /**
     * Cette methode permet de mettre a jour les modeles de tous les {@code FunctionalPanel}
     */
    public void updateModels() {
    	this.functionalPanelList.forEach(FunctionalPanel::update);
    }


	@Override
	public void actionPerformed(ActionEvent e) {
		// Au cas ou on a besoin d'evenements globals
		
	}




}
