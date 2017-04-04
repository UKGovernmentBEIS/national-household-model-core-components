package uk.org.cse.nhm.simulator.state.dimensions.energy;

import org.joda.time.DateTime;
import org.joda.time.Duration;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public class EnergyMeter implements IEnergyMeter {
	private final double[] energyUseByFuel = new double[FuelType.values().length];

	private final DateTime readingDate;
	private final IPowerTable powerTableWhenRead;
	private final int resetCounter;
	
	private EnergyMeter(final DateTime readingDate, final IPowerTable powerTableWhenRead, int resetCounter) {
		this.readingDate = readingDate;
		this.powerTableWhenRead = powerTableWhenRead;
		this.resetCounter = resetCounter;
	}

	@Override
	public double getEnergyUseByFuel(final FuelType fuel) {
		return energyUseByFuel[fuel.ordinal()];
	}

	public IEnergyMeter integrateAndUpdate(final DateTime now, final IPowerTable powerTable) {
		final EnergyMeter next = new EnergyMeter(now, powerTable, resetCounter);
				
		final long standardSeconds = new Duration(readingDate, now).getStandardSeconds();
		
		final double proportionOfYear = standardSeconds / (365.25 * 86400);
		
		for (final FuelType ft : FuelType.values()) {
			final int ordinal = ft.ordinal();
			next.energyUseByFuel[ordinal] = energyUseByFuel[ordinal] + 
					(proportionOfYear * powerTableWhenRead.getPowerByFuel(ft));
		}
		
		return next;
	}

	public static IEnergyMeter start(final DateTime now, final IPowerTable powerTable) {
		return new EnergyMeter(now, powerTable, 0);
	}
	
	public IEnergyMeter reset(final DateTime now, final IPowerTable powerTable, int resetCounter) {
		return new EnergyMeter(now, powerTable, resetCounter);
	}
	
	public int getResetCounter() {
		return resetCounter;
	}
}
