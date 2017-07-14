package dwgfx;

import dialogs.ExceptionDialog;
import dwgfx.view.PrimaryScene;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

/**
 * The main application class.
 *
 * @author rodemfa
 */
public class MainApp extends Application {
    @Override public void start(Stage primaryStage) {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("view/PrimaryScene.fxml"));
        try {
            BorderPane root = loader.load();
            primaryStage.setScene(new Scene(root));
            primaryStage.setTitle("DrawingFX");
            PrimaryScene controller = loader.getController();
            controller.setStage(primaryStage);
            primaryStage.show();
        } catch (Exception ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.showAndWait();
        }
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
}
