package uk.org.cse.nhm.simulator.state.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.simulator.main.IDateRunnable;
import uk.org.cse.nhm.simulator.main.Priority;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IGlobals;
import uk.org.cse.nhm.simulator.state.IHypotheticalBranch;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.util.RandomSource;

/**
 * A default implementation of {@link IBranch}, which is created by
 * {@link CanonicalState} or by another instanceof {@link BranchState} when
 * their {@link #branch(int)} method is invoked.
 *
 * @author hinton
 *
 */
public class BranchState implements IBranch {

    private static final Logger log = LoggerFactory.getLogger(BranchState.class);
    /**
     * The single, canonical state for this simulation
     */
    final CanonicalState canonicalState;
    /**
     * If not null, a branch which this branch is branching off from
     */
    final BranchState branchedState;
    /**
     * All the dwellings created in this branch
     */
    final Set<IDwelling> created = new LinkedHashSet<IDwelling>();
    /**
     * All the dwellings destroyed in this branch
     */
    final Set<IDwelling> destroyed = new LinkedHashSet<IDwelling>();
    /**
     * All the dwellings changed in this branch
     */
    final Set<IDwelling> modified = new LinkedHashSet<IDwelling>();

    final int originalGlobalsGeneration;

    /**
     * A map from the global, singleton dimension handle for each dimension in
     * this simulation, to the branch-specific forked version of that dimension
     * in this branch.
     */
    @SuppressWarnings("rawtypes")
    final IInternalDimension[] branchedDimensions;

    /**
     * A flag used to temporarily make
     * {@link #getCreated()}, {@link #getModified()}, {@link #getDestroyed()}
     * and {@link #getAnnotations(IDwelling)} return nothing, so that when a
     * child branch is being applied back to this branch we can detect just
     * those changes for which the child branch is responsible.
     */
    boolean isolateChildren = false;

    private final Globals globals;
    final RandomSource random;

    final List<Scheduled> thingsToSchedule = new ArrayList<>();

    static class Scheduled {

        public final DateTime when;
        public final Priority priority;
        public final IDateRunnable callback;

        public Scheduled(final DateTime when, final Priority priority,
                final IDateRunnable callback) {
            super();
            this.when = when;
            this.priority = priority;
            this.callback = callback;
        }
    }

    BranchState(final CanonicalState canonicalState, final int capacity) {
        this.canonicalState = canonicalState;
        this.branchedDimensions = new IInternalDimension[canonicalState.dimensionCounter.max()];
        this.globals = canonicalState.globals.branch();
        originalGlobalsGeneration = globals.getGeneration();
        this.branchedState = null;
        for (final IInternalDimension<?> dimension : canonicalState.getDimensions()) {
            final IInternalDimension<?> fork = dimension.branch(this, capacity);
            branchedDimensions[dimension.index()] = fork;
        }
        this.random = canonicalState.random.dup();
    }

    BranchState(final BranchState branchedState, final int capacity) {
        this.canonicalState = branchedState.canonicalState;
        this.branchedDimensions = new IInternalDimension[canonicalState.dimensionCounter.max()];
        this.globals = branchedState.globals.branch();
        originalGlobalsGeneration = globals.getGeneration();
        this.branchedState = branchedState;
        for (final IInternalDimension<?> dimension : canonicalState.getDimensions()) {
            final IInternalDimension<?> fork = branchedState.getInternalDimension(dimension).branch(this, capacity);
            if (fork == null) {
                log.error("Dimension {} forked to null", dimension);
            }
            branchedDimensions[dimension.index()] = fork;
        }
        this.random = branchedState.random.dup();
    }

    @Override
    public Set<IInternalDimension<?>> getDimensions() {
        return canonicalState.getDimensions();
    }

    /**
     * Given a dimension from the {@link IState#getDimensions()} on the
     * {@link ICanonicalState} for this simulation, returns the branched
     * dimension within this branch
     *
     * @param canonicalDimension
     * @return
     */
    @SuppressWarnings("unchecked")
    protected <T> IInternalDimension<T> getInternalDimension(final IDimension<T> canonicalDimension) {
        if (canonicalDimension == null) {
            log.error("Requested internal dimension for null");
            throw new RuntimeException("Requested null internal dimension");
        }
        final IInternalDimension<T> internal = branchedDimensions[canonicalDimension.index()];
        if (internal == null) {
            log.error("Internal dimension for {} was null", canonicalDimension);
        }
        return internal;
    }

    @Override
    public Set<IDwelling> getDwellings() {
        final Set<IDwelling> result = new LinkedHashSet<IDwelling>((branchedState == null ? canonicalState : branchedState).getDwellings());

        result.addAll(created);
        result.removeAll(destroyed);

        return result;
    }

    @Override
    public IBranch branch(final int capacity) {
        return new BranchState(this, capacity);
    }

    @Override
    public IHypotheticalBranch hypotheticalBranch() {
        return new HypotheticalBranchState(this);
    }

    @Override
    public <T> T get(final IDimension<T> dimension, final IDwelling instance) {
        final T value = getInternalDimension(dimension).get(instance);
        return value;
    }

    @Override
    public boolean isCanonical() {
        return false;
    }

    @Override
    public <T> void modify(final IDimension<T> dimension, final IDwelling instance, final IModifier<T> operation) {
        final IInternalDimension<T> internalDimension = getInternalDimension(dimension);

        final T modifiable = internalDimension.copy(instance);

        if (operation.modify(modifiable)) {
            if (internalDimension.set(instance, modifiable)) {
                modified.add(instance);
            }
        }
    }

    @Override
    public <T> void set(final IDimension<T> dimension, final IDwelling instance, final T value) {
        if (value == null) {
            log.warn("Setting {} to null for {}", dimension, instance);
        }
        if (getInternalDimension(dimension).set(instance, value)) {
            modified.add(instance);
        }
    }

    @Override
    public Set<IDwelling> getModified() {
        if (isolateChildren) {
            return Collections.emptySet();
        }
        final Set<IDwelling> result = new LinkedHashSet<IDwelling>(modified);

        if (branchedState != null) {
            result.addAll(branchedState.getModified());
        }

        result.removeAll(getCreated());
        result.removeAll(getDestroyed());

        return result;
    }

    @Override
    public Set<IDwelling> getDestroyed() {
        if (isolateChildren) {
            return Collections.emptySet();
        }
        final Set<IDwelling> result = new LinkedHashSet<IDwelling>(destroyed);

        if (branchedState != null) {
            result.addAll(branchedState.getDestroyed());
        }

        return result;
    }

    @Override
    public Set<IDwelling> getCreated() {
        if (isolateChildren) {
            return Collections.emptySet();
        }
        final Set<IDwelling> result = new LinkedHashSet<IDwelling>(created);

        if (branchedState != null) {
            result.addAll(branchedState.getCreated());
            result.removeAll(destroyed);
        }

        return result;
    }

    @Override
    public IDwelling createDwelling(final float weight) {
        final IDwelling i = new Instance(canonicalState.getNextID(), weight);
        created.add(i);
        return i;
    }

    @Override
    public void destroyDwelling(final IDwelling lenny) {
        if (created.contains(lenny)) {
            created.remove(lenny);
        } else {
            destroyed.add(lenny);
        }
    }

    protected void dieIfCannotMergeWith(final IBranch child) {
        if (child.isHypothetical()) {
            throw new IllegalArgumentException("A hypothetical branch should never be merged");
        }
    }

    @Override
    public void merge(final IBranch fork) {
        if (fork == this) {
            return;
        }

        dieIfCannotMergeWith(fork);

        globals.merge(fork.getGlobals());
        random.sync(fork.getRandom());

        isolateChildren();
        final Set<IDwelling> createdInFork = fork.getCreated();
        final Set<IDwelling> destroyedInFork = fork.getDestroyed();
        final Set<IDwelling> modifiedInFork = fork.getModified();

        unisolateChildren();

        created.addAll(createdInFork);
        destroyed.addAll(destroyedInFork);
        created.removeAll(destroyed);

        modified.addAll(modifiedInFork);
        modified.removeAll(destroyed);

        for (final IInternalDimension<?> dimension : getDimensions()) {
            copyState(dimension, fork, createdInFork);
            copyState(dimension, fork, modifiedInFork);
        }

        thingsToSchedule.addAll(((BranchState) fork).thingsToSchedule);
    }

    @Override
    public boolean isHypothetical() {
        return false;
    }

    private void unisolateChildren() {
        isolateChildren = false;
    }

    private void isolateChildren() {
        isolateChildren = true;
    }

    /**
     * Copy the value for the given dimension of the affected dwellings in the
     * given state into this state.
     *
     * @param dimension
     * @param state
     * @param affected
     */
    protected <T> void copyState(final IInternalDimension<T> dimension, final IBranch state, final Collection<? extends IDwelling> affected) {
        final IInternalDimension<T> internal = getInternalDimension(dimension);
        final IInternalDimension<T> branch = ((BranchState) state).getInternalDimension(dimension);
        for (final IDwelling a : affected) {
            internal.merge(a, branch);
        }
    }

    @Override
    public int getGeneration(final IDimension<?> dimension, final IDwelling instance) {
        return getInternalDimension(dimension).getGeneration(instance);
    }

    @Override
    public IComponentsScope detachedScope(final IDwelling dwelling) {
        return canonicalState.scopeFactory.createImmutableScope(this, dwelling);
    }

    @Override
    public IGlobals getGlobals() {
        return globals;
    }

    @Override
    public boolean wereGlobalsModified() {
        return globals.getGeneration() != originalGlobalsGeneration;
    }

    @Override
    public RandomSource getRandom() {
        return random;
    }

    @Override
    public void schedule(final DateTime dateTime, final Priority priority, final IDateRunnable callback) {
        thingsToSchedule.add(new Scheduled(dateTime, priority, callback));
    }

    @Override
    public IState getPriorState() {
        return branchedState == null ? canonicalState : branchedState;
    }
}
