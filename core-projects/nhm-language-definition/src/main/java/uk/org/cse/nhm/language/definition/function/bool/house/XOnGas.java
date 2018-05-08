package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

@Doc("A test which determines whether a house is connected to the national gas grid.")
@Bind("house.is-on-gas")
public class XOnGas extends XHouseBoolean {

}
