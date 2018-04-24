package uk.org.cse.nhm.language.definition.action;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;


@Bind("action.do-nothing")
@Doc("The do nothing action does nothing; it's mainly useful as an option within a choice.")
@Category(CategoryType.ACTIONCOMBINATIONS)
public class XDoNothingAction extends XFlaggedDwellingAction {

}
