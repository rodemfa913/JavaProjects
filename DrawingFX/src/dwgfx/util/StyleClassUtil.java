package dwgfx.util;

import java.util.*;

/**
 * Helper class for convert a List of style classes to String and vice-versa.
 * That List is equivalent to the styleClass property of a Node.
 * @author rodemfa
 */
public abstract class StyleClassUtil {
    /**
     * Converts a formatted String to a List of style classes.
     * @param classes the formatted String.
     * @return the List of style classes.
     */
    public static List<String> parseClasses(String classes) {
        List<String> classList = new ArrayList();
        classList.addAll(Arrays.asList(classes.split("\\s+")));
        return classList;
    }
    
    /**
     * Converts a List of style classes to a formatted String.
     * @param classList the List of style classes.
     * @return the formatted String.
     */
    public static String toString(List<String> classList) {
        String classes = "";
        for (String styleClass : classList) {
            if (!classes.isEmpty()) {
                classes += " ";
            }
            classes += styleClass;
        }
        return classes;
    }
}