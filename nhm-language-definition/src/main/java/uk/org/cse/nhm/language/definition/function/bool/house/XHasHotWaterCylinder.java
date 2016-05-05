package uk.org.cse.nhm.language.definition.function.bool.house;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

import com.larkery.jasb.bind.Bind;

@Bind("house.has-hot-water-cylinder")
@Doc("A test that returns true if a house has central heating with a storage tank.")
public class XHasHotWaterCylinder extends XHouseBoolean {

}
