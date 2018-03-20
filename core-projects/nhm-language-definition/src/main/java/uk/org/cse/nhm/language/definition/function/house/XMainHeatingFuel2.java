package uk.org.cse.nhm.language.definition.function.house;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.definition.function.bool.house.XMainHeatingFuelIs;

@Doc("The main heating fuel of the house. If there are no suitable heaters, portable electric heaters will be assumed.")
@Bind("house.heating-fuel")
@ReturnsEnum(XFuelType.class)
@SeeAlso(XMainHeatingFuelIs.class)
public class XMainHeatingFuel2 extends XCategoryFunction {

}
