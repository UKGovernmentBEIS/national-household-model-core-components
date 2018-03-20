package uk.org.cse.nhm.language.definition.function.num.random;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("random.uniform")
@Doc("Produces uniformly distributed random numbers.")
public class XUniform extends XNumber {
	public static class P {
		public static final String start = "start";
		public static final String end = "end";
	}
	
	private double start;
	private double end = 1;
	
	@BindNamedArgument
	@Prop(P.start)
	@NotNull(message = "random.uniform must always have a start.")
	@Doc("The start of the uniform distribution.")
	public double getStart() {
		return start;
	}
	
	public void setStart(final double start) {
		this.start = start;
	}
	
	@BindNamedArgument
	@Prop(P.end)
	@NotNull(message = "random.uniform must always have an end.")
	@Doc("The end of the uniform distribution.")
	public double getEnd() {
		return end;
	}
	
	public void setEnd(final double end) {
		this.end = end;
	}
}
