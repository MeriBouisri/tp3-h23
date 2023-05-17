package utils;

import view.components.FunctionalPanel;

/**
 * L'interface {@code Readable} permet de recuperer des donnees de n'importe quel composant graphique.
 * Cette interface est utile pour les {@code FunctionalPanel} qui ont besoin de recuperer des donnees
 * de composants differents.
 */
public interface Readable {
	
	public boolean isEmpty();
	
	public void addEventListener(FunctionalPanel<?> funPanel);
	
	public void removeEventListener(FunctionalPanel<?> funPanel);
	
	public void clearData();
	
    public String readData();
}
