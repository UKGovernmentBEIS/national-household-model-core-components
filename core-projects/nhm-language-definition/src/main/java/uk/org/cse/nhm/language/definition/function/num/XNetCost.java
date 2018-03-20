package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import com.google.common.base.Predicate;
import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;

@Bind("net-cost")
@Doc({ 
	"Calculates the net cost of the current action to the current house; this is the balance of all the transactions the house has gone through because of the current action.",
	"",
	"The value of the net cost is context-sensitive because of the notion of the current action. Used in a choice's selector, it will be the",
	"net cost of the alternative being considered; used within a (set) statement within a (do), it will be the net cost of the do so-far.",
	"Inside subsidy and loan functions, it is the net cost of the action being subsidized or given a loan.",
	"",
	"Note that using this within, for example, a report, will typically produce zero, because the cost of the report is zero.",
	"To report on historical transactions, try house.sum-transactions."
})
@Category(CategoryType.MONEY)
@SeeAlso({XCapitalCost.class, XSumTransactions.class})
public class XNetCost extends XHouseNumber {
	public static class P {
		public static final String tagged = "tagged";
		public static final String taggedPredicate = "taggedPredicate";
	}

	private List<Glob> tagged = new ArrayList<>();

	@BindNamedArgument(P.tagged)
	@Doc("Filters the transactions which will be included in the sum to only those whose tags match this pattern.")
	public List<Glob> getTagged() {
		return tagged;
	}

	@Prop(P.tagged)
	public void setTagged(final List<Glob> tagged) {
		this.tagged = tagged;
	}
	
	@Prop(P.taggedPredicate)
	public Predicate<Collection<String>> getTaggedPredicate() {
		return Glob.requireAndForbid(tagged);
	}
}
