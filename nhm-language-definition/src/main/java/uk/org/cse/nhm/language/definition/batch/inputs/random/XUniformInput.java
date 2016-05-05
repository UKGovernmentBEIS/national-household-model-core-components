package uk.org.cse.nhm.language.definition.batch.inputs.random;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("uniform")
@Doc(value = {"A batch input which draws randomly from a uniform distribution.",
		"Distributions are unbounded, and so may yield as many values as required."})
public class XUniformInput extends XDistributionInput {
	public static final class P {
		public static final String START = "start";
		public static final String END = "end";
	}
	
	private double start;
	private double end;
	
	
@BindNamedArgument
	@Prop(P.START)
	@Doc("The start of the uniform distribution.")
	@NotNull(message = "uniform distribution must always have a 'start' attribute.")
	public double getStart() {
		return start;
	}
	
	public void setStart(double start) {
		this.start = start;
	}
	
	
@BindNamedArgument
	@Prop(P.END)
	@Doc("The end of the uniform distribution.")
	@NotNull(message = "uniform distribution must always have an 'end' attribute.")
	public double getEnd() {
		return end;
	}
	
	public void setEnd(double end) {
		this.end = end;
	}
}
