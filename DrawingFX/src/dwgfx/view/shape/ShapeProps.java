package dwgfx.view.shape;

import dwgfx.util.*;
import dwgfx.view.Properties;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;

/**
 * Controller class of Shape form layout of properties dialog.
 * The form is loaded when the item to be edited is a Shape, which can be a Rectangle,
 * Circle, Ellipse, Arc, Path or Text. A specific form is shown in the General drop-down panel.
 * @author rodemfa
 */
public class ShapeProps implements Initializable, Properties {
    @FXML private Accordion shapeRoot;
    @FXML private ColorPicker fillColorPicker, strokeColorPicker;
    @FXML private ComboBox<StrokeLineCap> lineCapCombo;
    @FXML private ComboBox<StrokeLineJoin> lineJoinCombo;
    @FXML private Spinner<Double> strokeWidthSpin;
    @FXML private TextArea transText;
    @FXML private TextField classText;
    @FXML private TitledPane genTitledPane;
    private Properties shapeController;
    private Shape shape;
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        shapeRoot.setExpandedPane(genTitledPane);
    }

    /**
     * Applies the changes on item.
     * @throws Exception if the Transforms property is bad formatted;
     * if the Shape is a Path, can or cannot thrown an Exception;
     * see the {@link PathProps#handleApply() PathProps} class for details.
     */
    @Override public void handleApply() throws Exception {
        shapeController.handleApply();
        shape.getStyleClass().setAll(StyleClassUtil.parseClasses(classText.getText()));
        shape.setFill(fillColorPicker.getValue());
        shape.setStroke(strokeColorPicker.getValue());
        shape.setStrokeWidth(strokeWidthSpin.getValue());
        shape.setStrokeLineCap(lineCapCombo.getValue());
        shape.setStrokeLineJoin(lineJoinCombo.getValue());
        shape.getTransforms().set(0, TransUtil.parseTransform(transText.getText()));
    }

    /**
     * Sets the item for edition.
     * @param item the item to be edited.
     * @throws Exception in the case of loading error.
     */
    @Override public void setItem(Node item) throws Exception {
        shape = (Shape) item;
        classText.setText(StyleClassUtil.toString(shape.getStyleClass()));
        fillColorPicker.setValue((Color) shape.getFill());
        strokeColorPicker.setValue((Color) shape.getStroke());
        strokeWidthSpin.getValueFactory().setValue(shape.getStrokeWidth());
        lineCapCombo.setValue(shape.getStrokeLineCap());
        lineJoinCombo.setValue(shape.getStrokeLineJoin());
        transText.setText(TransUtil.toString((Affine) shape.getTransforms().get(0)));
        FXMLLoader loader = new FXMLLoader();
        String filePath =
                shape instanceof Text ? "TextProps.fxml" :
                shape instanceof Rectangle ? "RectProps.fxml" :
                shape instanceof Path ? "PathProps.fxml" : "ArcProps.fxml";
        loader.setLocation(getClass().getResource(filePath));
        Pane root = loader.load();
        genTitledPane.setContent(root);
        shapeController = loader.getController();
        shapeController.setItem(shape);
    }
}
