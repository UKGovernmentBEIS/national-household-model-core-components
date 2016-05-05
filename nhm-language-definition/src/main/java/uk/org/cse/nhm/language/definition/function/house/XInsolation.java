package uk.org.cse.nhm.language.definition.function.house;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("house.insolation")
@Doc("The total annual solar insolation per square meter for the house under the current weather conditions, in kWh.")
public class XInsolation extends XHouseNumber {
    public static final class P {
        public static final String orientation = "orientation";
        public static final String inclination = "inclination";
    }

    private double orientation = 180;
    private double inclination = 30;

    @Prop(P.inclination)
    @BindNamedArgument
    @Doc("The inclination from the horizontal of the panel, in degrees (0 is flat, 90 is vertical)")
    public double getInclination() {
        return inclination;
    }

    public void setInclination(double inclination) {
        this.inclination = inclination;
    }

    @Prop(P.orientation)
    @BindNamedArgument
    @Doc("The angle from north of the panel in degrees (0 is north facing, 180 is south facing)")
    public double getOrientation() {
        return orientation;
    }

    public void setOrientation(double orientation) {
        this.orientation = orientation;
    }
}
