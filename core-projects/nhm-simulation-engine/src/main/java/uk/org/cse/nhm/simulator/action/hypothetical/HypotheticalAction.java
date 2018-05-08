package uk.org.cse.nhm.simulator.action.hypothetical;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

abstract class HypotheticalAction extends AbstractNamed implements IComponentsAction {

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public final boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
        if (isSuitable(scope, lets)) {
            return doApply((IHypotheticalComponentsScope) scope, lets);
        } else {
            return false;
        }
    }

    protected abstract boolean doApply(final IHypotheticalComponentsScope scope, final ILets lets);

    @Override
    public final boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return scope instanceof IHypotheticalComponentsScope && doIsSuitable(scope);
    }

    @Override
    public final boolean isAlwaysSuitable() {
        return false;
    }

    protected boolean doIsSuitable(final IComponentsScope scope) {
        return true;
    }
}
