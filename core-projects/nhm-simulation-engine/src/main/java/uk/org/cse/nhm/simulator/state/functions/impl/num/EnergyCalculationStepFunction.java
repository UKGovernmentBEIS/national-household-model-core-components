package uk.org.cse.nhm.simulator.state.functions.impl.num;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;
import org.joda.time.DateTime;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

import java.util.Collections;
import java.util.Optional;
import java.util.Set;

public class EnergyCalculationStepFunction extends AbstractNamed implements IComponentsFunction<Double> {
    private final IDimension<IPowerTable> energy;
    private final EnergyCalculationStep step;
    private final Optional<Integer> month;

    @AssistedInject
    public EnergyCalculationStepFunction(final IDimension<IPowerTable> energy,
                                         @Assisted final EnergyCalculationStep step,
                                         @Assisted final Optional<Integer> month) {
        this.energy = energy;
        this.step = step;
        this.month = month;

        if (step.isSkipped()) {
            throw new RuntimeException("Attempted to get the value for an EnergyCalculationStep which is skipped. This should not be allowed: " + step);
        }

        if (month.isPresent() && !step.isMonthly()) {
            throw new RuntimeException("Attempted to get the monthly value for an EnergyCalculationStep which is only calculated annually: " + step);
        }
    }

    @Override
    public Double compute(IComponentsScope scope, ILets lets) {
        IPowerTable power = scope.get(energy);

        if (month.isPresent()) {
            return power.readStepMonthly(step, month.get());
        } else {
            return power.readStepAnnual(step);
        }
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return ImmutableSet.of(energy);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }
}
