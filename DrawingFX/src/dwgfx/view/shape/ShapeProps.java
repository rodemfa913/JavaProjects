package dwgfx.view.shape;

import dwgfx.view.Props;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

/**
 * Controller class for Shape subcomponent of Properties dialog.
 *
 * @author rodemfa
 */
public class ShapeProps implements Initializable, Props {
    @FXML private Accordion shapeRoot;
    @FXML private ColorPicker fillColorPicker;
    @FXML private ColorPicker strokeColorPicker;
    @FXML private ComboBox<StrokeLineCap> lineCapCombo;
    @FXML private ComboBox<StrokeLineJoin> lineJoinCombo;
    @FXML private Spinner<Double> strokeWidthSpin;
    @FXML private TitledPane genTitledPane;
    private Props controller;
    private Shape shape;
    
    /**
     * Initializes the controller class.
     * 
     * @param url
     * @param rb
     */
    @Override public void initialize(URL url, ResourceBundle rb) {
        shapeRoot.setExpandedPane(genTitledPane);
    }

    @Override public void setItem(Node item) throws Exception {
        shape = (Shape) item;
        fillColorPicker.setValue((Color) shape.getFill());
        strokeColorPicker.setValue((Color) shape.getStroke());
        strokeWidthSpin.getValueFactory().setValue(shape.getStrokeWidth());
        lineCapCombo.setValue(shape.getStrokeLineCap());
        lineJoinCombo.setValue(shape.getStrokeLineJoin());
        FXMLLoader loader = new FXMLLoader();
        String filePath = "RectProps.fxml";
        loader.setLocation(getClass().getResource(filePath));
        Pane root = loader.load();
        genTitledPane.setContent(root);
        controller = loader.getController();
        controller.setItem(shape);
    }

    @Override public void handleApply() throws Exception {
        controller.handleApply();
        shape.setFill(fillColorPicker.getValue());
        shape.setStroke(strokeColorPicker.getValue());
        shape.setStrokeWidth(strokeWidthSpin.getValue());
        shape.setStrokeLineCap(lineCapCombo.getValue());
        shape.setStrokeLineJoin(lineJoinCombo.getValue());
    }
}
