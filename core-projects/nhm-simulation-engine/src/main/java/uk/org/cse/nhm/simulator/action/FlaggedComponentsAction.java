package uk.org.cse.nhm.simulator.action;

import java.util.ArrayList;
import java.util.List;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.commons.Glob;
import uk.org.cse.commons.names.ISettableIdentified;
import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.action.IUnifiedReport.IRecord;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.components.IFlags;

public class FlaggedComponentsAction implements IComponentsAction, IModifier<IFlags>, ISettableIdentified {

    final IDimension<IFlags> flags;

    final List<Glob> preconditions;
    final List<Glob> postactions;

    final IComponentsAction delegate;

    private List<IUnifiedReport> reports;

    @AssistedInject
    public FlaggedComponentsAction(
            final IDimension<IFlags> flags,
            @Assisted("test") final List<Glob> testFlags,
            @Assisted("update") final List<Glob> updateFlags,
            @Assisted final List<IUnifiedReport> reports,
            @Assisted final IComponentsAction delegate) {
        super();
        this.flags = flags;
        this.reports = reports;
        this.preconditions = testFlags;
        this.postactions = updateFlags;
        this.delegate = delegate;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return delegate.getSourceType();
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
        List<IRecord> before = null;
        if (!reports.isEmpty()) {
            before = new ArrayList<IRecord>();
            for (final IUnifiedReport report : reports) {
                before.add(report.before(getIdentifier().getName(), scope, lets));
            }
        }

        final boolean applied;

        if (testFlags(scope)) {
            applied = delegate.apply(scope, lets);
            if (applied) {
                if (!postactions.isEmpty()) {
                    scope.modify(flags, this);
                }
                if (before != null) {
                    for (final IRecord record : before) {
                        record.after(scope, lets);
                    }
                }
            }
        } else {
            applied = false;
        }

        return applied;
    }

    @Override
    public boolean modify(final IFlags modifiable) {
        return modifiable.modifyFlagsWith(postactions);
    }

    @Override
    public Name getIdentifier() {
        return delegate.getIdentifier();
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return testFlags(scope) && delegate.isSuitable(scope, lets);
    }

    @Override
    public boolean isAlwaysSuitable() {
        return delegate.isAlwaysSuitable() && preconditions.isEmpty();
    }

    private boolean testFlags(final IComponentsScope scope) {
        return preconditions.isEmpty()
                || scope.get(flags).flagsMatch(preconditions);
    }

    @Override
    public void setIdentifier(Name newName) {
        if (delegate instanceof ISettableIdentified) {
            ((ISettableIdentified) delegate).setIdentifier(newName);
        }
    }
}
