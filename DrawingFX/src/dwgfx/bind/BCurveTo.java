package dwgfx.bind;

import javafx.scene.shape.*;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * ...
 * @author Rodemfa913
 */
public class BCurveTo extends BClose {
    @XmlAttribute private final double x;
    @XmlAttribute private final double x1;
    @XmlAttribute private final double x2;
    @XmlAttribute private final double y;
    @XmlAttribute private final double y1;
    @XmlAttribute private final double y2;
    
    /**
     * ...
     */
    public BCurveTo() {
        x1 = y1 = x2 = y2 = x = y = 0.0;
    }
    
    /**
     * ...
     * @param curveto ...
     */
    public BCurveTo(CubicCurveTo curveto) {
        x1 = curveto.getControlX1();
        y1 = curveto.getControlY1();
        x2 = curveto.getControlX2();
        y2 = curveto.getControlY2();
        x = curveto.getX();
        y = curveto.getY();
    }
    
    @Override public PathElement get() {
        return new CubicCurveTo(x1, y1, x2, y2, x, y);
    }
}