package uk.org.cse.nhm.language.definition.batch.inputs.random;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;


@Bind("uniform-integers")
@Doc(value = {"A batch input which draws randomly from a range of whole numbers.",
		"Distributions are unbounded, and so may yield as many values as required."})
public class XUniformIntegerInput extends XDistributionInput {
	public static final class P {
		public static final String START = "start";
		public static final String END = "end";
	}
	
	private int start;
	private int end;
	
	
@BindNamedArgument
	@Prop(P.START)
	@Doc("The start of the uniform-integer distribution.")
	@NotNull(message = "uniform-integer distribution must always have a 'start' attribute.")
	public int getStart() {
		return start;
	}
	
	public void setStart(int start) {
		this.start = start;
	}
	
	
@BindNamedArgument
	@Prop(P.END)
	@Doc("The end of the uniform-integer distribution.")
	@NotNull(message = "uniform-integer distribution must always have an 'end' attribute.")
	public int getEnd() {
		return end;
	}
	
	public void setEnd(int end) {
		this.end = end;
	}
}
