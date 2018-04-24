package uk.org.cse.nhm.language.definition.function.house;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

@Bind("house.total-floor-area")
@Doc("The floor area of the current house, in square metres.")
public class XTotalFloorArea extends XHouseNumber {

}
