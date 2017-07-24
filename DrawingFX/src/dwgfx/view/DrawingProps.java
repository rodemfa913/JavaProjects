package dwgfx.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Controller class of drawing form layout of properties dialog.
 * The form is loaded when the item to be edited is the entire drawing.
 * @author rodemfa
 */
public class DrawingProps implements Properties {
    @FXML private ColorPicker bgColorPicker;
    @FXML private Spinner<Double> widthSpin, heightSpin;
    private AnchorPane drawing;
    
    /**
     * Sets the item for edition.
     * @param item the item to be edited.
     */
    @Override public void setItem(Node item) {
        drawing = (AnchorPane) item;
        widthSpin.getValueFactory().setValue(drawing.getMinWidth());
        heightSpin.getValueFactory().setValue(drawing.getMinHeight());
        bgColorPicker.setValue((Color) drawing.getBackground().getFills().get(0).getFill());
    }

    /**
     * Applies the changes on item.
     */
    @Override public void handleApply() {
        drawing.setMinWidth(widthSpin.getValue());
        drawing.setMinHeight(heightSpin.getValue());
        drawing.setBackground(new Background(new BackgroundFill(bgColorPicker.getValue(), null, null)));
    }
}