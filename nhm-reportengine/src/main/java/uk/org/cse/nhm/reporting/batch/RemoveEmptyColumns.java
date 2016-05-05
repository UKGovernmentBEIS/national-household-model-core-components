package uk.org.cse.nhm.reporting.batch;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Table;

class RemoveEmptyColumns {
	public BatchTable transform(final BatchTable original) {
		return new BatchTable(
				original.getInputColumns(),
				original.getInputRows(),
				original.getGroupColumns(),
				cleanOutputs(original.getOutputColumns(), original.getOutputValuesByUUIDAndGroup()),
				original.getOutputValuesByUUIDAndGroup(),
				original.getFailures());
	}

	private Set<String> cleanOutputs(final Set<String> originalOutputs, final Table<UUID, Map<String, String>, Map<String, Double>> table) {
		final LinkedHashSet<String> toRemove = new LinkedHashSet<>();
		toRemove.addAll(originalOutputs);
		
		for (final Map<String, Double> cell : table.values()) {
			toRemove.removeAll(cell.keySet());
		}
		
		final LinkedHashSet<String> cleaned = new LinkedHashSet<>();
		cleaned.addAll(originalOutputs);
		cleaned.removeAll(toRemove);
		
		return cleaned;
	}
}
