package uk.org.cse.nhm.language.definition.batch.inputs.random;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("gaussian")
@Doc(value={"A batch input which draws values from a Gaussian distribution (also known as a normal distribution).",
		"Distributions are unbounded, and so may yield as many values as required."})
public class XGaussianInput extends XDistributionInput {
	public static final class P {
		public static final String MEAN = "mean";
		public static final String STANDARD_DEVIATION = "standardDeviation";
	}
	
	private double mean = 0.0;
	private double standardDeviation = 1.0;
	
	
@BindNamedArgument
	@Prop(P.MEAN)
	@Doc("The mean (mu) of the Gaussian.")
	public double getMean() {
		return mean;
	}
	
	public void setMean(final double mean) {
		this.mean = mean;
	}
	
	
@BindNamedArgument
	@Prop(P.STANDARD_DEVIATION)
	@Doc("The standard deviation (sigma squared) of the Gaussian.")
	public double getStandardDeviation() {
		return standardDeviation;
	}
	
	public void setStandardDeviation(final double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
	
	
}
