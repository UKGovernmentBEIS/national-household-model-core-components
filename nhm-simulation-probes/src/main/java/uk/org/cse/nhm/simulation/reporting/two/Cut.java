package uk.org.cse.nhm.simulation.reporting.two;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.simulation.reporting.two.UnifiedReport.Record;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.utility.DeduplicatingMap;

public class Cut extends AbstractNamed implements IReportPart {
	public static final String OUTCOME_COLUMN = "suitable";
	public static final String SENT_FROM_COLUMN = "sent-from";
	public static final String SELECTED_COLUMN = "selected";
	private static final int NOT_FOUND = -1;
	private static final int SELECTED = -2;
	private static final int SENT_FROM = -3;
	private static final int OUTCOME = -4;
	
	private final List<String> names;
	private final int[] indices;
	
	private final Map<List<Object>, Double> inCounter = new LinkedHashMap<>();
    private final Map<List<Object>, Double> outCounter = new LinkedHashMap<>();
	
	public Cut(final List<String> names) {
		this.names = names;
		this.indices = new int[names.size()];
		Arrays.fill(indices, NOT_FOUND);
	}

	void takeColumnIndices(final List<Column> columns) {
		int index = 0;
		for (final String name : names) {
			int columnIndex = NOT_FOUND;
			switch (name.toLowerCase()) {
			case SELECTED_COLUMN:
				columnIndex = SELECTED;
				break;
			case SENT_FROM_COLUMN:
				columnIndex = SENT_FROM;
				break;
			case OUTCOME_COLUMN:
				columnIndex = OUTCOME;
				break;
			default:
				for (final Column c : columns) {
					if (c.getIdentifier().getName().equals(name)) {
						columnIndex = c.getIndex();
						break;
					}
				}
			}
			
			indices[index++] = columnIndex;
		}
	}
	
	List<Object> count(final Record record) {
		final ImmutableList.Builder<Object> result = ImmutableList.builder();
		for (final int index : indices) {
			switch (index) {
			case NOT_FOUND: break;
			case SELECTED: 
				result.add(record.selected);
				break;
			case SENT_FROM:
				result.add(record.key);
				break;
			case OUTCOME:
				result.add(record.columnValuesAfter != null);
				break;
			default:
				result.add(record.columnValuesBefore[index]);
                result.add(record.columnValuesAfter == null ?
                           "n/a" : 
                           record.columnValuesAfter[index]);
				break;
			}
		}
		final List<Object> out = result.build();
		
		addCounter(inCounter, out, record.dwelling.getWeight());

        if (record.columnValuesAfter != null) {
            addCounter(outCounter, out, record.dwelling.getWeight());
        }
        
		return out;
	}

    private static void addCounter(final Map<List<Object>, Double> counter,
                                   final List<Object> key,
                                   final double weight) {
        if (counter.containsKey(key)) {
            counter.put(key, counter.get(key) + weight);
        } else {
            counter.put(key, weight);
        }
    }
	
	void reset() {
		inCounter.clear();
        outCounter.clear();
	}
	
	@Override
	public Set<Cut> cuts() {
		return Collections.singleton(this);
	}
	
	@Override
	public Set<Column> columns() {
		return Collections.emptySet();
	}
	
	public double getCountBefore(final List<Object> group) {
		final Double out = inCounter.get(group);
		if (out == null) {
			return 0d;
		} else {
			return out;
		}
	}

    public double getCountAfter(final List<Object> group) {
		final Double out = outCounter.get(group);
		if (out == null) {
			return 0d;
		} else {
			return out;
		}
	}
	
	public Set<List<Object>> getGroups() {
		return inCounter.keySet();
	}
	
	public ImmutableMap<String, String> createRowKey(List<Object> group) {
		final DeduplicatingMap.Builder<String> key = DeduplicatingMap.stringBuilder();
        int groupIndex = 0;
		for (int i = 0; i<names.size(); i++) {
			if (indices[i] == NOT_FOUND) continue;
            
            if (indices[i] < 0) {
                //these columns have a special case behaviour, which means they don't need duplicating.
                key.put(names.get(i), String.valueOf(group.get(groupIndex)));
                groupIndex++;
            } else {
                key.put(names.get(i) + " (Before)", String.valueOf(group.get(groupIndex)));
                key.put(names.get(i) + " (After)", String.valueOf(group.get(groupIndex+1)));
                groupIndex += 2;
            }
		}
		return key.build();
	}
}
