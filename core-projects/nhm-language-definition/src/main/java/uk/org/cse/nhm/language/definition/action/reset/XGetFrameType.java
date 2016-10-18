package uk.org.cse.nhm.language.definition.action.reset;

import uk.org.cse.nhm.hom.components.fabric.types.FrameType;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

import com.larkery.jasb.bind.Bind;

@RequireParent(XResetGlazing.class)
@Bind("glazing.frame-type")
@Doc("The frame type of the current glazing")
@ReturnsEnum(FrameType.class)
@Category(CategoryType.RESETACTIONS)
public class XGetFrameType extends XCategoryFunction {

}
