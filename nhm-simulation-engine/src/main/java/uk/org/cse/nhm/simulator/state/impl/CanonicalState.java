package uk.org.cse.nhm.simulator.state.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;

import com.carrotsearch.hppc.ObjectIntOpenHashMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.action.ConstructHousesAction;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.profile.IProfiler;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateAction;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.scope.ScopeFactory;
import uk.org.cse.nhm.simulator.scope.StateScope;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IGlobals;
import uk.org.cse.nhm.simulator.state.IHypotheticalBranch;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.util.RandomSource;

public class CanonicalState implements ICanonicalState {
	private static final IStateScope TIMESCOPE = new TimeScope();
	private Set<IInternalDimension<?>> dimensions;
	private final Set<IDwelling> instances = new LinkedHashSet<IDwelling>();
	private final Set<IStateListener> listeners = new LinkedHashSet<IStateListener>();
	final Map<String, GlobalTransactionHistory> accounts = new HashMap<String, GlobalTransactionHistory>(); 

	private final Map<IDimension<?>, ObjectIntOpenHashMap<IDwelling>> generations = new HashMap<IDimension<?>, ObjectIntOpenHashMap<IDwelling>>();
	private ITimeDimension timeDimension;
	protected final ScopeFactory scopeFactory;
	
	protected final Globals globals;
	protected final DimensionCounter dimensionCounter;
    protected final RandomSource random;
	
    private ISimulator simulator;
    
	@Inject
	private IProfiler profiler;
	
	@Inject
	public CanonicalState(final DimensionCounter dimensionCounter, final ScopeFactory scopeFactory,
                          @Named(SimulatorConfigurationConstants.GRANULARITY) final int quantum,
                          @Named(SimulatorConfigurationConstants.RANDOM_SEED) final long seed) {
		this.dimensionCounter = dimensionCounter;
		globals = new Globals(quantum);
		this.scopeFactory = scopeFactory;
        this.random = new RandomSource(seed);
	}

    @Inject
	public void setDimensions(final Set<IInternalDimension<?>> dimensions) {
		this.dimensions = dimensions;
		for (final IDimension<?> d : dimensions) {
			generations.put(d, new ObjectIntOpenHashMap<IDwelling>());
		}
	}
	
	@Inject
	public void setTimeDimension(final ITimeDimension timeDimension) {
		this.timeDimension = timeDimension;
	}
	
	@Inject
	public void setSimulator(final ISimulator simulator) {
		this.simulator = simulator;
	}
	

	@Override
	public Set<IInternalDimension<?>> getDimensions() {
		return Collections.unmodifiableSet(dimensions);
	}

	@Override
	public Set<IDwelling> getDwellings() {
		return Collections.unmodifiableSet(instances);
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
		if (dimension instanceof IInternalDimension && dimensions.contains(dimension)) {
			return ((IInternalDimension<T>) dimension).get(instance);
		} else {
			throw new RuntimeException("Unknown dimension " + dimension);
		}
	}

	@Override
	public boolean isCanonical() {
		return true;
	}

	@Override
	public String toString() {
		final StringBuffer sb = new StringBuffer();
		for (final IDwelling instance : instances) {
			sb.append("{");
			for (final IInternalDimension<?> dim : dimensions) {
				sb.append(dim + ":" + get(dim, instance));
				sb.append(" ");
			}
			sb.append("} ");
		}
		return sb.toString();
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void applyChanges(final IStateScope rootScope, final boolean globalsModified, final Set<IDwelling> created, final Set<IDwelling> destroyed, final Set<IDwelling> modified, final IBranch state, final Set<IStateChangeSource> causes) {
		instances.addAll(created);
		instances.removeAll(destroyed);	
		final SetMultimap<IInternalDimension<?>, IDwelling> changes = LinkedHashMultimap.create();
		final SetMultimap<IInternalDimension<?>, IDwelling> creations = LinkedHashMultimap.create();
		
		if (profiler != null) profiler.start("FINDING MODIFIED GENS", "STATE");
		for (final IInternalDimension<?> dimension : dimensions) {
			for (final IDwelling a : modified) {
				if (dimension.getGeneration(a) != state.getGeneration(dimension, a)) {
					changes.put(dimension, a);
				}
			}
		}
		if (profiler != null) profiler.stop();
		
		for (final IInternalDimension<?> dimension : dimensions) {
			for (final IDwelling a : created) {
				if (dimension.getGeneration(a) != state.getGeneration(dimension, a)) {
					creations.put(dimension, a);
				}
			}
		}
		
		if (profiler != null) profiler.start("COPY STATE", "STATE");
		
		copyState(state, creations);
		copyState(state, changes);
		
		if (profiler != null) profiler.stop();
		
		for (final IInternalDimension<?> d : dimensions) {
			creations.putAll(d, created);
		}
		

		if (profiler != null) profiler.start("UPDATE GEN", "STATE");
		updateLastGenerations(changes);
		updateLastGenerations(creations);
		if (profiler != null) profiler.stop();
		

		final StateChangeNotification notification = new StateChangeNotification(
				ImmutableSet.<IStateChangeSource>builder().addAll(causes).add(rootScope.getTag()).build(),
				rootScope,
				globalsModified,
				created, 
				destroyed, 
				(SetMultimap) changes, // this evil cast is OK, because we know the types are consistent. 
				timeDimension.get(null).get(ILets.EMPTY));
		if (profiler != null) profiler.start("NOTIFY LISTENER", "STATE");
		notifyListeners(notification);
		if (profiler != null) profiler.stop();
	}

	private void updateLastGenerations(final SetMultimap<? extends IDimension<?>, IDwelling> changes) {
		for (final Entry<? extends IDimension<?>, IDwelling> e : changes.entries()) {
			generations.get(e.getKey()).put(e.getValue(), getGeneration(e.getKey(), e.getValue()));
		}
	}

	private void notifyListeners(final StateChangeNotification notification) {
		if (notification.isChange()) {
			for (final IStateListener listener : listeners.toArray(new IStateListener[0])) {
				if (profiler != null) profiler.start(listener.toString(), "SL");
				listener.stateChanged(this, notification);
				if (profiler != null) profiler.stop();
			}
		}
	}

	/**
	 * This method previously ran a simple loop to copy the state out of one
	 * dimension and into another.
	 * 
	 * However, it was somewhat slow, because we couldn't copy energy calculator
	 * or fuel cost data directly as it is derived.
	 * 
	 * The intended behaviour here is that we will directly copy the state out
	 * of the branched dimensions, including the generation counts, avoiding any
	 * recomputation.
	 * 
	 * The downside of doing this is a lot more detailed dependency on the
	 * internal states of particular things.
	 * 
	 * @param dimension
	 * @param state
	 * @param changes
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" }) /* We know that we have the same IInternalDimension<?> for both the dimension and the branchdimension, but the type system does not. */
	private void copyState(final IBranch state, final SetMultimap<IInternalDimension<?>, IDwelling> changes) {
		for (final IInternalDimension<?> dimension : changes.keySet()) {
			final IInternalDimension<?> branchDimension = ((BranchState) state).getInternalDimension(dimension);
			for (final IDwelling dwelling : changes.get(dimension)) {
				dimension.merge(dwelling, (IInternalDimension) branchDimension);
			}
		}
//
//		for (final IDwelling a : changes) {
////			final int currentGeneration = dimension.getGeneration(a);
////			final int nextGeneration = branchDimension.getGeneration(a);
//
////			if (nextGeneration < currentGeneration) {
////				throw new RuntimeException("Generation for " + a.getID() + " in dimension " + dimension + " has decreased");
////			}
//
////			if (dimension.getGeneration(a) != state.getGeneration(dimension, a)) {
//				dimension.merge(a, branchDimension);
////			}
//		}
	}
	
	public void merge(final IBranch fork, final IStateScope rootScope, final Set<IStateChangeSource> causes) {
		if (fork != this) {
			if (fork.isHypothetical()) {
				throw new IllegalArgumentException("A hypothetical branch should never be merged");
			}
		
			if (profiler != null) profiler.start("MERGE ACCOUNTS", "STATE");
			
			globals.merge(fork.getGlobals());
            random.sync(fork.getRandom());
            
			if (profiler != null) profiler.stop();
			if (profiler != null) profiler.start("MERGE CONTENTS", "STATE");
			applyChanges(rootScope, fork.wereGlobalsModified(), fork.getCreated(), fork.getDestroyed(), fork.getModified(), fork, causes);
			
			for (final BranchState.Scheduled scheduled : ((BranchState) fork).thingsToSchedule) {
				simulator.schedule(scheduled.when, scheduled.priority, scheduled.callback);
			}
			
			if (profiler != null) profiler.stop();
		}
	}

	int nextID = 0;

	public int getNextID() {
		return nextID++;
	}

	@Override
	public int getGeneration(final IDimension<?> dimension, final IDwelling instance) {
//		if (dimension instanceof IInternalDimension && dimensions.contains(dimension)) {
			return ((IInternalDimension<?>) dimension).getGeneration(instance);
//		} else {
//			throw new RuntimeException("Unknown dimension " + dimension);
//		}
	}

	@Override
	public void addStateListener(final IStateListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeStateListener(final IStateListener listener) {
		listeners.remove(listener);
	}

	@Override
	public void checkForEndogenousChanges() {
		if (profiler != null) profiler.start("CHECK", "STATE");
		final SetMultimap<IDimension<?>, IDwelling> changes = LinkedHashMultimap.create();

		
		for (final IInternalDimension<?> dimension : dimensions) {
			for (final IDwelling a : getDwellings()) {
				if (dimension.getGeneration(a) != generations.get(dimension).get(a)) {
					changes.put(dimension, a);
				}
			}
		}

		updateLastGenerations(changes);

		final Set<IDwelling> noDwellingsCreatedOrDestroyed = Collections.<IDwelling>emptySet();
		final StateChangeNotification notification = 
				new StateChangeNotification(
						Collections.<IStateChangeSource>singleton(TimeScope.TIME),
						TIMESCOPE, 
						false,
						noDwellingsCreatedOrDestroyed, 
						noDwellingsCreatedOrDestroyed, 
						changes, 
						timeDimension.get(null).get(ILets.EMPTY));

		notifyListeners(notification);
		if (profiler != null) profiler.stop();
	}
	
	@Override
	public IStateScope apply(final IStateChangeSource proximateCause, final IStateAction action, final Set<IDwelling> set, final ILets lets) throws NHMException {
		return apply(Collections.<IStateChangeSource>emptySet(), proximateCause, action, set, lets);
	}
	
	@Override
	public IStateScope apply(final Set<IStateChangeSource> causes, final IStateChangeSource proximate, final IStateAction action, final Set<IDwelling> targets, final ILets lets) throws NHMException {
		// because ConstructHousesAction makes new houses, we need to hint double the target
		// count to the branch, because otherwise if we construct 1 house we will end up without
		// room for its double, because of the 1-house optimisation in most dimensions.
		final int targetCount;
		if (action instanceof ConstructHousesAction) {
			targetCount = targets.size() * 2;
		} else {
			targetCount = targets.size();
		}
		
		final IBranch branch = branch(targetCount);
		final StateScope root = scopeFactory.createStateScope(proximate, branch);
		if (profiler != null) profiler.start("MOD", "STATE");
		root.apply(action, targets, lets);
		root.close();
		if (profiler != null) profiler.stop();
		if (profiler != null) profiler.start("MERGE", "STATE");
		merge(branch, root, causes);
		if (profiler != null) profiler.stop();
		return root;
	}

    @Override
    public IStateScope branch(final IStateChangeSource tag) {
        final IBranch branch = branch(Math.max(2, getDwellings().size()));
		final StateScope root = scopeFactory.createStateScope(tag, branch);
        return root;
    }

    @Override
    public void apply(final IStateScope branch, final Set<IStateChangeSource> causes) {
        final StateScope scope = (StateScope) branch;
        scope.close();
        merge(scope.getState(), scope, causes);
    }
    
	@Override
	public IComponentsScope detachedScope(final IDwelling dwelling) {
		return scopeFactory.createImmutableScope(this, dwelling);
	}
	
	@Override
	public IGlobals getGlobals() {
		return globals;
	}

    @Override
    public RandomSource getRandom() {
        return random;
    }
    
    @Override
    public DateTime getTrueDate() {
    	return timeDimension.get(null).get(XForesightLevel.Always);
    }

    @Override
    public IState getPriorState() {
        return this;
    }
}
