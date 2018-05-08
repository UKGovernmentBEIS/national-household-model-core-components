package uk.org.cse.nhm.language.definition.action.choices;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("select.weighted")
@Doc({
    "A rule which picks one of several alternative outcomes by making a pseudo-random choice between them.",
    "",
    "The random choice is biased using a weight given as the first argument."
})
public class XWeightedSelector extends XChoiceSelector {

    public static final class P {

        public static final String weight = "weight";
    }

    private XNumber weight;

    @Doc("A synonym for the first unnamed argument.")
    @BindNamedArgument
    @Prop(P.weight)
    @NotNull(message = "select.weighted must contain a function which will be computed for each alternative to produce its weighting.")
    public XNumber getWeight() {
        return weight;
    }

    public void setWeight(final XNumber objective) {
        this.weight = objective;
    }

    @Doc({
        "This is the function which will be used to bias the choice.",
        "",
        "Its value will be computed for each suitable alternative outcome that is being considered; if the value is zero or negative, ",
        "the alternative will never be selected.",
        "",
        "Over all the alternatives which have a positive value, the probability that a particular alternative will be chosen is",
        "that alternative's weight divided by the sum of the others' weights.",
        "",
        "So for example if the weight used is a constant like '1', all suitable alternatives have equal likelihood of selection.",
        "If there are two alternatives, and for one this value is '1' and the other it is '2', the first will be selected half as frequently as the second."
    })
    @BindPositionalArgument(0)
    public XNumber getWeight2() {
        return weight;
    }

    public void setWeight2(final XNumber objective) {
        this.weight = objective;
    }
}
