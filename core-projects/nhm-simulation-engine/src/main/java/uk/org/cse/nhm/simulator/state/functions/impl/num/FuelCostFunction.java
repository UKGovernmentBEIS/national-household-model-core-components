package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IEnergyMeter;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.energy.PretendEnergyMeter;
import uk.org.cse.nhm.simulator.state.dimensions.energy.calibration.ModifiedPowerTable;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.IExtraCharge;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariff;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariffs;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.transactions.IPayment;

public class FuelCostFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private final IDimension<ITariffs> tariffs;
	private final Optional<FuelType> fuelType;
	private final Set<IDimension<?>> allDimensions;
	private final IDimension<IEnergyMeter> meterDimension;
	private final IDimension<IPowerTable> powerDimension;
	private final Set<ServiceType> excludedServices;
	
	
	@Inject
	public FuelCostFunction(
			final Set<IDimension<?>> allDimensions,
			final IDimension<IEnergyMeter> meterDimension,
			final IDimension<IPowerTable> powerDimension,
			final IDimension<ITariffs> tariffs,
			@Assisted final Optional<FuelType> fuelType,
			@Assisted final Set<ServiceType> excludedServices
			)
			{
		this.allDimensions = allDimensions;
		this.meterDimension = meterDimension;
		this.powerDimension = powerDimension;
		this.tariffs = tariffs;
		this.fuelType = fuelType;
		this.excludedServices = excludedServices;
	}

    private static void runTariff(final ITariffs tariffs,
                                  final IHypotheticalComponentsScope hypothesis,
                                  final ILets lets,
                                  final FuelType fuelType) {
        final ITariff tariff = tariffs.getTariff(fuelType);
			
        tariff.apply(fuelType, hypothesis);
			
        for (final IExtraCharge charge : tariffs.getExtraCharges(fuelType)) {
            charge.apply(hypothesis, lets);
        }
    }
    
    /*
	BEISDOC
	NAME: Fuel cost
	DESCRIPTION: The cost of each type of fuel consumed by the dwelling, computed using the tariffs written by the scenario author. A unit of fuel is a kWh here.  
	TYPE: scenario-element
	UNIT: £
	SAP: Table 12, Section 10a
	DEPS: total-fuel-energy-demand
	GET: house.fuel-cost
	NOTES: Not part of the energy calculator.
	NOTES: To use SAP Table 12 fuel prices, you must specify them as a tariff in your NHM scenario.  
	ID: fuel-cost
	CODSIEB
	*/
	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		final ITariffs tariffs = scope.get(this.tariffs);
		final IHypotheticalComponentsScope hypothesis = scope.createHypothesis();
		
		final IPowerTable power;
		
		if (excludedServices.isEmpty()) {
			power = scope.get(powerDimension);
		} else {
			power = ModifiedPowerTable.excludingEnergyServices(scope.get(powerDimension), excludedServices);
		}
		
		hypothesis.imagine(meterDimension, PretendEnergyMeter.of(power));
		
		if (fuelType.isPresent()) {
            final FuelType ft = fuelType.get();
            if (ft == FuelType.ELECTRICITY) {
                runTariff(tariffs, hypothesis, lets, FuelType.PEAK_ELECTRICITY);
                runTariff(tariffs, hypothesis, lets, FuelType.OFF_PEAK_ELECTRICITY);
            } else {
                runTariff(tariffs, hypothesis, lets, ft);
            }
			
			double sum = 0;
			for (final IPayment p : hypothesis.getAllNotes(IPayment.class)) {
				sum += p.getAmount();
			}
            
			return sum;
		} else {
			tariffs.computeCharges(hypothesis, lets);
			
			double sum = 0;
			for (final IPayment p : hypothesis.getAllNotes(IPayment.class)) {
				sum += p.getAmount();
			}
			return sum;
		}
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return allDimensions;
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}
