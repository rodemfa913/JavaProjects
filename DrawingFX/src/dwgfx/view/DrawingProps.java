package dwgfx.view;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * Controller class for Drawing subcomponent of Propertis dialog.
 *
 * @author rodemfa
 */
public class DrawingProps implements Props {
    @FXML private ColorPicker bgColorPicker;
    @FXML private Spinner<Double> widthSpin, heightSpin;
    private AnchorPane drawing;
    
    @Override public void setItem(Node item) {
        drawing = (AnchorPane) item;
        widthSpin.getValueFactory().setValue(drawing.getMinWidth());
        heightSpin.getValueFactory().setValue(drawing.getMinHeight());
        bgColorPicker.setValue((Color) drawing.getBackground().getFills().get(0).getFill());
    }

    @Override public void handleApply() throws Exception {
        drawing.setMinWidth(widthSpin.getValue());
        drawing.setMinHeight(heightSpin.getValue());
        drawing.setBackground(new Background(new BackgroundFill(bgColorPicker.getValue(), null, null)));
    }
}