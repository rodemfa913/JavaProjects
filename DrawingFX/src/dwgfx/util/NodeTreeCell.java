package dwgfx.util;

import javafx.scene.Node;
import javafx.scene.control.TreeCell;
import javafx.scene.shape.Shape;

/**
 * A subclass of TreeCell&lt;Node&gt;.
 * @author rodemfa
 */
public class NodeTreeCell extends TreeCell<Node> {
    @Override public void updateItem(Node item, boolean empty) {
        super.updateItem(item, empty);
        if (empty || item == null) {
            setText(null);
        } else {
            String text = item.getId();
            if (item instanceof Shape) {
                text += " (" + item.getClass().getSimpleName() + ")";
            }
            setText(text);
        }
        setGraphic(null);
    }
}
