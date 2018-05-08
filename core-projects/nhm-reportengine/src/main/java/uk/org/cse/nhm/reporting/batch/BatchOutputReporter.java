package uk.org.cse.nhm.reporting.batch;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.HashBasedTable;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Table;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.batch.BatchInputEntry;
import uk.org.cse.nhm.logging.logentry.batch.BatchOutputEntry;
import uk.org.cse.nhm.logging.logentry.errors.SystemErrorLogEntry;
import uk.org.cse.nhm.reporting.standard.GenericDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor.Type;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;

public class BatchOutputReporter implements IReporter {

    private static final Logger log = LoggerFactory.getLogger(BatchOutputReporter.class);
    private final IOutputStreamFactory factory;

    private final Set<UUID> failures = new HashSet<>();
    private final LinkedHashSet<String> inputColumns = new LinkedHashSet<>();
    private final Map<UUID, Map<String, String>> inputsByUUID = new HashMap<>();
    private final LinkedHashSet<String> outputColumns = new LinkedHashSet<>();
    private final LinkedHashSet<String> groupColumns = new LinkedHashSet<>();
    private final Table<UUID, Map<String, String>, Map<String, Double>> outputValuesByUUIDAndGroup = HashBasedTable.create();

    private BufferedWriter errorLog;
    private final BatchTableOutput batchTableOutput;
    private final BatchSummary summariser;
    private final WidenBatchTable widener;

    @Inject
    public BatchOutputReporter(final IOutputStreamFactory factory) {
        this.factory = factory;
        batchTableOutput = new BatchTableOutput(factory);
        summariser = new BatchSummary();
        widener = new WidenBatchTable(new RemoveEmptyColumns());
    }

    @Override
    public void close() throws IOException {
        writeErrors();

        if (!inputsByUUID.isEmpty()) {
            writeLongTable();
        }
    }

    private void writeLongTable() throws IOException {
        final String normal = "The aggregated results from this batch run as a table.";
        final String summary = "Summary stats from this batch run as a table.";
        final String longTable = " This may include multiple rows per batch run.";
        final String wideTable = " This will include one row per batch run, but may include many columns per output.";

        final BatchTable longBatchTable = new BatchTable(inputColumns, inputsByUUID, groupColumns, outputColumns, outputValuesByUUIDAndGroup, failures);
        int rows = batchTableOutput.write(longBatchTable, "batch-results-long.tab", normal + longTable);

        log.debug("wrote {} long batch rows", rows);

        final BatchTable summarisedLongBatchTable = summariser.summariseOverSeed(longBatchTable);
        rows = batchTableOutput.write(summarisedLongBatchTable, "batch-results-long-summary.tab", summary + longTable);

        log.debug("wrote {} long batch summary rows", rows);

        final BatchTable wideBatchTable = widener.widen(longBatchTable);
        rows = batchTableOutput.write(wideBatchTable, "batch-results.tab", normal + wideTable);

        log.debug("wrote {} wide batch rows", rows);

        final BatchTable summarisedWideBatchTable = summariser.summariseOverSeed(wideBatchTable);
        rows = batchTableOutput.write(summarisedWideBatchTable, "batch-results-summary.tab", summary + wideTable);
    }

    private void writeErrors() {
        if (errorLog != null) {
            try {
                errorLog.close();
            } catch (final IOException ex) {

            } finally {
                errorLog = null;
            }
        }
    }

    @Override
    public Set<Class<? extends ISimulationLogEntry>> getEntryClasses() {
        return ImmutableSet.of(BatchInputEntry.class, BatchOutputEntry.class, SystemErrorLogEntry.class);
    }

    @Override
    public void handle(final ISimulationLogEntry entry) {
        if (entry instanceof SystemErrorLogEntry) {
            logFailedBatch((SystemErrorLogEntry) entry);
        } else if (entry instanceof BatchInputEntry) {
            logInputs((BatchInputEntry) entry);
        } else if (entry instanceof BatchOutputEntry) {
            logOutputs((BatchOutputEntry) entry);
        }
    }

    private void logOutputs(final BatchOutputEntry entry) {
        final ImmutableMap<String, String> groupings = entry.getReducedRowKey();
        final ImmutableMap<String, Double> outputs = entry.getColumns();

        if (outputValuesByUUIDAndGroup.contains(entry.getRunID(), groupings)) {
            error(String.format("Outputs were presented twice for batch run with id %s and groupings %s.", entry.getRunID(), groupings));
        }

        groupColumns.addAll(groupings.keySet());
        outputColumns.addAll(outputs.keySet());
        outputValuesByUUIDAndGroup.put(entry.getRunID(), groupings, outputs);
    }

    private void logInputs(final BatchInputEntry entry) {
        if (inputsByUUID.containsKey(entry.getRunID())) {
            error("Inputs were presented twice for batch run with id " + entry.getRunID());
        }

        inputColumns.addAll(entry.getColumns().keySet());
        inputsByUUID.put(entry.getRunID(), entry.getColumns());
    }

    private void logFailedBatch(final SystemErrorLogEntry entry) {
        failures.add(entry.getUuid());
    }

    private void error(final String message) {
        if (errorLog == null) {
            errorLog = new BufferedWriter(new OutputStreamWriter(
                    factory.createReportFile("reporting errors.txt", Optional.<IReportDescriptor>of(GenericDescriptor.of(Type.Problems)))));
        }

        try {
            errorLog.write(message + "\n");
        } catch (final IOException e) {
            log.error("Error writing the error log!", e);
        }
    }
}
