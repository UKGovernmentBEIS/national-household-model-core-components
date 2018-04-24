package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.Obsolete;
import uk.org.cse.nhm.language.definition.enums.XWallConstructionTypeRule;
import uk.org.cse.nhm.language.definition.enums.XWallInsulationRule;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;


@Obsolete(reason="This is confusing to use effectively; the replacements have finer control over insulation matching", 
	inFavourOf={XAnyWall.class, XEveryWall.class})
@Bind("house.has-wall")
@Doc(
		{
			"A test to ensure that a house has at least one wall matching the given construction and insulation criteria."
		}
	)
public class XAnyWalls extends XHouseBoolean {
	public static final class P {
		public static final String WITH_INSULATION = "withInsulation";
		public static final String WITH_CONSTRUCTION = "withConstruction";
	}
	
	private XWallConstructionTypeRule withConstruction = XWallConstructionTypeRule.Any;
	private XWallInsulationRule withInsulation = XWallInsulationRule.NoInsulation;
	
	@Prop(P.WITH_CONSTRUCTION)
	
	@BindNamedArgument("with-construction")
	@Doc("A requirement on the construction type of the wall")
	public XWallConstructionTypeRule getWithConstruction() {
		return withConstruction;
	}
	public void setWithConstruction(final XWallConstructionTypeRule withConstruction) {
		this.withConstruction = withConstruction;
	}
	
	@Prop(P.WITH_INSULATION)
	@Doc("A requirement on the insulation of the wall")
	@BindNamedArgument("with-insulation")
	
	public XWallInsulationRule getWithInsulation() {
		return withInsulation;
	}
	public void setWithInsulation(final XWallInsulationRule withInsulation) {
		this.withInsulation = withInsulation;
	}
}
