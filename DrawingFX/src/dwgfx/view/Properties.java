package dwgfx.view;

import dialogs.ExceptionDialog;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;

/**
 * Controller class for properties dialog.
 *
 * @author rodemfa
 */
public class Properties implements Props {
    @FXML private TextField idText;
    @FXML private VBox propsRoot;
    private Node item;
    private Props controller;
    
    @Override public void setItem(Node it) throws Exception {
        item = it;
        idText.setText(item.getId());
        if (item instanceof AnchorPane) {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getResource("DrawingProps.fxml"));
            Region root = loader.load();
            VBox.setVgrow(root, Priority.ALWAYS);
            propsRoot.getChildren().set(1, root);
            controller = loader.getController();
            controller.setItem(item);
        }
    }

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