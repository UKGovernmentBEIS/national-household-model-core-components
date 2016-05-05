package uk.org.cse.nhm.language.definition.action.reset;

import uk.org.cse.nhm.hom.components.fabric.types.DoorType;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

import com.larkery.jasb.bind.Bind;

@RequireParent(XResetDoors.class)
@Bind("door.type")
@Doc("Get the door type of a door, when in the reset doors action.")
@ReturnsEnum(DoorType.class)
@Category(CategoryType.RESETACTIONS)
public class XGetDoorType extends XCategoryFunction {

}
