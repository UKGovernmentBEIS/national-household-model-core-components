package uk.org.cse.nhm.language.definition.action.reset;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.energycalculator.api.types.DoorType;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

@RequireParent(XResetDoors.class)
@Bind("door.type")
@Doc("Get the door type of a door, when in the reset doors action.")
@ReturnsEnum(DoorType.class)
@Category(CategoryType.RESETACTIONS)
public class XGetDoorType extends XCategoryFunction {

}
