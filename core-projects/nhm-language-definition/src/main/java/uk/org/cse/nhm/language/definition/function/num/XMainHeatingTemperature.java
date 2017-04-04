package uk.org.cse.nhm.language.definition.function.num;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.XHeatingTemperaturesAction;

import com.larkery.jasb.bind.Bind;

@Doc("The current living area temperature of the house")
@Bind("house.living-area-temperature")
@SeeAlso(XHeatingTemperaturesAction.class)
public class XMainHeatingTemperature extends XHouseNumber {
	
}
