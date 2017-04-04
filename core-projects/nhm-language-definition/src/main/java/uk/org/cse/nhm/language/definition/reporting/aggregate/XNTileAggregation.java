package uk.org.cse.nhm.language.definition.reporting.aggregate;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;
import uk.org.cse.nhm.language.validate.BoundedDouble;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindPositionalArgument;

@Doc({
	"Compute a given quantile of a function over a house.",
	"The quantiles are computed according to the 7th quantile calculation algorithm defined in Hyndman and Fan's 1995 review paper,",
	"so the p-tile for n houses is produced by sorting the function's value over the n houses, and linearly interpolating between the values",
	"which when ordered have positions bounding p*n.",
	"If any of the values are not-a-number, the n-tile will be not-a-number."
})
@Bind("aggregate.n-tile")
public class XNTileAggregation extends XAggregation implements IHouseContext  {
	public static class P {
		public static final String function = "function";
		public static final String n = "n";
	}
	private double n = 0.5;
	private XNumber function = one();
	
	@NotNull(message="aggregate.n-tile must have a function to evaluate")
	@BindPositionalArgument(0)
	@Prop(P.function)
	@Doc("The function to take a quantile of")
	public XNumber getFunction() {
		return function;
	}

	private static XNumber one() {
		final XNumberConstant c = new XNumberConstant();
		c.setValue(1);
		return c;
	}

	public void setFunction(final XNumber function) {
		this.function = function;
	}

	@Doc("The quantile to compute")
	@BindNamedArgument
	@Prop(P.n)
	@BoundedDouble(lower=0, upper=1, message="aggregate.n-tile must have a value for n between 0 and 1")
	public double getN() {
		return n;
	}

	public void setN(final double p) {
		this.n = p;
	}
}
