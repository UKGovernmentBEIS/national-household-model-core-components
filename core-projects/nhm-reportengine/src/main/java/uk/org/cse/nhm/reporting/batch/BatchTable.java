package uk.org.cse.nhm.reporting.batch;

import java.util.Map;
import java.util.Set;
import java.util.UUID;

import com.google.common.collect.Table;

class BatchTable {

    private final Set<String> inputColumns;
    private final Map<UUID, Map<String, String>> inputRows;
    private final Set<String> groupColumns;
    private final Set<String> outputColumns;
    private final Table<UUID, Map<String, String>, Map<String, Double>> outputValuesByUUIDAndGroup;

    private final Set<UUID> failures;

    public BatchTable(
            final Set<String> inputColumns,
            final Map<UUID, Map<String, String>> inputByUUID,
            final Set<String> groupColumns,
            final Set<String> outputColumns,
            final Table<UUID, Map<String, String>, Map<String, Double>> outputValuesByUUIDAndGroup,
            final Set<UUID> failures
    ) {
        this.inputColumns = inputColumns;
        this.inputRows = inputByUUID;
        this.groupColumns = groupColumns;
        this.outputColumns = outputColumns;
        this.outputValuesByUUIDAndGroup = outputValuesByUUIDAndGroup;
        this.failures = failures;

    }

    public Set<String> getInputColumns() {
        return inputColumns;
    }

    public Map<UUID, Map<String, String>> getInputRows() {
        return inputRows;
    }

    public Set<String> getGroupColumns() {
        return groupColumns;
    }

    public Set<String> getOutputColumns() {
        return outputColumns;
    }

    public Table<UUID, Map<String, String>, Map<String, Double>> getOutputValuesByUUIDAndGroup() {
        return outputValuesByUUIDAndGroup;
    }

    public Set<UUID> getFailures() {
        return failures;
    }
}
