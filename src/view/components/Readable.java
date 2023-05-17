package view.components;

/**
 * L'interface {@code Readable} permet de recuperer des donnees de n'importe quel composant graphique.
 * Cette interface est utile pour les {@code FunctionalPanel} qui ont besoin de recuperer des donnees
 * de composants differents.
 */
public interface Readable {
    public void addEventListener(FunctionalPanel parentPanel);
    public void removeEventListener(FunctionalPanel parentPanel);
    public void clearData();
    public boolean isEmpty();
    public String readData();
}
