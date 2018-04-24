package uk.org.cse.nhm.language.definition.function.house;

import com.larkery.jasb.bind.Bind;

import uk.org.cse.nhm.hom.types.TenureType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.XCategoryFunction;
import uk.org.cse.nhm.language.definition.function.bool.house.XTenureIs;

@Doc("The tenure type of the household.")
@ReturnsEnum(TenureType.class)
@Bind("house.tenure")
@SeeAlso(XTenureIs.class)
public class XTenure extends XCategoryFunction {

}
