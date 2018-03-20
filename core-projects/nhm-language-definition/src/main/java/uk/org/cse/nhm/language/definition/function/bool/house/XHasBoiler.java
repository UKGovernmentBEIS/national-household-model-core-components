package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;


@Bind("house.has-boiler")
@Doc("A test that matches houses which have a boiler.")
public class XHasBoiler extends XHouseBoolean {
}
