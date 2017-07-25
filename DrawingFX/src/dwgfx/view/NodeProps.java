package dwgfx.view;

import dialogs.ExceptionDialog;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

/**
 * Controller class of the root layout of properties dialog.
 * @author rodemfa
 */
public class NodeProps implements Properties {
    @FXML private TextField idText;
    @FXML private VBox propsRoot;
    private Node item;
    private Properties formController;

    /**
     * Applies the changes on item.
     */
    @Override public void handleApply() {
        item.setId(idText.getText());
        try {
            formController.handleApply();
        } catch (Exception ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.showAndWait();
        }
    }
    
    /**
     * Sets the item for edition.
     * @param it the item to be edited.
     * @throws Exception in the case of loading error.
     */
    @Override public void setItem(Node it) throws Exception {
        item = it;
        idText.setText(item.getId());
        FXMLLoader loader = new FXMLLoader();
        String filePath =
                item instanceof AnchorPane ? "DrawingProps.fxml" :
                item instanceof Group ? "LayerProps.fxml" : "shape/ShapeProps.fxml";
        loader.setLocation(getClass().getResource(filePath));
        Region root = loader.load();
        VBox.setVgrow(root, Priority.ALWAYS);
        propsRoot.getChildren().set(1, root);
        formController = loader.getController();
        formController.setItem(item);
    }
}