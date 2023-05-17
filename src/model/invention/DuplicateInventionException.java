package model.invention;

import javax.swing.JOptionPane;

public class DuplicateInventionException extends RuntimeException {    
    private static String exceptionString = "Cette invention existe deja : ";

    public DuplicateInventionException(Invention invention) {
        this(invention.getInventionName());
    }

    public DuplicateInventionException(String inventionName) {
        super(exceptionString + "[" + inventionName + "]");
    }

    
}
