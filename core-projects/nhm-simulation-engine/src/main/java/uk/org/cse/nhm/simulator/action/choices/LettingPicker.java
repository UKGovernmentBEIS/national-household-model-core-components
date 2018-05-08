package uk.org.cse.nhm.simulator.action.choices;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope.IPicker;
import uk.org.cse.nhm.simulator.scope.PickOption;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;
import uk.org.cse.nhm.simulator.util.RandomSource;

abstract class LettingPicker implements IPicker {

    private final List<ISequenceSpecialAction> bindings;

    protected LettingPicker(final List<ISequenceSpecialAction> bindings) {
        this.bindings = ImmutableList.copyOf(bindings);
    }

    @Override
    public final PickOption pick(final RandomSource random, final Set<PickOption> options) {
        if (options.isEmpty()) {
            return null;
        }
        if (bindings.isEmpty()) {
            return doPick(random, options);
        }

        final ImmutableSet.Builder<PickOption> builder = ImmutableSet.builder();

        for (final PickOption option : options) {
            ILets lets = option.lets;
            for (final ISequenceSpecialAction action : bindings) {
                lets = action.reallyApply(option.scope, lets);
            }

            builder.add(option.withBindings(lets));
        }

        return doPick(random, builder.build());
    }

    protected abstract PickOption doPick(final RandomSource random, final Set<PickOption> options);
}
