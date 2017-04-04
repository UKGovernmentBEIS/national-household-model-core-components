package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("cost.sum")
@Doc({ "Used inside a choice, measure or action: yields the sum of all transactions which the dwelling has been involved in as part of that action.", })
@Category(CategoryType.MONEY)
@Obsolete(
		reason="cost.sum has been renamed to net-cost, to make it clearer what it does.",
		version="5.1.4",
		inFavourOf=XNetCost.class)
public class XSumOfCosts extends XHouseNumber {
	public static class P {
		public static final String tagged = "tagged";
	}

	private List<Glob> tagged = new ArrayList<>();

	@BindNamedArgument(P.tagged)
	@Doc("Filters the transactions which will be included in the sum to only those which contain this tag.")
	public List<Glob> getTagged() {
		return tagged;
	}

	@Prop(P.tagged)
	public void setTagged(final List<Glob> tagged) {
		this.tagged = tagged;
	}
}
