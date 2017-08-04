package dwgfx.util;

import java.util.*;
import javafx.scene.control.TreeItem;

/**
 * ...
 * @author rodemfa
 */
public abstract class TreeItemUtil {
    /**
     * ...
     * @param item ...
     * @return ...
     */
    public static List<Integer> getTreePath(TreeItem<?> item) {
        List<Integer> treePath = new ArrayList<>();
        TreeItem<?> parent = item.getParent();
        while (parent != null) {
            treePath.add(0, parent.getChildren().indexOf(item));
            item = parent;
            parent = item.getParent();
        }
        return treePath;
    }
}