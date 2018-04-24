package uk.org.cse.nhm.language.definition.two.hooks;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.two.selectors.XAffectedHouses;

@Bind("on.change")
@Doc(
		{"A hook which will by triggered when something has changed how things are in the simulation.",
			"This could be another action, or the passage of time.",
			"This hook will never cause itself to be triggered, either directly or indirectly, and",
			"Ties are broken in scenario order, as usual."
		}
	)
@SeeAlso(XAffectedHouses.class)
public class XChangeHook extends XHook {
	public static final class P {
		public static final String stock = "stock";
	}
	
	private boolean stock = false;

	@Prop(P.stock)
	@BindNamedArgument("include-startup")
	@Doc("If true, include the initial change caused by loading the stock; otherwise, only activate for later changes")
	public boolean isStock() {
		return stock;
	}

	public void setStock(final boolean stock) {
		this.stock = stock;
	}
}
