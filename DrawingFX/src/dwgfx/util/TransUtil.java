package dwgfx.util;

import javafx.scene.transform.Affine;

/**
 * Helper class for convert Affine transform to String and vice-versa.
 * @author Rodemfa913
 */
public abstract class TransUtil {
    /**
     * Converts a formatted String to an Affine transform.
     * @param trans the formatted String.
     * @return the Affine transform.
     * @throws Exception if the String could not be converted.
     */
    public static Affine parseTransform(String trans) throws Exception {
        Affine transform = new Affine();
        String[] t = trans.split("\\s+");
        int i = 0;
        while (i < t.length) {
            double x, y;
            switch (t[i++]) {
                case "matrix":
                    double xx = Double.parseDouble(t[i++]);
                    double xy = Double.parseDouble(t[i++]);
                    x = Double.parseDouble(t[i++]);
                    double yx = Double.parseDouble(t[i++]);
                    double yy = Double.parseDouble(t[i++]);
                    y = Double.parseDouble(t[i++]);
                    transform.append(xx, xy, x, yx, yy, y);
                    break;
                case "rotate":
                    x = Double.parseDouble(t[i++]);
                    transform.appendRotation(x);
                    break;
                case "scale":
                    x = Double.parseDouble(t[i++]);
                    y = Double.parseDouble(t[i++]);
                    transform.appendScale(x, y);
                    break;
                case "shear":
                    x = Double.parseDouble(t[i++]);
                    y = Double.parseDouble(t[i++]);
                    transform.appendShear(x, y);
                    break;
                case "translate":
                    x = Double.parseDouble(t[i++]);
                    y = Double.parseDouble(t[i++]);
                    transform.appendTranslation(x, y);
                    break;
                default:
                    i = t.length;
            }
        }
        return transform;
    }
    
    /**
     * Converts an Affine transform to a formatted String.
     * @param transform the Affine transform.
     * @return the formatted String.
     */
    public static String toString(Affine transform) {
        return "matrix " +
                transform.getMxx() + " " + transform.getMxy() + " " + transform.getTx() + " " +
                transform.getMyx() + " " + transform.getMyy() + " " + transform.getTy();
    }
}