package dwgfx.bind;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.shape.Arc;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * ...
 * @author Rodemfa913
 */
public class BArc extends BShape {
    @XmlAttribute private final double length;
    @XmlAttribute private final double rx;
    @XmlAttribute private final double ry;
    @XmlAttribute private final double start;
    
    /**
     * ...
     */
    public BArc() {
        super();
        rx = 100.0;
        ry = 75.0;
        start = 0.0;
        length = 90.0;
    }
    
    /**
     * ...
     * @param arc ...
     */
    public BArc(Arc arc) {
        super(arc);
        rx = arc.getRadiusX();
        ry = arc.getRadiusY();
        start = arc.getStartAngle();
        length = arc.getLength();
    }
    
    @Override public TreeItem<Node> get() {
        Arc arc = new Arc(0, 0, rx, ry, start, length);
        load(arc);
        return new TreeItem<>(arc);
    }
}