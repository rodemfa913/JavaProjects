package dwgfx.view;

import dwgfx.util.TreeCellFactory;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.beans.value.*;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.stage.Stage;

/**
 * Controller class for the primary interface
 *
 * @author rodemfa
 */
public class PrimaryScene implements Initializable {
    @FXML private AnchorPane canvas;
    @FXML private ComboBox<String> typeCombo;
    @FXML private TreeView<Node> nodeTree;
    private IdListener idListener;
    private ShapeHandler shapeHandler;
    private Stage primaryStage;
    
    /**
     * Listener class for id property of Shape.
     * Updates the TreeView when id changes.
     */
    private class IdListener implements ChangeListener<String> {
        @Override public void changed(
                ObservableValue<? extends String> observable,
                String oldValue,
                String newValue) {
            nodeTree.refresh();
        }
    }
    
    private class ShapeHandler implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent event) {
            Node selected = (Node) event.getSource();
            Group layer = (Group) selected.getParent();
            int i = canvas.getChildren().indexOf(layer);
            int j = layer.getChildren().indexOf(selected);
            TreeItem<Node> layItem = nodeTree.getRoot().getChildren().get(i);
            TreeItem<Node> item = layItem.getChildren().get(j);
            nodeTree.getSelectionModel().select(item);
        }
    }
    
    private class TreeSelectionListener implements ChangeListener<TreeItem<Node>> {
        @Override public void changed(
                ObservableValue<? extends TreeItem<Node>> observable,
                TreeItem<Node> oldValue,
                TreeItem<Node> newValue) {
            InnerShadow effect;
            if (oldValue == null || oldValue.getParent() == null) {
                effect = new InnerShadow();
            } else {
                Node value = oldValue.getValue();
                effect = (InnerShadow) value.getEffect();
                value.setEffect(null);
            }
            if (newValue != null && newValue.getParent() != null) {
                newValue.getValue().setEffect(effect);
            }
        }
    }
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        nodeTree.setCellFactory(new TreeCellFactory());
        MultipleSelectionModel<TreeItem<Node>> selectionModel = nodeTree.getSelectionModel();
        selectionModel.selectedItemProperty().addListener(new TreeSelectionListener());
        canvas.setId("Drawing");
        idListener = new IdListener();
        canvas.idProperty().addListener(idListener);
        canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        nodeTree.setRoot(new TreeItem<>(canvas));
        selectionModel.select(0);
        shapeHandler = new ShapeHandler();
    }
    
    @FXML private void handleAdd() {
        String type = typeCombo.getValue();
        if (type == null) {
            type = "Layer";
        }
        if (type.equals("Layer")) {
            addLayer();
        } else {
            TreeItem<Node> layItem = nodeTree.getSelectionModel().getSelectedItem();
            Node node = layItem.getValue();
            if (node instanceof Shape) {
                layItem = layItem.getParent();
            } else if (!(node instanceof Group)) {
                layItem = addLayer();
            }
            Shape shape;
            switch (type) {
                case "Arc":
                    shape = new Arc(0, 0, 100, 75, 0, 90);
                    break;
                case "Circle":
                    shape = new Circle(50);
                    break;
                case "Ellipse":
                    shape = new Ellipse(100, 75);
                    break;
                case "Path":
                    shape = new Path(
                            new MoveTo(0, 0),
                            new LineTo(100, 0),
                            new CubicCurveTo(150, 0, 75, 100, 125, 150),
                            new LineTo(50, 175),
                            new ClosePath()
                    );
                    break;
                case "Text":
                    Text text = new Text("Testing\n1 2 3");
                    text.setFont(new Font("Monospaced Regular", 24));
                    shape = text;
                    break;
                default:
                    shape = new Rectangle(200, 150);
            }
            shape.setId("item");
            shape.idProperty().addListener(idListener);
            shape.setOnMouseClicked(shapeHandler);
            Group layer = (Group) layItem.getValue();
            layer.getChildren().add(shape);
            layItem.getChildren().add(new TreeItem<>(shape));
        }
    }
    
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
    
    private TreeItem<Node> addLayer() {
        Group layer = new Group();
        layer.setId("layer");
        layer.idProperty().addListener(idListener);
        canvas.getChildren().add(layer);
        TreeItem<Node> layItem = new TreeItem<>(layer);
        nodeTree.getRoot().getChildren().add(layItem);
        return layItem;
    }
    
    public void setStage(Stage stage) {
        primaryStage = stage;
    }
}