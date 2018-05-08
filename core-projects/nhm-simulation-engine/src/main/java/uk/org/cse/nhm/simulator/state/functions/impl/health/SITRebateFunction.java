package uk.org.cse.nhm.simulator.state.functions.impl.health;

import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.Sets;
import com.google.inject.assistedinject.Assisted;

import uk.ac.ucl.hideem.IHealthModule;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class SITRebateFunction extends AbstractNamed implements IComponentsFunction<Number> {

    final IComponentsFunction<Number> temperature;
    final IComponentsFunction<Number> rebate;
    final IHealthModule healthModule;

    @Inject
    public SITRebateFunction(@Assisted("temperature") final IComponentsFunction<Number> temperature,
            @Assisted("rebate") final IComponentsFunction<Number> rebate,
            final IHealthModule healthModule) {
        super();
        this.temperature = temperature;
        this.rebate = rebate;
        this.healthModule = healthModule;
    }

    @Override
    public Number compute(final IComponentsScope scope, final ILets lets) {
        final double baseTemperature = temperature.compute(scope, lets).doubleValue();
        final double rebateAmount = rebate.compute(scope, lets).doubleValue();

        // if we have computed a function which looks like
        // cost -> T
        // then given T we can go to cost, then we can go to cost + rebate, and then we can get T
        // this is not exactly right, but it should do the trick.
        // It is best to put this in the health module as it's part of that really
        final double deltaTemperature
                = healthModule.getRebateDeltaTemperature(baseTemperature, rebateAmount);

        return baseTemperature + deltaTemperature;
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Sets.union(temperature.getDependencies(), rebate.getDependencies());
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Sets.union(temperature.getChangeDates(), rebate.getChangeDates());
    }
}
