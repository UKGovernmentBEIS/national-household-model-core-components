package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IEnergyMeter;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MeterReadingFunction extends AbstractNamed implements IComponentsFunction<Double> {
	final IDimension<IEnergyMeter> meter;
	final FuelType fuelType;
	
	@AssistedInject
	public MeterReadingFunction(final IDimension<IEnergyMeter> meter, @Assisted final FuelType fuelType) {
		this.meter = meter;
		this.fuelType = fuelType;
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		return scope.get(meter).getEnergyUseByFuel(fuelType);
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(meter);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		throw new UnsupportedOperationException("The meter reading should not be used in a time-sensitive function (like the weather definition).");
	}
}
