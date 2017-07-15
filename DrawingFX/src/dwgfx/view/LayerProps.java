package dwgfx.view;

import javafx.fxml.FXML;
import javafx.scene.*;
import javafx.scene.control.*;

/**
 * Controller class for Layer subcomponent of Properties dialog.
 *
 * @author rodemfa
 */
public class LayerProps implements Props {
    @FXML private CheckBox visibleCheck;
    @FXML private Slider opacitySlider;
    private Group layer;
    
    @Override public void setItem(Node item) {
        layer = (Group) item;
        opacitySlider.setValue(100.0 * layer.getOpacity());
        visibleCheck.setSelected(layer.isVisible());
    }

    @Override public void handleApply() {
        layer.setOpacity(opacitySlider.getValue() / 100.0);
        layer.setVisible(visibleCheck.isSelected());
    }
}