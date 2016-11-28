package uk.org.cse.nhm.language.definition.function.house;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.bool.house.XBuildYearIs;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

@Bind("house.build-year")
@Doc("The year of construction of the house.")
@SeeAlso(XBuildYearIs.class)
public class XBuildYear2 extends XHouseNumber {

}
