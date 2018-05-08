package uk.org.cse.nhm.simulator.groups.impl;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.groups.IDwellingGroup;
import uk.org.cse.nhm.simulator.groups.IDwellingGroupListener;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * This is a group which uses a function which takes some components to a
 * boolean to evaluate whether or not it should contain a particular dwelling.
 *
 * @author hinton
 *
 */
public class FunctionDwellingGroup extends BaseDwellingGroup implements IDwellingGroup {

    private IComponentsFunction<Boolean> function;
    private IDwellingGroup source;

    private final ICanonicalState state;

    private final IDwellingGroupListener groupListener = new IDwellingGroupListener() {
        @Override
        public void dwellingGroupChanged(final IStateChangeNotification cause, final IDwellingGroup source, final Set<IDwelling> added, final Set<IDwelling> removed) {
            if (source == FunctionDwellingGroup.this.source) {
                if (FunctionDwellingGroup.this.function == null) {
                    return;
                }

                final LinkedHashSet<IDwelling> toAdd = new LinkedHashSet<IDwelling>();
                final LinkedHashSet<IDwelling> toRemove = new LinkedHashSet<IDwelling>(removed);

                for (final IDwelling d : added) {
                    if (FunctionDwellingGroup.this.function.compute(state.detachedScope(d), ILets.EMPTY)) {
                        toAdd.add(d);
                    }
                }

                FunctionDwellingGroup.this.update(cause, toAdd, toRemove);
            }
        }
    };

    private final IStateListener stateListener = new IStateListener() {
        @Override
        public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
            if (function != null) {
                final Set<IDwelling> changedDwellings = notification.getChangedDwellings(function.getDependencies());
                final LinkedHashSet<IDwelling> toAdd = new LinkedHashSet<IDwelling>();
                final LinkedHashSet<IDwelling> toRemove = new LinkedHashSet<IDwelling>();

                consider(changedDwellings, toAdd, toRemove);

                FunctionDwellingGroup.this.update(notification, toAdd, toRemove);
            }
        }
    };

    @Inject
    public FunctionDwellingGroup(final ICanonicalState state,
            @Assisted final IDwellingGroup source,
            @Assisted final IComponentsFunction<Boolean> function
    ) {
        super();
        this.state = state;
        state.addStateListener(stateListener);
        setSource(source);
        setFunction(function);
    }

    private <T> void setFunction(final IComponentsFunction<Boolean> function) {
        this.function = function;
    }

    private void setSource(final IDwellingGroup source) {
        if (this.source != null) {
            source.removeListener(groupListener);
        }

        this.source = source;

        if (this.source != null) {
            source.addListener(groupListener);

            final LinkedHashSet<IDwelling> toAdd = new LinkedHashSet<IDwelling>();
            final LinkedHashSet<IDwelling> toRemove = new LinkedHashSet<IDwelling>();

            consider(source.getContents(), toAdd, toRemove);
            update(null, toAdd, toRemove);
        }
    }

    /**
     * Consider the given set of dwellings for inclusion in the group
     *
     * @param changed
     * @param toAdd put any dwellings to add into this set
     * @param toRemove put any dwellings to remove into this set.
     */
    private void consider(final Collection<IDwelling> changed,
            final Set<IDwelling> toAdd,
            final Set<IDwelling> toRemove) {
        if (function == null) {
            return;
        }

        // having arrived here, the changes must be relevant to the interests of our function
        for (final IDwelling d : changed) {
            // we only need to consider whether we contain an element if our source contains that element as well
            if (source.contains(d)) {
                final boolean in = contains(d);
                final boolean shouldBeIn = function.compute(state.detachedScope(d), ILets.EMPTY);
                if (in != shouldBeIn) {
                    (shouldBeIn ? toAdd : toRemove).add(d);
                }
            }
        }
    }
}
