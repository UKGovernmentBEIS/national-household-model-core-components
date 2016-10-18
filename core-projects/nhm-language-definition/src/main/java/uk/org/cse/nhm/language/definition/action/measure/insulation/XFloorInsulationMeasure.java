package uk.org.cse.nhm.language.definition.action.measure.insulation;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("measure.floor-insulation")
@Doc("Applies insulation to uninsulated floors within a house")
@Unsuitability(
	{
		"Houses whose floors do not match the floor type specified",
			"Houses with floor insulation",
			"Flats"
	})
public class XFloorInsulationMeasure extends XInsulationMeasure {
	public static final class P  {
		public static final String type = "type";
	}
	@Doc("Describes the construction type of a floor")
	public enum XFloorType {
		@Doc("Suspended timber floors")
		Suspended,
		@Doc("Solid floors")
		Solid
	}

	private XFloorType type = XFloorType.Suspended;

	@Prop(P.type)
	@BindNamedArgument
	@Doc("The type of floor to which the insulation can be applied")
	public XFloorType getType() {
		return type;
	}

	public void setType(XFloorType type) {
		this.type = type;
	}
}
