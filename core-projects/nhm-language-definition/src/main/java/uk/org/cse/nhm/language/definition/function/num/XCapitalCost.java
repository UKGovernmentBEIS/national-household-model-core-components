package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.money.TransactionTags;
import uk.org.cse.nhm.language.definition.money.XLoanAction;


@Bind("capital-cost")
@Doc(value = { 
		"Used inside a choice, measure or action: yields the sum of the capex of every measure which has been installed in the dwelling as part of that action.",
		"Used within a set within a do, yields the sum of the capex of each measure installed up to that point by the actions in the do.",
		"",
		"This does not include other non-capex costs like loans or subsidies. If in doubt, check if a transaction has the " + TransactionTags.Internal.capex + " tag ",
		"This is intended to be useful for creating subsidies and loans which vary depending on the capex of a measure. It may also be useful inside probes.",
		"",
		"Note that using this within, for example, a report, will typically produce zero, because the cost of the report is zero.",
		"To report on historical transactions, try house.sum-transactions."
		})
@SeeAlso({XLoanAction.class, XNetCost.class, XSumTransactions.class})
@Category(CategoryType.MONEY)
public class XCapitalCost extends XNumber {
}
