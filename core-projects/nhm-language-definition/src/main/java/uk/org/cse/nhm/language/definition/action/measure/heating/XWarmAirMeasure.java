package uk.org.cse.nhm.language.definition.action.measure.heating;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

/**
 * XWarmAirMeasure.
 * 
 * @author richardTiffin
 */
@Doc("Installs a Warm Air heating system")
@Unsuitability({
        "fuel type must be mains gas, botteled LPG or LPG",
        "a warm air system must already be installed and have an efficiency less than that being installed"
})
@Bind("measure.warm-air-system")
public class XWarmAirMeasure extends XHeatingMeasure {
    public static final class P {
        public static final String EFFICIENCY = "efficiency";
        public static final String FUEL = "fuel";
    }

    private XNumber efficiency = XNumberConstant.create(0.85);
    private XFuelType fuel = XFuelType.MainsGas;

    @BindNamedArgument
    @Prop(P.EFFICIENCY)
    @Doc("The efficiency of the system, as a proportion")
    public XNumber getEfficiency() {
        return efficiency;
    }

    public void setEfficiency(final XNumber efficiency) {
        this.efficiency = efficiency;
    }

    //TODO: Validation rule - on allowed fuel types (BoundedDouble)
    @BindNamedArgument("fuel")
    @Prop(P.FUEL)
    @Doc("The fuel of the system")
    public XFuelType getFuel() {
        return fuel;
    }

    public void setFuel(final XFuelType fuel) {
        this.fuel = fuel;
    }
}
