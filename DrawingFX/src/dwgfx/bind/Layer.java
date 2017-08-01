package dwgfx.bind;

import java.util.*;
import javafx.scene.*;
import javafx.scene.shape.Rectangle;
import javax.xml.bind.annotation.*;

/**
 * Binding class for a layer.
 * A layer is represented by a Group.
 * @author Rodemfa913
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Layer {
    @XmlAttribute private final boolean visible;
    @XmlAttribute private final double opacity;
    @XmlAttribute private final String id;
    @XmlElementWrapper @XmlElements({
        @XmlElement(name = "rectangle", type = BRectangle.class)
    })
    private List<BShape> items;
    
    /**
     * Creates a default instance of Layer.
     */
    public Layer() {
        id = "layer";
        opacity = 1.0;
        visible = true;
        items = new ArrayList<>();
    }
    
    /**
     * Creates an instance of Layer with properties of specified layer.
     * @param layer a layer.
     */
    public Layer(Group layer) {
        id = layer.getId();
        opacity = layer.getOpacity();
        visible = layer.isVisible();
        items = new ArrayList<>();
        layer.getChildren().forEach((item) -> {
            // if (...) else {
            items.add(new BRectangle((Rectangle) item));
            // }
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
        items.forEach((item) -> {
            children.add(item.get());
        });
        return layer;
    }
}