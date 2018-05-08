package uk.org.cse.nhm.simulator.action.choices;

import java.util.List;
import java.util.Set;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope.IPicker;
import uk.org.cse.nhm.simulator.scope.PickOption;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;
import uk.org.cse.nhm.simulator.util.RandomSource;

public class FallbackPicker extends LettingPicker {

    private final List<IPicker> delegates;

    @AssistedInject
    public FallbackPicker(
            @Assisted final List<ISequenceSpecialAction> bindings,
            @Assisted final List<IPicker> delegates) {
        super(bindings);
        this.delegates = delegates;
    }

    @Override
    protected PickOption doPick(final RandomSource random, final Set<PickOption> options) {
        for (final IPicker d : delegates) {
            final PickOption pick = d.pick(random, options);
            if (pick != null) {
                return pick;
            }
        }

        return null;
    }
}
