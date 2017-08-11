package dwgfx.bind;

import javafx.scene.shape.PathElement;

/**
 * Interface for the PathElement binding classes.
 * @author rodemfa
 */
public interface BElement {
    /**
     * Gets a new instance of PathElement with properties of this BElement implementation.
     * @return a new PathElement.
     */
    public PathElement get();
}
