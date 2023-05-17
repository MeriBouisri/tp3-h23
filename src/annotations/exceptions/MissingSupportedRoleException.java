package annotations.exceptions;

public class MissingSupportedRoleException extends AnnotationException {
    
	private static final long serialVersionUID = 1L;
	
	private static String exceptionString = "The SupportedRoles enum does not contain the follow role : ";

    public MissingSupportedRoleException(Object role) {
        super(exceptionString + role.toString());
    }

}
