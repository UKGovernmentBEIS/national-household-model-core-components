package uk.org.cse.nhm.language.definition.action.measure.adjust;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;

@Doc("Add an appliance adjustment to the energy calculator for a house.")
@Bind("measure.add-adjustment")
@Unsuitability("The house already has this appliance adjustment")
public class XAddAdjustmentAction extends XMeasure {

    public static final class P {

        public static final String adjustment = "adjustment";
    }
    private XAdjustment adjustment = null;

    @Prop(P.adjustment)
    @NotNull(message = "An adjustment is required")
    @BindPositionalArgument(0)
    @Doc("The adjustment to add")
    public XAdjustment getAdjustment() {
        return adjustment;
    }

    public void setAdjustment(final XAdjustment adjustment) {
        this.adjustment = adjustment;
    }
}
