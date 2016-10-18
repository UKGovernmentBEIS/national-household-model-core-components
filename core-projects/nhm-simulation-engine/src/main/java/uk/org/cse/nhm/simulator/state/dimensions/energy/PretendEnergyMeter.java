package uk.org.cse.nhm.simulator.state.dimensions.energy;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public class PretendEnergyMeter implements IEnergyMeter {
	private final IPowerTable powerTable;
	
	private PretendEnergyMeter(final IPowerTable powerTable) {
		this.powerTable = powerTable;
	}
	
	public static final IEnergyMeter of(final IPowerTable power) {
		return new PretendEnergyMeter(power);
	}

	@Override
	public double getEnergyUseByFuel(final FuelType fuel) {
		return powerTable.getPowerByFuel(fuel);
	}
}
