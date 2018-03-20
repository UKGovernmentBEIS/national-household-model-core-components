package uk.org.cse.nhm.language.definition.function.house;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.definition.function.bool.house.XAnyWalls;

@Doc(value = "The type of external wall with the most area in this house.")
@ReturnsEnum(WallConstructionType.class)
@Bind("house.predominant-wall-type")
@SeeAlso(XAnyWalls.class)
public class XPredominantWallType extends XCategoryFunction {

}
