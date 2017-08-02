package dwgfx.bind;

import java.util.*;
import javafx.beans.value.ChangeListener;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseEvent;
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
        layer.getChildren().forEach((item) -> {
            // if (item instanceof ...) {} else {
            shapes.add(new BRectangle((Rectangle) item));
            // }
        });
    }
    
    /*/**
     * Gets a new layer with properties of Layer.
     * @return a new layer.
     *
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
    }*/
    
    public void load(
            Group layer, TreeItem<Node> layItem,
            ChangeListener<String> idListener, EventHandler<MouseEvent> shapeHandler) {
        layer.setId(id);
        layer.setOpacity(opacity);
        layer.setVisible(visible);
        List<Node> shps = layer.getChildren();
        List<TreeItem<Node>> shpItems = layItem.getChildren();
        shapes.forEach((shape) -> {
            Shape shp;
            // if (shape instanceof ...) {} else {
            shp = new Rectangle();
            // }
            TreeItem<Node> shpItem = new TreeItem<>(shp);
            shape.load(shp);
            shp.idProperty().addListener(idListener);
            shp.setOnMouseClicked(shapeHandler);
            shps.add(shp);
            shpItems.add(shpItem);
        });
    }
}