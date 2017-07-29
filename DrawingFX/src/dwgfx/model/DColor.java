package dwgfx.model;

import javafx.scene.paint.Color;
import javax.xml.bind.annotation.*;

/**
 * ...
 * @author Rodemfa913
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class DColor {
    @XmlAttribute private final double alpha;
    @XmlAttribute private final double blue;
    @XmlAttribute private final double green;
    @XmlAttribute private final double red;
    
    public DColor() {
        this(null);
    }
    
    public DColor(Color color) {
        if (color == null) {
            color = Color.BLACK;
        }
        red = color.getRed();
        green = color.getGreen();
        blue = color.getBlue();
        alpha = color.getOpacity();
    }
    
    public Color get() {
        return new Color(red, green, blue, alpha);
    }
}