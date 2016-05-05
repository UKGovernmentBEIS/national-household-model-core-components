package uk.org.cse.nhm.language.definition.action.measure.renewable;

import javax.validation.constraints.NotNull;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.Unsuitability;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XNumberConstant;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

@Bind("measure.solar-photovoltaic")
@Doc({
	"Installs solar photovoltaic panels. These will generate electricity, which may be used to meet the dwelling's needs.",
	"If the electricity generated is greater than the needs of the house, then it will be sold back to the grid.",
	"Electricity generation is worked out as: efficiency * area of panels installed * mean annual insolation of the region the dwelling is situated in.",
	"Solar photovoltaic always generates peak electricity.",
	"Panels are assumed to be south facing and tilted at 30 degrees (pi / 6 radians)."
})
@Unsuitability({
	"dwelling has no remaining exposed roof area", 
	"dwelling is a flat", 
	"dwelling has an existing solar photovoltaic system"
	})
public class XSolarPhotovoltaicMeasure extends XMeasure {
	public static final class P {
		public static final String efficiency = "efficiency";
		public static final String roofCoverage = "roofCoverage";
		public static final String capex = "capex";
		public static final String ownUseProportion = "ownUseProportion";
	}
	
	private XNumber efficiency;
	private XNumber roofCoverage;
	private XNumber capex;
	private XNumber ownUseProportion = defaultOwnUseProportion();
	
	@Prop(P.efficiency)
	@Doc("The efficiency with which the solar panels will convert photons into electricity.")
	@BindNamedArgument
	@NotNull(message = "neasure.solar-photovoltaic must specify an efficiency")
	public XNumber getEfficiency() {
		return efficiency;
	}
	
	private static XNumber defaultOwnUseProportion() {
		final XNumberConstant result = new XNumberConstant();
		result.setValue(0.5);
		return result;
	}

	public void setEfficiency(final XNumber efficiency) {
		this.efficiency = efficiency;
	}
	
	@Prop(P.roofCoverage)
	@BindNamedArgument("roof-coverage")
	@NotNull(message = "measure.solar-photovoltaic must specify a roof-coverage")
	@Doc({
		"The proportion of the roof which will be covered with solar panels.",
		"If this proportion of the roof is not available, for example because of an existing solar photovoltaic or solar thermal installation, then all of the remaining area will be covered instead."
		})
	public XNumber getRoofCoverage() {
		return roofCoverage;
	}
	
	public void setRoofCoverage(final XNumber roofCoverage) {
		this.roofCoverage = roofCoverage;
	}

	@NotNull(message = "measure.solar-photovoltaic must specify a capex")
	@Prop(P.capex)
	@Doc({
		"The function which will be used to determine the capital expenditure spent on the installation.",
		"This may be a function of the total size installed (size.m2), or any other house properties."
		})
	@BindNamedArgument
	public XNumber getCapex() {
		return capex;
	}

	public void setCapex(final XNumber capex) {
		this.capex = capex;
	}

	@Prop(P.ownUseProportion)
	@BindNamedArgument("own-use-proportion")
	@Doc({"The proportion of electricity that is generated when the house is consuming electricity.",
		"This fraction of the generated amount will be taken from the peak electricity import requirement",
		"from the grid.",
		"Any remaining generation will be exported to the grid, potentially at a different price."
	})
	public XNumber getOwnUseProportion() {
		return ownUseProportion;
	}

	public void setOwnUseProportion(final XNumber ownUseProportion) {
		this.ownUseProportion = ownUseProportion;
	}
}
