package uk.org.cse.nhm.language.definition.action.hypothetical;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XFlaggedDwellingAction;
import uk.org.cse.nhm.language.definition.function.num.IHypotheticalContext;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

@Category(CategoryType.COUNTERFACTUALS)
@Unsuitability("outside under")
@RequireParent({IHypotheticalContext.class})
public abstract class XCounterfactualAction extends XFlaggedDwellingAction {

}
