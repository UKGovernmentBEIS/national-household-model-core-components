package uk.org.cse.nhm.language.definition.function.house;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

import com.larkery.jasb.bind.Bind;

@Bind("house.household-income")
@Doc("Returns the total income before tax for the household. If no income is set for a house will assume it's 0.")
@Category(CategoryType.MONEY)
public class XHouseholdIncome extends XHouseNumber {

	
}
