package dwgfx.view;

import javafx.scene.Node;

/**
 * Interface for {@link NodeProps properties} dialog and its components.
 * @author rodemfa
 */
public interface Properties {
    /**
     * Sets the item for edition.
     * @param item the item to be edited.
     * @throws Exception may or may not throw an Exception; see the subclasses for details.
     */
    public void setItem(Node item) throws Exception;
    
    /**
     * Applies the edition on item.
     * @throws Exception idem for the {@link #setItem(javafx.scene.Node) setItem()} method.
     */
    public void handleApply() throws Exception;
}