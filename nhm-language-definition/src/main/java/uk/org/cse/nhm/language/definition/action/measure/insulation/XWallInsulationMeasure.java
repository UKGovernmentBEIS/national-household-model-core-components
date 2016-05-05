package uk.org.cse.nhm.language.definition.action.measure.insulation;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.SeeAlso;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XWallInsulationMeasure.XWallInsulationType;
import uk.org.cse.nhm.language.definition.enums.XWallConstructionType;
import uk.org.cse.nhm.language.definition.enums.XWallConstructionTypeRule;
import uk.org.cse.nhm.language.definition.enums.XWallInsulationRule;
import uk.org.cse.nhm.language.definition.function.bool.house.XAnyWall;
import uk.org.cse.nhm.language.definition.function.bool.house.XEveryWall;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;


@Bind("measure.wall-insulation")
@Doc({	"Installs wall insulation into suitable households.",
	
		"As with other insulation measures you can specify the thickness (in mm) as well as functions for calculating the Capital Expenditure (capex),",
		"insulation resistance (r-value) or the u-value for the wall itself following installation.",
		
		"Houses in the NHM have multiple walls, each of which can have different construction and insulation on it.",
		
		"When this measure is used, the model will determine which walls are suitable to be insulated using the rules defined below.",
		"If no walls are suitable, installation will fail; otherwise, all of the suitable walls will be modified to reflect their new insulation status.",
		"The way in which they are modified depends on whether the u-value: or resistance: argument is used to specify the thermal property."
})
@Unsuitability("There are no walls which are suitable for this kind of insulation. "+
		"By default, this is the case if and only if every wall is either (a) unsuitable for this type of insulation (see type:), or (b) already has some of this type of insulation." +
		"However, the suitable-construction: and suitable-insulation: arguments allow you to override these requirements if you wish.")
@SeeAlso({XWallInsulationRule.class, XWallInsulationType.class, XWallConstructionType.class, XAnyWall.class, XEveryWall.class})
public class XWallInsulationMeasure extends XInsulationMeasure {
	private static final String CAVITY_TIMBER_FRAME_METAL_FRAME_OR_SYSTEM_BUILD = "Cavity, TimberFrame, MetalFrame or SystemBuild";
	@Doc("A type of insulation")
	public enum XWallInsulationType {
		@Doc("Cavity wall insulation can only be applied to walls whose construction type is one of "
				+ CAVITY_TIMBER_FRAME_METAL_FRAME_OR_SYSTEM_BUILD
				+ " that currently have no cavity insulation.")
		Cavity,
		@Doc("Internal wall insulation can only be applied to walls that currently have no internal insulation; all wall construction types are suitable.")
		Internal,
		@Doc("External wall insulation can only be applied to walls that currently have no external insulation; all wall construction types are suitable.")
		External;
	}
	
	public static final class P {
		public static final String type = "type";
		public static final String suitableConstruction = "suitableConstruction";
		public static final String suitableInsulation = "suitableInsulation";
	}
	
	private XWallInsulationType type = XWallInsulationType.Cavity;
	private XWallConstructionTypeRule suitableConstruction = null;
	private XWallInsulationRule suitableInsulation = null;
	
	@BindNamedArgument
	@Prop(P.type)
	@Doc("The type of wall insulation to apply - if this is Cavity, the measure will only be suitable for walls of " + CAVITY_TIMBER_FRAME_METAL_FRAME_OR_SYSTEM_BUILD + " construction.")
	@NotNull(message = "measure.wall-insulation must have 'type' set to the type of wall insulation being applied")
	public XWallInsulationType getType() {
		return type;
	}

	public void setType(final XWallInsulationType type) {
		this.type = type;
	}

	@Doc("If set, overrides the construction types for which the measure is suitable. If not set, the rule is defined by the insulation type chosen - see the legal values for type: for the details.")
	@BindNamedArgument("suitable-construction")
	@Prop(P.suitableConstruction)
	public XWallConstructionTypeRule getSuitableConstruction() {
		return suitableConstruction;
	}

	public void setSuitableConstruction(final XWallConstructionTypeRule suitableConstruction) {
		this.suitableConstruction = suitableConstruction;
	}

	@Doc("If set, overrides the insulation conditions for which the measure is suitable. If not set, the measure will only apply to walls which do not currently have any of the specified type of insulation.")
	@BindNamedArgument("suitable-insulation")
	@Prop(P.suitableInsulation)
	public XWallInsulationRule getSuitableInsulation() {
		return suitableInsulation;
	}

	public void setSuitableInsulation(final XWallInsulationRule suitableInsulation) {
		this.suitableInsulation = suitableInsulation;
	}
}
