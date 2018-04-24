package uk.org.cse.nhm.language.definition.action.reset;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

@RequireParent(XResetWalls.class)
@Bind("wall.infiltration")
@Category(CategoryType.RESETACTIONS)
public class XWallInfiltration extends XHouseNumber {

}
