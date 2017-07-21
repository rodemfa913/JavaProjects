package dialogs;

import java.io.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Shows a dialog with the exception details.
 * Reference: {@linktourl http://code.makery.ch/blog/javafx-dialogs-official}
 * 
 * @author rodemfa
 */
public final class ExceptionDialog extends Alert {
    public ExceptionDialog(Exception ex) {
        super(AlertType.ERROR);
        setHeaderText(ex.getClass().getSimpleName() + " caught!");
        setContentText(ex.getMessage());
        
        StringWriter sw = new StringWriter();
        PrintWriter pw = new PrintWriter(sw);
        ex.printStackTrace(pw);
        
        DialogPane dialogPane = getDialogPane();
        dialogPane.setPrefWidth(600);
        dialogPane.setPrefHeight(450);
        
        VBox expContent = new VBox();
        dialogPane.setExpandableContent(expContent);
        expContent.setMaxWidth(Double.MAX_VALUE);
        
        Label label = new Label("The exception stacktrace was:");
        expContent.getChildren().add(label);
        
        TextArea textArea = new TextArea(sw.toString());
        expContent.getChildren().add(textArea);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        VBox.setVgrow(textArea, Priority.ALWAYS);
    }
}
