package uk.org.cse.nhm.language.definition.function.house;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.definition.function.bool.house.XMainHeatingFuelIs;

import com.larkery.jasb.bind.Bind;

@Doc("The main heating fuel of the house (using a different, unhelpful coding)")
@Bind("house.main-heating-fuel")
@ReturnsEnum(FuelType.class)
@Obsolete(inFavourOf=XMainHeatingFuel2.class, 
	reason="The enumeration values produced by this function are different from all other places where fuels are used in the model.",
	version="5.1.4")
@SeeAlso(XMainHeatingFuelIs.class)
public class XMainHeatingFuel extends XCategoryFunction {

}
