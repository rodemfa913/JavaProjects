package dwgfx.bind;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javax.xml.bind.annotation.*;

/**
 * Binding class for the drawing surface.
 * The drawing surface is represented by an AnchorPane.
 * @author Rodemfa913
 */
@XmlRootElement(name = "drawing") @XmlAccessorType(XmlAccessType.FIELD)
public class Drawing {
    @XmlAttribute private final double height;
    @XmlAttribute private final double width;
    @XmlAttribute private final String id;
    @XmlElement private final BColor background;
    
    /**
     * Creates a default instance of Drawing.
     */
    public Drawing() {
        id = "drawing";
        width = height = 400.0;
        background = new BColor();
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
    }
    
    /**
     * Loads the drawing surface with properties of Drawing.
     * @param drawing the drawing surface.
     */
    public void load(AnchorPane drawing) {
        drawing.setId(id);
        drawing.setMinWidth(width);
        drawing.setMinHeight(height);
        drawing.setBackground(new Background(new BackgroundFill(background.get(), null, null)));
    }
}