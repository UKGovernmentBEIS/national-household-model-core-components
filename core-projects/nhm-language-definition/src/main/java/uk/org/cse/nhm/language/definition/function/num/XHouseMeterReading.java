package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.context.calibration.ICalibratedEnergyFunction;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.fuel.XFullTariff;

@Doc(
        value = {
            "Reads the energy meter for the house for the given fuel.",
            "The energy meter contains the current unpaid-for kWh by fuel type;",
            "this function is primarily intended as part of the definition of a tariff",
            "since the amount a house pays is likely to be a function of the unpaid amount outstanding.",
            "The energy meter gets reset every time a house pays its fuel bill, which happens annually at the",
            "very end of the year."
        }
)
@SeeAlso(XFullTariff.class)
@Bind("house.meter-reading")
@Category(CategoryType.TARIFFS)
public class XHouseMeterReading extends XHouseNumber implements ICalibratedEnergyFunction {

    public static final class P {

        public static final String fuel = "fuel";
    }
    private XFuelType fuel;

    @Doc({"The type of fuel to read the meter for.",
        "For convenience, when this element is used inside a tariff's price definition for a particular fuel,",
        "this will default to the fuel for that price definition."})

    @BindNamedArgument
    @Prop(P.fuel)
    public XFuelType getFuel() {
        return fuel;
    }

    public void setFuel(final XFuelType fuel) {
        this.fuel = fuel;
    }
}
