package uk.org.cse.nhm.language.definition.function.house;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

import com.larkery.jasb.bind.Bind;

@Bind("house.number-of-occupants")
@Doc("The sum of all adult and child occupants within a house.")
public class XNumberOfOccupants extends XHouseNumber {

}
