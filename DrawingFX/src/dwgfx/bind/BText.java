package dwgfx.bind;

import javafx.scene.Node;
import javafx.scene.control.TreeItem;
import javafx.scene.text.*;
import javax.xml.bind.annotation.*;

/**
 * ...
 * @author Rodemfa913
 */
public class BText extends BShape {
    @XmlAttribute private final double fontSize;
    @XmlAttribute private final String fontFamily;
    @XmlAttribute private final String fontStyle;
    @XmlElement private final String body;
    
    /**
     * ...
     */
    public BText() {
        super();
        fontFamily = "Monospaced";
        fontSize = 12.0;
        fontStyle = "Regular";
        body = "";
    }
    
    /**
     * ...
     * @param text ...
     */
    public BText(Text text) {
        super(text);
        Font font = text.getFont();
        fontFamily = font.getFamily();
        fontSize = font.getSize();
        fontStyle = font.getStyle();
        body = text.getText().replace("\\", "\\\\").replace("\n", "\\n");
    }
    
    @Override public TreeItem<Node> get() {
        Text text = new Text(body.replace("\\n", "\n").replace("\\\\", "\\"));
        FontWeight weight = fontStyle.contains("Bold") ? FontWeight.BOLD : FontWeight.NORMAL;
        FontPosture posture = fontStyle.contains("Italic") ? FontPosture.ITALIC : FontPosture.REGULAR;
        text.setFont(Font.font(fontFamily, weight, posture, fontSize));
        load(text);
        return new TreeItem<>(text);
    }
}