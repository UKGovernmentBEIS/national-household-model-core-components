package uk.org.cse.nhm.language.definition.function.bool.house;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

import com.larkery.jasb.bind.Bind;

@Doc("A test which determines whether a house is connected to the national gas grid.")
@Bind("house.is-on-gas")
public class XOnGas extends XHouseBoolean {
	
}
