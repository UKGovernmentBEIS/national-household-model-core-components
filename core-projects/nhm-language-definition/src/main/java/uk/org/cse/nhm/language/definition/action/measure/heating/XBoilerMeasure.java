package uk.org.cse.nhm.language.definition.action.measure.heating;

import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;
import uk.org.cse.nhm.language.validate.efficiency.EfficiencyRequired;

@EfficiencyRequired
public abstract class XBoilerMeasure extends XWetHeatingMeasure {

    public static final class P {

        public static final String SUMMER_EFFICIENCY = "summer-efficiency";
        public static final String WINTER_EFFICIENCY = "winter-efficiency";
        public static final String fuel = "fuel";
    }

    private XNumber winterEfficiency = XNumberConstant.create(0.85);
    private XNumber summerEfficiency = XNumberConstant.create(-0.1);
    private XFuelType fuel = XFuelType.MainsGas;

    @BindNamedArgument("winter-efficiency")
    @Prop(P.WINTER_EFFICIENCY)
    @Doc(value = {
        "The winter efficiency of the boiler, as a proportion",
        "This number has no effect for electric boilers."
    })
    public XNumber getWinterEfficiency() {
        return winterEfficiency;
    }

    public void setWinterEfficiency(XNumber winterEfficiency) {
        this.winterEfficiency = winterEfficiency;
    }

    @BindNamedArgument("summer-efficiency")
    @Prop(P.SUMMER_EFFICIENCY)
    @Doc(value = {
        "The summer efficiency of the boiler, as a proportion.",
        "The proportion may also be specified as 0 or a negative number. In this case, it will be treated as an offset from winter efficiency",
        "This number has no effect for electric boilers.",
        "",
        "The default summer efficiency of 10% less than the winter efficient was decided based on the Product Characteristics Database (PCDB) 2009. It is very close to correct for most kinds of boilers.",
        "For oil boilers, solid fuel boilers, and CPSUs, the default is probably wrong, and you should override it with an accurate value."

    })
    public XNumber getSummerEfficiency() {
        return summerEfficiency;
    }

    public void setSummerEfficiency(final XNumber summerEfficiency) {
        this.summerEfficiency = summerEfficiency;
    }

    @BindNamedArgument("fuel")
    @Prop(P.fuel)
    @Doc("The fuel of the boiler")
    public XFuelType getFuel() {
        return fuel;
    }

    public void setFuel(final XFuelType fuel) {
        this.fuel = fuel;
    }
}
