package uk.org.cse.nhm.language.definition.action.measure.insulation;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;


@Bind("measure.loft-insulation")
@Doc(	{"Installs loft insulation into suitable households. ",
		"You can specify the top-up attribute to determine whether all households are insulated or just those without existing insulation.",
		"As with other insulation measures you can specify the thickness (in mm) as well as functions for calculating the Capital Expenditure (capex),",
		"insulation resistance (r-value) or the u-value for the wall itself following installation (this overrides r-value)."
})
@Unsuitability({
		"there is no roof area",
		"this is a basement, ground-floor or mid-floor flat",
		"the roof construction type is thatched or flat",
		"this is a topup AND the thickness of any existing loft insulation is greater than the topup thickness",
		"this is not a topup AND there is any existing loft insulation",
		"the property has no loft"})
public class XLoftInsulationMeasure extends XInsulationMeasure {
	public static final class P {
		public static final String topUp = "topUp";
	}

	private boolean topUp = true;


	@BindNamedArgument("top-up")
	@Prop(P.topUp)
	@Doc("If true, then existing insulation will be topped-up to the desired thickness. Otherwise, only uninsulated lofts will be eligible.")
	public boolean isTopUp() {
		return topUp;
	}
	public void setTopUp(final boolean topUp) {
		this.topUp = topUp;
	}
}
