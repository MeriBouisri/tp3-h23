package annotations.exceptions;

public class IllegalSupportedRolesClass extends AnnotationException {

	private static final long serialVersionUID = 1L;
	
	private static String exceptionString = "The SupportedRoles annotation does not refer to an enum class : ";

    public IllegalSupportedRolesClass(Class<?> enumClass) {
        super(exceptionString + enumClass.getTypeName());
    }
    
}
