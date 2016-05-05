package uk.org.cse.nhm.language.definition.function.house;

import uk.org.cse.nhm.hom.types.RegionType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.definition.function.bool.house.XRegionIs;

import com.larkery.jasb.bind.Bind;

@Doc("The EHS region of the house.")
@ReturnsEnum(RegionType.class)
@Bind("house.region")
@SeeAlso(XRegionIs.class)
public class XRegion extends XCategoryFunction {

}
