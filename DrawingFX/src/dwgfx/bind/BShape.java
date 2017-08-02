package dwgfx.bind;

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
    @XmlAttribute private final double x;
    @XmlAttribute private final double y;
    @XmlAttribute private final String id;
    @XmlElement private final BColor fill;
    @XmlElement private final Stroke stroke;
    
    /**
     * Creates a default instance of BShape.
     */
    public BShape() {
        id = "item";
        fill = new BColor();
        stroke = new Stroke();
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
        x = shape.getLayoutX();
        y = shape.getLayoutY();
    }
    
    /*/**
     * Loads a Shape with properties of BShape.
     * This method is called by subclasses that implements the {@link #get() get} method.
     * @param shape ...
     *
    protected void load(Shape shape) {
        shape.setId(id);
        shape.setFill(fill.get());
        shape.setStroke(stroke.getColor());
        shape.setStrokeWidth(stroke.getWidth());
        shape.setStrokeLineCap(stroke.getLineCap());
        shape.setStrokeLineJoin(stroke.getLineJoin());
        shape.getTransforms().add(new Affine());
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }
    
    /**
     * Gets a new Shape with properties of BShape.
     * @return a new Shape.
     *
    public abstract Shape get();*/
    
    public void load(Shape shape) {
        shape.setId(id);
        shape.setFill(fill.get());
        shape.setStroke(stroke.getColor());
        shape.setStrokeWidth(stroke.getWidth());
        shape.setStrokeLineCap(stroke.getLineCap());
        shape.setStrokeLineJoin(stroke.getLineJoin());
        shape.getTransforms().add(new Affine());
        shape.setLayoutX(x);
        shape.setLayoutY(y);
    }
}