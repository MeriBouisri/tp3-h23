package utils;

import java.util.Stack;

/**
 * Classe permettant de log les actions effectuees par l'utilisateur.
 * NOTE : J'aurai aime pouvoir utiliser une methode de Logging plus conventionnelle, comme
 * util.logging.Logger ou encore log4j, mais je n'ai pas reussi a rediriger le output de ces 
 * logs vers la zone d'affichage.
 */
public class ActionLogger {

    public static final String ADDER_CALLOUT = "[ADD] Ajout d'un objet de type ";
    public static final String REMOVER_CALLOUT = "[DELETE] Suppression d'un objet de type ";
    public static final String EDITOR_CALLOUT = "[EDIT] Modification d'un objet de type";
    public static final String UNDO_CALLOUT = "[UNDO] Annulation de la derniere action ";

    private static Stack<String> logHistory = new Stack<>();

    private static String logBuilder(String callout, Class<?> clazz, String objectInfo) {
        return callout + "[" + clazz.getSimpleName() + "] : " + objectInfo;
    }

    private static String logBuilder(String callout, String objectInfo) {
        return callout + objectInfo;
    }

    public static String logAdder(Class<?> clazz, String objectInfo) {
        return logBuilder(ADDER_CALLOUT, clazz, objectInfo);
    }

    public static String logRemover(Class<?> clazz, String objectInfo) {
        return logHistory.push(logBuilder(REMOVER_CALLOUT, clazz, objectInfo));
    }

    public static String logEditor(Class<?> clazz, String objectInfo, String oldField, String newField) {
        String str = " : [oldField=" + oldField + ", newField=" + newField + "]";
        return logBuilder(EDITOR_CALLOUT, clazz, objectInfo + str);
    }

    public static String logUndo() {
        return logBuilder(UNDO_CALLOUT, logHistory.pop());
    }

    public static String logLastAction(String log) {
        return logHistory.push(log);
    }
}
