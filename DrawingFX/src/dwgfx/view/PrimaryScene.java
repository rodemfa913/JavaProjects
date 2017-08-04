package dwgfx.view;

import dialogs.ExceptionDialog;
import dwgfx.bind.Drawing;
import dwgfx.util.TreeItemUtil;
import java.net.URL;
import java.util.*;
import javafx.beans.value.*;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Affine;
import javafx.stage.*;
import javax.xml.bind.*;

/**
 * ...
 * @author rodemfa
 */
public class PrimaryScene implements Initializable {
    @FXML private AnchorPane canvas;
    @FXML private ComboBox<String> typeCombo;
    @FXML private ScrollPane canvasScroll;
    @FXML private Slider zoomSlider;
    @FXML private TreeView<String> idTree;
    private FileChooser fileChooser;
    private InnerShadow effect;
    private Marshaller m;
    private Node selection;
    private Stage primaryStage;
    private TreeItem<String> selItem;
    private Unmarshaller um;
    
    private class TreeListener implements ChangeListener<TreeItem<String>> {
        @Override public void changed(
                ObservableValue<? extends TreeItem<String>> observable,
                TreeItem<String> oldValue,
                TreeItem<String> newValue) {
            List<Integer> path;
            if (oldValue != null) {
                selection.setEffect(null);
            }
            if (newValue != null) {
                selItem = newValue;
                path = TreeItemUtil.getTreePath(selItem);
                selection = canvas;
                path.forEach((index) -> {
                    selection = ((Parent) selection).getChildrenUnmodifiable().get(index);
                });
                if (selection != canvas) {
                    selection.setEffect(effect);
                }
            }
        }
    }
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        effect = new InnerShadow();
        MultipleSelectionModel<TreeItem<String>> selector = idTree.getSelectionModel();
        selector.selectedItemProperty().addListener(new TreeListener());
        canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        idTree.setRoot(new TreeItem<>(canvas.getId()));
        selector.select(0);
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("XML files (*.xml)", "*.xml"));
        try {
            JAXBContext context = JAXBContext.newInstance(Drawing.class);
            um = context.createUnmarshaller();
            m = context.createMarshaller();
            m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
        } catch (Exception ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.showAndWait();
        }
    }
    
    @FXML private void handleAdd() {
        String type = typeCombo.getValue();
        if (type == null || selection instanceof AnchorPane) {
            type = "Layer";
        }
        if (type.equals("Layer")) {
            Group layer = new Group();
            layer.setId("layer");
            canvas.getChildren().add(layer);
            idTree.getRoot().getChildren().add(new TreeItem<>(layer.getId()));
        } else {
            Node layer = selection;
            TreeItem<String> layItem = selItem;
            if (selection instanceof Shape) {
                layer = layer.getParent();
                layItem = layItem.getParent();
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
            shape.setId("shape");
            shape.setLayoutX(100.0);
            shape.setLayoutY(100.0);
            shape.getTransforms().add(new Affine());
            ((Group) layer).getChildren().add(shape);
            layItem.getChildren().add(new TreeItem<>(shape.getId()));
        }
    }
    
    @FXML private void handleDelete() {
        TreeItem<String> item = selItem;
        TreeItem<String> itParent = item.getParent();
        if (itParent != null) {
            List<TreeItem<String>> itChildren = itParent.getChildren();
            Node node = selection;
            Parent parent = node.getParent();
            List<Node> children;
            if (parent instanceof Group) {
                children = ((Group) parent).getChildren();
            } else {
                children = ((AnchorPane) parent).getChildren();
            }
            idTree.getSelectionModel().select(0);
            children.remove(node);
            itChildren.remove(item);
        }
    }
    
    @FXML private void handleEdit() {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("NodeProps.fxml"));
        try {
            VBox root = loader.load();
            Stage propsStage = new Stage();
            propsStage.setScene(new Scene(root));
            propsStage.setTitle("Properties");
            propsStage.initModality(Modality.WINDOW_MODAL);
            propsStage.initOwner(primaryStage);
            NodeProps controller = loader.getController();
            controller.setItem(selection);
            propsStage.showAndWait();
            selItem.setValue(selection.getId());
        } catch (Exception ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.showAndWait();
        }
    }
    
    @FXML private void handleMoveBack() {}
    
    @FXML private void handleMoveForward() {}
    
    @FXML private void handleNew() {}
    
    @FXML private void handleOpen() {}
    
    @FXML private void handleQuit() {
        primaryStage.close();
    }
    
    @FXML private void handleSave() {}
    
    @FXML private void handleZoomKey(KeyEvent event) {
        KeyCode key = event.getCode();
        if (key == KeyCode.LEFT || key == KeyCode.RIGHT) {
            handleZoomMouse();
        }
    }
    
    @FXML private void handleZoomMouse() {
        double zoom = Math.pow(2, 0.5 * zoomSlider.getValue());
        double h = canvasScroll.getHvalue();
        double v = canvasScroll.getVvalue();
        canvas.setScaleX(zoom);
        canvas.setScaleY(zoom);
        canvasScroll.setHvalue(h);
        canvasScroll.setVvalue(v);
    }
    
    /**
     * Sets a reference to the primary Stage.
     * This method is called by the main Application.
     * @param stage the primary Stage.
     */
    public void setStage(Stage stage) {
        primaryStage = stage;
    }
}