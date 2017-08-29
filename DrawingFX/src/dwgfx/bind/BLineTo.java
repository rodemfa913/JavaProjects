package dwgfx.bind;

import javafx.scene.shape.*;
import javax.xml.bind.annotation.*;

/**
 * Binding class for a LineTo element of Path.
 * @author Rodemfa913
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BLineTo implements BElement {
    @XmlAttribute private final double x, y;
    
    /**
     * Creates a default instance of BLineTo.
     */
    public BLineTo() {
        x = y = 0.0;
    }
    
    /**
     * Creates an instance of BLineTo with properties of specified LineTo.
     * @param lineto a LineTo element of Path.
     */
    public BLineTo(LineTo lineto) {
        x = lineto.getX();
        y = lineto.getY();
    }
    
    @Override public PathElement get() {
        return new LineTo(x, y);
    }
}