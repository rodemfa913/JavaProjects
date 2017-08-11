package dwgfx.bind;

import javafx.scene.shape.*;
import javax.xml.bind.annotation.*;

/**
 * Binding class for a MoveTo element of Path.
 * @author Rodemfa913
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BMoveTo implements BElement {
    @XmlAttribute private final double x;
    @XmlAttribute private final double y;
    
    /**
     * Creates a default instance of BMoveTo.
     */
    public BMoveTo() {
        x = y = 0.0;
    }
    
    /**
     * Creates an instance of BMoveTo with properties of specified MoveTo.
     * @param moveto a MoveTo element of Path.
     */
    public BMoveTo(MoveTo moveto) {
        x = moveto.getX();
        y = moveto.getY();
    }
    
    @Override public PathElement get() {
        return new MoveTo(x, y);
    }
}