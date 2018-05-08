package uk.org.cse.nhm.reporting.batch;

import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.apache.commons.math3.stat.descriptive.DescriptiveStatistics;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;
import com.google.common.collect.Table;
import com.google.common.collect.Table.Cell;

import uk.org.cse.nhm.language.definition.batch.inputs.combinators.XRepetitions;

class BatchSummary {

    private static final String COUNT = "count";

    BatchTable summariseOverSeed(final BatchTable table) {
        final List<ISummaryStat> stats = getStats();

        final Map<UUID, Map<String, String>> inputValuesByUUIDWithoutSeed = inputValuesByUUIDWithoutSeed(table);

        return new BatchTable(
                inputColumnsWithoutSeed(table),
                inputValuesByUUIDWithoutSeed,
                table.getGroupColumns(),
                generateExtraOutputs(table, stats),
                summaryTable(table, inputValuesByUUIDWithoutSeed, stats),
                table.getFailures());
    }

    private Table<UUID, Map<String, String>, Map<String, Double>> summaryTable(final BatchTable table, final Map<UUID, Map<String, String>> inputValuesByUUIDWithoutSeed, final List<ISummaryStat> stats) {
        final LinkedHashMap<Map<String, String>, UUID> uuidByInputs = new LinkedHashMap<>();

        final Table<Map<String, String>, Map<String, String>, Map<ColumnWithStat, DescriptiveStatistics>> groupedByInputs = HashBasedTable.create();

        for (final Cell<UUID, Map<String, String>, Map<String, Double>> cell : table.getOutputValuesByUUIDAndGroup().cellSet()) {
            final UUID uuid = cell.getRowKey();
            final Map<String, String> inputsWithoutSeed = inputValuesByUUIDWithoutSeed.get(uuid);

            if (!groupedByInputs.contains(inputsWithoutSeed, cell.getColumnKey())) {
                groupedByInputs.put(inputsWithoutSeed, cell.getColumnKey(), new LinkedHashMap<ColumnWithStat, DescriptiveStatistics>());
            }

            if (!uuidByInputs.containsKey(inputsWithoutSeed)) {
                uuidByInputs.put(inputsWithoutSeed, uuid);
            }

            final Map<ColumnWithStat, DescriptiveStatistics> newOutputs = groupedByInputs.get(inputsWithoutSeed, cell.getColumnKey());
            for (final Entry<String, Double> originalColumn : cell.getValue().entrySet()) {
                for (final ISummaryStat s : stats) {
                    final ColumnWithStat newColumn = new ColumnWithStat(originalColumn.getKey(), s);
                    if (!newOutputs.containsKey(newColumn)) {
                        newOutputs.put(newColumn, new DescriptiveStatistics());
                    }

                    newOutputs.get(newColumn).addValue(originalColumn.getValue());
                }
            }
        }

        final Table<UUID, Map<String, String>, Map<String, Double>> summarised = HashBasedTable.create();

        for (final Cell<Map<String, String>, Map<String, String>, Map<ColumnWithStat, DescriptiveStatistics>> inputGroup : groupedByInputs.cellSet()) {
            summarised.put(
                    uuidByInputs.get(inputGroup.getRowKey()),
                    inputGroup.getColumnKey(),
                    summarised(inputGroup.getValue()));
        }

        return summarised;
    }

    private Map<String, Double> summarised(
            final Map<ColumnWithStat, DescriptiveStatistics> groupedNumbers) {
        final ImmutableMap.Builder<String, Double> summary = ImmutableMap.builder();

        if (groupedNumbers.isEmpty()) {
            summary.put(COUNT, 0d);
        } else {
            final Number count = Iterables.get(groupedNumbers.entrySet(), 0).getValue().getN();
            summary.put(COUNT, count.doubleValue());
        }

        for (final Entry<ColumnWithStat, DescriptiveStatistics> group : groupedNumbers.entrySet()) {
            final ColumnWithStat columnWithStat = group.getKey();
            summary.put(columnWithStat.toString(), columnWithStat.stat.getValue(group.getValue()));
        }

        return summary.build();
    }

    @AutoProperty
    public static class ColumnWithStat {

        private final String column;
        private final ISummaryStat stat;

        public ColumnWithStat(final String column, final ISummaryStat stat) {
            this.column = column;
            this.stat = stat;
        }

        @Override
        public String toString() {
            return column + "-" + stat.getHeader();
        }

        @Override
        public boolean equals(final Object obj) {
            return Pojomatic.equals(this, obj);
        }

        @Override
        public int hashCode() {
            return Pojomatic.hashCode(this);
        }

        public String getColumn() {
            return column;
        }

        public ISummaryStat getStat() {
            return stat;
        }
    }

    private LinkedHashSet<String> generateExtraOutputs(final BatchTable table,
            final List<ISummaryStat> stats) {
        final LinkedHashSet<String> outputColumns = new LinkedHashSet<>();

        outputColumns.add(COUNT);

        for (final String column : table.getOutputColumns()) {
            for (final ISummaryStat s : stats) {
                outputColumns.add(new ColumnWithStat(column, s).toString());
            }
        }
        return outputColumns;
    }

    private LinkedHashSet<String> inputColumnsWithoutSeed(final BatchTable table) {
        final LinkedHashSet<String> inputs = new LinkedHashSet<>();
        for (final String input : table.getInputColumns()) {
            if (!input.equals(XRepetitions.SEED)) {
                inputs.add(input);
            }
        }
        return inputs;
    }

    private Map<UUID, Map<String, String>> inputValuesByUUIDWithoutSeed(final BatchTable table) {
        final ImmutableMap.Builder<UUID, Map<String, String>> inputsByUUID = ImmutableMap.builder();

        for (final Entry<UUID, Map<String, String>> old : table.getInputRows().entrySet()) {
            inputsByUUID.put(old.getKey(), withoutSeed(old.getValue()));
        }

        return inputsByUUID.build();
    }

    private Map<String, String> withoutSeed(final Map<String, String> row) {
        final ImmutableMap.Builder<String, String> inputsMap = ImmutableMap.builder();
        for (final Entry<String, String> old : row.entrySet()) {
            if (!old.getKey().equals(XRepetitions.SEED)) {
                inputsMap.put(old);
            }
        }
        return inputsMap.build();
    }

    interface ISummaryStat {

        String getHeader();

        double getValue(DescriptiveStatistics values);
    }

    private static List<ISummaryStat> getStats() {
        return ImmutableList.<ISummaryStat>builder()
                .add(new ISummaryStat() {
                    @Override
                    public String getHeader() {
                        return "mean";
                    }

                    @Override
                    public double getValue(final DescriptiveStatistics values) {
                        return values.getMean();
                    }
                })
                .add(new ISummaryStat() {
                    @Override
                    public String getHeader() {
                        return "median";
                    }

                    @Override
                    public double getValue(final DescriptiveStatistics values) {
                        return values.getPercentile(50);
                    }
                })
                .add(new ISummaryStat() {
                    @Override
                    public String getHeader() {
                        return "variance";
                    }

                    @Override
                    public double getValue(final DescriptiveStatistics values) {
                        return values.getVariance();
                    }
                })
                .add(new ISummaryStat() {
                    @Override
                    public String getHeader() {
                        return "std-error";
                    }

                    @Override
                    public double getValue(final DescriptiveStatistics values) {
                        final long count = values.getN();
                        if (count == 0) {
                            return 0;
                        }
                        return values.getStandardDeviation() / Math.sqrt(count);
                    }
                })
                .add(new ISummaryStat() {
                    @Override
                    public String getHeader() {
                        return "max";
                    }

                    @Override
                    public double getValue(final DescriptiveStatistics values) {
                        return values.getMax();
                    }
                })
                .add(new ISummaryStat() {
                    @Override
                    public String getHeader() {
                        return "min";
                    }

                    @Override
                    public double getValue(final DescriptiveStatistics values) {
                        return values.getMin();
                    }
                })
                .add(new ISummaryStat() {
                    @Override
                    public String getHeader() {
                        return "std-dev";
                    }

                    @Override
                    public double getValue(final DescriptiveStatistics values) {
                        return values.getStandardDeviation();
                    }
                })
                .add(new ISummaryStat() {
                    @Override
                    public String getHeader() {
                        return "skew";
                    }

                    @Override
                    public double getValue(final DescriptiveStatistics values) {
                        if (values.getVariance() == 0) {
                            /* Apache Commons Math returns skew as NaN in this case. */
                            return 0;
                        } else {
                            return values.getSkewness();
                        }
                    }
                })
                .build();
    }
}
