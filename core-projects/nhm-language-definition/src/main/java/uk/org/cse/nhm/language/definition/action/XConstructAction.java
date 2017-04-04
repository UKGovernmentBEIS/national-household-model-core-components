package uk.org.cse.nhm.language.definition.action;

import uk.org.cse.nhm.language.definition.Doc;

import com.larkery.jasb.bind.Bind;


@Bind("action.construct")
@Doc("Duplicates all of the houses to which it is applied, updating the build year of the house, and the installation year of all technologies in the house.")
public class XConstructAction extends XAction {

}
