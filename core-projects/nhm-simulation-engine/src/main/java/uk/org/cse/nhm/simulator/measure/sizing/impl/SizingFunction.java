package uk.org.cse.nhm.simulator.measure.sizing.impl;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.measure.ISizingResult;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.measure.impl.SizingResult;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class SizingFunction implements ISizingFunction {

    private final IComponentsFunction<Number> value;
    private final double min;
    private final double max;

    @AssistedInject
    public SizingFunction(
            @Assisted final IComponentsFunction<Number> value,
            @Assisted("min") final double min,
            @Assisted("max") final double max) {
        if (min >= max) {
            throw new IllegalArgumentException("Sizing function min must be less than max.");
        }
        this.value = value;
        this.min = min;
        this.max = max;
    }

    @Override
    public ISizingResult computeSize(final IComponentsScope scope, final ILets lets, final Units units) {
        final Double result = value.compute(scope, lets).doubleValue();
        if (result < min || result > max) {
            return SizingResult.unsuitable(result, units);
        } else {
            return SizingResult.suitable(result, units);
        }
    }
}
