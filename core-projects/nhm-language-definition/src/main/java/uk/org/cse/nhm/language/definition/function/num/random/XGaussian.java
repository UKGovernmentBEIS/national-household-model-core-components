package uk.org.cse.nhm.language.definition.function.num.random;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XNumber;

@Bind("random.gaussian")
@Doc({
	"Produces normally distributed random numbers."
})
public class XGaussian extends XNumber {
	public static class P {
		public static final String mean = "mean";
		public static final String standardDeviation = "standardDeviation";
	}
	
	private double mean;
	private double standardDeviation;
	
	@BindNamedArgument
	@Prop(P.mean)
	@NotNull(message = "random.gaussian must always have a mean.")
	@Doc("The mean (mu) of the Gaussian.")
	public double getMean() {
		return mean;
	}
	
	public void setMean(final double mean) {
		this.mean = mean;
	}
	
	@BindNamedArgument("standard-deviation")
	@Prop(P.standardDeviation)
	@NotNull(message = "random.gaussian must always have a standard deviation.")
	@Doc("The standard deviation (sigma) of the Gaussian.")
	public double getStandardDeviation() {
		return standardDeviation;
	}
	
	public void setStandardDeviation(final double standardDeviation) {
		this.standardDeviation = standardDeviation;
	}
}
