package dwgfx.bind;

import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javax.xml.bind.annotation.*;

/**
 * Binding class for a stroke.
 * A stroke contains a set of properties: a Color, a width, a end cap style
 * (butt, round or square) and a line join style (miter, round or bevel).
 * @author Rodemfa913
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class Stroke {
    @XmlAttribute private final double width;
    @XmlAttribute private final StrokeLineCap lineCap;
    @XmlAttribute private final StrokeLineJoin lineJoin;
    @XmlElement private final BColor color;
    
    /**
     * Creates a default instance of Stroke.
     */
    public Stroke() {
        color = new BColor();
        width = 1.0;
        lineCap = StrokeLineCap.BUTT;
        lineJoin = StrokeLineJoin.MITER;
    }
    
    /**
     * Creates an instance of Stroke with specified properties.
     * @param clr the Color of Stroke.
     * @param wdt the width of Stroke.
     * @param lcp the end cap style of Stroke.
     * @param ljn the line join style of Stroke.
     */
    public Stroke(Color clr, double wdt, StrokeLineCap lcp, StrokeLineJoin ljn) {
        color = new BColor(clr);
        width = wdt;
        lineCap = lcp;
        lineJoin = ljn;
    }
    
    /**
     * Gets the Color of Stroke.
     * @return the Color of Stroke.
     */
    public Color getColor() {
        return color.get();
    }
    
    /**
     * Gets the width of Stroke.
     * @return the width of Stroke.
     */
    public double getWidth() {
        return width;
    }
    
    /**
     * Gets the end cap style of Stroke.
     * @return the end cap style.
     */
    public StrokeLineCap getLineCap() {
        return lineCap;
    }
    
    /**
     * Gets the line join style of Stroke.
     * @return the line join style.
     */
    public StrokeLineJoin getLineJoin() {
        return lineJoin;
    }
}