package model.categorie;

import java.util.List;

import java.util.Stack;
import java.util.TreeSet;

import model.invention.DuplicateInventionException;
import model.invention.Invention;
import model.invention.InventionSet;

public class CategoryTree extends TreeSet<Category> {

	private static final long serialVersionUID = 1L;

	/**
     * Historique des modifications de l'arbre.
     */
    public Stack<CategoryTree> treeHistory;

    /**
     * Ensemble de toutes les inventions contenues dans le TreeSet, toutes categories confondues.
     */
    private InventionSet allInventions;

    public CategoryTree() {
        super();
        this.allInventions = new InventionSet();
        this.treeHistory = new Stack<CategoryTree>();   
    }

    public void updateAllInventions() {
        this.allInventions = new InventionSet(this);
    }

    public void updateTreeHistory() {
        this.treeHistory.push(this.clone());
    }
    
    public boolean contains(String categoryName) {
        return this.stream().anyMatch(category ->
                    category.getCategoryName().equalsIgnoreCase(categoryName));
    }

    public Category getCategory(String categoryName) {
    	
        if (!this.contains(categoryName))
            throw new NoSuchCategoryException(categoryName);

        return this.stream().filter(category -> 
                    category.getCategoryName().equalsIgnoreCase(categoryName))
                        .findFirst().get();
    }

    public boolean addCategory(String categoryName) {
        return this.addCategory(new Category(categoryName));
    }
    
    public boolean addCategory(Category category) {
    	
        if (this.contains(category.getCategoryName()))
            throw new DuplicateCategoryException(category);
            
        return super.add(category);
    }

    /**
     * Ajouter un objet de type {@code Invention} a l'une des categories 
     * @param categoryName La categorie dans laquelle l'invention sera ajoutee
     * @param inventionName Nom de l'invention
     * @param inventorName Nom de l'inventeur
     * @param inventionYear Annee d'invention
     * @return
     */
    public boolean addInventionToCategory(String categoryName, String inventionName, String inventorName, int inventionYear) {

        if (!this.contains(categoryName))
            throw new NoSuchCategoryException(categoryName);
        
        if (this.allInventions.contains(inventionName))
            throw new DuplicateInventionException(inventionName);
        
        return this.getCategory(categoryName)
                    .add(inventionName, inventorName, inventionYear);
    }
    
    public boolean removeCategory(Category category) {
        if (!this.contains(category))
            throw new NoSuchCategoryException(category);

        return super.remove(category);
    }

    public boolean removeCategory(String categoryName) {
    	// Backup l'etat du CategoryTree avant la suppression
        this.updateTreeHistory();
        
        return this.removeCategory(getCategory(categoryName));
    }
    
    /**
     * Retour a l'etat du CategoryTree avant la derniere suppression.
     */
    public boolean undo() {
        if (this.treeHistory.isEmpty()) return false;
        
        // Reinitialiser le CategoryTree
        this.clear();
        
        // Puis rajouter tous les elements de l'etat precedent
        return this.addAll(this.treeHistory.pop());  
    }
    
    @Override
    public String toString() {
        String str = "-\n";
        
        for (Category category : this) 
            str += category.toString();
        
        str += "-\n";

        return str;
    }
    
   
    public InventionSet getAllInventions() {
        return this.allInventions;
    }
    
    /**
     * Retourne un deep clone de cette instance de {@code CategoryTree}.
     * Les elements du clone sont clones. Les elements des elements du clone sont clones aussi.
     */
    public CategoryTree clone() {
        CategoryTree clone = new CategoryTree();
        this.forEach(category -> clone.add(category.clone()));
        return clone;
    }
    
    public List<String> getInventionNames() {
        return this.stream().flatMap(category -> 
                    category.getInventionSet().stream())
                        .map(Invention::getInventionName).toList();
    }


    public List<String> getElementNames() {
        return this.stream().map(Category::getCategoryName).toList();
    }

}
