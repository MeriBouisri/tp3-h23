package view.components;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.function.Consumer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.event.AncestorListener;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import annotations.FunctionalMethod;
import model.FunctionalModel;
import utils.Readable;
import utils.Updateable;

/**
 * Un {@code FunctionalPanel} est un {@code JPanel} qui sert de representation visuelle d'une methode. 
 * Un {@code FunctionalPanel} doit contenir un objet {@code JButton} qui permet d'invoquer la fonction de rappel ({@code callbackMethod}) qui lui est associee.
 * Un {@code FunctionalPanel} peut optionnellement contenir des composants {@code IReadable}. Les donnees lues a partir des composants {@code IReadable} 
 * seront passees en parametres a la fonction de rappel. Si aucun composant {@code IReadable} n'est present, la fonction de rappel sera invoquee sans parametres.
 * La
 */
public class FunctionalPanel<E extends Updateable> extends JPanel implements ActionListener, DocumentListener {

    
	private static final long serialVersionUID = 1L;
	
	private JButton eventButton;
	
    private ArrayList<Readable> dataSourceList;

    /**
     * Un {@code callbackMethod} est, a la base, une methode definie dans la classe du {@code targetObject}.
     * Donc ce n'est pas reellement une fonction de rappel, mais elle est utilisee comme telle.
     */
    private Method callbackMethod;

    /**
     * Le {@code targetObject} est l'objet sur lequel la methode {@code callbackMethod} sera invoquee.
     * Le {@code targetObject} doit etre une instance de la classe qui contient la methode {@code callbackMethod}.
     * L'invocation de {@code callbackMethod} sur {@code targetObject} est l'equivalent de {@code targetObject.callbackMethod()}.
     */
    private E targetObject;

    /**
     * Le {@code functionalModel} permet de convertir une classe en un modele fonctionnel 
     * compatible avec {@code FunctionalPanel}. 
     */
    private FunctionalModel<E> functionalModel;
    
    /**
     * Constructeur permettant d'initialiser un {@code FunctionalPanel} 
     */
    public FunctionalPanel() {
        super();
        this.dataSourceList = new ArrayList<Readable>();
 
    }

    public FunctionalPanel(FunctionalModel<E> functionelModel) {
        this();
        this.functionalModel = functionelModel;
    }

    public void setFunctionalModel(FunctionalModel<E> functionelModel) {
        this.functionalModel = functionelModel;
    }

    public void setTargetObject(E targetObject) {
        this.targetObject = targetObject;
    }

    /**
     * Configuration automatique de tous les composants du {@code FunctionalPanel} en cherchant
     * tous les composants JButton et IReadable.
     */
    public void autoConfigureAllComponents() {
        Component[] components = this.sortComponentsByDisplay(this.getComponents());

        if (components.length == 0)
            return;
        
        for (Component comp : components) {

            if (comp instanceof JButton)
                this.setEventButton((JButton) comp);
            
            else if (comp instanceof Readable) {
                this.addDataSource((Readable) comp);
            }
        }
    }

    public void setDataSourceEventListeners(Readable readableDataSource) {
    	readableDataSource.addEventListener(this);
    }

    public void clearAllReadableComponents() {
        this.dataSourceList.forEach(Readable::clearData);
    }

    public void setCallbackMethod(Method callbackMethod) {
        this.callbackMethod = callbackMethod;
       
    }

    /**
     * Attribuer le {@code callbackMethod} a partir du role passe en parametre. Si plusieurs methodes
     * ont ete annotees avec le meme role, la premiere methode trouvee sera utilisee.
     * @param role Le String qui represente le role de la fonction de rappel, tel que defini avec les annotations
     * {@code @FunctionalMethod} et {@code @FunctionalRole}.
     * @see FunctionalMethod
     * @see FunctionalRole
     */
    public void setCallbackMethod(String role) {
        this.callbackMethod = this.functionalModel.filterByRole(role);
    }

    /**
     * Trier la liste des composants selon l'ordre d'affichage vertical.
     * Les composants les plus hauts (selon l'axe Y) seront les premiers dans la liste.
     * @param compList La liste des composants non-tries
     * @return La liste des composants tries.
     */
    public Component[] sortComponentsByDisplay(Component[] compList) {

        // Comparer la position verticale de deux composants
        Comparator<Component> compareComponentPosition = 
                (comp1, comp2) -> { return comp1.getY() - comp2.getY(); };

        // Trier la liste
        return Arrays.asList(compList).stream()
                    .sorted(compareComponentPosition)
                    .toArray(Component[]::new);
    }
    
    

    /**
     * @return {@code true} si et seulement si tous les composants {@code IReadable} du 
     * {@code FunctionalPanel} contiennent du texte.
     */
    public boolean allEmptyDataSources() {
        return this.dataSourceList.stream().anyMatch(Readable::isEmpty);
    }

    /**
     * Attribuer un bouton qui permetta d'invoquer la fonction de rappel.
     * Le {@code ActionListener} est automatiquement ajoute au bouton.
     */
    public void setEventButton(JButton eventButton) {
        this.eventButton = eventButton;
        this.eventButton.addActionListener(this);   
    }
    
    /**
     * @param globalListener ActionListener optionnel pour dispatch l'event a d'autres components
     */
    public void setGlobalActionListener(ActionListener globalListener) {
    	this.eventButton.addActionListener(globalListener);
    }

    /**
     * Ajouter un composant {@code IReadable} au {@code FunctionalPanel}.
     * Un {@code EventListener} approprie est automatiquement ajoute au composant.
     * @param dataSource Le composant {@code IReadable} a ajouter.
     */
    public void addDataSource(Readable readableDataSource) {
        if (this.dataSourceList.add(readableDataSource))
        	readableDataSource.addEventListener(this);
    }

    /**
     * Ajouter une liste de composants {@code IReadable} au {@code FunctionalPanel}.
     * Des {@code EventListener} appropries sont automatiquement ajoutes aux composants.
     * @param dataSourceList La liste des composants {@code IReadable} a ajouter.
     */
    public void addAllDataSource(Collection<Readable> dataSourceList) {
        if (this.dataSourceList.addAll(dataSourceList))
        	this.dataSourceList.forEach(readable -> readable.addEventListener(this));
    }

    /**
     * Retirer un composant {@code IReadable} du {@code FunctionalPanel}.
     * @param dataSource Le composant {@code IReadable} a retirer.
     * @return {@code true} si le composant a ete retire avec succes.
     */
    public boolean removeDataSource(Readable readableDataSource) {
    	if (!this.dataSourceList.remove(readableDataSource)) return false;
    
    	readableDataSource.removeEventListener(this);
    	
        return true;
    }

    public String[] retrieveAllData() {
        return this.dataSourceList
                    .stream()
                    .map(Readable::readData)
                    .toArray(String[]::new);
    }

    /**
     * Verifier si tous les composants {@code IReadable} du {@code FunctionalPanel} contiennent du texte.
     * Sinon, le bouton d'evenement est desactive.
     */
    public void update() {
        this.eventButton.setEnabled(!this.allEmptyDataSources());   
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == this.eventButton) {

            // Invoquer la methode de rappel sur le target object, avec les donnees recuperees des composants IReadable
            this.functionalModel.invokeMethod(this.targetObject, this.callbackMethod, this.retrieveAllData());

            this.clearAllReadableComponents();
            this.targetObject.update();

        } else this.update();
    }

    /**
     * Le DocumentListener sert a detecter les changements dans les JTextField au fur et a mesure.
     */

    @Override
    public void insertUpdate(DocumentEvent e) {
       this.update();
    }

    @Override
    public void removeUpdate(DocumentEvent e) {
      this.update();
    }

    @Override
    public void changedUpdate(DocumentEvent e) {


    }
}
