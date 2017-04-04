package uk.org.cse.nhm.language.definition.batch.inputs.random;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.batch.inputs.random.validation.TriangularParameters;
import uk.org.cse.nhm.language.definition.function.num.random.ITriangular;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("triangular")
@Doc(value = {"A batch input which draws values randomly from a triangular distribution.",
		"Distributions are unbounded, and so may yield as many values as required."})
@TriangularParameters(message = "triangular distribution's peak must be between its start and end values inclusive.")
public class XTriangularInput extends XDistributionInput implements ITriangular {
	public static final class P {
		public static final String START = "start";
		public static final String PEAK = "peak";
		public static final String END = "end";
	}
	
	private double start;
	private double peak;
	private double end;
	
	
@Override
@BindNamedArgument
	@Doc("The start of the distribution.")
	@NotNull(message = "triangular distribution must specify a start.")
	@Prop(P.START)
	public double getStart() {
		return start;
	}
	
	public void setStart(final double start) {
		this.start = start;
	}
	
	
@Override
@BindNamedArgument
	@Doc("The position of the peak of the distribution. This must be between the start and end values. The height of the peak will be calculated as (end - start) / 2.")
	@NotNull(message = "triangular distribution must specify a peak.")
	@Prop(P.PEAK)
	public double getPeak() {
		return peak;
	}
	
	public void setPeak(final double peak) {
		this.peak = peak;
	}
	
	
@Override
@BindNamedArgument
	@Doc("The end of the distribution.")
	@NotNull(message = "triangular distribution must specify an end.")
	@Prop(P.END)
	public double getEnd() {
		return end;
	}
	
	public void setEnd(final double end) {
		this.end = end;
	}
}
