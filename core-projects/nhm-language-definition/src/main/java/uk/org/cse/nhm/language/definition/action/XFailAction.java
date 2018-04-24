package uk.org.cse.nhm.language.definition.action;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.sequence.XSequenceAction;

@Bind("action.fail")
@Unsuitability(value="Always")
@Doc("This action will always fail, and is never suitable. It is useful within action.case and do, to terminate a package under some conditions.")
@SeeAlso({XSequenceAction.class, XCaseAction.class})
@Category(CategoryType.ACTIONCOMBINATIONS)
public class XFailAction extends XDwellingAction {

}
