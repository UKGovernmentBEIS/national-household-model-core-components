package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.List;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.context.calibration.ICalibratedEnergyFunction;

@Doc(
        {
            "Forecasts the cost of the dwelling's obligations, under the current conditions.",
            "The simulation year is not advanced in the prediction, so things like tariffs and",
            "carbon factors remain consistent with the current values. Energy consumption is predicted",
            "to continue at the current annual rate.",
            "Effectively, this is like an undiscounted version of the future costs in net present value."
        }
)

@Bind("obligations.predict")
@Category(CategoryType.MONEY)
@Obsolete(inFavourOf = XAnnualCost.class)
public class XPredictObligations extends XHouseNumber implements ICalibratedEnergyFunction {

    public static final class P {

        public static final String include = "include";
        public static final String years = "years";
        public static final String tags = "tags";
    }

    public enum XObligationSource {
        @Doc("Just existing obligations - this ignores those produced by the current action.")
        Existing,
        @Doc("Just new obligations - this ignores all the existing ones. This will exclude fuel bill and maintenance costs.")
        NewlyAdded,
        @Doc("All obligations, both existing and newly added.")
        All
    }

    private XObligationSource include = XObligationSource.All;
    private int years = 1;
    private List<Glob> tags = new ArrayList<>();

    @Doc("Which obligations to include.")

    @BindNamedArgument
    @Prop(P.include)
    public XObligationSource getInclude() {
        return include;
    }

    public void setInclude(final XObligationSource include) {
        this.include = include;
    }

    @Doc("The number of years to predict obligations for.")

    @BindNamedArgument
    @Prop(P.years)
    public int getYears() {
        return years;
    }

    public void setYears(final int years) {
        this.years = years;
    }

    @Doc("Tags to match on transactions you want to include in the prediction.")

    @BindNamedArgument
    @Prop(P.tags)
    public List<Glob> getTags() {
        return tags;
    }

    public void setTags(final List<Glob> tags) {
        this.tags = tags;
    }
}
