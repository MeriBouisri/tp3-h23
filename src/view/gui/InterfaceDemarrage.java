package view.gui;

import java.awt.Color;
import java.io.IOException;
import java.io.OutputStream;

import java.io.PrintStream;
import java.io.PrintWriter;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.border.TitledBorder;

import model.GestionInventions;
import view.components.FunctionalPanel;
import view.components.ReadableComboBox;
import view.components.ReadableTextField;

import java.awt.Font;


// Lea H-2023 - ©Caroline Houle et Yvon Charest
// Merieme Bouisri

public class InterfaceDemarrage {


	public JFrame frame;
	protected ReadableTextField txtCategoAjoutCatego;
	protected ReadableTextField txtInventionAjoutInv;
	protected ReadableTextField txtInventeurAjoutInv;
	protected ReadableTextField txtAnneeAjoutInv;
	protected ReadableTextField txtInventionModif;
	protected ReadableTextField txtAnneeModif;

	protected ReadableComboBox boxCategoAjoutInv;
	protected ReadableComboBox boxCategoSuppression;
	protected ReadableComboBox boxInventionModif;
	protected ReadableComboBox boxInventeurAffInventions;

	private static JTextArea txtZoneAffichage; 

	protected JButton btnAjouterCatego;
	protected JButton btnAjouterInvention;
	protected JButton btnModifierAnnee;
	protected JButton btnSupprimerCatego;
	protected JButton btnAnnulerDerniereSuppress;
	protected JButton btnAfficherCategories;
	protected JButton btnAfficherInventionsDunInventeur;

	protected FunctionalPanel<GestionInventions> addCategoryPane;
	protected FunctionalPanel<GestionInventions> deleteCategoryPane;
	protected FunctionalPanel<GestionInventions> addInventionPane;
	protected FunctionalPanel<GestionInventions> editInventionPane;
	protected FunctionalPanel<GestionInventions> displayInformationPane;
	protected FunctionalPanel<GestionInventions> undoActionPane;
	protected FunctionalPanel<GestionInventions> displayInventorPane;


	

	/**
	 * Create the application.
	 */
	public InterfaceDemarrage() {
		initialize();
		redirigerLaConsole(); //permet de rediriger System.out dans le JTextArea
		System.out.println("Bienvenue! Commencez par ajouter une cat�gorie.");
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(211, 211, 211));
		frame.setBounds(100, 100, 743, 457);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
	
		// ========== PANELS ========== //
		
		addCategoryPane = new FunctionalPanel();
		addCategoryPane.setBackground(Color.LIGHT_GRAY);
		addCategoryPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
				"Ajouter une cat\u00E9gorie", 
				TitledBorder.LEADING, TitledBorder.TOP, null, Color.BLUE));
		addCategoryPane.setBounds(6, 11, 344, 53);
		frame.getContentPane().add(addCategoryPane);
		addCategoryPane.setLayout(null);
			
		addInventionPane = new FunctionalPanel();
		addInventionPane.setBackground(Color.LIGHT_GRAY);
		addInventionPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"), 
				"Ajouter une invention", TitledBorder.LEADING, 
				TitledBorder.TOP, null, Color.BLUE));
		addInventionPane.setBounds(6, 70, 344, 147);
		frame.getContentPane().add(addInventionPane);
		addInventionPane.setLayout(null);
	
		editInventionPane = new FunctionalPanel();
		editInventionPane.setForeground(new Color(165, 42, 42));
		editInventionPane.setBackground(Color.LIGHT_GRAY);
		editInventionPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Modifier l'ann\u00E9e d'une invention",
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(139, 0, 0)));
		editInventionPane.setBounds(360, 11, 357, 78);
		frame.getContentPane().add(editInventionPane);
		editInventionPane.setLayout(null);
	
		deleteCategoryPane = new FunctionalPanel();
		deleteCategoryPane.setForeground(new Color(165, 42, 42));
		deleteCategoryPane.setBackground(Color.LIGHT_GRAY);
		deleteCategoryPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Supprimer une cat\u00E9gorie", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(139, 0, 0)));
		deleteCategoryPane.setBounds(360, 95, 357, 50);
		frame.getContentPane().add(deleteCategoryPane);
		deleteCategoryPane.setLayout(null);

		displayInformationPane = new FunctionalPanel();
		displayInformationPane.setForeground(new Color(165, 42, 42));
		displayInformationPane.setBackground(Color.LIGHT_GRAY);
		displayInformationPane.setBounds(6, 228, 344, 50);
		frame.getContentPane().add(displayInformationPane);

		

		undoActionPane = new FunctionalPanel();
		undoActionPane.setForeground(new Color(165, 42, 42));
		undoActionPane.setBackground(Color.LIGHT_GRAY);
		undoActionPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Annuler la derni\u00E8re suppression", TitledBorder.LEADING,
				TitledBorder.TOP, null, new Color(139, 0, 0)));
		undoActionPane.setBounds(360, 156, 357, 61);
		frame.getContentPane().add(undoActionPane);
		undoActionPane.setLayout(null);
		
		displayInventorPane = new FunctionalPanel();
		displayInventorPane.setBackground(Color.LIGHT_GRAY);
		displayInventorPane.setBorder(new TitledBorder(UIManager.getBorder("TitledBorder.border"),
				"Afficher les inventions d'un inventeur particulier", 
				TitledBorder.LEADING, TitledBorder.TOP, null, new Color(199, 21, 133)));
		displayInventorPane.setBounds(360, 228, 357, 50);
		frame.getContentPane().add(displayInventorPane);
		displayInventorPane.setLayout(null);

		// ========== LABELS ==========

		JLabel lblNomDeLa = new JLabel("Nom de la cat\u00E9gorie:");
		lblNomDeLa.setFont(new Font("Arial", Font.PLAIN, 10));
		lblNomDeLa.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNomDeLa.setBounds(6, 20, 105, 26);
		addCategoryPane.add(lblNomDeLa);
		
		JLabel lblCatgorie = new JLabel("Cat\u00E9gorie:");
		lblCatgorie.setFont(new Font("Arial", Font.PLAIN, 10));
		lblCatgorie.setBounds(6, 23, 110, 14);
		addInventionPane.add(lblCatgorie);
		lblCatgorie.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblNomDeLinvention = new JLabel("Nom de l'invention:");
		lblNomDeLinvention.setFont(new Font("Arial", Font.PLAIN, 10));
		lblNomDeLinvention.setBounds(6, 50, 110, 14);
		addInventionPane.add(lblNomDeLinvention);
		lblNomDeLinvention.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblNomDeLenventeur = new JLabel("Nom de l'inventeur:");
		lblNomDeLenventeur.setFont(new Font("Arial", Font.PLAIN, 10));
		lblNomDeLenventeur.setBounds(6, 78, 110, 14);
		addInventionPane.add(lblNomDeLenventeur);
		lblNomDeLenventeur.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblAnneDeLinvention = new JLabel("Ann\u00E9e de l'invention:");
		lblAnneDeLinvention.setFont(new Font("Arial", Font.PLAIN, 10));
		lblAnneDeLinvention.setBounds(6, 106, 110, 14);
		addInventionPane.add(lblAnneDeLinvention);
		lblAnneDeLinvention.setHorizontalAlignment(SwingConstants.RIGHT);
			
		JLabel lblNomDeLinvention_1 = new JLabel("Nom de l'invention:");
		lblNomDeLinvention_1.setFont(new Font("Arial", Font.PLAIN, 10));
		lblNomDeLinvention_1.setBounds(16, 25, 97, 14);
		editInventionPane.add(lblNomDeLinvention_1);
		lblNomDeLinvention_1.setHorizontalAlignment(SwingConstants.RIGHT);
		
		JLabel lblNouvelleAnne = new JLabel("Nouvelle ann\u00E9e:");
		lblNouvelleAnne.setFont(new Font("Arial", Font.PLAIN, 10));
		lblNouvelleAnne.setBounds(16, 50, 97, 14);
		editInventionPane.add(lblNouvelleAnne);
		lblNouvelleAnne.setHorizontalAlignment(SwingConstants.RIGHT);
			
		JLabel label = new JLabel("Cat\u00E9gorie:");
		label.setFont(new Font("Arial", Font.PLAIN, 10));
		label.setBounds(6, 23, 110, 14);
		deleteCategoryPane.add(label);
		label.setHorizontalAlignment(SwingConstants.RIGHT);
			
		JLabel label_1 = new JLabel("Nom de l'inventeur:");
		label_1.setFont(new Font("Arial", Font.PLAIN, 10));
		label_1.setBounds(6, 24, 110, 14);
		displayInventorPane.add(label_1);
		label_1.setHorizontalAlignment(SwingConstants.RIGHT);


		// ========== TEXTFIELDS ========== //

		boxInventionModif = new ReadableComboBox();
		boxInventionModif.setFont(new Font("Arial", Font.PLAIN, 11));
		boxInventionModif.setBounds(123, 22, 112, 20);
		editInventionPane.add(boxInventionModif);
	

		txtAnneeAjoutInv = new ReadableTextField();
		txtAnneeAjoutInv.setFont(new Font("Arial", Font.PLAIN, 11));
		txtAnneeAjoutInv.setBounds(126, 103, 112, 20);
		addInventionPane.add(txtAnneeAjoutInv);
		txtAnneeAjoutInv.setColumns(10);

		txtInventeurAjoutInv = new ReadableTextField();
		txtInventeurAjoutInv.setFont(new Font("Arial", Font.PLAIN, 11));
		txtInventeurAjoutInv.setBounds(126, 75, 191, 20);
		addInventionPane.add(txtInventeurAjoutInv);
		txtInventeurAjoutInv.setColumns(10);

		txtInventionAjoutInv = new ReadableTextField();
		txtInventionAjoutInv.setFont(new Font("Arial", Font.PLAIN, 11));
		txtInventionAjoutInv.setBounds(126, 51, 191, 20);
		addInventionPane.add(txtInventionAjoutInv);
		txtInventionAjoutInv.setColumns(10);

		txtCategoAjoutCatego = new ReadableTextField();
		txtCategoAjoutCatego.setFont(new Font("Arial", Font.PLAIN, 11));
		txtCategoAjoutCatego.setBounds(126, 23, 105, 20);
		addCategoryPane.add(txtCategoAjoutCatego);
		txtCategoAjoutCatego.setColumns(10);

		txtAnneeModif = new ReadableTextField();
		txtAnneeModif.setFont(new Font("Arial", Font.PLAIN, 11));
		txtAnneeModif.setBounds(123, 47, 112, 20);
		editInventionPane.add(txtAnneeModif);
		txtAnneeModif.setColumns(10);

		boxInventeurAffInventions = new ReadableComboBox();
		boxInventeurAffInventions.setFont(new Font("Arial", Font.PLAIN, 11));
		boxInventeurAffInventions.setBounds(126, 21, 112, 20);
		displayInventorPane.add(boxInventeurAffInventions);


		// ========== COMBO BOXES ========== //

		boxCategoSuppression = new ReadableComboBox();
		boxCategoSuppression.setFont(new Font("Arial", Font.PLAIN, 11));
		boxCategoSuppression.setBounds(126, 20, 112, 20);
		deleteCategoryPane.add(boxCategoSuppression);

		boxCategoAjoutInv = new ReadableComboBox();
		boxCategoAjoutInv.setFont(new Font("Arial", Font.PLAIN, 11));
		boxCategoAjoutInv.setBounds(126, 20, 112, 20);
		addInventionPane.add(boxCategoAjoutInv);
		

		// ========== BUTTONS ========== //

		btnAjouterCatego = new JButton("Ajouter");
		btnAjouterCatego.setFont(new Font("Arial", Font.PLAIN, 10));
		btnAjouterCatego.setBounds(240, 22, 89, 23);
		addCategoryPane.add(btnAjouterCatego);

		btnAjouterInvention = new JButton("Ajouter");
		btnAjouterInvention.setFont(new Font("Arial", Font.PLAIN, 10));
		btnAjouterInvention.setBounds(245, 102, 89, 23);
		addInventionPane.add(btnAjouterInvention);

		btnModifierAnnee = new JButton("Modifier");
		btnModifierAnnee.setFont(new Font("Arial", Font.PLAIN, 10));
		btnModifierAnnee.setBounds(245, 46, 88, 23);
		editInventionPane.add(btnModifierAnnee);

		btnSupprimerCatego = new JButton("Supprimer");
		btnSupprimerCatego.setFont(new Font("Arial", Font.PLAIN, 10));
		btnSupprimerCatego.setBounds(248, 19, 89, 23);
		deleteCategoryPane.add(btnSupprimerCatego);

		btnAnnulerDerniereSuppress = new JButton("Annuler la suppression la plus r\u00E9cente");
		btnAnnulerDerniereSuppress.setBounds(32, 27, 300, 23);
		undoActionPane.add(btnAnnulerDerniereSuppress);
		
		btnAfficherCategories = new JButton("Afficher toutes les cat\u00E9gories et leurs inventions");
		btnAfficherCategories.setFont(new Font("Arial", Font.PLAIN, 11));
		//btnAfficherCategories.setBounds(6, 228, 344, 50);
		displayInformationPane.add(btnAfficherCategories);
		
		btnAfficherInventionsDunInventeur = new JButton("Afficher");
		btnAfficherInventionsDunInventeur.setFont(new Font("Arial", Font.PLAIN, 10));
		btnAfficherInventionsDunInventeur.setBounds(248, 20, 89, 23);
		displayInventorPane.add(btnAfficherInventionsDunInventeur);

		
		// ========== DISPLAY ========== //

		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		scrollPane.setBounds(6, 291, 711, 121);
		frame.getContentPane().add(scrollPane);
		
		txtZoneAffichage = new JTextArea();
		scrollPane.setViewportView(txtZoneAffichage);

		frame.setVisible(true);

	}

	/**
	 * 
	 * La m�thode qui suit permet la redirection de System.out vers le 
	 * JTextArea nomm� txtZoneAffichage
	 */

	private void redirigerLaConsole() { 
		OutputStream out = new OutputStream() { 
			@Override 
			public void write(int b) throws IOException { 
				txtZoneAffichage.append(String.valueOf((char) b)); 
			} 

			@Override 
			public void write(byte[] b, int off, int len) throws IOException { 
				txtZoneAffichage.append(new String(b, off, len)); 
			} 

			@Override 
			public void write(byte[] b) throws IOException { 
				write(b, 0, b.length); 
			} 
		}; 

		System.setOut(new PrintStream(out, true));
		
	}



	
}//fin classe
