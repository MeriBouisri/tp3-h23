package model.categorie;

import model.invention.DuplicateInventionException;
import model.invention.Invention;
import model.invention.InventionSet;


/**
 * Un objet de type {@code Category} possede un nom et un {@code InventionSet} qui contient des objets de type {@code Invention}.
 */
public class Category implements Comparable<Category> {
    private String categoryName;
    private InventionSet inventionSet;

    /**
     * Constructeur avec parametres pour initialiser une {@code Category}
     * a partir d'un {@code InventionSet} existant. Le {@code InventionSet} 
     * 
     */
    public Category(String categoryName, InventionSet inventionSet) {
        super();
        this.categoryName = categoryName;
        this.inventionSet = inventionSet.clone();
    }

    /**
     * Constructeur avec parametres pour initialiser une categorie
     * avec un {@code InventionSet} vide;
     */
    public Category(String categoryName) {
        super();
        this.categoryName = categoryName;
        this.inventionSet = new InventionSet();
    }

    public String getCategoryName() {
        return categoryName;
    }

    public InventionSet getInventionSet() {
        return this.inventionSet;
    }

   
    public boolean add(String inventionName, String inventorName, int inventionYear) {

        if (this.inventionSet.contains(inventionName))
            throw new DuplicateInventionException(inventionName);

        return this.inventionSet.add(new Invention(this.categoryName, inventionName, inventorName, inventionYear));
    }

    @Override
    public int compareTo(Category category) {
        return categoryName.compareToIgnoreCase(category.getCategoryName());
    }

    @Override
    public String toString() {
        return "Categorie [categoryName="+ categoryName + "]\n" 
                + this.inventionSet.toString();
    }

    /**
     * Retourne un clone de l'objet Category. Le InventionSet du clone est
     * un deep copy. 
     */
    @Override
    public Category clone() {
        return new Category(this.categoryName, this.inventionSet);
    }
}
