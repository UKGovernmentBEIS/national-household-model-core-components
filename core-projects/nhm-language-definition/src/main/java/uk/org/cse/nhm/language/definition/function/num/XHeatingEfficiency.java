package uk.org.cse.nhm.language.definition.function.num;

import javax.validation.constraints.NotNull;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.enums.XHeatingSystem;

@Bind("house.heating-efficiency")
@Doc({
	"The efficiency of a heating system within the house.",
	"If the system is absent or not functioning, an efficiency of 0 will be returned.",
	"Multiple heat sources may exist for a Central Hot Water system. In this case, the primary source's efficiency will be used if it exists."
})
public class XHeatingEfficiency extends XHouseNumber {
	public static class P {
		public static final String of = "of";
		public static final String measurement = "measurement";
	}
	
	private XHeatingSystem of;
	private XEfficiencyMeasurement measurement = XEfficiencyMeasurement.Winter;

	@BindNamedArgument
	@NotNull(message = "house.heating-efficiency must have an 'of' attribute.")
	@Prop(P.of)
	@Doc("Location of the heating system to lookup the efficiency for.")
	public XHeatingSystem getOf() {
		return of;
	}

	public void setOf(final XHeatingSystem of) {
		this.of = of;
	}
	
	@BindNamedArgument
	@NotNull(message = "house.heating-efficiency must have a 'measurement' attribute.")
	@Prop(P.measurement)
	@Doc("Determines how we measure the efficiency.")
	public XEfficiencyMeasurement getMeasurement() {
		return measurement ;
	}

	public void setMeasurement(final XEfficiencyMeasurement measurement) {
		this.measurement = measurement;
	}
}
