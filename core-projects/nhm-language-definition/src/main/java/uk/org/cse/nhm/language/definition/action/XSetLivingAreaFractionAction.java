package uk.org.cse.nhm.language.definition.action;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.validate.BoundedDouble;

@Doc({"Change the living area fraction of the house; BREDEM uses this to determine the mean internal",
    "temperature, which is a weighted average of the mean internal temperature of the living area",
    "and the mean internal temperature in the rest of the house, weighted by this fraction ",
    "(the mean internal temperature for each area is a function of the house's thermal mass, heating schedule, demand temperature and external temperature).",
    "A larger living area fraction will likely",
    "increase heating energy demand, and a smaller fraction will typically reduce it. However, the effect will usually be small,",
    "as the difference between living area temperature and rest of dwelling temperature is normally only around a degree or two."})

@Bind("action.set-living-area-fraction")
@Category(CategoryType.RESETACTIONS)
public class XSetLivingAreaFractionAction extends XFlaggedDwellingAction {

    public static final class P {

        public static final String fraction = "fraction";
    }

    private double fraction = 1;

    @Doc("The new living area fraction (a proportion)")
    @BindNamedArgument("to")

    @BoundedDouble(lower = 0, upper = 1, property = "Living area fraction")
    public double getFraction() {
        return fraction;
    }

    public void setFraction(final double fraction) {
        this.fraction = fraction;
    }
}
