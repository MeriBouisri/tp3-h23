package model.invention;

public class NoSuchInventionException extends RuntimeException {
    private static String exceptionString = "Cette invention n'existe pas : ";


    public NoSuchInventionException(String inventionName) {
        super(exceptionString + "[" + inventionName + "]");
    }

    public NoSuchInventionException(Invention invention) {
        this(invention.getInventionName());
    }
}