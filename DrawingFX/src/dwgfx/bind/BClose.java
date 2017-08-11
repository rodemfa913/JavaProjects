package dwgfx.bind;

import javafx.scene.shape.*;
import javax.xml.bind.annotation.*;

/**
 * Binding class of a ClosePath element of Path.
 * @author Rodemfa913
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BClose implements BElement {
    @Override public PathElement get() {
        return new ClosePath();
    }
}