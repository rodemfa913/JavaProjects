package dwgfx.bind;

import javafx.scene.paint.Color;
import javax.xml.bind.annotation.*;

/**
 * Binding class for a Color.
 * @author Rodemfa913
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BColor {
    @XmlAttribute private final double alpha, blue, green, red;
    
    /**
     * Creates a default instance of BColor.
     * Equivalent to call {@link #BColor(Color) BColor(Color)}
     * with null as parameter.
     */
    public BColor() {
        this(null);
    }
    
    /**
     * Creates an instance of BColor with properties of specified Color.
     * @param color a Color; if null, the value Color.TRANSPARENT is used.
     */
    public BColor(Color color) {
        if (color == null) {
            color = Color.TRANSPARENT;
        }
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
        alpha = color.getOpacity();
    }
    
    /**
     * Gets a new instance of Color with properties of BColor.
     * @return a new Color.
     */
    public Color get() {
        return new Color(red, green, blue, alpha);
    }
}