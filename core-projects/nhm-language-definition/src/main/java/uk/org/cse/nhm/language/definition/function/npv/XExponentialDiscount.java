package uk.org.cse.nhm.language.definition.function.npv;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

@Bind("exponential-discount")
@Category(CategoryType.MONEY)
@SeeAlso({XFutureValue.class, XHyperbolicDiscount.class})
@Doc({"When used within predict-sum, this applies a geometric or exponential discounting factor to another function.",
    "In the yth year of the prediction, if the discount rate specified is r, this takes the value (1 + r) ^ y.",
    "When evaluated, this function will compute the rate, then compute the discount ratio from the rate, then compute the value, and finally produce the value divided by the discount ratio.",
    "You can use it to compute a geometrically discounted sum by using it around something in future-value. See the documentation for",
    "future-value for an example of exactly how to do this.",
    "This function is exactly equivalent to writing (/ value (pow (1+rate) (- (sim.year foresight:Always) (sim.year foresight:Never))))."
})
@RequireParent(value = XFutureValue.class, message = "discount.exponential only makes sense inside a prediction function (outside a prediction function, its value is always just the value of its argument, because it is always now and never the future).")
public class XExponentialDiscount extends XNumber {

    public static final class P {

        public static final String rate = "rate";
        public static final String value = "value";
    }
    private XNumber rate = XNumberConstant.create(0.05);
    private XNumber value = XNumberConstant.create(1);

    @Prop(P.rate)
    @BindNamedArgument
    @Doc({
        "Gives the discount rate that is to be used; this discount rate can be a function, and it will be re-computed each time",
        "the discount factor is computed. This means that (in-principle) it is possible for the discount rate to change during the calculation",
        "of a prediction, if (for example) the rate were a function of the apparent year. However, if you just write a constant here, or a",
        "deterministic function of the state of a house, there is nothing to worry about.",
        "If you do want to use a changeable function (for example if you wanted a random but consistent discount rate), you should",
        "write the value into a variable, and then refer to that variable here (as the variable will not be changing)"
    })
    public XNumber getRate() {
        return rate;
    }

    public void setRate(final XNumber rate) {
        this.rate = rate;
    }

    @Prop(P.value)
    @Doc({"This gives the function that is to be discounted; the default value (1) makes this function compute the discount factor",
        "by which you would need to multiply something to discount it, so you can either use the function you want to discount here, or",
        "just multiply by the result leaving the default value of 1 in place."})
    @BindPositionalArgument(0)
    public XNumber getValue() {
        return value;
    }

    public void setValue(final XNumber value) {
        this.value = value;
    }
}
