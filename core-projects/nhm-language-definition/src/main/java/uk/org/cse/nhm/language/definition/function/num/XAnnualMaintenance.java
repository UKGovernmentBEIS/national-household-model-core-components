package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;

@Doc({
    "Yields the combined annual maintenance cost (opex) of the heating technologies currently installed in the house.",})
@Bind("house.annual-maintenance")
@Category(CategoryType.MONEY)
public class XAnnualMaintenance extends XHouseNumber {
}
