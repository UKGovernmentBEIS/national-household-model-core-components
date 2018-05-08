package uk.org.cse.nhm.language.definition.two.selectors;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.validate.contents.ActsAsParent;

@Doc("Sample a set of houses using a bernoulli process (coin tossing)")
@Bind("bernoulli")
public class XBernoulliSet extends XSetOfHouses {

    public static final class P {

        public static final String number = "number";
        public static final String source = "source";
    }
    private XNumber number = null;
    private XSetOfHouses source = new XAllTheHouses();

    @Prop(P.number)
    @Doc("A function whose value will be used to toss a biased coin for each house in the source set.")
    @NotNull(message = "bernoulli requires a function for its first argument")
    @BindPositionalArgument(0)
    @ActsAsParent(IHouseContext.class)
    public XNumber getNumber() {
        return number;
    }

    public void setNumber(final XNumber number) {
        this.number = number;
    }

    @Prop(P.source)
    @BindPositionalArgument(1)
    @Doc("The set of houses to sample from")
    public XSetOfHouses getSource() {
        return source;
    }

    public void setSource(final XSetOfHouses source) {
        this.source = source;
    }
}
