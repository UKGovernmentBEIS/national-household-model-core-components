package uk.org.cse.nhm.language.definition.fuel.extracharges;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;

@Bind("action.remove-fuel-charge")
@Doc({
	"Removes an extra charge which was previously added to a dwelling.", 
	"Cannot be used to remove charges from tariffs."
	})
@SeeAlso(XExtraChargeAction.class)
@Unsuitability({"The dwelling is not currently paying the extra charge we are trying to remove."})
@Category(CategoryType.TARIFFS)
public class XRemoveChargeAction extends XFlaggedDwellingAction {
	public static class P {
		public static final String charge = "charge";
	}
	
	private XExtraCharge charge;

	@Doc("The extra charge which should be removed from the dwelling. If not specified, all charges will be removed.")
	@BindPositionalArgument(0)
	@Prop(P.charge)
	public XExtraCharge getCharge() {
		return charge;
	}

	public void setCharge(final XExtraCharge charge) {
		this.charge = charge;
	}
}
