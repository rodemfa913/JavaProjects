package dwgfx.view.shape;

import dwgfx.util.PathUtil;
import dwgfx.view.Properties;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.shape.Path;

/**
 * Controller class of Path form layout of properties dialog.
 * The form is loaded when the item to be edited is an Path.
 * It is shown in the General panel of Shape form.
 * @author rodemfa
 */
public class PathProps implements Properties {
    @FXML private Spinner<Double> xSpin;
    @FXML private Spinner<Double> ySpin;
    @FXML private TextArea dataText;
    private Path path;
    
    /**
     * Sets the item for edition.
     * @param item the item to be edited.
     */
    @Override public void setItem(Node item) {
        path = (Path) item;
        xSpin.getValueFactory().setValue(path.getLayoutX());
        ySpin.getValueFactory().setValue(path.getLayoutY());
        dataText.setText(PathUtil.toString(path.getElements()));
    }

    /**
     * Applies the changes on item.
     * @throws Exception if the Data property of Path is bad formated.
     */
    @Override public void handleApply() throws Exception {
        path.setLayoutX(xSpin.getValue());
        path.setLayoutY(ySpin.getValue());
        path.getElements().setAll(PathUtil.parseElements(dataText.getText()));
    }
}