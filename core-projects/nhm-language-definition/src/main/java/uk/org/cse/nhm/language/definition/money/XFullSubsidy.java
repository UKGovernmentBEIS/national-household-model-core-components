package uk.org.cse.nhm.language.definition.money;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ProducesTags;
import uk.org.cse.nhm.language.definition.ProducesTags.Tag;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.num.XSumOfCosts;

import com.larkery.jasb.bind.Bind;


@Bind("finance.fully")
@Doc({
	"Subsidises another action for the entireity of its cost.",
	"This action first performs the action written inside of it. It then works out the net cost to the house of that action, and pays it back.",
	"No subsidy will be applied if the cost to the house was less than or equal to 0."
})
@ProducesTags(value = { @Tag(
		value = TransactionTags.Internal.subsidy, 
		detail = "When this action is applied, it will produce a single transaction with this tag.") })
@Category(CategoryType.MONEY)
@SeeAlso({XSubsidy.class, XAdditionalCost.class, XSumOfCosts.class})
public class XFullSubsidy extends XFinanceAction {
}
