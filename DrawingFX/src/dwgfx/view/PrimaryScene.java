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
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.text.*;
import javafx.scene.transform.Affine;
import javafx.stage.*;
import javafx.stage.FileChooser.ExtensionFilter;
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
    private FileChooser fileChooser;
    private IdListener idListener;
    private Marshaller m;
    private ShapeHandler shapeHandler;
    private Stage primaryStage;
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
        MultipleSelectionModel<TreeItem<Node>> selector = nodeTree.getSelectionModel();
        selector.selectedItemProperty().addListener(new TreeSelectionListener());
        idListener = new IdListener();
        canvas.idProperty().addListener(idListener);
        canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
        nodeTree.setRoot(new TreeItem<>(canvas));
        selector.select(0);
        shapeHandler = new ShapeHandler();
        fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new ExtensionFilter("XML files (*.xml)", "*.xml"));
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
            shape.setLayoutX(100.0);
            shape.setLayoutY(100.0);
            shape.setId("item");
            shape.idProperty().addListener(idListener);
            shape.getTransforms().add(new Affine());
            shape.setOnMouseClicked(shapeHandler);
            Group layer = (Group) layItem.getValue();
            layer.getChildren().add(shape);
            layItem.getChildren().add(new TreeItem<>(shape));
        }
    }
    
    @FXML private void handleDelete() {
        MultipleSelectionModel<TreeItem<Node>> selector = nodeTree.getSelectionModel();
        TreeItem<Node> treeItem = selector.getSelectedItem();
        TreeItem<Node> tiParent = treeItem.getParent();
        if (tiParent != null) {
            List<TreeItem<Node>> tiList = tiParent.getChildren();
            Node item = treeItem.getValue();
            Parent parent = item.getParent();
            List<Node> list;
            if (parent instanceof Group) {
                list = ((Group) parent).getChildren();
            } else {
                list = ((AnchorPane) parent).getChildren();
            }
            selector.select(0);
            tiList.remove(treeItem);
            list.remove(item);
        }
    }
    
    @FXML private void handleEdit() {
        Node item = nodeTree.getSelectionModel().getSelectedItem().getValue();
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
            controller.setItem(item);
            propsStage.showAndWait();
        } catch (Exception ex) {
            ExceptionDialog dialog = new ExceptionDialog(ex);
            dialog.showAndWait();
        }
    }
    
    @FXML private void handleMoveBack() {
        MultipleSelectionModel<TreeItem<Node>> selector = nodeTree.getSelectionModel();
        TreeItem<Node> treeItem = selector.getSelectedItem();
        TreeItem<Node> tiParent = treeItem.getParent();
        if (tiParent != null) {
            List<TreeItem<Node>> tiList = tiParent.getChildren();
            int index = tiList.indexOf(treeItem);
            if (index > 0) {
                Node item = treeItem.getValue();
                Parent parent = item.getParent();
                List<Node> list;
                if (parent instanceof Group) {
                    list = ((Group) parent).getChildren();
                } else {
                    list = ((AnchorPane) parent).getChildren();
                }
                selector.select(0);
                tiList.remove(treeItem);
                tiList.add(index - 1, treeItem);
                list.remove(item);
                list.add(index - 1, item);
                selector.select(treeItem);
            }
        }
    }
    
    @FXML private void handleMoveForward() {
        MultipleSelectionModel<TreeItem<Node>> selector = nodeTree.getSelectionModel();
        TreeItem<Node> treeItem = selector.getSelectedItem();
        TreeItem<Node> tiParent = treeItem.getParent();
        if (tiParent != null) {
            List<TreeItem<Node>> tiList = tiParent.getChildren();
            int index = tiList.indexOf(treeItem);
            if (index < tiList.size()) {
                Node item = treeItem.getValue();
                Parent parent = item.getParent();
                List<Node> list;
                if (parent instanceof Group) {
                    list = ((Group) parent).getChildren();
                } else {
                    list = ((AnchorPane) parent).getChildren();
                }
                selector.select(0);
                tiList.remove(treeItem);
                tiList.add(index + 1, treeItem);
                list.remove(item);
                list.add(index + 1, item);
                selector.select(treeItem);
            }
        }
    }
    
    @FXML private void handleNew() {
        nodeTree.getSelectionModel().select(0);
        nodeTree.getRoot().getChildren().clear();
        canvas.getChildren().clear();
        canvas.setId("drawing");
        canvas.setBackground(new Background(new BackgroundFill(Color.WHITE, null, null)));
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
                TreeItem<Node> root = nodeTree.getRoot();
                canvas.getChildrenUnmodifiable().forEach((layer) -> {
                    layer.idProperty().addListener(idListener);
                    TreeItem<Node> layItem = new TreeItem<>(layer);
                    root.getChildren().add(layItem);
                    Parent parent = (Parent) layer;
                    parent.getChildrenUnmodifiable().forEach((item) -> {
                        item.idProperty().addListener(idListener);
                        item.setOnMouseClicked(shapeHandler);
                        layItem.getChildren().add(new TreeItem<>(item));
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
            String path = file.getPath();
            if (!path.endsWith(".xml")) {
                file = new File(path + ".xml");
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
        TreeItem<Node> layItem = new TreeItem<>(layer);
        nodeTree.getRoot().getChildren().add(layItem);
        return layItem;
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