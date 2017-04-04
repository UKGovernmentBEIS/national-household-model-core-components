package uk.org.cse.nhm.language.definition.function.num;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.commons.Glob;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.context.calibration.ICalibratedEnergyFunction;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;
import com.larkery.jasb.bind.BindRemainingArguments;

@Doc({ "The expected cost of all or some of a house's obligations for the next year from now.",
       "Obligations are produced by (for example), the house's tariff and energy use, the house's operational costs, the repayment of extant loans, or finance.with-obligation.",
       "Each such obligation will produce some transactions for the house on some future dates.",
       "This command looks at the house's current obligations, and asks them to predict the transactions that they will make during the next year, ceteris paribus.",
       "The resulting transactions are filtered according to the patterns supplied, and then added up.",
       "The resulting sum is the value produced by this command.",
       "If you wish to know about more than one year's likely obligations, you can use the predict-sum command to predict the future value of this function in later years.",
       "The bounds of the next year are determined by the include-year-end: argument. By default, this excludes now and includes the date exactly one year hence."
})
@Bind("house.annual-cost")
@Category(CategoryType.MONEY)
public class XAnnualCost extends XHouseNumber implements ICalibratedEnergyFunction {
	boolean containsEnd = true;
	List<List<Glob>> match = new ArrayList<>();
	
	@Doc({"If true, transactions which occur exactly 1 year from the present date will be included, and transactions which occur exactly on the present date will be excluded.",
		"Otherwise, transactions which occur exactly on the present date will be included, and transactions exactly one year from now will be excluded.",
		"This bears consideration when defining present cost or present value functions with predict-sum, as it determines (for example) whether the first payment from a loan just taken out",
		"which will occur on the anniversary of today will "})
	@BindNamedArgument("include-year-end")
	public boolean isContainsEnd() {
		return containsEnd;
	}
	public void setContainsEnd(final boolean containsEnd) {
		this.containsEnd = containsEnd;
	}
	
	@Doc({
		"All the remaining arguments are lists of positive or negative tags; any transaction predicted in the next year will be included so long as it matches at least one of these lists.",
		"A transaction will match one of these lists if it has all of the positive tags and none of the negative tags."	
	})
	
	@BindRemainingArguments
	public List<List<Glob>> getMatch() {
		return match;
	}
	public void setMatch(final List<List<Glob>> match) {
		this.match = match;
	}
}
