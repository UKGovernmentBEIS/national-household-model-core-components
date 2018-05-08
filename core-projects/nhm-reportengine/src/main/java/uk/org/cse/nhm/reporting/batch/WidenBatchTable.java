package uk.org.cse.nhm.reporting.batch;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.UUID;

import com.google.common.base.Joiner;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Table;

/**
 * This device takes a batch table and returns a wider batch table with fewer
 * rows and more columns.
 *
 * It finds all the group columns which have 2 or more values. It removes these
 * from the group columns, and uses them to create more output columns instead.
 * New output columns = old output columns * combinations of groups The
 * resulting table should have only 1 row per scenario run.
 */
class WidenBatchTable {

    private final RemoveEmptyColumns removeEmpty;

    public WidenBatchTable(final RemoveEmptyColumns removeEmpty) {
        this.removeEmpty = removeEmpty;
    }

    public BatchTable widen(final BatchTable original) {

        final SplitGroups splitGroups = new SplitGroups(original.getOutputValuesByUUIDAndGroup());

        final LinkedHashSet<Map<String, String>> groupCombinations = getGroupCombinations(splitGroups.seenTwo, original.getOutputValuesByUUIDAndGroup().columnKeySet());
        if (groupCombinations.isEmpty()) {
            return original;
        }

        final LinkedHashSet<String> newOutputColumns = extendOutputColumns(original.getOutputColumns(), groupCombinations);

        final BatchTable widened = new BatchTable(
                original.getInputColumns(),
                original.getInputRows(),
                splitGroups.seenOne.keySet(),
                newOutputColumns,
                widenData(original.getOutputValuesByUUIDAndGroup(), splitGroups, original.getOutputColumns(), groupCombinations),
                original.getFailures());

        return removeEmpty.transform(widened);
    }

    /**
     * Remakes the main data table. Removes the grouping columns which were
     * found to have multiple values. Uses them to fill in the data for the
     * output columns.
     *
     * @param originalOutputColumns
     */
    private Table<UUID, Map<String, String>, Map<String, Double>> widenData(
            final Table<UUID, Map<String, String>, Map<String, Double>> outputValuesByUUIDAndGroup,
            final SplitGroups splitGroups,
            final Set<String> originalOutputColumns,
            final LinkedHashSet<Map<String, String>> groupCombinations) {

        final Table<UUID, Map<String, String>, Map<String, Double>> result = HashBasedTable.create();

        for (final Entry<UUID, Map<Map<String, String>, Map<String, Double>>> row : outputValuesByUUIDAndGroup.rowMap().entrySet()) {
            final UUID runID = row.getKey();
            final Map<Map<String, String>, Map<String, Double>> groupsToOutputs = onlyRelevantGroups(row.getValue(), splitGroups.seenTwo);

            if (groupsToOutputs.isEmpty()) {
                result.put(runID, splitGroups.seenOne, ImmutableMap.<String, Double>of());
            } else {

                final Map<String, Double> newOutputs = new LinkedHashMap<>();

                for (final String column : originalOutputColumns) {
                    for (final Map<String, String> groupCombination : groupCombinations) {

                        if (groupsToOutputs.containsKey(groupCombination)) {
                            final String outputColumnName = newOutputColumn(column, combinationName(groupCombination));
                            final Map<String, Double> outputs = groupsToOutputs.get(groupCombination);

                            if (outputs.containsKey(column)) {
                                newOutputs.put(outputColumnName, outputs.get(column));
                            }
                        }
                    }
                }

                result.put(runID, splitGroups.seenOne, newOutputs);
            }
        }

        return result;
    }

    private Map<Map<String, String>, Map<String, Double>> onlyRelevantGroups(
            final Map<Map<String, String>, Map<String, Double>> original,
            final LinkedHashSet<String> includeGroups) {
        final LinkedHashMap<Map<String, String>, Map<String, Double>> result = new LinkedHashMap<>();

        for (final Entry<Map<String, String>, Map<String, Double>> entry : original.entrySet()) {
            /* This will not cause any collisions, because we are only eliminating fields we know to be irrelevant from the map. */
            result.put(reducedGroups(includeGroups, entry.getKey()), entry.getValue());
        }

        return result;
    }

    /**
     * Quite often we will get reports where one or more of the grouping columns
     * only ever takes one value. For example, all our reports might have the
     * same date (the end of the scenario). We don't want to include these in
     * the widening process since they'd only clutter up the column names.
     */
    static class SplitGroups {

        LinkedHashMap<String, String> seenOne;
        LinkedHashSet<String> seenTwo;

        public SplitGroups(final Table<UUID, Map<String, String>, Map<String, Double>> outputValuesByUUIDAndGroup) {
            seenOne = new LinkedHashMap<>();
            seenTwo = new LinkedHashSet<>();

            for (final Map<String, String> grouping : outputValuesByUUIDAndGroup.columnKeySet()) {
                for (final Entry<String, String> groupEntry : grouping.entrySet()) {
                    final String groupColumn = groupEntry.getKey();
                    final String groupValue = groupEntry.getValue();

                    if (seenTwo.contains(groupColumn)) {
                        // We've already seen that this column has two different values. 
                    } else {

                        if (seenOne.containsKey(groupColumn)) {
                            if (seenOne.get(groupColumn).equals(groupValue)) {
                                // We saw the same value for this column that we saw before.
                            } else {
                                // We've now seen two values for this column.
                                seenOne.remove(groupColumn);
                                seenTwo.add(groupColumn);
                            }

                        } else {
                            // We've now seen one value for this column.
                            seenOne.put(groupColumn, groupValue);
                        }
                    }
                }
            }
        }
    }

    private LinkedHashSet<Map<String, String>> getGroupCombinations(final LinkedHashSet<String> includeColumns, final Set<Map<String, String>> groupings) {
        final LinkedHashSet<Map<String, String>> reduced = new LinkedHashSet<>();
        for (final Map<String, String> g : groupings) {
            final Map<String, String> r = reducedGroups(includeColumns, g);
            if (!r.isEmpty()) {
                reduced.add(r);
            }
        }

        return reduced;
    }

    private Map<String, String> reducedGroups(final LinkedHashSet<String> includeColumns, final Map<String, String> groups) {
        final LinkedHashMap<String, String> reduced = new LinkedHashMap<>();

        for (final Entry<String, String> e : groups.entrySet()) {
            if (includeColumns.contains(e.getKey())) {
                reduced.put(e.getKey(), e.getValue());
            }
        }

        return reduced;
    }

    private LinkedHashSet<String> extendOutputColumns(final Set<String> outputs, final Set<Map<String, String>> groupCombinations) {
        final LinkedHashSet<String> combinationNames = new LinkedHashSet<>();
        final LinkedHashSet<String> extendedOutputColumns = new LinkedHashSet<>();

        for (final Map<String, String> combination : groupCombinations) {
            combinationNames.add(combinationName(combination));
        }

        for (final String output : outputs) {
            for (final String combination : combinationNames) {
                extendedOutputColumns.add(newOutputColumn(output, combination));
            }
        }

        return extendedOutputColumns;
    }

    private String newOutputColumn(final String output, final String combination) {
        return output + "-" + combination;
    }

    private String combinationName(final Map<String, String> combination) {
        return Joiner.on('-').join(combination.values());
    }
}
