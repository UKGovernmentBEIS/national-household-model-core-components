package uk.org.cse.nhm.simulator.action.choices;

import java.util.List;
import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.scope.PickOption;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.util.RandomSource;

public class MaximumPicker extends LettingPicker {

    private final IComponentsFunction<Number> objective;

    @AssistedInject
    public MaximumPicker(
            @Assisted final List<ISequenceSpecialAction> bindings,
            @Assisted final IComponentsFunction<Number> objective) {
        super(bindings);
        this.objective = objective;
    }

    @Override
    protected PickOption doPick(final RandomSource random, final Set<PickOption> options) {
        PickOption best = null;
        double max = Double.NEGATIVE_INFINITY;
        for (final PickOption option : options) {
            final double current = objective.compute(option.scope, option.lets).doubleValue();
            if (current > max) {
                max = current;
                best = option;
            }
        }

        return best;
    }
}
