package uk.org.cse.nhm.language.definition.action.measure.lighting;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

@Bind("measure.lighting")
@Doc({
    "Replace all lighting with lighting types in the given proportion.",
    "Proportions for the different types of lighting should sum to 1.",
    "",
    "The relative efficiency of the different types of light is determined by",
    "the energy calculator mode being used. In SAP or BREDEM modes, all non-incandescent lights",
    "use half the energy of incandescent lights.",
    "",
    "In BEIS mode, a set of efficiencies determined by the products policy team are used.",
    "These efficiencies are documented in the manual section on energy calculator modes."
})
@Unsuitability({
    "The proportions given do not sum to one."
})
public class XLightingProportionsMeasure extends XMeasure {

    public static final class P {

        public static final String proportionOfCfl = "proportion-cfl";
        public static final String proportionOfIcandescent = "proportion-incandescent";
        public static final String proportionOfHAL = "proportion-hal";
        public static final String proportionOfLED = "proportion-led";
        public static final String proportionOfLVHAL = "proportion-lvhal";
        public static final String proportionOfAPlusPlus = "proportion-aplusplus";
    }

    private XNumber proportionOfIncandescent = XNumberConstant.create(0);
    private XNumber propotionOfHAL = XNumberConstant.create(0);
    private XNumber proportionOfLED = XNumberConstant.create(0);
    private XNumber proportionOfCfl = XNumberConstant.create(0);
    private XNumber proportionOfLVHal = XNumberConstant.create(0);
    private XNumber proportionOfAPlusPlus = XNumberConstant.create(0);

    @BindNamedArgument("proportion-incandescent")
    @Prop(P.proportionOfIcandescent)
    @Doc("A function used to compute the proportion of lights to be set as incandescent.")
    public XNumber getProportionOfIncandescent() {
        return proportionOfIncandescent;
    }

    public void setProportionOfIncandescent(XNumber proportionOfIncandescent) {
        this.proportionOfIncandescent = proportionOfIncandescent;
    }

    @BindNamedArgument("proportion-hal")
    @Prop(P.proportionOfHAL)
    @Doc("A function used to compute the proportion of lights to be set as halogen.")
    public XNumber getPropotionOfHAL() {
        return propotionOfHAL;
    }

    public void setPropotionOfHAL(XNumber propotionOfHAL) {
        this.propotionOfHAL = propotionOfHAL;
    }

    @BindNamedArgument("proportion-led")
    @Prop(P.proportionOfLED)
    @Doc("A function used to compute the proportion of lights to be set as LED.")
    public XNumber getProportionOfLED() {
        return proportionOfLED;
    }

    public void setProportionOfLED(XNumber proportionOfLED) {
        this.proportionOfLED = proportionOfLED;
    }

    @BindNamedArgument("proportion-cfl")
    @Prop(P.proportionOfCfl)
    @Doc("A function used to compute the proportion of lights to be set as CFL.")
    public XNumber getProportionOfCfl() {
        return proportionOfCfl;
    }

    public void setProportionOfCfl(XNumber proportionOfCfl) {
        this.proportionOfCfl = proportionOfCfl;
    }

    @BindNamedArgument("proportion-lv-hal")
    @Prop(P.proportionOfLVHAL)
    @Doc("A function used to compute the proportion of lights to be set as low-voltage halogen.")
    public XNumber getProportionOfLVHal() {
        return proportionOfLVHal;
    }

    public void setProportionOfLVHal(XNumber proportionOfLVHal) {
        this.proportionOfLVHal = proportionOfLVHal;
    }

    @BindNamedArgument("proportion-a++")
    @Prop(P.proportionOfAPlusPlus)
    @Doc("A function used to compute the proportion of lights to be set as A-plus-plus rated.")
    public XNumber getProportionOfAPlusPlus() {
        return proportionOfAPlusPlus;
    }

    public void setProportionOfAPlusPlus(XNumber proportionOfAPlusPlus) {
        this.proportionOfAPlusPlus = proportionOfAPlusPlus;
    }
}
