package dwgfx.bind;

import javafx.scene.shape.*;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * ...
 * @author rodemfa
 */
public class BRectangle extends BShape {
    @XmlAttribute private final double arcHeight;
    @XmlAttribute private final double arcWidth;
    @XmlAttribute private final double height;
    @XmlAttribute private final double width;
    
    /**
     * ...
     */
    public BRectangle() {
        super();
        width = 100.0;
        height = 75.0;
        arcWidth = arcHeight = 0.0;
    }
    
    /**
     * ...
     * @param rect ...
     */
    public BRectangle(Rectangle rect) {
        super(rect);
        width = rect.getWidth();
        height = rect.getHeight();
        arcWidth = rect.getArcWidth();
        arcHeight = rect.getArcHeight();
    }
    
    @Override public Shape get() {
        Rectangle rect = new Rectangle(width, height);
        rect.setArcWidth(arcWidth);
        rect.setArcHeight(arcHeight);
        load(rect);
        return rect;
    }
}