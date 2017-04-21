package uk.org.cse.nhm.language.definition.action.measure.adjust;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

/**
 * XSetAdjustmentTerms.
 *
 * @author trickyBytes
 */
@Doc({"Applies a linear correction factor to the energy use of either Appliances or Cooking",
        "Correction factor is applied to result of equation SAP2012 L10"})
@Bind("measure.set-adjustment-terms")
public class XSetAdjustmentTerms extends XMeasure {
    public static final class P {
        public static final String adjustmentType = "adjustment-type";
        public static final String constantTerm = "constant-term";
        public static final String linearFactor = "linear-factor";
    }
    
    @Doc("The type of energy use the adjustment should be applied to.")
    public enum XAdjustmentType {
        Appliances, Cooking
    }
    
    private XNumber constantTerm = null;
    private XNumber linearFactor = null;
    private XAdjustmentType adjustmentType = null;
    
    @Prop(P.constantTerm)
    @BindNamedArgument(P.constantTerm)
    @NotNull(message="A constant term is required")
    @Doc("The constant term (C) for the adjustment")
    public XNumber getConstantTerm() {
        return constantTerm;
    }
    
    public void setConstantTerm(XNumber constantTerm) {
        this.constantTerm = constantTerm;
    }
    
    @Prop(P.linearFactor)
    @BindNamedArgument(P.linearFactor)
    @NotNull(message="A linear factor is required")
    @Doc("The linear factor (k) for the adjustment")
    public XNumber getLinearFactor() {
        return linearFactor;
    }
    
    public void setLinearFactor(XNumber linearFactor) {
        this.linearFactor = linearFactor;
    }

    @Prop(P.adjustmentType)
    @BindNamedArgument(P.adjustmentType)
    @NotNull(message="You must define what type of adjustment to make")
    @Doc("The type of energy use to be adjusted")
    public XAdjustmentType getAdjustmentType() {
        return adjustmentType;
    }

    /**
     * Set the adjustmentType.
     * @param adjustmentType the adjustmentType 
     */
    public void setAdjustmentType(XAdjustmentType adjustmentType) {
        this.adjustmentType = adjustmentType;
    }
}
