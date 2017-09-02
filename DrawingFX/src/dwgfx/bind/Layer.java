package dwgfx.bind;

import java.util.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javax.xml.bind.annotation.*;

/**
 * Binding class for a layer.
 * A layer is represented by the Group class.
 * @author Rodemfa913
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Layer {
    @XmlAttribute private final boolean visible;
    @XmlAttribute private final double opacity;
    @XmlAttribute private final String id;
    @XmlElementWrapper @XmlElements({
        @XmlElement(name = "arc", type = BArc.class),
        @XmlElement(name = "circle", type = BCircle.class),
        @XmlElement(name = "ellipse", type = BEllipse.class),
        @XmlElement(name = "path", type = BPath.class),
        @XmlElement(name = "rectangle", type = BRectangle.class),
        @XmlElement(name = "text", type = BText.class)
    })
    private final List<BShape> shapes;
    
    /**
     * Creates a default instance of Layer.
     */
    public Layer() {
        id = "layer";
        opacity = 1.0;
        visible = true;
        shapes = new ArrayList();
    }
    
    /**
     * Creates an instance of Layer with properties of specified layer.
     * @param layer a layer.
     */
    public Layer(Group layer) {
        id = layer.getId();
        opacity = layer.getOpacity();
        visible = layer.isVisible();
        shapes = new ArrayList();
        layer.getChildren().forEach((shape) -> {
            if (shape instanceof Arc) {
                shapes.add(new BArc((Arc) shape));
            } else if (shape instanceof Circle) {
                shapes.add(new BCircle((Circle) shape));
            } else if (shape instanceof Ellipse) {
                shapes.add(new BEllipse((Ellipse) shape));
            } else if (shape instanceof Path) {
                shapes.add(new BPath((Path) shape));
            } else if (shape instanceof Text) {
                shapes.add(new BText((Text) shape));
            } else {
                shapes.add(new BRectangle((Rectangle) shape));
            }
        });
    }
    
    /**
     * Gets a new layer with properties of Layer.
     * @return a new layer.
     */
    public Group get() {
        Group layer = new Group();
        layer.setId(id);
        layer.setOpacity(opacity);
        layer.setVisible(visible);
        List<Node> children = layer.getChildren();
        shapes.forEach((shape) -> {
            children.add(shape.get());
        });
        return layer;
    }
}