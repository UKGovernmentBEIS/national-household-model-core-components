package uk.org.cse.nhm.language.definition.action.reset;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

@RequireParent(XResetWalls.class)
@Bind("wall.construction")
@Doc("When used within special wall actions, returns the construction type of the current wall")
@SeeAlso(XResetWalls.class)
@ReturnsEnum(WallConstructionType.class)
@Category(CategoryType.RESETACTIONS)
public class XWallConstruction extends XCategoryFunction {

}
