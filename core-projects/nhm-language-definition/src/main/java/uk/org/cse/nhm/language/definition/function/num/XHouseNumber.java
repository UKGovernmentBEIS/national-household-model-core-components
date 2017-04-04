package uk.org.cse.nhm.language.definition.function.num;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

@Category(CategoryType.HOUSEPROPERTIES)
@RequireParent(IHouseContext.class)
public abstract class XHouseNumber extends XNumber {

}
