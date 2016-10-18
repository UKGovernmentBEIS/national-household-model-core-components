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

@Doc({ "The expected cost of all or some of a house's obligations for the next year.",
	"This includes predicted transactions from the (apparent) current date until a year from that date.",
	"The foresight level of calculations made by obligations which are not on the current date will be determined from the context;",
	"If this is used with predict-sum, only those things which the predict-sum can predict will change. Otherwise, everything will be fully predictable."
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
