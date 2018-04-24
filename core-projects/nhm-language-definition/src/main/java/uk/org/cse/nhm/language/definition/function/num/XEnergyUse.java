package uk.org.cse.nhm.language.definition.function.num;

import com.larkery.jasb.bind.Bind;
import com.larkery.jasb.bind.BindNamedArgument;

import uk.org.cse.nhm.language.definition.Doc;
import uk.org.cse.nhm.language.definition.context.calibration.IEnergyFunction;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.enums.XServiceType;

@Doc("The annual energy use (in kWh) for a house.")
@Bind("house.energy-use")
public class XEnergyUse extends XHouseNumber implements IEnergyFunction {
	public static final class P {
		public static final String byFuel = "byFuel";
		public static final String byService = "byService";
		public static final String calibrated = "calibrated";
	}
	
	private XFuelType byFuel = null;
	private XServiceType byService = null;
	private boolean calibrated = true;
	
	@BindNamedArgument("by-fuel")
	@Doc("If set, yields the energy used of the given fuel type.")
	public XFuelType getByFuel() {
		return byFuel;
	}
	public void setByFuel(final XFuelType byFuel) {
		this.byFuel = byFuel;
	}
	
	@Doc("If set, yields the energy used by the given service.")
	@BindNamedArgument("by-service")
	public XServiceType getByService() {
		return byService;
	}
	public void setByService(final XServiceType byService) {
		this.byService = byService;
	}
	
	@Doc("If set to false, this will return the uncalibrated energy result. If used within a calibration rule, this is forced to false.")
	@BindNamedArgument("calibrated")
	public boolean isCalibrated() {
		return calibrated;
	}
	public void setCalibrated(final boolean calibrated) {
		this.calibrated = calibrated;
	}
}
