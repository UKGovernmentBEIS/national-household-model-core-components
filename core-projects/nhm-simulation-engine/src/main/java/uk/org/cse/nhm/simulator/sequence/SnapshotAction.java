package uk.org.cse.nhm.simulator.sequence;

import java.util.Collections;
import java.util.List;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class SnapshotAction extends AbstractNamed implements IComponentsAction, ISequenceSpecialAction {

    private final String name;
    private final List<IComponentsAction> delegates;

    @AssistedInject
    public SnapshotAction(@Assisted final String name, @Assisted final List<IComponentsAction> delegates) {
        super();
        this.name = name;
        this.delegates = delegates;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
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

    @Override
    public ILets reallyApply(final IComponentsScope scope, final ILets lets) {
        final IHypotheticalComponentsScope h = scope.createHypothesis();

        for (final IComponentsAction d : delegates) {
            h.apply(d, lets);
        }

        return lets.withBinding(name, h);
    }

    @Override
    public ILets reallyApply(final ISettableComponentsScope scope, final ILets lets) {
        return reallyApply((IComponentsScope) scope, lets);
    }

    @Override
    public Iterable<? extends DateTime> getChangeDates() {
        return Collections.emptySet();
    }

    @Override
    public Iterable<? extends IDimension<?>> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public boolean needsIsolation() {
        return true;
    }
}
