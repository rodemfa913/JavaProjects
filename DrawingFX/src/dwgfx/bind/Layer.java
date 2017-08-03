package dwgfx.bind;

import java.util.*;
import javafx.scene.*;
import javafx.scene.shape.*;
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
        @XmlElement(name = "circle", type = BCircle.class),
        @XmlElement(name = "rectangle", type = BRectangle.class)
    })
    private final List<BShape> shapes;
    
    /**
     * Creates a default instance of Layer.
     */
    public Layer() {
        id = "layer";
        opacity = 1.0;
        visible = true;
        shapes = new ArrayList<>();
    }
    
    /**
     * Creates an instance of Layer with properties of specified layer.
     * @param layer a layer.
     */
    public Layer(Group layer) {
        id = layer.getId();
        opacity = layer.getOpacity();
        visible = layer.isVisible();
        shapes = new ArrayList<>();
        layer.getChildren().forEach((shape) -> {
            if (shape instanceof Circle) {
                shapes.add(new BCircle((Circle) shape));
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