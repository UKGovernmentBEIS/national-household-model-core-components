package uk.org.cse.nhm.simulation.reporting.state;

import java.util.Set;

import javax.inject.Inject;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.logging.logentry.components.AbstractFuelServiceLogComponent;
import uk.org.cse.nhm.logging.logentry.components.EnergyLogComponent;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;

/**
 * Flattens energy calculator results into a mongoable data structure
 * 
 * @author hinton
 * 
 */
public class EnergyFlattener implements IComponentFlattener {
//	final Map<IDwelling, IEnergyCalculatorResult> previous = new HashMap<IDwelling, IEnergyCalculatorResult>();
	private IDimension<IPowerTable> energy;

	@Inject
	public EnergyFlattener(final IDimension<IPowerTable> energy) {
		this.energy = energy;
	}

	@Override
	public Object flatten(IState state, IDwelling dwelling) {
//		final IEnergyCalculatorResult old = previous.get(dwelling);
		final IPowerTable energyResult = state.get(energy, dwelling);
//		if (energyResult == null || energyResult.equals(old))
//			return null;
//		previous.put(dwelling, energyResult);
		final AbstractFuelServiceLogComponent.MapBuilder b = AbstractFuelServiceLogComponent.MapBuilder.builder();
		
		for (final ServiceType es : ServiceType.values()) {
			for (final FuelType ft : FuelType.values()) {
				if (ft != FuelType.ELECTRICITY) {
					b.put(ft, es, energyResult.getFuelUseByEnergyService(es, ft));
				}
			}

			double totalElectricity = energyResult.getFuelUseByEnergyService(es, FuelType.PEAK_ELECTRICITY)
					+ energyResult.getFuelUseByEnergyService(es, FuelType.OFF_PEAK_ELECTRICITY);
			b.put(FuelType.ELECTRICITY, es, totalElectricity);
		}

		return new EnergyLogComponent(b.build());
	}

	@Override
	public Set<IDimension<?>> getComponents() {
		return ImmutableSet.<IDimension<?>> of(energy);
	}
}
