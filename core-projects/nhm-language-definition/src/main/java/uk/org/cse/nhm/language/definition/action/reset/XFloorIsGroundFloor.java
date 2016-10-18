package uk.org.cse.nhm.language.definition.action.reset;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

import com.larkery.jasb.bind.Bind;

@RequireParent(XResetFloors.class)
@Bind("floor.is-on-ground")
@Doc("True if the current floor is in contact with the ground")
@SeeAlso(XResetFloors.class)
public class XFloorIsGroundFloor extends XHouseBoolean {

}
