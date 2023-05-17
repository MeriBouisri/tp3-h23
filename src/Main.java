

import java.awt.EventQueue;

import controller.Controller;
import model.GestionInventions;
import view.gui.InterfaceDemarrage;

/**
 * TRAVAIL PRATIQUE 3 - GESTION D'INVENTIONS (420-SCB-MA)
 * DATE : 2023-05-13
 * GROUPE : 02
 * 
 * Ce programme permet de gerer des inventions et leurs categories.
 * 
 * J'ai opte pour une architecture MVC (Model-View-Controller) pour ce projet afin d'ameliorer la 
 * structure du programme et de faciliter le lien entre l'interface et le modele.
 * 
 * Ce programme fait aussi fort usage des annotations et de java.lang.reflect pour la manipulation
 * des methodes d'une classe. Cela peut paraitre comme une solution inutilement compliquee pour 
 * le probleme, mais ca permet de rendre le code plus flexible avec n'importe 
 * quel modele.
 * 
 * @author Merieme Bouisri
 */
public class Main {

    public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {

					InterfaceDemarrage window = new InterfaceDemarrage();
                    GestionInventions tree = new GestionInventions();
                    Controller controller = new Controller(window, tree);
			
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
    }
    
}
