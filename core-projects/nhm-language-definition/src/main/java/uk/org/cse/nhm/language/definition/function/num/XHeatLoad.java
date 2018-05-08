package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.validation.constraints.Size;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.context.calibration.IEnergyFunction;

@Doc({"The annual heat load (in kWh) for a house.",
    "",
    "This is the amount of heat that needs putting into the house to keep it heated and to provide hot water.",
    "The need for space heat is met both by heating systems and internal gains."
})
@Bind("house.heat-load")
public class XHeatLoad extends XHouseNumber implements IEnergyFunction {

    private List<Double> weights = ones();
    private boolean space = true, water = true;

    public static final class P {

        public static final String weights = "weights";
        public static final String space = "space";
        public static final String water = "water";
    }

    private static List<Double> ones() {
        return new ArrayList<Double>(Collections.nCopies(12, 1d));
    }

    @BindNamedArgument("fraction")
    @Doc("A list of twelve numbers; the result will be the inner product of the monthly values with this list.")
    @Prop(P.weights)
    @Size(min = 12, max = 12, message = "house.heat-load fraction: must have 12 values; one for each month.")
    public List<Double> getWeights() {
        return weights;
    }

    public void setWeights(List<Double> weights) {
        this.weights = weights;
    }

    @BindNamedArgument("space-heat")
    @Doc("Whether to include space heat demand.")
    @Prop(P.space)
    public boolean isSpace() {
        return space;
    }

    public void setSpace(boolean space) {
        this.space = space;
    }

    @BindNamedArgument("hot-water-heat")
    @Doc("Whether to include hot water heat demand.")
    @Prop(P.water)
    public boolean isWater() {
        return water;
    }

    public void setWater(boolean water) {
        this.water = water;
    }
}
