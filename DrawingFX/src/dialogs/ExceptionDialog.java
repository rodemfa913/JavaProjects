package dialogs;

import java.io.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

/**
 * Shows a dialog with the exception details.
 * Reference: http://code.makery.ch/blog/javafx-dialogs-official/
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
        
        GridPane expContent = new GridPane();
        dialogPane.setExpandableContent(expContent);
        expContent.setMaxWidth(Double.MAX_VALUE);
        
        Label label = new Label("The exception stacktrace was:");
        expContent.add(label, 0, 0);
        
        TextArea textArea = new TextArea(sw.toString());
        expContent.add(textArea, 0, 1);
        textArea.setEditable(false);
        textArea.setWrapText(true);
        textArea.setMaxWidth(Double.MAX_VALUE);
        textArea.setMaxHeight(Double.MAX_VALUE);
        GridPane.setHgrow(textArea, Priority.ALWAYS);
        GridPane.setVgrow(textArea, Priority.ALWAYS);
    }
}