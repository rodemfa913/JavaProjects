package dwgfx.bind;

import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javax.xml.bind.annotation.*;

/**
 * ...
 * @author rodemfa
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BShape {
    @XmlAttribute private final double x;
    @XmlAttribute private final double y;
    @XmlAttribute private final String id;
    @XmlElement private final BColor fill;
    
    /**
     * ...
     */
    public BShape() {
        id = "item";
        fill = new BColor();
        x = y = 100.0;
    }
    
    /**
     * ...
     * @param shape ...
     */
    public BShape(Shape shape) {
        id = shape.getId();
        fill = new BColor((Color) shape.getFill());
        x = shape.getLayoutX();
        y = shape.getLayoutY();
    }
    
    /**
     * ...
     * @return ...
     */
    public abstract Shape get();
    
    /**
     * ...
     * @param shape ...
     */
    protected void load(Shape shape) {
        shape.setId(id);
        shape.setFill(fill.get());
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }
}