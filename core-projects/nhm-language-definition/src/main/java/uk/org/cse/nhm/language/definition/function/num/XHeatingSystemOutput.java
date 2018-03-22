package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.context.calibration.IEnergyFunction;
import uk.org.cse.nhm.language.definition.enums.XHeatingSystem;

@Doc({"The annual heating system output (in kWh annually) for a house."})
@Bind("house.heating-system-output")
public class XHeatingSystemOutput extends XHouseNumber implements IEnergyFunction {
	private XHeatingSystem of = null;

	@BindNamedArgument
	@Doc({"If specified, get the heat output from the given system in kWh/year.",
		"",
		"Otherwise, get the total space and water heating system output."})
	public XHeatingSystem getOf() {
		return of;
	}

	public void setOf(XHeatingSystem of) {
		this.of = of;
	}
}
