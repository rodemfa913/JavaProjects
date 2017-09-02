package dwgfx.view;

import dialogs.ExceptionDialog;
import dwgfx.bind.Drawing;
import dwgfx.util.TreeCellFactory;
import java.io.File;
import java.net.URL;
import java.util.*;
import javafx.beans.value.*;
import javafx.event.EventHandler;
import javafx.fxml.*;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.effect.InnerShadow;
import javafx.scene.input.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.transform.Affine;
import javafx.stage.*;
import javax.xml.bind.*;

/**
 * Controller class of the primary user interface.
 * @author rodemfa
 */
public class PrimaryScene implements Initializable {
    @FXML private AnchorPane canvas;
    @FXML private ComboBox<String> typeCombo;
    @FXML private ScrollPane canvasScroll;
    @FXML private Slider zoomSlider;
    @FXML private TreeView<Node> nodeTree;
    private ChangeListener<String> idListener;
    private EventHandler<MouseEvent> shapeHandler;
    private FileChooser fileChooser;
    private InnerShadow effect;
    private Stage primaryStage;
    private Marshaller m;
    private Unmarshaller um;
    
    private class IdListener implements ChangeListener<String> {
        @Override public void changed(
                ObservableValue<? extends String> observable,
                String oldValue,
                String newValue) {
            nodeTree.refresh();
        }
    }
    
    private class ShapeHandler implements EventHandler<MouseEvent> {
        @Override public void handle(MouseEvent event) {
            Shape shape = (Shape) event.getSource();
            Group layer = (Group) shape.getParent();
            int i = canvas.getChildren().indexOf(layer);
            int j = layer.getChildren().indexOf(shape);
            TreeItem<Node> layItem = nodeTree.getRoot().getChildren().get(i);
            TreeItem<Node> shpItem = layItem.getChildren().get(j);
            nodeTree.getSelectionModel().select(shpItem);
        }
    }
    
    private class TreeListener implements ChangeListener<TreeItem<Node>> {
        @Override public void changed(
                ObservableValue<? extends TreeItem<Node>> observable,
                TreeItem<Node> oldValue,
                TreeItem<Node> newValue) {
            if (oldValue != null && oldValue.getParent() != null) {
                oldValue.getValue().setEffect(null);
            }
            if (newValue != null && newValue.getParent() != null) {
                newValue.getValue().setEffect(effect);
            }
        }
    }
    
    @Override public void initialize(URL url, ResourceBundle rb) {
        nodeTree.setCellFactory(new TreeCellFactory());
        effect = new InnerShadow();
        MultipleSelectionModel<TreeItem<Node>> selector = nodeTree.getSelectionModel();
        selector.selectedItemProperty().addListener(new TreeListener());
        handleNew();
        idListener = new IdListener();
        canvas.idProperty().addListener(idListener);
        shapeHandler = new ShapeHandler();
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
            shape.setId("shape");
            shape.idProperty().addListener(idListener);
            shape.setOnMouseClicked(shapeHandler);
            shape.setLayoutX(100.0);
            shape.setLayoutY(100.0);
            shape.getTransforms().add(new Affine());
            Group layer = (Group) layItem.getValue();
            layer.getChildren().add(shape);
            layItem.getChildren().add(new TreeItem(shape));
        }
    }
    
    @FXML private void handleDelete() {
        move(0);
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
            TreeItem<Node> item = nodeTree.getSelectionModel().getSelectedItem();
            Node node = item.getValue();
            controller.setItem(node);
            propsStage.showAndWait();
        } catch (Exception ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.showAndWait();
        }
    }
    
    @FXML private void handleMoveBack() {
        move(-1);
    }
    
    @FXML private void handleMoveForward() {
        move(1);
    }
    
    @FXML private void handleNew() {
        nodeTree.setRoot(new TreeItem(canvas));
        nodeTree.getSelectionModel().select(0);
        canvas.getChildren().clear();
        canvas.setId("drawing");
        canvas.setBackground(new Background(new BackgroundFill(null, null, null)));
        canvas.setMinWidth(400.0);
        canvas.setMinHeight(400.0);
        zoomSlider.setValue(0);
        handleZoomMouse();
    }
    
    @FXML private void handleOpen() {
        File file = fileChooser.showOpenDialog(primaryStage);
        if (file != null) {
            try {
                Drawing drawing = (Drawing) um.unmarshal(file);
                handleNew();
                drawing.load(canvas);
                List<TreeItem<Node>> layItems = nodeTree.getRoot().getChildren();
                canvas.getChildren().forEach((lay) -> {
                    Group layer = (Group) lay;
                    layer.idProperty().addListener(idListener);
                    TreeItem<Node> layItem = new TreeItem(layer);
                    layItems.add(layItem);
                    layer.getChildren().forEach((shape) -> {
                        shape.idProperty().addListener(idListener);
                        shape.setOnMouseClicked(shapeHandler);
                        layItem.getChildren().add(new TreeItem(shape));
                    });
                });
            } catch (Exception ex) {
                ExceptionDialog dialog = new ExceptionDialog(ex);
                dialog.showAndWait();
            }
        }
    }
    
    @FXML private void handleQuit() {
        primaryStage.close();
    }
    
    @FXML private void handleSave() {
        File file = fileChooser.showSaveDialog(primaryStage);
        if (file != null) {
            String filePath = file.getPath();
            if (!filePath.endsWith(".xml")) {
                file = new File(filePath + ".xml");
            }
            Drawing drawing = new Drawing(canvas);
            try {
                m.marshal(drawing, file);
            } catch (Exception ex) {
                ExceptionDialog dialog = new ExceptionDialog(ex);
                dialog.showAndWait();
            }
        }
    }
    
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
    
    private TreeItem<Node> addLayer() {
        Group layer = new Group();
        layer.setId("layer");
        layer.idProperty().addListener(idListener);
        canvas.getChildren().add(layer);
        TreeItem<Node> layItem = new TreeItem(layer);
        nodeTree.getRoot().getChildren().add(layItem);
        return layItem;
    }
    
    private void move(int direction) {
        MultipleSelectionModel<TreeItem<Node>> selector = nodeTree.getSelectionModel();
        TreeItem<Node> item = selector.getSelectedItem();
        TreeItem<Node> itParent = item.getParent();
        if (itParent != null) {
            List<TreeItem<Node>> itChildren = itParent.getChildren();
            Node node = item.getValue();
            Parent parent = node.getParent();
            List<Node> children;
            if (parent instanceof Group) {
                children = ((Group) parent).getChildren();
            } else {
                children = ((AnchorPane) parent).getChildren();
            }
            selector.select(0);
            int index = itChildren.indexOf(item);
            itChildren.remove(item);
            children.remove(node);
            if (direction != 0) {
                if (direction < 0 && index > 0) {
                    --index;
                } else if (direction > 0 && index < itChildren.size()) {
                    ++index;
                }
                itChildren.add(index, item);
                children.add(index, node);
                selector.select(item);
            }
        }
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