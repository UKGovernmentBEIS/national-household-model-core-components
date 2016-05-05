package uk.org.cse.nhm.language.definition.exposure;

import javax.validation.constraints.DecimalMax;
import javax.validation.constraints.DecimalMin;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;


@Doc(
		{
			"A sampler which will pick a fixed proportion from the source group, chosen uniformly at random from all subsets of the given proportion.",
			"Samples with replacement."
		}
	)
@Bind("sample.proportion")
public class XProportionSampler extends XSampler {
	public static final class P {
		public static final String PROPORTION = "proportion";
	}
	
	private double proportion;

	private static final String PROPORTION_MSG = "sample.proportion 'proportion' attribute must be between 0.0 and 1.0 inclusive.";
	@Prop(P.PROPORTION)
	
@BindPositionalArgument(0)
	@Doc("The proportion of the source group to sample each time (e.g. 0.1 is ten percent).")
	@DecimalMin(value = "0.0", message = PROPORTION_MSG)
	@DecimalMax(value="1.0", message = PROPORTION_MSG)
	public double getProportion() {
		return proportion;
	}

	public void setProportion(final double proportion) {
		this.proportion = proportion;
	}

	public static XSampler all() {
		final XProportionSampler result = new XProportionSampler();
		
		result.setProportion(1);
		
		return result;
	}
}
