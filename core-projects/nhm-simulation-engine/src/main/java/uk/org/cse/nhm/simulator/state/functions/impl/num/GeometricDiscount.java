package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.Sets;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITime;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class GeometricDiscount extends AbstractNamed implements IComponentsFunction<Number> {

    private final ITimeDimension time;
    private final IComponentsFunction<Number> rate;
    private final IComponentsFunction<Number> value;

    @AssistedInject
    public GeometricDiscount(
            final ITimeDimension time,
            @Assisted("rate") final IComponentsFunction<Number> rate,
            @Assisted("value") final IComponentsFunction<Number> value) {
        super();
        this.time = time;
        this.rate = rate;
        this.value = value;
    }

    @Override
    public Number compute(final IComponentsScope scope, final ILets lets) {
        // compute the discount rate
        final double rate = this.rate.compute(scope, lets).doubleValue();
        // figure out the year
        final ITime time = scope.get(this.time);
        final double year = time.get(XForesightLevel.Always).getYear() - time.get(XForesightLevel.Never).getYear();
        // compute the discount ratio
        final double ratio = Math.pow(1 + rate, year);
        // compute the value function
        final double value = this.value.compute(scope, lets).doubleValue();
        return value / ratio;
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Sets.union(rate.getDependencies(), value.getDependencies());
    }

    @Override
    public Set<DateTime> getChangeDates() {
        throw new UnsupportedOperationException("geometric discount should not be used in a time-sensitive context");
    }
}
