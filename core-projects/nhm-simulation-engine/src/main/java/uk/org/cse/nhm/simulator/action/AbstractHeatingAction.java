package uk.org.cse.nhm.simulator.action;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.behaviour.IHeatingBehaviour;

/**
 * Base class for measures which poke the {@link IHeatingBehaviour} dimension.
 *
 * @author hinton
 *
 */
abstract class AbstractHeatingAction extends AbstractNamed implements IComponentsAction {

    protected final IDimension<IHeatingBehaviour> heatingDimension;

    AbstractHeatingAction(final IDimension<IHeatingBehaviour> heatingDimension) {
        this.heatingDimension = heatingDimension;
    }

    @Override
    public final boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
        doApply(scope, lets);
        return true;
    }

    abstract protected void doApply(final ISettableComponentsScope scope, final ILets lets);

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public final boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return true;
    }

    @Override
    public final boolean isAlwaysSuitable() {
        return true;
    }
}
