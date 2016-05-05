package uk.org.cse.nhm.language.definition.function;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

@Category(CategoryType.HOUSEPROPERTIES)
@RequireParent(value = IHouseContext.class)
public abstract class XCategoryFunction extends XFunction {

}
