package dwgfx.bind;

import java.util.*;
import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.shape.*;
import javax.xml.bind.annotation.*;

/**
 * ...
 * @author Rodemfa913
 */
public class BPath extends BShape {
    @XmlElementWrapper @XmlElements({
        @XmlElement(name = "arcto", type = BArcTo.class),
        @XmlElement(name = "close", type = BClose.class),
        @XmlElement(name = "curveto", type = BCurveTo.class),
        @XmlElement(name = "lineto", type = BLineTo.class),
        @XmlElement(name = "moveto", type = BMoveTo.class)
    })
    private final List<BClose> elements;
    
    /**
     * ...
     */
    public BPath() {
        super();
        elements = new ArrayList<>();
    }
    
    /**
     * ...
     * @param path ...
     */
    public BPath(Path path) {
        super(path);
        elements = new ArrayList<>();
        path.getElements().forEach((element) -> {
            if (element instanceof ArcTo) {
                elements.add(new BArcTo((ArcTo) element));
            } else if (element instanceof CubicCurveTo) {
                elements.add(new BCurveTo((CubicCurveTo) element));
            } else if (element instanceof LineTo) {
                elements.add(new BLineTo((LineTo) element));
            } else if (element instanceof MoveTo) {
                elements.add(new BMoveTo((MoveTo) element));
            } else {
                elements.add(new BClose());
            }
        });
    }
    
    @Override public TreeItem<Node> get() {
        Path path = new Path();
        List<PathElement> elems = path.getElements();
        elements.forEach((element) -> {
            elems.add(element.get());
        });
        load(path);
        return new TreeItem<>(path);
    }
}