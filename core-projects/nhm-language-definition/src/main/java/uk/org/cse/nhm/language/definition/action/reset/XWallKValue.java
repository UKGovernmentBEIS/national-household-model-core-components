package uk.org.cse.nhm.language.definition.action.reset;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

import com.larkery.jasb.bind.Bind;

@RequireParent(XResetWalls.class)
@Bind("wall.k-value")
@Category(CategoryType.RESETACTIONS)
public class XWallKValue extends XHouseNumber {

}
