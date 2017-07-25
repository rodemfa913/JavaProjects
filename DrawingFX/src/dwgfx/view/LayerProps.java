package dwgfx.view;

import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;

/**
 * Controller class of layer form layout of properties dialog.
 * The form is loaded when the item to be edited is a layer.
 * @author rodemfa
 */
public class LayerProps implements Properties {
    @FXML private CheckBox visibleCheck;
    @FXML private Slider opacitySlider;
    private Group layer;

    /**
     * Applies the changes on item.
     */
    @Override public void handleApply() {
        layer.setOpacity(opacitySlider.getValue() / 100.0);
        layer.setVisible(visibleCheck.isSelected());
    }
    
    /**
     * Sets the item for edition.
     * @param item the item to be edited.
     */
    @Override public void setItem(Node item) {
        layer = (Group) item;
        opacitySlider.setValue(100.0 * layer.getOpacity());
        visibleCheck.setSelected(layer.isVisible());
    }
}