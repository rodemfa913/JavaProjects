package dwgfx.view;

import dialogs.ExceptionDialog;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

/**
 * Controller class for the root layout of properties dialog.
 * The dialog components depends of item to be edited, which
 * can be the entire drawing, a layer or one of the shapes.
 * @author rodemfa
 */
public class NodeProps implements Properties {
    @FXML private TextField idText;
    @FXML private VBox propsRoot;
    private Node item;
    private Properties controller;
    
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
        controller = loader.getController();
        controller.setItem(item);
    }

    /**
     * Applies the edition on item.
     * All the Exceptions thrown by any component will be caught here.
     */
    @Override public void handleApply() {
        item.setId(idText.getText());
        try {
            controller.handleApply();
        } catch (Exception ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.showAndWait();
        }
    }
}