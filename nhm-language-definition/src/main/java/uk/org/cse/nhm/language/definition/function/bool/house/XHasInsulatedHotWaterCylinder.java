package uk.org.cse.nhm.language.definition.function.bool.house;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

import com.larkery.jasb.bind.Bind;

/**
 * XHasInsulatedHotWaterCylinder.
 *
 * @author richardTiffin
 * @version $Id$
 */
@Bind("house.has-insulated-hot-water-cylinder")
@Doc("A test that returns true if a house has central heating with an insulated storage tank.")
public class XHasInsulatedHotWaterCylinder extends XHouseBoolean {

}
