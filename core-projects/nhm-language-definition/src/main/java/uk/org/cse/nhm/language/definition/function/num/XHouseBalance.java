package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;


@Bind("house.balance")
@Doc({
	"Yields the current balance of the house, which is the sum of all the transactions which it has entered into so far.",
	"Does not account for any other assets or liabilities (in particular, this number will not take unpaid loans into account)."
})
@Category(CategoryType.MONEY)
public class XHouseBalance extends XHouseNumber {
}
