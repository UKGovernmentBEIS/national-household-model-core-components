package uk.org.cse.nhm.language.definition.action.reset;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

@RequireParent(XResetGlazing.class)
@Bind("glazing.insulation-type")
@Doc("The insulation type of the current glazing")
@ReturnsEnum(WindowInsulationType.class)
@Category(CategoryType.RESETACTIONS)
public class XGetGlazingInsulationType extends XCategoryFunction {

}
