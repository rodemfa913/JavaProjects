package dwgfx.bind;

import javafx.scene.Group;
import javax.xml.bind.annotation.*;

/**
 * Binding class for a layer.
 * A layer is represented by a Group.
 * @author Rodemfa913
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Layer {
    @XmlAttribute private final String id;
    @XmlAttribute private final double opacity;
    @XmlAttribute private final boolean visible;
    
    /**
     * Creates a default instance of Layer.
     */
    public Layer() {
        id = "layer";
        opacity = 1.0;
        visible = true;
    }
    
    /**
     * Creates an instance of Layer with properties of specified layer.
     * @param layer a layer.
     */
    public Layer(Group layer) {
        id = layer.getId();
        opacity = layer.getOpacity();
        visible = layer.isVisible();
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
        return layer;
    }
}