package dwgfx.bind;

import javafx.scene.transform.Affine;
import javax.xml.bind.annotation.*;

/**
 * Binding class for an Affine transform.
 * @author rodemfa
 */
@XmlAccessorType(XmlAccessType.FIELD)
public class BAffine {
    @XmlAttribute private final double mxx, mxy, myx, myy, tx, ty;
    
    /**
     * Creates a default instance of BAffine.
     */
    public BAffine() {
        mxx = myy = 1.0;
        mxy = myx = tx = ty = 0.0;
    }
    
    /**
     * Creates an instance of BAffine with parameters of specified Affine transform.
     * @param transform the Affine transform.
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
     * Gets a new instance of Affine with properties of BAffine.
     * @return a new Affine transform.
     */
    public Affine get() {
        return new Affine(mxx, mxy, tx, myx, myy, ty);
    }
}