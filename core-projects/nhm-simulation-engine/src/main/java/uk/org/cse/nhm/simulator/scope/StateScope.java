package uk.org.cse.nhm.simulator.scope;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.management.timer.Timer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.commons.scopes.impl.Scope;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;

public class StateScope extends Scope<IStateChangeSource> implements IStateScope {

    private static final int LOG_SECONDS = 10;
    private static final Logger log = LoggerFactory.getLogger(StateScope.class);
    private final IBranch branch;
    private final ScopeFactory factory;
    final StateScope parentScope;

    private final Map<IDwelling, InnerScope> perDwellingScopes = new HashMap<IDwelling, InnerScope>();

    StateScope(final StateScope rootScope, final IStateChangeSource tag, final IBranch branch, final ScopeFactory factory) {
        super(tag);
        this.branch = branch;
        this.factory = factory;
        this.parentScope = rootScope;
    }

    static class InnerScope extends Scope<IStateChangeSource> {

        public InnerScope(final IStateChangeSource source) {
            super(source);
        }

        @Override
        public void addChild(final Scope<? extends IStateChangeSource> child) {
            super.addChild(child);
        }
    }

    @Override
    public Set<IDwelling> apply(final IStateAction action, final Set<IDwelling> dwellings, final ILets lets) throws NHMException {
        final StateScope child = factory.fromParentScope(this, action, branch);
        addChild(child);
        final Set<IDwelling> output = action.apply(child, dwellings, lets);
        child.close();
        combineDwellingScopes(child);
        return output;
    }

    @Override
    public Set<IDwelling> apply(final IComponentsAction action, final Set<IDwelling> dwellings, final ILets lets) throws NHMException {
        final ImmutableSet.Builder<IDwelling> suitable = ImmutableSet.builder();
        int counter = 0;
        final long start = System.currentTimeMillis();
        int percent = 0;
        int logpercent = 0;
        for (final IDwelling d : dwellings) {
            final ComponentsScope child
                    = factory.createComponentsScope(this, action, branch, d, new HashMap<String, Double>());

            addChild(child);
            if (action.apply(child, lets)) {
                suitable.add(d);
            }
            child.close();

            if (!perDwellingScopes.containsKey(d)) {
                perDwellingScopes.put(d, new InnerScope(getTag()));
            }

            perDwellingScopes.get(d).addChild(child);
            final int seconds = (int) ((System.currentTimeMillis() - start) / Timer.ONE_SECOND);
            counter++;
            if (seconds > LOG_SECONDS) {
                percent = 10 * counter / dwellings.size();
                if (percent > logpercent) {
                    logpercent = percent;
                    log.info("applying {} to {} houses; done {} ({} %) in {}s", action, dwellings.size(), counter, 10 * percent, seconds);
                }
            }
        }
        return suitable.build();
    }

    @Override
    public IBranch getState() {
        return branch;
    }

    @Override
    public IStateScope branch(final IStateChangeSource tag) {
        final StateScope child = factory.fromParentScope(this,
                tag,
                branch.branch(Math.max(2, branch.getDwellings().size())));
        return child;
    }

    @Override
    public void merge(final IStateScope scope_) {
        // requires that the thing is a StateScope
        final StateScope scope = (StateScope) scope_;
        addChild(scope);
        scope.close();
        // merge the branch in the branch
        branch.merge(scope.branch);
        // merge the per-dwelling scopes we contain with each other
        combineDwellingScopes(scope);
    }

    private void combineDwellingScopes(final StateScope scope) {
        for (final Map.Entry<IDwelling, InnerScope> pds : scope.perDwellingScopes.entrySet()) {
            final IDwelling d = pds.getKey();

            if (!perDwellingScopes.containsKey(d)) {
                perDwellingScopes.put(d, new InnerScope(getTag()));
            }

            perDwellingScopes.get(d).addChild(pds.getValue());
        }
    }

    @Override
    public void close() {
        for (final InnerScope s : perDwellingScopes.values()) {
            s.close();
        }
        super.close();
    }

    @Override
    public Optional<IComponentsScope> getComponentsScope(final IDwelling dwelling) {
        if (perDwellingScopes.containsKey(dwelling)) {
            final IComponentsScope result = new UnifiedComponentsScope(perDwellingScopes.get(dwelling),
                    branch.detachedScope(dwelling));
            return Optional.of(result);
        } else {
            return Optional.absent();
        }
    }

    @Override
    public IHypotheticalComponentsScope createHypothesis(final IDwelling dwelling) {
        return branch.detachedScope(dwelling).createHypothesis();
    }

    @Override
    public IComponentsScope getPriorScope(final IDwelling dwelling) {
        // this is a bit horrendous
        if (parentScope == null) {
            // we are the topmost modification
            return branch.getPriorState().detachedScope(dwelling);
        } else {
            final Optional<IComponentsScope> scope = parentScope.getComponentsScope(dwelling);
            if (scope.isPresent()) {
                return scope.get();
            } else {
                return branch.getPriorState().detachedScope(dwelling);
            }
        }
    }
}
