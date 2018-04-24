package uk.org.cse.nhm.language.definition.function.bool.house;

import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.enums.XWallConstructionTypeRule;
import uk.org.cse.nhm.language.definition.function.bool.XHouseBoolean;

public abstract class XWallsTest extends XHouseBoolean {
	public static final class P {
		public static final String WITH_CONSTRUCTION = "withConstruction";
		public static final String cavityInsulation = "cavityInsulation";
		public static final String internalInsulation = "internalInsulation";
		public static final String externalInsulation = "externalInsulation";
	}
	
	private XWallConstructionTypeRule withConstruction = XWallConstructionTypeRule.Any;
	private Boolean cavityInsulation;
	private Boolean internalInsulation;
	private Boolean externalInsulation;
	
	@Prop(P.WITH_CONSTRUCTION)
	@BindNamedArgument("has-construction")
	@Doc("For a wall to meet this requirement, it must have a construction type that is compatible with this rule. See the documentation on legal values for what construction types are matched by what rules.")
	public XWallConstructionTypeRule getWithConstruction() {
		return withConstruction;
	}
	public void setWithConstruction(final XWallConstructionTypeRule withConstruction) {
		this.withConstruction = withConstruction;
	}
	
	@Doc({"If this is true, only walls currently recorded as having some cavity insulation will be allowed.",
		  "If this is false, only walls currently recorded as having no cavity insulation will be allowed (a wall which can never have cavity insulation will still pass this test, as it has no cavity insulation).",
		  "If it is not specified, any wall which meets the other criteria will be allowed."})
	@BindNamedArgument("has-cavity-insulation")
	public Boolean getCavityInsulation() {
		return cavityInsulation;
	}
	public void setCavityInsulation(final Boolean cavityInsulation) {
		this.cavityInsulation = cavityInsulation;
	}
	
	@Doc({"If this is true, only walls currently recorded as having some internal insulation will be allowed.",
		"If this is false, only walls currently recorded as having no internal insulation will be allowed.",
		"If it is not specified, any wall which meets the other criteria will be allowed."})
	@BindNamedArgument("has-internal-insulation")
	public Boolean getInternalInsulation() {
		return internalInsulation;
	}
	public void setInternalInsulation(final Boolean internalInsulation) {
		this.internalInsulation = internalInsulation;
	}
	
	@Doc({"If this is true, only walls currently recorded as having some external insulation will be allowed.",
		"If this is false, only walls currently recorded as having no external insulation will be allowed.",
		"If it is not specified, any wall which meets the other criteria will be allowed."})
	@BindNamedArgument("has-external-insulation")
	public Boolean getExternalInsulation() {
		return externalInsulation;
	}
	public void setExternalInsulation(final Boolean externalInsulation) {
		this.externalInsulation = externalInsulation;
	}
}
