package uk.org.cse.nhm.language.definition.batch.inputs;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;


@Doc(value = {
		"A input which generates values from a range specified by start, end and step by using 'start' as the initial value and incrementing by 'step' each time it is asked for a subsequent value, until it reaches a value larger than or equal to 'end', at which point no more values will be yielded.",
		"The end attribute may be left unspecified, in which case the resulting range will be unbounded.",
		"Note that the step attribute may be negative, and the end attribute may be less than the start attribute." })
@Bind("range")
public class XRange extends XSingleInput {
	public static final class P {
		public static final String START = "start";
		public static final String END = "end";
		public static final String STEP = "step";
	}

	private double start;
	private Double end;
	private double step;
	
	
@BindNamedArgument
	@Prop(P.START)
	@Doc("The starting value of the range (inclusive).")
	@NotNull(message = "range element must always have a start attribute.")
	public double getStart() {
		return start;
	}
	
	public void setStart(final double start) {
		this.start = start;
	}
	
	
@BindNamedArgument
	@Prop(P.END)
	@Doc("The value before which the range ends (not inclusive). If this is unspecified then the range will be unbounded.")
	public Double getEnd() {
		return end;
	}
	
	public void setEnd(final Double end) {
		this.end = end;
	}
	
	
@BindNamedArgument
	@Prop(P.STEP)
	@NotNull(message = "range element must always have a step attribute.")
	@Doc("The step size of the range.")
	public double getStep() {
		return step;
	}
	
	public void setStep(final double step) {
		this.step = step;
	}

	@Override
	public boolean hasBound() {
		if(end == null) {
			return false;
		} else {
			return true;
		}
	}
}
