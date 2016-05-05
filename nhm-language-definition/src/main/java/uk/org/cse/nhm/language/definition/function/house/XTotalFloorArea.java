package uk.org.cse.nhm.language.definition.function.house;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

import com.larkery.jasb.bind.Bind;

@Bind("house.total-floor-area")
@Doc("The floor area of the current house, in square metres.")
public class XTotalFloorArea extends XHouseNumber {

}
