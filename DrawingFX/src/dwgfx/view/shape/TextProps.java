package dwgfx.view.shape;

import dwgfx.view.Properties;
import java.net.URL;
import java.util.*;
import javafx.collections.FXCollections;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.text.*;

/**
 * Controller class of Text form layout of properties dialog.
 * The form is loaded when the item to be edited is a Text.
 * It is shown in the General panel of Shape form.
 * <br>
 * Layout reference: <a href="https://github.com/farrukh-obaid/custom-controls">Font Picker</a>.
 * @author rodemfa
 */
public class TextProps implements Initializable, Properties {
    @FXML private CheckBox boldCheck;
    @FXML private CheckBox italicCheck;
    @FXML private ComboBox<String> familyCombo;
    @FXML private Spinner<Double> sizeSpin;
    @FXML private Spinner<Double> xSpin;
    @FXML private Spinner<Double> ySpin;
    @FXML private TextArea bodyText;
    private Text text;
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        List<String> families = Font.getFamilies();
        familyCombo.setItems(FXCollections.observableArrayList(families));
    }

    /**
     * Applies the changes on item.
     */
    @Override public void handleApply() {
        text.setLayoutX(xSpin.getValue());
        text.setLayoutY(ySpin.getValue());
        String family = familyCombo.getValue();
        FontWeight weight = boldCheck.isSelected() ? FontWeight.BOLD : FontWeight.NORMAL;
        FontPosture posture = italicCheck.isSelected() ? FontPosture.ITALIC : FontPosture.REGULAR;
        Double size = sizeSpin.getValue();
        text.setFont(Font.font(family, weight, posture, size));
        text.setText(bodyText.getText());
    }

    /**
     * Sets the item for edition.
     * @param item the item to be edited.
     */
    @Override public void setItem(Node item) {
        text = (Text) item;
        xSpin.getValueFactory().setValue(text.getLayoutX());
        ySpin.getValueFactory().setValue(text.getLayoutY());
        Font font = text.getFont();
        familyCombo.setValue(font.getFamily());
        sizeSpin.getValueFactory().setValue(font.getSize());
        String style = font.getStyle();
        boldCheck.setSelected(style.contains("Bold"));
        italicCheck.setSelected(style.contains("Italic"));
        bodyText.setText(text.getText());
    }
}