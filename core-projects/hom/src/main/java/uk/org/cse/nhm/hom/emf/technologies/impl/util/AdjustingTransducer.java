package uk.org.cse.nhm.hom.emf.technologies.impl.util;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Iterator;

import org.eclipse.emf.common.util.EList;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public class AdjustingTransducer implements IEnergyTransducer {
	private final EnumSet<EnergyType> inputs;
	private final EnumSet<EnergyType> outputs;
	private final EnumMap<EnergyType, Double> deltas;
	
	public AdjustingTransducer(final EList<FuelType> fuelTypes, final EList<Double> values, final double extraHeatDemand) {
		super();
		deltas = new EnumMap<>(EnergyType.class);
		final Iterator<Double> vi = values.iterator();
		final Iterator<FuelType> fi = fuelTypes.iterator();
		while (vi.hasNext() && fi.hasNext()) {
			final FuelType ft = fi.next();
			final double value = vi.next();
			
			final EnergyType et;
			if (ft == FuelType.ELECTRICITY) {
				et = EnergyType.FuelPEAK_ELECTRICITY;
			} else {
				et = ft.getEnergyType();
			}
			
			if (deltas.containsKey(et)) {
				deltas.put(et, deltas.get(et)+value);
			} else {
				deltas.put(et, value);
			}
		}
		
		if (extraHeatDemand != 0) {
			deltas.put(EnergyType.DemandsHEAT, extraHeatDemand);
		}
		
		inputs = EnumSet.noneOf(EnergyType.class);
		outputs = EnumSet.noneOf(EnergyType.class);
		
		for (final EnergyType et : deltas.keySet()) {
			if (deltas.get(et) < 0) {
				outputs.add(et);
			} else if (deltas.get(et) > 0) {
				inputs.add(et);
			}
		}
	}

	@Override
	public ServiceType getServiceType() {
		return ServiceType.APPLIANCES;
	}

	@Override
	public void generate(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final ISpecificHeatLosses losses, final IEnergyState state) {
		for (final EnergyType et : deltas.keySet()) {
			if (et == EnergyType.DemandsHEAT) {
				if (!parameters.getClimate().isHeatingOn()) continue;
			}
			
			final double delta = deltas.get(et);
			
			if (delta > 0) {
				state.increaseDemand(et, delta);
			} else {
				state.meetSupply(et, -delta);
			}
		}
	}

	@Override
	public int getPriority() {
		return 10;
	}

	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.AfterGains;
	}
}
