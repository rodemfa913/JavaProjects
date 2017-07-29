package dwgfx.model;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javax.xml.bind.annotation.*;

/**
 * Model class for a drawing.
 * Used for loading and saving the drawing to a XML file.
 * @author Rodemfa913
 */
@XmlRootElement(name = "drawing") @XmlAccessorType(XmlAccessType.FIELD)
public class Drawing {
    @XmlAttribute private final double height;
    @XmlAttribute private final double width;
    @XmlAttribute private final String id;
    @XmlElement private final DColor background;
    
    /**
     * Creates a default drawing.
     */
    public Drawing() {
        id = "drawing";
        width = height = 400.0;
        background = new DColor();
    }
    
    /**
     * Creates a drawing with properties of specified ...
     * @param drawing 
     */
    public Drawing(AnchorPane drawing) {
        id = drawing.getId();
        width = drawing.getMinWidth();
        height = drawing.getMinHeight();
        background = new DColor((Color) drawing.getBackground().getFills().get(0).getFill());
    }
    
    public void load(AnchorPane drawing) {
        drawing.setId(id);
        drawing.setMinWidth(width);
        drawing.setMinHeight(height);
        drawing.setBackground(new Background(new BackgroundFill(background.get(), null, null)));
    }
}