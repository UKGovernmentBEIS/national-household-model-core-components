package uk.org.cse.nhm.simulator.sequence;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class FailUnless extends AbstractNamed implements IComponentsAction, ISequenceScopeAction {

    private final IComponentsFunction<Boolean> condition;

    @AssistedInject
    public FailUnless(@Assisted final IComponentsFunction<Boolean> condition) {
        super();
        this.condition = condition;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
        return condition.compute(scope, lets);
    }

    @Override
    public boolean isSuitable(IComponentsScope scope, ILets lets) {
        return condition.compute(scope, lets);
    }

    @Override
    public boolean isAlwaysSuitable() {
        return false;
    }
}
