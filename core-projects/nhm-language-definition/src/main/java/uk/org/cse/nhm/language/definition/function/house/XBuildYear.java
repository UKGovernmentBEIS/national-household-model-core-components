package uk.org.cse.nhm.language.definition.function.house;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.bool.house.XBuildYearIs;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;

import com.larkery.jasb.bind.Bind;


@Bind("house.buildyear")
@Doc("The year of construction of the house.")
@SeeAlso(XBuildYearIs.class)
@Obsolete(inFavourOf = {XBuildYear2.class}, reason = "Did not follow the NHM's naming convention.", version = "6.5.0")
public class XBuildYear extends XHouseNumber {

}
