package dwgfx.bind;

import javafx.scene.shape.*;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * ...
 * @author rodemfa
 */
public class BEllipse extends BShape {
    @XmlAttribute private final double rx;
    @XmlAttribute private final double ry;
    
    /**
     * ...
     */
    public BEllipse() {
        super();
        rx = 100.0;
        ry = 75.0;
    }
    
    /**
     * ...
     * @param ellipse ...
     */
    public BEllipse(Ellipse ellipse) {
        super(ellipse);
        rx = ellipse.getRadiusX();
        ry = ellipse.getRadiusY();
    }

    @Override public Shape get() {
        Ellipse ellipse = new Ellipse(rx, ry);
        load(ellipse);
        return ellipse;
    }
}