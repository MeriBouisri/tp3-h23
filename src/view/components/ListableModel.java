package view.components;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.event.ListDataEvent;
import javax.swing.event.ListDataListener;

import model.categorie.Category;
import utils.Listable;

/**
 * La classe {@code ListableModel} permet de creer un modele de donnees a 
 * partir d'un objet qui implemente l'interface {@code Listable}. Les items du ComboBox
 * changent de facon dynamique, selon les donnees de l'objet {@code Listable}.
 */
public class ListableModel extends DefaultComboBoxModel<String> {

    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	Listable data;

    public ListableModel(Listable data) {
    	
    	//this.addListDataListener(this);
        this.data = data;    
    }
    


    @Override
    public int getSize() {
    
    	if (!validSelection())
    		super.setSelectedItem(null);

        return this.data.getElementNames().size();
    }
    
    /**
     * Hack pour update l'affichage des donnees suite a une une suppression.
  	 * La fonction getSize est appelee a chaque fois quon clique sur le combo box
   	 * Donc c'est la facon la moins compliquee de le faire 
   	 */
    private boolean validSelection() {
    	return (this.data.getElementNames().contains(this.getSelectedItem()));
    	
    }

    @Override
    public String getElementAt(int index) {
    	
        return this.data.getElementNames().get(index);
    }

    @Override
    public void setSelectedItem(Object anItem) {
    	
    	super.setSelectedItem((String) anItem);
//    	if (!this.data.getElementNames().contains(super.getSelectedItem()))
//    	 super.setSelectedItem(null);
    	
        
        //System.out.println("setSelectedItem" + this.data.getElementNames().contains((String) anItem));
        	
    }

    public int getIndexOf(Object anItem) {
    	
        return this.data.getElementNames().indexOf((String) anItem);
    }

    @Override
    public String getSelectedItem() {
       return (String) super.getSelectedItem(); // to add the selection to the combo box
    }

}
