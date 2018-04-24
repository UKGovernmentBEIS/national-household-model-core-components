package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

@Doc("A test which determines whether a house has a central heating system.")
@Bind("house.has-central-heating")
public class XHasCentralHeating extends XHouseBoolean {
	public static class P {
		public static final String includeBroken = "includeBroken";
	}
	
	private boolean includeBroken = false;
	
	@Prop(P.includeBroken)
	@BindNamedArgument("include-broken")
	@Doc("Include broken heating systems")
	public boolean getIncludeBroken() {
		return includeBroken;
	}

	public void setIncludeBroken(final boolean includeBroken) {
		this.includeBroken = includeBroken;
	}
}
