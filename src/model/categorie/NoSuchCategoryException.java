package model.categorie;

import java.util.NoSuchElementException;

public class NoSuchCategoryException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	private static String exceptionString = "Cette categorie n'existe pas : ";

    public NoSuchCategoryException(Category category) {
        this(category.getCategoryName());
    }

    public NoSuchCategoryException(String categoryName) {
        super(exceptionString + "[" + categoryName + "]");
    }
}