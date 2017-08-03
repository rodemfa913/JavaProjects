package dwgfx.bind;

import javafx.scene.shape.*;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * ...
 * @author Rodemfa913
 */
public class BCircle extends BShape {
    @XmlAttribute private final double radius;
    
    /**
     * ...
     */
    public BCircle() {
        super();
        radius = 50.0;
    }
    
    /**
     * ...
     * @param circle ...
     */
    public BCircle(Circle circle) {
        super(circle);
        radius = circle.getRadius();
    }
    
    @Override public Shape get() {
        Circle circle = new Circle(radius);
        load(circle);
        return circle;
    }
}