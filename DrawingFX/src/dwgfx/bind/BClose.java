package dwgfx.bind;

import javafx.scene.shape.*;
import javax.xml.bind.annotation.*;

/**
 * ...
 * @author Rodemfa913
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BClose {
    /**
     * ...
     * @return ...
     */
    public PathElement get() {
        return new ClosePath();
    }
}