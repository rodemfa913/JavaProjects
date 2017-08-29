package dwgfx.bind;

import java.util.*;
import javafx.scene.*;
import javafx.scene.control.TreeItem;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javax.xml.bind.annotation.*;

/**
 * Binding class for the drawing surface.
 * The drawing surface is represented the AnchorPane class.
 * @author Rodemfa913
 */
@XmlRootElement(name = "drawing") @XmlAccessorType(XmlAccessType.FIELD)
public class Drawing {
    @XmlAttribute private final double height, width;
    @XmlAttribute private final String id;
    @XmlElement private final BColor background;
    @XmlElementWrapper @XmlElement(name = "layer")
    private final List<Layer> layers;
    
    /**
     * Creates a default instance of Drawing.
     */
    public Drawing() {
        id = "drawing";
        width = height = 400.0;
        background = new BColor();
        layers = new ArrayList();
    }
    
    /**
     * Creates an instance of Drawing with properties of specified drawing surface.
     * @param drawing the drawing surface.
     */
    public Drawing(AnchorPane drawing) {
        id = drawing.getId();
        width = drawing.getMinWidth();
        height = drawing.getMinHeight();
        background = new BColor((Color) drawing.getBackground().getFills().get(0).getFill());
        layers = new ArrayList();
        drawing.getChildren().forEach((layer) -> {
            layers.add(new Layer((Group) layer));
        });
    }
    
    /**
     * Sets the properties of Drawing into the drawing surface which is
     * the value of specified TreeItem.
     * @param root a TreeItem whose value is the drawing surface.
     */
    public void load(TreeItem<Node> root) {
        AnchorPane drawing = (AnchorPane) root.getValue();
        drawing.setId(id);
        drawing.setMinWidth(width);
        drawing.setMinHeight(height);
        drawing.setBackground(new Background(new BackgroundFill(background.get(), null, null)));
        List<TreeItem<Node>> layItems = root.getChildren();
        List<Node> layNodes = drawing.getChildren();
        layers.forEach((layer) -> {
            TreeItem<Node> layItem = layer.get();
            layItems.add(layItem);
            layNodes.add(layItem.getValue());
        });
    }
}