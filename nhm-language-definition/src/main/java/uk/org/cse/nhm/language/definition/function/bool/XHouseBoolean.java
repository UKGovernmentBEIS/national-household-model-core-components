package uk.org.cse.nhm.language.definition.function.bool;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

@RequireParent(IHouseContext.class)
@Category(CategoryType.LOGICHOUSE)
public abstract class XHouseBoolean extends XBoolean {

}
