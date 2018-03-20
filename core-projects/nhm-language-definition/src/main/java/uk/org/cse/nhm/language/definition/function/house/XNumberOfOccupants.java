package uk.org.cse.nhm.language.definition.function.house;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

@Bind("house.number-of-occupants")
@Doc({
	"The sum of all adult and child occupants within a house.",
	"In SAP 2012 mode, this number will be ignored and the number of occupants will be computed from floor area as specified in step (42) of the SAP worksheet."
})
public class XNumberOfOccupants extends XHouseNumber {

}
