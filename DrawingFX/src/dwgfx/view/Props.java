package dwgfx.view;

import javafx.scene.Node;

/**
 * Interface for properties dialog and its subcomponents.
 *
 * @author rodemfa
 */
public interface Props {
    /**
     * Set item for edition.
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