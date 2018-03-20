package uk.org.cse.nhm.language.definition.group;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;


@Bind("group.random-sample")
@Doc("A group which contains a subsample of its source group, drawn uniformly at random from all possible subsamples.")
public class XRandomSampleGroup extends XGroupWithSource {
	public static final class P {
		public static final String PROPORTION = "proportion";
	}
	private double proportion;

	
@BindNamedArgument
	@Prop(P.PROPORTION)
	@Doc("The proportion of the source group to sample")
	public double getProportion() {
		return proportion;
	}

	public void setProportion(double proportion) {
		this.proportion = proportion;
	}	
}
