package dwgfx.bind;

import javafx.scene.shape.*;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * ...
 * @author Rodemfa913
 */
public class BLineTo extends BClose {
    @XmlAttribute private final double x;
    @XmlAttribute private final double y;
    
    /**
     * ...
     */
    public BLineTo() {
        x = y = 0.0;
    }
    
    /**
     * ...
     * @param lineto ...
     */
    public BLineTo(LineTo lineto) {
        x = lineto.getX();
        y = lineto.getY();
    }
    
    @Override public PathElement get() {
        return new LineTo(x, y);
    }
}