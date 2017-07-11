package dwgfx.view;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.*;
import javafx.scene.Node;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 * Controller class for the primary interface
 *
 * @author rodemfa
 */
public class PrimaryScene implements Initializable {
    @FXML private AnchorPane canvas;
    @FXML private TreeView<Node> nodeTree;
    private Stage primaryStage;
    
    @Override public void initialize(URL url, ResourceBundle rb) {}
    
    @FXML private void handleNew() {
        System.out.println("New menu item activated.");
    }
    
    @FXML private void handleOpen() {
        System.out.println("Open menu item activated.");
    }
    
    @FXML private void handleQuit() {
        primaryStage.close();
    }
    
    @FXML private void handleSave() {
        System.out.println("Save menu item activated.");
    }
    
    public void setStage(Stage stage) {
        primaryStage = stage;
    }
}