package uk.org.cse.nhm.language.definition.function.health;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Doc("The permeability of the house; this is equivalent to (* 20 house.air-change-rate house.volume (/ 1 house.envelope-area))")
@Bind("house.permeability")
public class XPermeabilityFunction extends XHouseNumber {
	public static final class P {
        public static final String includeDeliberate = "includeDeliberate";
    }
	
	private boolean includeDeliberate = false;

	@Prop(P.includeDeliberate)
	@BindNamedArgument("include-deliberate-ventilation")
	@Doc("Include deliberate ventilation in air change rate calculation (SAP blah) defalt if false")
	public boolean getIncludeDeliberate() {
		return includeDeliberate;
	}

	public void setIncludeDeliberate(boolean includeDeliberate) {
		this.includeDeliberate = includeDeliberate;
	}
}