package uk.org.cse.nhm.language.definition.action.reset;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

import com.larkery.jasb.bind.Bind;

@Doc({"When used within reset.opex, produces the fuel of the technology being ",
"considered."})
@Bind("technology.fuel")
@SeeAlso(XResetOpex.class)
@RequireParent({XResetOpex.class, IHouseContext.class})
@ReturnsEnum(FuelType.class)
@Category(CategoryType.RESETACTIONS)
public class XTechnologyFuel extends XFunction {

}
