package uk.org.cse.nhm.language.definition.action.measure.adjust;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;

/**
 * XAdjustNumberOfPassiveVentsAction.
 *
 * @author trickyBytes
 */
@Doc("Adjust the number of passive vents within a property")
@Bind("measure.adjust-passive-vents")
@Unsuitability(alwaysSuitable = true)
public class XAdjustNumberOfPassiveVentsMeasure extends XMeasure {
    public static final class P {
        public static final String adjustment = "adjustment";
    }
    private int adjustment;
    /**
     * Return the adjustment.
     *
     * @return the adjustment
     */
    @BindNamedArgument("adjustment")
    @Doc("The adjustment in number of vents to make.")
    @NotNull(message = "measure.adjust-passive-vents must define an adjustment")
    @Prop(P.adjustment)
    public int getAdjustment() {
        return adjustment;
    }
    
    /**
     * Set the adjustment.
     *
     * @param adjustment the adjustment 
     */
    public void setAdjustment(int adjustment) {
        this.adjustment = adjustment;
    }
}
