package dwgfx.bind;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.shape.Circle;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Binding class for a Circle.
 * @author Rodemfa913
 */
public class BCircle extends BShape {
    @XmlAttribute private final double radius;
    
    /**
     * Creates a default instance of BCircle.
     */
    public BCircle() {
        super();
        radius = 50.0;
    }
    
    /**
     * Creates an instance of BCircle with properties of specified Circle.
     * @param circle a Circle.
     */
    public BCircle(Circle circle) {
        super(circle);
        radius = circle.getRadius();
    }
    
    @Override public TreeItem<Node> get() {
        Circle circle = new Circle(radius);
        load(circle);
        return new TreeItem<>(circle);
    }
}