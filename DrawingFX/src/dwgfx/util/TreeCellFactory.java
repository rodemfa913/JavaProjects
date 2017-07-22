package dwgfx.util;

import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.util.Callback;

/**
 * A cell factory class.
 * @author rodemfa
 */
public class TreeCellFactory implements Callback<TreeView<Node>, TreeCell<Node>> {
    @Override public TreeCell<Node> call(TreeView<Node> param) {
        return new NodeTreeCell();
    }
}