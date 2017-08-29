package dwgfx.view.shape;

import dwgfx.view.Properties;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Spinner;
import javafx.scene.shape.Rectangle;

/**
 * Controller class of Rectangle form layout of properties dialog.
 * The form is loaded when the item to be edited is an Rectangle.
 * It is shown in the General panel of Shape form.
 * @author Rodemfa913
 */
public class RectProps implements Properties {
    @FXML private Spinner<Double> arcHeightSpin, arcWidthSpin, heightSpin, widthSpin, xSpin, ySpin;
    private Rectangle rect;

    /**
     * Applies the changes on item.
     */
    @Override public void handleApply() {
        rect.setLayoutX(xSpin.getValue());
        rect.setLayoutY(ySpin.getValue());
        rect.setWidth(widthSpin.getValue());
        rect.setHeight(heightSpin.getValue());
        rect.setArcWidth(arcWidthSpin.getValue());
        rect.setArcHeight(arcHeightSpin.getValue());
    }
    
    /**
     * Sets the item for edition.
     * @param item the item to be edited.
     */
    @Override public void setItem(Node item) {
        rect = (Rectangle) item;
        xSpin.getValueFactory().setValue(rect.getLayoutX());
        ySpin.getValueFactory().setValue(rect.getLayoutY());
        widthSpin.getValueFactory().setValue(rect.getWidth());
        heightSpin.getValueFactory().setValue(rect.getHeight());
        arcWidthSpin.getValueFactory().setValue(rect.getArcWidth());
        arcHeightSpin.getValueFactory().setValue(rect.getArcHeight());
    }
}