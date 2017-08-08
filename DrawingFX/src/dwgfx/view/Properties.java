package dwgfx.view;

import javafx.scene.Node;

/**
 * Interface for the properties dialog.
 * The dialog is composed by the {@link NodeProps root} layout and one of form layouts,
 * which depend of item to be edited.
 * @author rodemfa
 */
public interface Properties {
    /**
     * Applies the changes on item.
     * @throws Exception can or cannot throw an {@link Exception};
     * see the class implementations for details.
     */
    public void handleApply() throws Exception;
    
    /**
     * Sets the item for edition.
     * @param item the item to be edited.
     * @throws Exception idem for the {@link #handleApply() handleApply} method.
     */
    public void setItem(Node item) throws Exception;
}