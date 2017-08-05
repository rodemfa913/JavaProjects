package dwgfx.bind;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.shape.Rectangle;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * Binding class for a Rectangle.
 * @author rodemfa
 */
public class BRectangle extends BShape {
    @XmlAttribute private final double arcHeight;
    @XmlAttribute private final double arcWidth;
    @XmlAttribute private final double height;
    @XmlAttribute private final double width;
    
    /**
     * Creates a default instance of BRectangle.
     */
    public BRectangle() {
        super();
        width = 100.0;
        height = 75.0;
        arcWidth = arcHeight = 0.0;
    }
    
    /**
     * Creates an instance of BRectangle with properties of specified Rectangle.
     * @param rect a Rectangle.
     */
    public BRectangle(Rectangle rect) {
        super(rect);
        width = rect.getWidth();
        height = rect.getHeight();
        arcWidth = rect.getArcWidth();
        arcHeight = rect.getArcHeight();
    }
    
    @Override public TreeItem<Node> get() {
        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(arcWidth);
        rect.setArcHeight(arcHeight);
        load(rect);
        return new TreeItem<>(rect);
    }
}