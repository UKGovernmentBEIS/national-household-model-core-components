package uk.org.cse.nhm.language.definition.function.num;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;


@Bind("rise-in")
@Doc({
        "Computes the increase in the value of a function or variable.",
            "",
            "The first argument should be something numeric.",
            "The result will be the increase in the value of the first argument as a result of the CURRENT CHANGE.",
            "",
            "The CURRENT CHANGE is always delimited by the hierarchically closest enclosing do, choice, in-order, or under.",
            "If the first argument has increased during the current change, the delta will be positive. Otherwise it will be negative (or zero).",
            "",
            "(rise-in x) is equivalent to (- x (original x))."
})
@SeeAlso({ XPrior.class })
public class XRiseIn extends XHouseNumber {
	public static class P {
        public static final String DELEGATE = "delegate";
	}
	
	private XNumber delegate;

    @Prop(P.DELEGATE)
	@BindPositionalArgument(0)
    @NotNull(message = "delta must always contain a function to be compared.")
    @Doc("The function or variable whose delta should be computed.")
	public XNumber getDelegate() {
		return delegate;
	}
	
	public void setDelegate(final XNumber delegate) {
		this.delegate = delegate;
    }
}
