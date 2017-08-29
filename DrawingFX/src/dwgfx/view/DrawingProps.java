package dwgfx.view;

import java.io.File;
import java.net.URL;
import java.util.*;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;

/**
 * Controller class of drawing form layout of properties dialog.
 * The form is loaded when the item to be edited is the drawing surface.
 * @author rodemfa
 */
public class DrawingProps implements Initializable, Properties {
    @FXML private Button styleButton;
    @FXML private ColorPicker bgColorPicker;
    @FXML private Spinner<Double> heightSpin, widthSpin;
    private AnchorPane drawing;
    private File styleFile;
    private FileChooser fileChooser;

    @Override public void initialize(URL url, ResourceBundle rb) {
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSS files (*.css)", "*.css"));
    }
    
    /**
     * Applies the changes on item.
     * @throws Exception if the Stylesheet property is incorrect (?).
     */
    @Override public void handleApply() throws Exception {
        drawing.setMinWidth(widthSpin.getValue());
        drawing.setMinHeight(heightSpin.getValue());
        drawing.setBackground(new Background(new BackgroundFill(bgColorPicker.getValue(), null, null)));
        List<String> stylesheets = drawing.getStylesheets();
        stylesheets.clear();
        if (styleFile != null) {
            stylesheets.add(styleFile.toURI().toURL().toString());
        }
    }
    
    /**
     * Sets the item for edition.
     * @param item the item to be edited.
     */
    @Override public void setItem(Node item) {
        drawing = (AnchorPane) item;
        widthSpin.getValueFactory().setValue(drawing.getMinWidth());
        heightSpin.getValueFactory().setValue(drawing.getMinHeight());
        bgColorPicker.setValue((Color) drawing.getBackground().getFills().get(0).getFill());
        List<String> stylesheets = drawing.getStylesheets();
        if (!stylesheets.isEmpty()) {
            setStyleFile(new File(stylesheets.get(0)));
        }
    }
    
    @FXML private void handleStyle() {
        setStyleFile(fileChooser.showOpenDialog(styleButton.getScene().getWindow()));
    }
    
    private void setStyleFile(File sf) {
        styleFile = sf;
        if (styleFile == null) {
            styleButton.setText("Choose...");
        } else {
            styleButton.setText(styleFile.getName());
        }
    }
}