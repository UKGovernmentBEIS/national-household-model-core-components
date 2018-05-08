package uk.org.cse.nhm.language.definition.function.num;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;

@Bind("original")
@Doc({"Computes the original value of a function or variable.",
    "",
    "The first argument should be something numeric.",
    "The result will be the value which the first argument took for the house, before the CURRENT CHANGE had any effect on it.",
    "",
    "The CURRENT CHANGE is always delimited by the hierarchically closest enclosing do, choice, in-order, under, repeat, or apply",
    "",
    "Note that this is the hierarchically closest element once templates have been replaced with their contents."
})
@SeeAlso({XRiseIn.class, XFallIn.class})
public class XPrior extends XHouseNumber {

    public static class P {

        public static final String DELEGATE = "delegate";
    }

    private XNumber delegate;

    @Prop(P.DELEGATE)
    @BindPositionalArgument(0)
    @Doc("The function or variable whose prior value should be computed.")
    @NotNull(message = "prior must have at least one argument.")
    public XNumber getDelegate() {
        return delegate;
    }

    public void setDelegate(final XNumber delegate) {
        this.delegate = delegate;
    }

    public static XNumber valueOf(final XNumber thing) {
        final XPrior result = new XPrior();
        result.setDelegate(thing);
        return result;
    }
}
