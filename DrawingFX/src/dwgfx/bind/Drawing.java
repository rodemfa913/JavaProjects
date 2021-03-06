package dwgfx.bind;

import java.util.*;
import javafx.scene.*;
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
    @XmlAttribute private final String id, stylesheet;
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
        stylesheet = "";
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
        List<String> stylesheets = drawing.getStylesheets();
        stylesheet = stylesheets.isEmpty() ? "" : stylesheets.get(0);
        layers = new ArrayList();
        drawing.getChildren().forEach((layer) -> {
            layers.add(new Layer((Group) layer));
        });
    }
    
    /**
     * Sets the properties of Drawing into specified drawing surface.
     * @param drawing the drawing surface.
     */
    public void load(AnchorPane drawing) {
        drawing.setId(id);
        drawing.setMinWidth(width);
        drawing.setMinHeight(height);
        drawing.setBackground(new Background(new BackgroundFill(background.get(), null, null)));
        List<String> stylesheets = drawing.getStylesheets();
        stylesheets.clear();
        if (!stylesheet.isEmpty()) {
            stylesheets.add(stylesheet);
        }
        List<Node> children = drawing.getChildren();
        layers.forEach((layer) -> {
            children.add(layer.get());
        });
    }
}