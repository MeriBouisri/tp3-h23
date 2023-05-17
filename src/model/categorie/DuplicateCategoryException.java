package model.categorie;

public class DuplicateCategoryException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	private static String exceptionString = "Cette categorie existe deja : ";

    public DuplicateCategoryException(String categoryName) {
        super(exceptionString + "[" + categoryName + "]");
    }

    public DuplicateCategoryException(Category category) {
        this(category.getCategoryName());
    }

}
