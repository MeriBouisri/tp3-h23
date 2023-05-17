package model;
//Lea H-2023 - �Caroline Houle et Yvon Charest

import java.util.Arrays;

import javax.swing.JOptionPane;

import annotations.FunctionalMethod;
import annotations.SupportedRoles;
import model.categorie.Category;

import model.categorie.CategoryTree;
import model.categorie.DuplicateCategoryException;
import model.categorie.NoSuchCategoryException;
import model.invention.DuplicateInventionException;
import model.invention.Invention;
import model.invention.NoSuchInventionException;
import utils.ActionLogger;
import utils.Updateable;



@SupportedRoles(roles = { "ADDER", "DEEP_ADDER", "REMOVER", "DISPLAYER", "INVENTOR_DISPLAYER","UNDOER", "SETTER" })
public class GestionInventions implements Updateable {

	// ========== FUNCTIONAL METHODS ========== //

    /*
     * Les "FunctionalMethod" sont des methodes qui portent l'annotation @FunctionalMethod.
     * Le but de ces methodes est de leur assigner un role qui pourra ensuite etre utilisé pour
     * les identifier et les invoquer par la suite. 
     * 
     * L'annotation @FunctionalMethod permet donc à la classe FunctionalModel d'accéder aux méthodes d'une classe,
     * et donc de générer un FunctionalModel tant qu'il possède des méthodes annotées.
     */

	 // ========== EXCEPTION HANDLING ========== //

	 /**
	  Le throw d'exceptions est effectue a partir des classes de categories, inventions, etc., 
	  elles seront donc attrapees par les methodes de cette GestionInventions.
	  Remarquez que plusieurs exceptions sont deja prevenues par le l'interface, notamment par le
	  fait que l'utilisateur doit choisir parmi les options donnees dans les combo box. 
	  
	  */



	private CategoryTree categoryTree;

	public GestionInventions() {
		this.categoryTree = new CategoryTree();
	}

	public CategoryTree getCategoryTree() {
		return categoryTree;
	}

	@FunctionalMethod(role = "ADDER")
	public void ajouterCategorie(String nomCategorie) {
		try {

			this.categoryTree.addCategory(nomCategorie);
			System.out.println(ActionLogger.logAdder(Category.class, nomCategorie));
	
		} catch (DuplicateCategoryException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), DuplicateCategoryException.class.getSimpleName(), 0);
		
		// La fonction throw cette erreur aussi for some reason, mais elle n'est pas importante
		} catch (DuplicateInventionException e) {}
	}

	@FunctionalMethod(role = "DEEP_ADDER")
	public void ajouterInvention(String nomCategorie, String nomInvention, String nomInventeur, int annee) {
		try {

			if (this.categoryTree.addInventionToCategory(nomCategorie, nomInvention, nomInventeur, annee))
				System.out.println(ActionLogger.logAdder(Invention.class, nomInvention));

		} catch (DuplicateInventionException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), DuplicateInventionException.class.getSimpleName(), 0);
		} catch (NoSuchCategoryException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), NoSuchCategoryException.class.getSimpleName(), 0);
		}
	}

	@FunctionalMethod(role = "REMOVER")
	public void supprimerCategorie(String nomCategorie) {
		// Afficher message de confirmation
		try {
			if (this.deleteMessage(nomCategorie) == 1) return;

			if (this.categoryTree.removeCategory(nomCategorie))
				System.out.println(ActionLogger
						.logRemover(Category.class, nomCategorie));
		} catch (NoSuchCategoryException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), NoSuchCategoryException.class.getSimpleName(), 0);
		}
	}

	@FunctionalMethod(role = "DISPLAYER")
	public void afficherCategories() {
		System.out.println(this.categoryTree);
	}

	@FunctionalMethod(role = "UNDOER")
	public void annulerDerniereAction() {
		if (this.categoryTree.undo())
			System.out.println(ActionLogger.logUndo());
	}

	@FunctionalMethod(role = "INVENTOR_DISPLAYER")
	public void afficherInventions(String nomInventeur) throws NoSuchFieldException, SecurityException {
		System.out.println("-\nINVENTEUR [inventorName=" + nomInventeur + "]");

		Arrays.asList(
				this.categoryTree.getAllInventions()
				.filterByField("inventorName", nomInventeur))
			.forEach(System.out::print);
	}
	

	@FunctionalMethod(role = "SETTER")
	public void modifierAnnee(String nomInvention, int newInventionYear) {
		try {
			Invention invention = 
				this.categoryTree.getAllInventions()
					.stream().filter(inv -> inv.equals(nomInvention))
					.findFirst().get();
			
			System.out.println(
				ActionLogger.logEditor(
					Invention.class, nomInvention, 
					String.valueOf(invention.getInventionYear()), 
					String.valueOf(newInventionYear)));

			invention.setInventionYear(newInventionYear);
		} catch (NoSuchInventionException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), NoSuchInventionException.class.getSimpleName(), 0);
		} 
	}

	@Override
	public void update() {
		try {
			this.categoryTree.updateAllInventions();
		} catch (DuplicateInventionException e) {
			JOptionPane.showMessageDialog(null, e.getMessage(), DuplicateInventionException.class.getSimpleName(), 0);
			
		}
	}

	/**
	 * @return 0 si l'utilisateur a cliqué sur "Oui", 1 sinon
	 */
	public int deleteMessage(String categoryName) {
		return JOptionPane.showConfirmDialog(null, 
			"Etes-vous sur de vouloir supprimer [" + categoryName +"] ?", 
			"Confirmation",
			JOptionPane.YES_NO_OPTION);
		
	}
}
