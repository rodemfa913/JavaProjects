package dwgfx.bind;

import javafx.scene.transform.Affine;
import javax.xml.bind.annotation.*;

/**
 * ...
 * @author rodemfa
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BAffine {
    @XmlAttribute private final double mxx;
    @XmlAttribute private final double mxy;
    @XmlAttribute private final double myx;
    @XmlAttribute private final double myy;
    @XmlAttribute private final double tx;
    @XmlAttribute private final double ty;
    
    /**
     * ...
     */
    public BAffine() {
        mxx = myy = 1.0;
        mxy = myx = tx = ty = 0.0;
    }
    
    /**
     * ...
     * @param transform ...
     */
    public BAffine(Affine transform) {
        mxx = transform.getMxx();
        mxy = transform.getMxy();
        tx = transform.getTx();
        myx = transform.getMyx();
        myy = transform.getMyy();
        ty = transform.getTy();
    }
    
    /**
     * ...
     * @return ...
     */
    public Affine get() {
        return new Affine(mxx, mxy, tx, myx, myy, ty);
    }
}