package uk.org.cse.nhm.language.definition.function.house;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.hom.types.MorphologyType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.definition.function.bool.house.XMorphologyIs;

@Doc("The morphology type of the house.")
@ReturnsEnum(MorphologyType.class)
@Bind("house.morphology")
@SeeAlso(XMorphologyIs.class)
public class XMorphology extends XCategoryFunction {

}
