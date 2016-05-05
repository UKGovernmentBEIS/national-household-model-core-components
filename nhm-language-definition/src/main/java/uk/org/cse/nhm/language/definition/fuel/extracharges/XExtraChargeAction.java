package uk.org.cse.nhm.language.definition.fuel.extracharges;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindPositionalArgument;

@Bind("action.extra-fuel-charge")
@Doc({
	"Adds an extra fuel charge (or subsidy, if negative) to a dwelling's fuel bill."
})
@Unsuitability("The dwelling in question already pays this extra charge.")
@SeeAlso(XRemoveChargeAction.class)
@Category(CategoryType.TARIFFS)
public class XExtraChargeAction extends XFlaggedDwellingAction {
	public static class P {
		public static final String charge = "charge";
	}
	
	private XExtraCharge charge;
	
	@Prop(P.charge)
	@Doc("The charge which will be added to the dwelling's fuel bill.")
	@BindPositionalArgument(0)
	@NotNull(message = "action.extra-fuel-charge must specify a charge.")
	public XExtraCharge getCharge() {
		return charge;
	}
	
	public void setCharge(final XExtraCharge charge) {
		this.charge = charge;
	}
}
