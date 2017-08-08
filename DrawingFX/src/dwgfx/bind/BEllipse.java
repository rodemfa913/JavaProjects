package dwgfx.bind;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.shape.Ellipse;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Binding class for an Ellipse.
 * @author rodemfa
 */
public class BEllipse extends BShape {
    @XmlAttribute private final double rx;
    @XmlAttribute private final double ry;
    
    /**
     * Creates a default instance of BEllipse.
     */
    public BEllipse() {
        super();
        rx = 100.0;
        ry = 75.0;
    }
    
    /**
     * Creates an instance of BEllipse with properties of specified Ellipse.
     * @param ellipse an {@link Ellipse}.
     */
    public BEllipse(Ellipse ellipse) {
        super(ellipse);
        rx = ellipse.getRadiusX();
        ry = ellipse.getRadiusY();
    }

    @Override public TreeItem<Node> get() {
        Ellipse ellipse = new Ellipse(rx, ry);
        load(ellipse);
        return new TreeItem<>(ellipse);
    }
}