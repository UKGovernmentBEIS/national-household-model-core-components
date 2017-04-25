package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class EnergyUseFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private final IDimension<IPowerTable> energy;
	private final Optional<FuelType> fuelType;
	private final Optional<List<ServiceType>> serviceType;
	
	@Inject
	public EnergyUseFunction(
			final IDimension<IPowerTable> energy,
			@Named("uncalibrated") final IDimension<IPowerTable> uncalibrated,
			@Assisted final boolean isCalibrated,
			@Assisted final Optional<FuelType> fuelType,
			@Assisted final Optional<List<ServiceType>> serviceType
			)
			{
		this.energy = isCalibrated ? energy : uncalibrated;
		this.fuelType = fuelType;
		this.serviceType = serviceType;
	}

	@SuppressWarnings("unchecked")
    @Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
        return compute(scope.get(energy), fuelType, serviceType);
	}

    private static double compute(final IPowerTable table,
                                  final Optional<FuelType> fuelType,
                                  final Optional<List<ServiceType>> serviceType) {
        if (fuelType.isPresent()) {
            final FuelType ft = fuelType.get();
            if (ft == FuelType.ELECTRICITY) {
                // electricity has a special case, because it is zero in the table
                // to prevent doublecounting with peak and off peak.
                // however, if the user has asked for electricity, we make the sum for them
                if (serviceType.isPresent()) {
                    return
                        table.getFuelUseByEnergyService(serviceType.get(), FuelType.PEAK_ELECTRICITY) +
                        table.getFuelUseByEnergyService(serviceType.get(), FuelType.OFF_PEAK_ELECTRICITY);
                } else {
                    return
                        table.getPowerByFuel(FuelType.PEAK_ELECTRICITY) +
                        table.getPowerByFuel(FuelType.OFF_PEAK_ELECTRICITY);
                }
            } else {
                if (serviceType.isPresent()) {
                    return (double) table.getFuelUseByEnergyService(serviceType.get(), fuelType.get());
                } else {
                    return (double) table.getPowerByFuel(fuelType.get());
                }
            }
        } else if (serviceType.isPresent()) {
            double acc = 0;
			final List<ServiceType> st = serviceType.get();
			for (final FuelType ft : FuelType.values()) {
				acc += table.getFuelUseByEnergyService(st, ft);
			}
			return acc;
        } else {
            double acc = 0;
			for (final FuelType ft : FuelType.values()) {
				acc += table.getPowerByFuel(ft);
			}
			return acc;
        }
    }
    
	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(energy);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}
