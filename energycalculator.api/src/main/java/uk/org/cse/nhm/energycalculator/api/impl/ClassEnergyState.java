package uk.org.cse.nhm.energycalculator.api.impl;

import java.util.EnumMap;
import java.util.Map;

import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;

/**
 * An {@link IEnergyState} which works with the {@link EnergyType} just introduced that is not an enum.
 * @author hinton
 *
 * @since 1.0.0
 */
public class ClassEnergyState implements IEnergyState {
	private final DoubleMap<EnergyType> byType = 
			new DoubleMap<EnergyType>(EnergyType.class, 2);
	
	private final Map<ServiceType, DoubleMap<EnergyType>> byService = 
			new EnumMap<ServiceType, DoubleMap<EnergyType>>(ServiceType.class);
	
	private DoubleMap<EnergyType> forCurrentService;
	
	private static final int SUPPLY = 0;
	private static final int DEMAND = 1;
	
	/**
	 * @since 1.0.0
	 */
	public ClassEnergyState() {
		for (final ServiceType t : ServiceType.values()) {
			byService.put(t, new DoubleMap<EnergyType>(EnergyType.class, 2));
		}
		
		setCurrentServiceType(ServiceType.INTERNALS, "None");
	}
	
	@Override
	public void setCurrentServiceType(final ServiceType serviceType, final String name) {
		forCurrentService = byService.get(serviceType);
	}

	@Override
	public double getUnsatisfiedDemand(final EnergyType energy) {
		return Math.max(0, byType.getDifference(energy, DEMAND, SUPPLY));
	}

	@Override
	public void increaseSupply(final EnergyType energy, final double amount) {
		byType.increment(energy, SUPPLY, amount);
		forCurrentService.increment(energy, SUPPLY, amount);
	}

	@Override
	public void increaseDemand(final EnergyType energy, final double amount) {
		byType.increment(energy, DEMAND, amount);
		forCurrentService.increment(energy, DEMAND, amount);
	}

	@Override
	public double getTotalSupply(final EnergyType energy) {
		return byType.get(energy, SUPPLY);
	}

	@Override
	public double getTotalSupply(final EnergyType energy, final ServiceType service) {
		return byService.get(service).get(energy, SUPPLY);
	}

	@Override
	public double getTotalDemand(final EnergyType energyType, final ServiceType serviceType) {
		return byService.get(serviceType).get(energyType, DEMAND);
	}

	@Override
	public double getTotalDemand(final EnergyType energy) {
		return byType.get(energy, DEMAND);
	}

	@Override
	public void increaseElectricityDemand(final double highRateFraction,final double amount) {
		increaseDemand(EnergyType.FuelPEAK_ELECTRICITY, highRateFraction * amount);
		increaseDemand(EnergyType.FuelOFFPEAK_ELECTRICITY, (1 - highRateFraction) * amount);
	}
	
	@Override
	public String toString() {
		return byService + "";
	}
	
	@Override
	public double getExcessSupply(final EnergyType energyType) {
		return Math.max(0, byType.getDifference(energyType, SUPPLY, DEMAND));
	}
	
	@Override
	public double getBoundedTotalDemand(final EnergyType energy,
			final double proportionOfTotal) {
		return Math.min(
				getUnsatisfiedDemand(energy),
				getTotalDemand(energy) * proportionOfTotal);
	}
	
	@Override
	public double getBoundedTotalHeatDemand(final double proportionOfTotal) {
		final double heatAfterGains = getTotalDemand(EnergyType.DemandsHEAT) - getTotalSupply(EnergyType.DemandsHEAT, ServiceType.INTERNALS);
		return Math.min(
				getUnsatisfiedDemand(EnergyType.DemandsHEAT),
				heatAfterGains * proportionOfTotal);
	}
	
	@Override
	public void meetSupply(final EnergyType et, final double delta) {
		final double d = getUnsatisfiedDemand(et);
		increaseSupply(et, Math.min(delta, d));
	}
}
