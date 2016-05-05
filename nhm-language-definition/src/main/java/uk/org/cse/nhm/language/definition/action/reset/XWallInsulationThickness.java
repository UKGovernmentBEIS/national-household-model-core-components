package uk.org.cse.nhm.language.definition.action.reset;

import java.util.ArrayList;
import java.util.List;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Category;
import uk.org.cse.nhm.language.definition.Category.CategoryType;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XWallInsulationMeasure.XWallInsulationType;
import uk.org.cse.nhm.language.definition.function.num.XHouseNumber;
import uk.org.cse.nhm.language.validate.contents.RequireParent;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindRemainingArguments;

@RequireParent(XResetWalls.class)
@Bind("wall.insulation-thickness")
@Doc({
	"When used in special wall actions, this will produce the total thickness of the specified types of insulation for the current wall."	
})
@SeeAlso(XResetWalls.class)
@Category(CategoryType.RESETACTIONS)
public class XWallInsulationThickness extends XHouseNumber {
	public static final class P {
		public static final String types = "types";
	}
	private List<XWallInsulationType> types = new ArrayList<>();

	public XWallInsulationThickness() {
		
	}
	
	@Prop(P.types)
	@Doc("The types of insulation to consider when adding up the thickness.")
	@BindRemainingArguments
	public List<XWallInsulationType> getTypes() {
		return types;
	}

	public void setTypes(final List<XWallInsulationType> types) {
		this.types = types;
	}
}