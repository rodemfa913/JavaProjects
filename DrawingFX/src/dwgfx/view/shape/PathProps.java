package dwgfx.view.shape;

import dwgfx.util.PathUtil;
import dwgfx.view.Properties;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.shape.Path;

/**
 * Controller class for Path subcomponent of {@link dwgfx.view.NodeProps Properties} dialog.
 *
 * @author rodemfa
 */
public class PathProps implements Properties {
    @FXML private Spinner<Double> xSpin;
    @FXML private Spinner<Double> ySpin;
    @FXML private TextArea dataText;
    private Path path;
    
    @Override public void setItem(Node item) {
        path = (Path) item;
        xSpin.getValueFactory().setValue(path.getLayoutX());
        ySpin.getValueFactory().setValue(path.getLayoutY());
        dataText.setText(PathUtil.toString(path.getElements()));
    }

    @Override public void handleApply() throws Exception {
        path.setLayoutX(xSpin.getValue());
        path.setLayoutY(ySpin.getValue());
        path.getElements().setAll(PathUtil.parseElements(dataText.getText()));
    }
}