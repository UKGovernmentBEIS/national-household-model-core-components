package uk.org.cse.nhm.simulator.action.choices;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope.IPicker;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class ChoiceAction extends AbstractNamed implements IComponentsAction {

    private final IPicker selector;
    private final Set<IComponentsAction> alternatives;

    @AssistedInject
    public ChoiceAction(
            @Assisted final IPicker selector,
            @Assisted final List<IComponentsAction> alternatives) {
        this.selector = selector;
        this.alternatives = ImmutableSet.copyOf(alternatives);
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets)
            throws NHMException {
        return scope.apply(alternatives, lets, selector);
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        for (final IComponentsAction a : alternatives) {
            if (a.isSuitable(scope, lets)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAlwaysSuitable() {
        //TODO could in-principle work this out
        return false;
    }
}
