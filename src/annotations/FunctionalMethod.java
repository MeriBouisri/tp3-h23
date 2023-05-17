package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * L'annotation {@code FunctionMethod} permet d'identifier les methodes d'une classe qui serviront de
 * callback functions pour le FunctionalModel. Les roles permis sont definis par l'utilisateur dans l'en-tete
 * de la classe qui contiendra les methodes {@code FunctionMethod}. 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface FunctionalMethod {

    public String role();
}
