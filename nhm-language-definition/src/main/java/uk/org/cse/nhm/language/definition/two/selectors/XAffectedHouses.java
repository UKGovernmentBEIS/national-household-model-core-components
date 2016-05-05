package uk.org.cse.nhm.language.definition.two.selectors;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.two.hooks.XChangeHook;
import uk.org.cse.nhm.language.definition.two.hooks.XFlagHook;
import uk.org.cse.nhm.language.definition.two.hooks.XConstructHook;

import com.larkery.jasb.bind.Bind;

@Doc("A special set, which contains the houses that have been affected by a change, when used in on.change or on.flag, or the newly constructed dwellings, when used within on.construction")
@Bind("affected-houses")
@SeeAlso({XChangeHook.class,XFlagHook.class, XConstructHook.class})
public class XAffectedHouses extends XSetOfHouses {

}
