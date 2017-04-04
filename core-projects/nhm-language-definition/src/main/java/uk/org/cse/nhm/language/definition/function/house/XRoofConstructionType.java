package uk.org.cse.nhm.language.definition.function.house;

import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;

import com.larkery.jasb.bind.Bind;

@Bind("house.roof-construction-type")
@Doc("The construction type of the roof of the house.")
@ReturnsEnum(RoofConstructionType.class)
public class XRoofConstructionType extends XCategoryFunction {
}
