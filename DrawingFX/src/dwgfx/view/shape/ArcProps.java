package dwgfx.view.shape;

import dwgfx.view.Properties;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.shape.*;

/**
 * Controller class of Arc-Circle-Ellipse form layout of properties dialog.
 * The form is loaded when the item to be edited is an Arc, Circle or Ellipse.
 * It is shown in the General panel of Shape form.
 * @author Rodemfa913
 */
public class ArcProps implements Properties {
    @FXML private Label lengthLabel, rxLabel, ryLabel, startLabel;
    @FXML private Spinner<Double> cxSpin, cySpin, lengthSpin, rxSpin, rySpin, startSpin;
    private Shape shape;

    /**
     * Applies the changes on item.
     */
    @Override public void handleApply() {
        shape.setLayoutX(cxSpin.getValue());
        shape.setLayoutY(cySpin.getValue());
        if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            circle.setRadius(rxSpin.getValue());
        } else if (shape instanceof Ellipse) {
            Ellipse ellipse = (Ellipse) shape;
            ellipse.setRadiusX(rxSpin.getValue());
            ellipse.setRadiusY(rySpin.getValue());
        } else {
            Arc arc = (Arc) shape;
            arc.setRadiusX(rxSpin.getValue());
            arc.setRadiusY(rySpin.getValue());
            arc.setStartAngle(startSpin.getValue());
            arc.setLength(lengthSpin.getValue());
        }
    }
    
    /**
     * Sets the item for edition.
     * @param item the item to be edited.
     */
    @Override public void setItem(Node item) {
        shape = (Shape) item;
        cxSpin.getValueFactory().setValue(shape.getLayoutX());
        cySpin.getValueFactory().setValue(shape.getLayoutY());
        if (shape instanceof Circle) {
            Circle circle = (Circle) shape;
            rxSpin.getValueFactory().setValue(circle.getRadius());
        } else {
            rxLabel.setText("Radius X:");
            ryLabel.setVisible(true);
            rySpin.setVisible(true);
            if (shape instanceof Ellipse) {
                Ellipse ellipse = (Ellipse) shape;
                rxSpin.getValueFactory().setValue(ellipse.getRadiusX());
                rySpin.getValueFactory().setValue(ellipse.getRadiusY());
            } else {
                startLabel.setVisible(true);
                startSpin.setVisible(true);
                lengthLabel.setVisible(true);
                lengthSpin.setVisible(true);
                Arc arc = (Arc) shape;
                rxSpin.getValueFactory().setValue(arc.getRadiusX());
                rySpin.getValueFactory().setValue(arc.getRadiusY());
                startSpin.getValueFactory().setValue(arc.getStartAngle());
                lengthSpin.getValueFactory().setValue(arc.getLength());
            }
        }
    }
}