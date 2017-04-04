package uk.org.cse.nhm.simulator.state.dimensions.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.inject.Inject;
import javax.inject.Named;

import org.joda.time.DateTime;
import org.joda.time.DateTimeComparator;

import uk.org.cse.nhm.language.definition.action.XForesightLevel;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.DimensionCounter;
import uk.org.cse.nhm.simulator.state.dimensions.IFunctionDimension;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITime;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.impl.IInternalDimension;


/**
 * A dimension which evaluates an {@link IComponentsFunction} to figure out
 * dimension values.
 * 
 * @author hinton
 *
 * @param <T>
 */
public class FunctionDimension<T> extends DerivedDimensionWithCache<T> implements IFunctionDimension<T> {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(FunctionDimension.class);
	
	private IState state;
	private IComponentsFunction<T> function;
	private ITimeDimension time;
	private DateTime startDate;
	private final HashSet<IDimension<?>> nonTemporalDependencies;
	private final List<DateTime> switchPoints;
	private boolean isTemporalFunction;
	private final FunctionDimension<T> parent;
	
	private final String name;

	private final ISimulator simulator;
	private final XForesightLevel requiredForesightLevel;

	private final ILets letsWithForesightLevel;
	
	@Inject
	public FunctionDimension(
			final DimensionCounter dc,
			final String name, final IState state, final ITimeDimension time, final ISimulator simulator,
			@Named(SimulatorConfigurationConstants.START_DATE) final DateTime startDate,
			final XForesightLevel requiredForesightLevel,
			final IComponentsFunction<T> defaultFunction) {
		super(dc.next(), null, IInternalDimension.DEFAULT_CAPACITY);
		this.name = name;
		this.state = state;
		this.time = time;
		this.simulator = simulator;
		this.startDate = startDate;
		this.requiredForesightLevel = requiredForesightLevel;
		this.function = null;
		this.parent = null;
		this.function = defaultFunction;
		this.letsWithForesightLevel = ILets.EMPTY.withBinding(ITime.TIME_KEY, requiredForesightLevel);
        this.nonTemporalDependencies = new HashSet<IDimension<?>>(1);
        this.switchPoints = new ArrayList<DateTime>(1);
	}

	private FunctionDimension(final int index, final IState state, final FunctionDimension<T> object, final int capacity) {
		super(index, object, capacity);
		this.state = state;
		this.function = object.function;
		this.parent = object;
		this.name = object.name;
		this.simulator = object.simulator;
		this.requiredForesightLevel = object.requiredForesightLevel;
		this.letsWithForesightLevel = object.letsWithForesightLevel;
        this.nonTemporalDependencies = parent.nonTemporalDependencies;
        this.switchPoints = parent.switchPoints;
        this.time = parent.time;
        this.isTemporalFunction = parent.isTemporalFunction;
	}
	
	public void setState(final IState state) {
		this.state = state;
	}

	public IComponentsFunction<T> getFunction() {
		return function;
	}

	
	private SortedSet<DateTime> getSwitchPoints(final IComponentsFunction<T> function, final DateTime startDate) {
		final TreeSet<DateTime> result = new TreeSet<DateTime>();
		
		for (final DateTime dt : function.getChangeDates()) {
			if (dt.isBefore(startDate)) {
				result.add(startDate);
			} else {
				result.add(dt);
			}
		}
		
		return result;
	}
	
	@Override
	public void setFunction(final IComponentsFunction<T> function) {
		log.debug("Updating function for {}", this);
		nonTemporalDependencies.clear();
		this.function = function;
		nonTemporalDependencies.addAll(function.getDependencies());
		isTemporalFunction = false;
		
		if (function.getDependencies().contains(time)) {
			nonTemporalDependencies.remove(time);
			isTemporalFunction = true;
			
			final SortedSet<DateTime> switches = getSwitchPoints(function, startDate);
			log.debug("Switching points: {}", switches);
			
			if (switches.isEmpty()) {
				isTemporalFunction = false;
			} else {
				for (final DateTime dt : switches) {
					switchPoints.add(dt);
					simulator.addCheckpoint(dt);
				}
			}
		}
	}
	
	@Override
	public boolean set(final IDwelling instance, final T value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int getGeneration(final IDwelling instance) {
        if (function == null) return 0;
        int acc = 0;
        for (final IDimension<?> dep : nonTemporalDependencies) {
            acc += state.getGeneration(dep, instance);
        }
			
        if (isTemporalFunction) {
            acc += getInternalTimeGeneration(instance);
        }
			
        return acc;
	}

    public String debugGenerations(final IDwelling instance) {
        final StringBuffer sb = new StringBuffer();
        for (final IDimension<?> dep : nonTemporalDependencies) {
            sb.append(dep + ": " + state.getGeneration(dep, instance) + "\n");
        }
        sb.append("time: " + getInternalTimeGeneration(instance));
        return sb.toString();
    }

	private int getInternalTimeGeneration(final IDwelling instance) {
        final DateTime now = state.get(time, instance).get(requiredForesightLevel);
        // now we want to see which bit we are in, from our list of switch points
			
        final int index = Collections.<DateTime>binarySearch(switchPoints, now, DateTimeComparator.getInstance());
			
        final int thisInternalGeneration;
        // the ranges are FROM date inclusive
        // 0 is before the first element
        // 1 is first element onwards
        // 2 is second element onwards
        // and so on
        if (index >= 0) {
            // this is the inclusion of the start of a range
            thisInternalGeneration = index + 1;
        } else {
            // this is not a direct match to a point in the range
            // from the doc for binarySearch, we get the insertion point
            // that is where this thing belongs.
            final int insertionPoint = -(index + 1);
            thisInternalGeneration = insertionPoint;
        }
		
		return thisInternalGeneration;
	}

	@Override
	public IInternalDimension<T> branch(final IBranch forkingState, final int capacity) {
		return new FunctionDimension<T>(index, forkingState, this, capacity);
	}

	@Override
	protected T doGet(final IDwelling instance) {
		if (function == null) return null;
		return function.compute(state.detachedScope(instance), letsWithForesightLevel);
	}
	
	@Override
	public String toString() {
		return name;
	}
}
