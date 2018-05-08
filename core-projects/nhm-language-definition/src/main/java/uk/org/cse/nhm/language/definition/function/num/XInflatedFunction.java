package uk.org.cse.nhm.language.definition.function.num;

import org.joda.time.DateTime;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;

@Bind("inflate")
@Doc({"A function which applies a deflation or inflation rate",
    "to another function; the interest rate is taken to be an APR",
    "so the value in year n is the value of the contained function",
    "multiplied with the interest rate to the power of the number",
    "of whole years between the start year and the current year",
    "(so during the first year, it is one times the contained function).",
    "If this is used within a prediction (NPV, obligations.predict)",
    "the predicted value will not be inflated any more than it is at",
    "the moment of prediction."})
@Category(CategoryType.ARITHMETIC)
public class XInflatedFunction extends XNumber {

    public static class P {

        public static final String starting = "starting";
        public static final String rate = "rate";
        public static final String value = "value";
    }
    private DateTime starting = null;
    private double rate = 0;
    private XNumber value = null;

    @Doc(
            {
                "The year for which the inflation multiplier is 1.",
                "If unspecified, this is the first year of the simulation.",
                "If it is after the first year of the simulation, the inflation curve",
                "is projected backwards to the first year of the simulation."
            }
    )
    @BindNamedArgument("starting-on")
    @Prop(P.starting)
    public DateTime getStarting() {
        return starting;
    }

    public void setStarting(final DateTime starting) {
        this.starting = starting;
    }

    @Doc("The inflation rate to use; this is constant.")
    @BindNamedArgument
    @Prop(P.rate)
    public double getRate() {
        return rate;
    }

    public void setRate(final double rate) {
        this.rate = rate;
    }

    @Doc("This is the function which will be inflated.")
    @Prop(P.value)
    @BindPositionalArgument(0)
    public XNumber getValue() {
        return value;
    }

    public void setValue(final XNumber value) {
        this.value = value;
    }

    private XForesightLevel foresight = null;

    @BindNamedArgument
    @Doc({"The foresight level required to predict the change to this value in a prediction function like predict-sum.",
        "If unset, this is determined from the foresight level where the function is being used; for example, within",
        "the tariff definition this will have foresight level Tariffs."})
    public XForesightLevel getForesight() {
        return foresight;
    }

    public void setForesight(final XForesightLevel foresight) {
        this.foresight = foresight;
    }
}
