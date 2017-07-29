package dwgfx.model;

import javafx.scene.layout.AnchorPane;
import javax.xml.bind.annotation.*;

/**
 * ...
 * @author Rodemfa913
 */
@XmlRootElement(name = "drawing") @XmlAccessorType(XmlAccessType.FIELD)
public class Drawing {
    @XmlAttribute private final double height;
    @XmlAttribute private final double width;
    @XmlAttribute private final String id;
    
    public Drawing() {
        id = "drawing";
        width = height = 400.0;
    }
    
    public Drawing(AnchorPane drawing) {
        id = drawing.getId();
        width = drawing.getMinWidth();
        height = drawing.getMinHeight();
    }
    
    public void load(AnchorPane drawing) {
        drawing.setId(id);
        drawing.setMinWidth(width);
        drawing.setMinHeight(height);
    }
}