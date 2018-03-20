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

@Bind("hyperbolic-discount")
@Category(CategoryType.MONEY)
@SeeAlso({XFutureValue.class, XExponentialDiscount.class})
@Doc({
	"When used within future-value, this applies a quasi-hyperbolic discounting factor to another function (after Laibson, 1997).",
	"In year 0 the discount factor is 1; in year D, the discount is beta * delta ^ D, where beta and delta are the two parameters",
	"controlling the shape of the approximate hyperbola.",
	
	"Hyperbolic discounts have been found to fit experimentally measured behaviour better than simple exponentials",
	"with a range of reasonable theories as to why; in particular, in circumstances where future rewards have some uncertainty",
	"associated with their delivery can be optimally accounted for using hyperbolae.",
	
	"This is almost equivalent to writing (* value beta (pow delta (- (sim.year foresight:Always) (sim.year foresight:Never)))),",
	"apart from the correction for the zeroth year in which beta is replaced with 1."
})
@RequireParent(value=XFutureValue.class, message="discount.hyperbolic only makes sense inside a prediction function (outside a prediction function, its value is always just the value of its argument, because it is always now and never the future).")
public class XHyperbolicDiscount extends XNumber {
	public static final class P {
		public static final String beta = "beta";
		public static final String delta = "delta";
		public static final String value = "value";
	}
	private XNumber beta = XNumberConstant.create(0.9);
	private XNumber delta = XNumberConstant.create(0.8);
	private XNumber value = XNumberConstant.create(1);

	@BindNamedArgument
	@Doc({"The beta parameter; this controls the discount factor between the present and the first discounted year (beta * delta)",
		"so a low beta indicates larger discounting between now and next year. If beta is 1, the discount is actually exponential.",
		"If beta is zero, the future is completely disregarded."})
	@Prop(P.beta)
	public XNumber getBeta() {
		return beta;
	}

	public void setBeta(final XNumber beta) {
		this.beta = beta;
	}

	@BindNamedArgument
	@Doc({"The delta parameter; this controls the discount factor between future years; each future year is worth (beta * delta^year)",
		"so the inter-year discount factor is delta."})
	@Prop(P.delta)
	public XNumber getDelta() {
		return delta;
	}

	public void setDelta(final XNumber delta) {
		this.delta = delta;
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
