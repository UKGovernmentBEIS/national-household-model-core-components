package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.List;

import javax.validation.constraints.Min;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.context.calibration.ICalibratedEnergyFunction;
import uk.org.cse.nhm.language.definition.function.npv.XExponentialDiscount;
import uk.org.cse.nhm.language.definition.function.npv.XFutureValue;

@Bind("npv")
@Doc(
        value = {
            "Computes the present cost of a house in its current condition.",
            "The present cost of a house is composed of the capital cost of any measures which have been installed by the command containing the npv,",
            "summed with the discounted expected cost of the fuel bill, operational costs, and other financial obligations (loans, etc) of the house",
            "over the horizon for which the NPV is defined.",
            "Discount factors are computed on a yearly basis (rather than continuously), so all costs within one year of the present",
            "date (exclusive of the date one year hence) are undiscounted, all costs within the year following that (exclusive of the date two years hence)",
            "are discounted once, and so on.",
            "A cost C in year y with a discount rate of r contributes a cost of C / (1+r)^y to the total."
        }
)
@Category(CategoryType.MONEY)
@Obsolete(
        inFavourOf = {XFutureValue.class, XAnnualCost.class, XSumOfCosts.class, XExponentialDiscount.class},
        reason = "You can define your own present cost functions using predict-sum, with custom discounting schedules and included terms.",
        version = "5.1.4",
        compatibility = "This NPV is equivalent to (+ cost.sum (predict-sum horizon:10 predict:[] (exponential-discount rate:5% house.annual-cost))). "
        + "Using the newer terms, you can determine things like how your discount schedule works, and what things you want to include in the predicted costs."
)
public class XNetPresentValue extends XHouseNumber implements ICalibratedEnergyFunction {

    public static final class P {

        public static final String DISCOUNT = "discount";
        public static final String HORIZON = "horizon";
        public static final String TAGS = "tags";
    }

    private double discount;
    private int horizon;
    private List<Glob> tags = new ArrayList<>();

    @BindNamedArgument
    @Prop(P.TAGS)
    @Doc("Only transactions matching the given tag requirements will be included in the calculation. Matched transactions must have all of the given positive tags and none of the given negative tags.")
    public List<Glob> getTags() {
        return tags;
    }

    public void setTags(final List<Glob> tags) {
        this.tags = tags;
    }

    @BindNamedArgument
    @Prop(P.DISCOUNT)
    @Doc("This is the discount rate of the NPV calculation, as a proportion.")
    public double getDiscount() {
        return discount;
    }

    public void setDiscount(final double discount) {
        this.discount = discount;
    }

    @BindNamedArgument
    @Prop(P.HORIZON)
    @Doc("This is the number of years from now for which the NPV is summed.")
    @Min(value = 1, message = "npv must have a horizon greater than or equal to 1 year")
    public int getHorizon() {
        return horizon;
    }

    public void setHorizon(final int horizon) {
        this.horizon = horizon;
    }
}
