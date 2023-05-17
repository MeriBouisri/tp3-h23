package annotations.exceptions;

public class MissingAnnotationException extends AnnotationException {

	private static final long serialVersionUID = 1L;
	
	private static String exceptionString = "The class does not have the SupportedRoles annotation : ";

    public MissingAnnotationException(Class<?> targetClass) {
        super(exceptionString + targetClass.getName());
    }

}
