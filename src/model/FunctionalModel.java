package model;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.stream.Stream;

import javax.swing.JOptionPane;

import annotations.FunctionalMethod;
import annotations.exceptions.AnnotationException;
import annotations.exceptions.MissingAnnotationException;
import annotations.exceptions.MissingSupportedRoleException;
import annotations.*;


/**
 * La classe {@code FunctionalModel} est une classe qui permet de convertir un objet en un modele fonctionnel.
 * 
 * Plutot que d'avoir une instance d'une classe sur laquelle nous appelons des methodes, nous avons des fonctions
 * qui peuvent etre invoquees sur un objet de cette classe. Ces fonctions peuvent etre passees en parametre
 * dans d'autres fonctions, ce qui permet donc d'avoir des fonctions de rappel. 
 * 
 * En decomposant une classe de cette maniere, il est possible de construire un {@code FunctionalView} a partir
 * du "modele" de la classe. Un {@code FunctionalView} est compose de {@code FunctionalPanels} qui representent 
 * les methodes de la classe.
 * 
 * Bien que ce systeme semble inutilement complexe, il permet de simplifier la creation d'une interface
 * qui sert seulement a manipuler les donnees d'une seule classe. Plutot que de creer un event handling system
 * pour chaque fonctionnalite, il suffit de creer un {@code FunctionalPanel} et de l'associer a une methode qui prend 
 * le meme nombre de parametres que le nombre de composants {@code IReadable} dans le {@code FunctionalPanel}.
 * 
 * De plus, ce systeme permet aussi de creer des {@code JComboBox} dynamiques a partir des listes d'elements.
 *  
 * Pour générer un {@code FunctionalModel} a partir d'une classe, il faut que cette classe contienne
 * des méthodes annotées avec {@code FunctionalMethod}. Les roles des méthodes sont définies par l'utilisateur sous forme de tableau de Strings. 
 */
public class FunctionalModel<E>  {

    public E targetObject;
    public Class<? extends E> targetClass;

    /**
     * Instanciation d'un {@code FunctionalModel} a partir d'une classe.
     * @param targetClass la classe avec laquelle un {@code FunctionalModel} sera généré.
     */
    public FunctionalModel(Class<? extends E> targetClass) {
        this.targetClass = targetClass;
    }

    /**
     * Instanciation d'un {@code FunctionalModel} a partir d'un objet.
     * @param targetObject l'objet avec lequel un {@code FunctionalModel} sera généré.
     */
    public FunctionalModel(E targetObject) {
        this.targetClass = (Class<? extends E>) targetObject.getClass();
        this.targetObject = targetObject;
    }

    /**
     * @return L'objet sur lequel le functional model est base.
     */
    public E getTargetObject() {
        return this.targetObject;
    }

    /**
     * @return Tableau des roles permis pour les methodes de la classe, telle que defini
     * par l'annotation {@code SupportedRoles} dans l'en-tete de la classe.
     */
    public String[] getSupportedRoles() {
        String[] supportedRoles;
        
        if (!this.targetClass.isAnnotationPresent(SupportedRoles.class)) 
            throw new MissingAnnotationException(SupportedRoles.class);
        
        return this.targetClass.getAnnotation(SupportedRoles.class).roles();
    }

    /**
     * @return Tableau de toutes les methodes de la classe qui sont annotées avec {@code FunctionalMethod}, tous
     * roles confondus.
     */
    public Stream<Method> getFunctionalMethods() {
        return Arrays.asList(this.targetClass.getDeclaredMethods())
                .stream().filter(method -> method.isAnnotationPresent(FunctionalMethod.class));
    }

    /**
     * @param role le role associe a la methode l'on veut obtenir.
     * @return La premiere methode de la classe qui a le role specifie en parametre.
     */
    public Method filterByRole(String role) {

        String[] supportedRoles;

        try {  supportedRoles = getSupportedRoles(); } 
        catch (AnnotationException e) { e.printStackTrace(); return null; }

        if (!Arrays.asList(supportedRoles).contains(role)) 
            throw new MissingSupportedRoleException(role);
        
        return getFunctionalMethods()
                    .filter(method -> method
                        .getAnnotation(FunctionalMethod.class)
                        .role().equals(role))
                    .findFirst().get();   
    }

    /**
     * Cette fonction permet de deserialiser des donnees recues en forme de String en tableau 
     * d'objets associes aux types de donnees de la methode. Pour le moment, seul les types primitifs
     * (avec leurs wrapper classes) sont permis. 
     * Pour contourner cette difficulte pour des parametres plus complexes, il faut utiliser des parametres 
     * de type {@code String} et les deserialiser dans la corps de la methode invoquee, dans sa classe respective.
     * @param conversionTypes Tableau des types de donnees que la methode prend en parametre.
     * @param stringData Tableau des donnees a convertir
     * @return Tableau des donnees converties, {@code null} si la conversion a echoue.
     */
    public static Object[] deserializeData(Class<?>[] conversionTypes, String[] stringData) {

        Object[] data = new Object[conversionTypes.length];

        try {
            for (int i = 0; i < conversionTypes.length; i++) {
                if (conversionTypes[i].equals(String.class)) 
                    data[i] = stringData[i];
                
                else if (conversionTypes[i].equals(int.class)) 
                    data[i] = Integer.parseInt(stringData[i]);
                
                else if (conversionTypes[i].equals(double.class)) 
                    data[i] = Double.parseDouble(stringData[i]);
                
                else if (conversionTypes[i].equals(boolean.class)) 
                    data[i] = Boolean.parseBoolean(stringData[i]);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, 
                    "Parametre invalide.", 
                    "Erreur", JOptionPane.ERROR_MESSAGE);
            return null;
        }

        return data;
    }
    

    /**
     * @param targetObject Objet sur lequel la methode sera invoquee
     * @param method Methode qui sera invoquee sur l'objet
     * @param args Tableau des donnees a convertir et a passer en parametre a la methode
     */
    public void invokeMethod(E targetObject, Method method, String... args) {
        
        Object[] deserializedData = FunctionalModel.deserializeData(method.getParameterTypes(), args);

        // La methode ne sera pas invoquee si la conversion a echoue
        if (deserializedData == null) return;

        try {
            method.invoke(targetObject, deserializedData);
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
   
    
}
