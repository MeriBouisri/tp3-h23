package model.invention;

public class Invention {
	
	/**
	 * Les champs de {@code Invention} ont une portee protected afin de permettre a
	 * {@code InventionSet} d'acceder aux champs de la classe, ce qui permet ensuite
	 * d'acceder aux champs a partir des noms de champs, lorsque le FunctionalModel
	 * a besoin de fonctions set/get des champs.
	 */
	protected String inventionName;
	protected String inventorName;
	protected int inventionYear;
	protected String categoryName;

	// ===== CONSTRUCTORS ======

	public Invention(String inventionName, String inventorName, int inventionYear) {
		this.inventionName = inventionName;
		this.inventorName = inventorName;
		this.inventionYear = inventionYear;
	}

	public Invention(String categoryName, String inventionName, String inventorName, int inventionYear) {
		this.inventionName = inventionName;
		this.inventorName = inventorName;
		this.inventionYear = inventionYear;

		this.categoryName = categoryName;
	}
	
	@Override 
	public int hashCode() {
		return this.inventionName.hashCode();
	}
	
	@Override
	public boolean equals(Object obj) {
		return this.hashCode() == obj.hashCode();
	}

	// ===== GETTERS =====
	public String getInventionName() {
		return inventionName;
	}

	public String getCategory() {
		return categoryName;
	}

	public String getInventorName() {
		return inventorName;
	}

	public int getInventionYear() {
		return inventionYear;
	}

	// ===== SETTERS =====

	public void setInventionName(String inventionName) {
		this.inventionName = inventionName;
	}

	public void setCategory(String categoryName) {
		this.categoryName = categoryName;
	}

	public void setInventorName(String inventorName) {
		this.inventorName = inventorName;
	}

	public void setInventionYear(int inventionYear) {
		this.inventionYear = inventionYear;
	}

	@Override
	public String toString() {
		return "Invention [inventionName=" + inventionName 
				+ ", inventorName=" + inventorName 
				+ ", inventionYear=" + inventionYear + "]\n";
	}    

	/**
	 * Retourne un clone de l'objet invention.
	 */
	@Override
	public Invention clone() {
		return new Invention(this.inventionName, this.inventorName, this.inventionYear);
	}
}
