package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

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
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.IEmissions;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class CarbonEmissionsFunction extends AbstractNamed implements IComponentsFunction<Double> {

    private final IDimension<IEmissions> costs;
    private final Optional<FuelType> fuelType;
    private final Optional<List<ServiceType>> serviceType;

    @Inject
    public CarbonEmissionsFunction(
            final IDimension<IEmissions> costs,
            @Assisted final Optional<FuelType> fuelType,
            @Assisted final Optional<List<ServiceType>> serviceType
    ) {
        this.costs = costs;
        this.fuelType = fuelType;
        this.serviceType = serviceType;
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        /*
		BEISDOC
		NAME: Emissions
		DESCRIPTION: The CO2 equivalent emissions produced by the dwelling.
		TYPE: formula
		UNIT: Unit of carbon (unit is the same as that for carbon factors without the denominator)
		SAP: (272)
                SAP_COMPLIANT: No - user defined
                BREDEM_COMPLIANT: N/A - out of scope
		DEPS: total-fuel-energy-demand,carbon-factors
		GET: house.emissions
		ID: carbon-emissions
		CODSIEB
         */

        final IEmissions calc = scope.get(costs);

        if (fuelType.isPresent()) {
            final FuelType ft = fuelType.get();
            if (ft == FuelType.ELECTRICITY) {
                if (serviceType.isPresent()) {
                    return new Double(calc.getAnnualEmissions(FuelType.PEAK_ELECTRICITY, serviceType.get())
                            + calc.getAnnualEmissions(FuelType.OFF_PEAK_ELECTRICITY, serviceType.get()));
                } else {
                    double acc = 0;

                    for (final ServiceType st : ServiceType.values()) {
                        acc
                                += calc.getAnnualEmissions(FuelType.PEAK_ELECTRICITY, st)
                                + calc.getAnnualEmissions(FuelType.OFF_PEAK_ELECTRICITY, st);
                    }

                    return acc;
                }
            } else {
                if (serviceType.isPresent()) {
                    return (double) calc.getAnnualEmissions(fuelType.get(), serviceType.get());
                } else {
                    double acc = 0;

                    for (final ServiceType st : ServiceType.values()) {
                        acc += calc.getAnnualEmissions(ft, st);
                    }

                    return acc;
                }
            }
        } else if (serviceType.isPresent()) {
            double acc = 0;
            final List<ServiceType> st = serviceType.get();
            for (final FuelType ft : FuelType.values()) {
                acc += calc.getAnnualEmissions(ft, st);
            }
            return acc;
        } else {
            double acc = 0;
            for (final FuelType ft : FuelType.values()) {
                for (final ServiceType st : ServiceType.values()) {
                    acc += calc.getAnnualEmissions(ft, st);
                }
            }
            return acc;
        }
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return ImmutableSet.<IDimension<?>>of(costs);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }
}
