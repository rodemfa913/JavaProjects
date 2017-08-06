package dwgfx.bind;

import javafx.scene.shape.*;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * ...
 * @author Rodemfa913
 */
public class BMoveTo extends BClose {
    @XmlAttribute private final double x;
    @XmlAttribute private final double y;
    
    /**
     * ...
     */
    public BMoveTo() {
        x = y = 0.0;
    }
    
    /**
     * ...
     * @param moveto ...
     */
    public BMoveTo(MoveTo moveto) {
        x = moveto.getX();
        y = moveto.getY();
    }
    
    @Override public PathElement get() {
        return new MoveTo(x, y);
    }
}