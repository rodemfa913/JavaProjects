package dwgfx.bind;

import javafx.scene.shape.*;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Binding class for an Arc.
 * @author Rodemfa913
 */
public class BArc extends BShape {
    @XmlAttribute private final double length, rx, ry, start;
    
    /**
     * Creates a default instance of BArc.
     */
    public BArc() {
        super();
        rx = 100.0;
        ry = 75.0;
        start = 0.0;
        length = 90.0;
    }
    
    /**
     * Creates an instance of BArc with properties of specified Arc.
     * @param arc an Arc.
     */
    public BArc(Arc arc) {
        super(arc);
        rx = arc.getRadiusX();
        ry = arc.getRadiusY();
        start = arc.getStartAngle();
        length = arc.getLength();
    }
    
    @Override public Shape get() {
        Arc arc = new Arc(0, 0, rx, ry, start, length);
        load(arc);
        return arc;
    }
}