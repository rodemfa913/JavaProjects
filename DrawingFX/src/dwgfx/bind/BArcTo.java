package dwgfx.bind;

import javafx.scene.shape.*;
import javax.xml.bind.annotation.*;

/**
 * Binding class for an ArcTo element of Path.
 * @author Rodemfa913
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BArcTo implements BElement {
    @XmlAttribute private final boolean large;
    @XmlAttribute private final boolean sweep;
    @XmlAttribute private final double rx;
    @XmlAttribute private final double ry;
    @XmlAttribute private final double xRot;
    @XmlAttribute private final double x;
    @XmlAttribute private final double y;
    
    /**
     * Creates a default instance of BArcTo.
     */
    public BArcTo() {
        rx = ry = xRot = x = y = 0.0;
        large = sweep = false;
    }
    
    /**
     * Creates an instance of BArcTo with properties of specified ArcTo.
     * @param arcto an ArcTo element of Path.
     */
    public BArcTo(ArcTo arcto) {
        rx = arcto.getRadiusX();
        ry = arcto.getRadiusY();
        xRot = arcto.getXAxisRotation();
        x = arcto.getX();
        y = arcto.getY();
        large = arcto.isLargeArcFlag();
        sweep = arcto.isSweepFlag();
    }
    
    @Override public PathElement get() {
        return new ArcTo(rx, ry, xRot, x, y, large, sweep);
    }
}