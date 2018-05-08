package uk.org.cse.nhm.language.definition.action;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;

@Bind("action.demolish")
@Doc("Removes any house to which it is applied from the simulation entirely.")
public class XDestroyAction extends XAction {

}
