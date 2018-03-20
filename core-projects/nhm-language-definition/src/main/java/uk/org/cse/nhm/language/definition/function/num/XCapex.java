package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.money.TransactionTags;
import uk.org.cse.nhm.language.definition.money.XLoanAction;


@Bind("cost.capex")
@Doc(value = { "Used inside a choice, measure or action: yields the sum of the capex of every technology which has been installed in the dwelling as part of that action.",
		"This does not include other non-capex costs like loans or subsidies. If in doubt, check if a transaction has the " + TransactionTags.Internal.capex + " tag ",
		"This is intended to be useful for creating subsidies and loans which vary depending on the capex of a measure. It may also be useful inside probes."
		})
@SeeAlso({XLoanAction.class, XSumOfCosts.class})
@Category(CategoryType.MONEY)
@Obsolete(inFavourOf=XCapitalCost.class, reason="cost.capex has been renamed to capital-cost, to make it clearer what it does", version="5.1.4")
public class XCapex extends XNumber {
}
