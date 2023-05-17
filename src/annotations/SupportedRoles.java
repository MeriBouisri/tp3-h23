package annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * L'annotation {@code SupportedRoles} permet de definir les roles permis pour les methodes d'une classe.
 * Cette annotation est obligatoire pour l'usage de {@code FunctionalModel}. 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface SupportedRoles {
    public String[] roles();
}
