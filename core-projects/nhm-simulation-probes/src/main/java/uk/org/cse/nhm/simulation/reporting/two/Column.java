package uk.org.cse.nhm.simulation.reporting.two;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;

import uk.org.cse.nhm.simulation.reporting.two.Accumulator.IAccumulation;
import uk.org.cse.nhm.simulation.reporting.two.UnifiedReport.Record;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.utility.DeduplicatingMap.Deduplicator;

public class Column extends AbstractNamed implements IReportPart {
	private final IComponentsFunction<?> value;
	private final List<Accumulator> accumulators;
	
	private final Table<Cut, List<Object>, List<IAccumulation>> accumulationsBefore =
        HashBasedTable.create();

    private final Table<Cut, List<Object>, List<IAccumulation>> accumulationsAfter =
        HashBasedTable.create();
	
	public Column(final IComponentsFunction<?> value, final List<Accumulator> accumulators) {
		super();
		this.value = value;
		this.accumulators = accumulators;
	}

	/**
	 * This should be set by the containing {@link UnifiedReport} during construction.
	 * It's not nice, but it's because the {@link UnifiedReport.Record} contains things
	 * in an array rather than a map, since an array is rather lighter weight.
	 */
	private int index = -1;
	private String uniqueName;
	
	void setIndex(int index) {
		this.index = index;
	}
	
	void takeNames(final Deduplicator<Object> names) {
		uniqueName = names.add(this, getIdentifier().getName());
		for (final Accumulator acc : accumulators) {
			acc.takeName(uniqueName, names);
		}
	}
	
	public int getIndex() {
		return index;
	}
	
	public Object compute(final IComponentsScope scope, final ILets lets) {
		return value.compute(scope, lets);
	}

	public Map<String, Double> getAccumulatedValues(
			final Cut cut,
			final List<Object> reducedKey) {
		final ImmutableMap.Builder<String, Double> out = ImmutableMap.builder();
		
		putAccumulatedValues(cut, reducedKey, accumulationsBefore, "(Before)", out);
        putAccumulatedValues(cut, reducedKey, accumulationsAfter, "(After)", out);
		
		return out.build();
	}

    private void putAccumulatedValues(
        final Cut cut,
        final List<Object> reducedKey,
        final Table<Cut, List<Object>, List<IAccumulation>> accumulations,
        final String suffix, 
        final ImmutableMap.Builder<String, Double> out) {

        for (final IAccumulation acc : accumulations.get(cut, reducedKey)) {
			out.put(acc.source().uniqueName + " " + suffix, acc.get());
			acc.reset();
		}
    }
	
	/**
	 * When a record has been put into a cut, it is sent here to be accumulated
	 * within that cut.
	 */
	public void accumulate(Cut cut, List<Object> group, Record rec) {
		accumulate(rec.dwelling.getWeight(), accumulationsBefore, cut, group, rec.columnValuesBefore);
        accumulate(rec.dwelling.getWeight(), accumulationsAfter, cut, group, rec.columnValuesAfter);
	}

    private void accumulate(
        final float weight,
        final Table<Cut, List<Object>, List<IAccumulation>> accumulations,
        final Cut cut, final List<Object> group, final Object[] values) {
        
        final Object value = values == null ? null : values[index];
			
		for (final IAccumulation acc : accumulationsFor(accumulations, cut, group)) {
            // need to double up accumulations somehow into before and after
			acc.put(weight, value);
		}
    }

	private List<IAccumulation> accumulationsFor(
        final Table<Cut, List<Object>, List<IAccumulation>> accumulations,
        final Cut cut,
        final List<Object> group) {
        
		if (!accumulations.contains(cut, group)) {
			final List<IAccumulation> result = start();
			accumulations.put(cut, group, result);
			return result;
		} else {
			return accumulations.get(cut, group);
		}
	}

	private List<IAccumulation> start() {
		final ImmutableList.Builder<IAccumulation> b = ImmutableList.builder();
		for (final Accumulator accumulator : accumulators) {
			b.add(accumulator.start());
		}
		return b.build();
	}
	
	@Override
	public Set<Cut> cuts() {
		return Collections.emptySet();
	}
	
	@Override
	public Set<Column> columns() {
		return Collections.singleton(this);
	}
}
