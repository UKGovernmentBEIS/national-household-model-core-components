package uk.org.cse.nhm.language.definition.action.measure.heating;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.action.XMeasure;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.function.num.XNumber;
import uk.org.cse.nhm.language.definition.function.num.XSizingFunction;
import uk.org.cse.nhm.language.validate.efficiency.EfficiencyRequired;

@EfficiencyRequired

@Bind("measure.room-heater")
@Doc({"Installs a room heater. This counts as secondary heating: it is a space heater which it not connected to the central heating system. It does not provide domestic hot water."})
public class XRoomHeaterMeasure extends XMeasure {
	public static class P {
		public static final String FUEL = "fuel";
		public static final String EFFICIENCY = "efficiency";
		public static final String REPLACE_EXISTING = "replaceExisting";
		public static final String SIZING = "sizing";
		public static final String CAPEX = "capex";
		public static final String OPEX = "opex";
	}
	
	private XFuelType fuel;
	private Double efficiency;
	private boolean replaceExisting = false;
	
	private XSizingFunction sizing;
	private XNumber capex;
	private XNumber opex;
	
	
@BindNamedArgument
	@Prop(P.FUEL)
	@NotNull(message = "measure.room-heater must specify a fuel.")
	@Doc("The fuel which will be consumed by this heater to create heat.")
	public XFuelType getFuel() {
		return fuel;
	}
	
	public void setFuel(final XFuelType fuel) {
		this.fuel = fuel;
	}
	
	
@BindNamedArgument
	@Prop(P.EFFICIENCY)
	@Doc("The efficiency of a heater as a proportion. This will be ignored if the fuel type is electricity (since electricity is aways 1.0 efficient).")
	public Double getEfficiency() {
		return efficiency;
	}
	
	public void setEfficiency(final Double efficiency) {
		this.efficiency = efficiency;
	}

@BindNamedArgument("replace-existing")
	@Prop(P.REPLACE_EXISTING)
	@Doc({"If this is set to true, any existing secondary space heaters will also be removed and replaced with the new room heater.", 
		"If this is set to false, the new room heater will only be installed in houses which do not already have a secondary heater."})
	public boolean getReplaceExisting() {
		return replaceExisting;
	}
	
	public void setReplaceExisting(final boolean replaceExisting) {
		this.replaceExisting = replaceExisting;
	}
	
	@Prop(P.SIZING)
	
	@BindNamedArgument("size")
	@Doc("Contains the measure's sizing function")
	@NotNull(message = "measure.room-heater must always declare a sizing function")
	public XSizingFunction getSizing() {
		return sizing;
	}
	public void setSizing(final XSizingFunction sizing) {
		this.sizing = sizing;
	}
	
	@Prop(P.CAPEX)
	@BindNamedArgument
	@Doc("Contains the measure's capex function")
	@NotNull(message = "measure.room-heater must always declare a capex function")
	public XNumber getCapex() {
		return capex;
	}
	public void setCapex(final XNumber capex) {
		this.capex = capex;
	}

	@Prop(P.OPEX)
	@BindNamedArgument
	@Doc("Contains the measure's opex function")
	public XNumber getOpex() {
		return opex;
	}

	public void setOpex(final XNumber opex) {
		this.opex = opex;
	}
}
