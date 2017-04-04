package uk.org.cse.nhm.language.definition.action.reset;

import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.ReturnsEnum;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.reset.XTechnologyType.XTechnologyTypeValue;
import uk.org.cse.nhm.language.definition.function.XFunction;
import uk.org.cse.nhm.language.definition.function.num.IHouseContext;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

import com.larkery.jasb.bind.Bind;

@Doc({"When used within reset.opex, produces the type of technology being ",
	"considered."})
@Bind("technology.type")
@SeeAlso(XResetOpex.class)
@RequireParent({XResetOpex.class, IHouseContext.class})
@ReturnsEnum(XTechnologyTypeValue.class)
@Category(CategoryType.RESETACTIONS)
public class XTechnologyType extends XFunction {
	public enum XTechnologyTypeValue {
		StandardBoiler,
		InstantCombi,
		StorageCombi,
		CPSU,
		GSHP,
		ASHP,
		StorageHeater,
		RoomHeater,
		BackBoiler,
		SolarDHW,
		DistrictHeat
	}
}
