package dwgfx.view;

import javafx.scene.Node;

/**
 * Interface for {@link Properties} dialog and its subcomponents.
 *
 * @author rodemfa
 */
public interface Properties {
    /**
     * Sets the item for edition.
     * 
     * @param item
     * @throws Exception
     */
    public void setItem(Node item) throws Exception;
    
    /**
     * Handles action event of Apply button.
     * 
     * @throws Exception
     */
    public void handleApply() throws Exception;
}