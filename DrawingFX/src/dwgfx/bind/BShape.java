package dwgfx.bind;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.scene.transform.Affine;
import javax.xml.bind.annotation.*;

/**
 * Abstract binding class for a Shape.
 * It can be an Arc, Circle, Ellipse, Path, Rectangle or Text.
 * @author rodemfa
 */
@XmlAccessorType(XmlAccessType.FIELD)
public abstract class BShape {
    @XmlAttribute private final double x, y;
    @XmlAttribute private final String id;
    @XmlElement private final BAffine transform;
    @XmlElement private final BColor fill;
    @XmlElement private final Stroke stroke;
    
    /**
     * Creates a default instance of BShape.
     */
    public BShape() {
        id = "item";
        fill = new BColor();
        stroke = new Stroke();
        transform = new BAffine();
        x = y = 100.0;
    }
    
    /**
     * Creates an instance of BShape with parameters of specified Shape.
     * @param shape a Shape;
     */
    public BShape(Shape shape) {
        id = shape.getId();
        fill = new BColor((Color) shape.getFill());
        stroke = new Stroke(
                (Color) shape.getStroke(), shape.getStrokeWidth(),
                shape.getStrokeLineCap(), shape.getStrokeLineJoin()
        );
        transform = new BAffine((Affine) shape.getTransforms().get(0));
        x = shape.getLayoutX();
        y = shape.getLayoutY();
    }
    
    /**
     * Sets the properties of BShape into specified Shape.
     * This method is called by subclasses that implements the {@link #get() get} method.
     * @param shape a Shape.
     */
    protected void load(Shape shape) {
        shape.setId(id);
        shape.setFill(fill.get());
        shape.setStroke(stroke.getColor());
        shape.setStrokeWidth(stroke.getWidth());
        shape.setStrokeLineCap(stroke.getLineCap());
        shape.setStrokeLineJoin(stroke.getLineJoin());
        shape.getTransforms().add(transform.get());
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }
    
    /**
     * Gets a TreeItem containing a new Shape with properties of BShape.
     * @return a TreeItem containing a new Shape.
     */
    public abstract TreeItem<Node> get();
}