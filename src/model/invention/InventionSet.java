package model.invention;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import model.categorie.CategoryTree;
import utils.Listable;

/**
 * Classe {@code InventionSet} est un {@code HashSet} qui contient des objets de type {@code Invention}.
 * 
 */
public class InventionSet extends HashSet<Invention> implements Listable {

    /**
     * Construction d'un {@code InventionSet} vide.
     */
    public InventionSet() {
        super();
    }

    /**
     * Construction d'un {@code InventionSet} a partir d'un {@code CategoryTree}.
     * Les inventions sont extraites de chaque categorie du {@code CategoryTree} 
     * et ajoutees a l'{@code InventionSet}, de maniere a applatir les elements de l'arbre.
     */
    public InventionSet(CategoryTree categoryTree) {
        super();
    
        categoryTree.forEach(category -> 
            this.addAll(category.getInventionSet()));
    }

    /**
     * @param inventionName Nom de l'invention a rechercher.
     * @return {@code true} si le {@code InventionSet} contient une invention du meme nom que {@code inventionName}
     */
//    public boolean contains(String inventionName) {
//        return super.contains(inventionName);
//    }

    /**
     * @param invention Objet {@code Invention} a rechercher.
     * @return {@code true} si le {@code InventionSet} contient une référence à l'objet {@code invention}
     * @throws DuplicateInventionException Si une invention du meme nom que {@code invention} est deja presente dans le {@code InventionSet}
     */
    public boolean add(Invention invention) {

        if (this.contains(invention))
            throw new DuplicateInventionException(invention.getInventionName());

        return super.add(invention);
    }

    /**
     * @param inventionName Nom de l'invention a ajouter.
     * @param inventorName Nom de l'inventeur de l'invention a ajouter.
     * @param inventionYear Annee de l'invention a ajouter.
     * @return {@code true} si l'invention a ete ajoutee avec succes.
     * @throws DuplicateInventionException Si une invention du meme nom que {@code inventionName} est deja presente dans le {@code InventionSet}
     */
    public boolean add(String inventionName, String inventorName, int inventionYear) {

        if (this.contains(inventionName))
            throw new DuplicateInventionException(inventionName);

        return this.add(new Invention(inventionName, inventorName, inventionYear));
    }

    /**
     * @param inventionName Nom de l'invention a rechercher.
     * @return Reference a un objet {@code Invention} dont le nom correspond a {@code inventionName}
     * @throws NoSuchInventionException Si aucune invention du meme nom que {@code inventionName} n'est presente dans le {@code InventionSet}
     */
    public Invention get(String inventionName) {

        if (!this.contains(inventionName))
            throw new NoSuchInventionException(inventionName);
        
        return this.stream()
                    .filter(invention -> 
                    			invention.equals(inventionName))
                    .findFirst().get();
    }

    /**
     * Supprimer un element du {@code InventionSet} a partir de son nom.
     * @param inventionName Nom de l'invention a rechercher.
     * @return {@code true} si l'invention a ete supprimee avec succes.
     */
//    public boolean remove(String inventionName) {
//        return super.remove(inventionName);
//    }


    /**
     * Retourne un tableau de Strings contenant toutes les valeurs uniques pour un champ donné de 
     * la classe {@code Invention}. Cela permet d'avoir une liste d'option valables que l'utilisateur
     * aura le droit de rentrer dans un formulaire.
     * @param fieldName Nom du champ dont on veut récupérer les valeurs.
     */
    public String[] getAllValuesForField(String fieldName) {

        // Nous utilisons une liste dynamique, car on ne connait pas le nombre de valeurs uniques pour un champ donné.
        ArrayList<String> fieldValues = new ArrayList<String>();

        try {
 
            Field targetField = Invention.class.getDeclaredField(fieldName);

            for (Invention inv : this) {
                String value = targetField.get(inv).toString();

                if (!fieldValues.contains(value)) 
                    fieldValues.add(value);
            }
        } catch (Exception e) {
            System.out.println(e.getCause());
        }

        return fieldValues.toArray(String[]::new);
    }

    /**
     * Retourne un tableau d'objets {@code Invention} dont le champ {@code fieldName} a la valeur {@code value}.
     * @param fieldName Nom du champ dont on veut récupérer les valeurs.
     * @param value Valeur du champ dont on veut récupérer les valeurs.
     * @return Tableau d'objets {@code Invention} dont le champ {@code fieldName} a la valeur {@code value}.
     */
    public Invention[] filterByField(String fieldName, String value) {
        return this.stream()
                .filter(invention -> {
                    try {

                        return Invention.class
                            .getDeclaredField(fieldName)
                            .get(invention)
                            .equals(value);

                    } catch (Exception e) { return false; }
                }).toArray(Invention[]::new);
    }

    @Override
    public String toString() {
        String str = "";
        for (Invention invention : this) 
            str += invention.toString();
        return str;
    }
    
    @Override
    public List<String> getElementNames() {
        return this.stream()
                    .map(Invention::getInventionName).toList();
    }

    /**
     * Retourne un clone de l'objet InventionSet. Le clone est une copie profonde, 
     * donc les elements du HashSet sont copiés aussi.
     * @return Deep copy of this set.
     */
    @Override
    public InventionSet clone() {
        InventionSet clone = new InventionSet();
        this.forEach(inv -> clone.add(inv.clone()));
        return clone;
    }
}
