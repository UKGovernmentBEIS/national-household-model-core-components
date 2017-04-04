package uk.org.cse.nhm.language.definition.function.house;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.definition.function.bool.house.XMainHeatingFuelIs;
import uk.org.cse.nhm.types.MainHeatingSystemType;

import com.larkery.jasb.bind.Bind;

@Doc("The main heating system type of the house")
@Bind("house.main-heating-system-type")
@ReturnsEnum(MainHeatingSystemType.class)
@SeeAlso(XMainHeatingFuelIs.class)
public class XMainHeatingSystemType extends XCategoryFunction {

}
