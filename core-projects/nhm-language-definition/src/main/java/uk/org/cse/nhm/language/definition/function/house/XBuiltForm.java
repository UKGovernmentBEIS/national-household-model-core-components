package uk.org.cse.nhm.language.definition.function.house;

import uk.org.cse.nhm.hom.types.BuiltFormType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.definition.function.bool.house.XBuiltFormIs;

import com.larkery.jasb.bind.Bind;


@Doc("The actual built form of a house.")
@Bind("house.built-form")
@SeeAlso(XBuiltFormIs.class)
@ReturnsEnum(BuiltFormType.class)
public class XBuiltForm extends XCategoryFunction {

}
