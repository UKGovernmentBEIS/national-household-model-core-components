package uk.org.cse.nhm.simulator.action;

import com.google.common.base.Optional;
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

public class RepeatAction extends AbstractNamed implements IComponentsAction {

    private final int maxIterations;
    private final IComponentsAction delegate;
    private final IComponentsFunction<Boolean> performIf;
    private final IComponentsFunction<Boolean> stopIf;

    @AssistedInject
    public RepeatAction(
            @Assisted final int maxIterations,
            @Assisted final IComponentsAction delegate,
            @Assisted("while") final Optional<IComponentsFunction<Boolean>> performIf,
            @Assisted("until") final Optional<IComponentsFunction<Boolean>> stopIf
    ) {
        this.maxIterations = maxIterations;
        this.delegate = delegate;
        this.performIf = performIf.orNull();
        this.stopIf = stopIf.orNull();
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
        int remainingIterations = Math.max(maxIterations, 0);

        while (remainingIterations > 0) {
            remainingIterations--;

            if (performIf != null) {
                if (!performIf.compute(scope, lets)) {
                    break;
                }
            }

            scope.apply(delegate, lets);

            if (stopIf != null) {
                if (stopIf.compute(scope, lets)) {
                    break;
                }
            }
        }

        return true;
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return true;
    }

    @Override
    public boolean isAlwaysSuitable() {
        return true;
    }
}
