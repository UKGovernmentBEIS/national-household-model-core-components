package uk.org.cse.nhm.reporting.batch;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.batch.BatchInputEntry;
import uk.org.cse.nhm.logging.logentry.batch.BatchLogEntryConverter;
import uk.org.cse.nhm.logging.logentry.batch.BatchOutputEntry;
import uk.org.cse.nhm.logging.logentry.errors.SystemErrorLogEntry;
import uk.org.cse.nhm.reporting.IReportEngine;
import uk.org.cse.nhm.reporting.standard.IReportingSession;

public class StandaloneBatchReportEngine implements IReportEngine {
    class Session implements IReportingSession {
        private Map<String, String> inputs = new HashMap<>();
        private List<Map<String, Object>> outputs = new ArrayList<>();
        private List<String> errors = new ArrayList<>();

        private Path result;

        @Override
        public void close() throws IOException {
            final Map<String, Object> output = new HashMap<String, Object>();
            output.put("inputs", inputs);
            output.put("outputs", outputs);
            output.put("errors", errors);
            // make an objectmapper and write the json
            result = Files.createTempFile("batch-results-", ".json");
            try (final BufferedWriter bw = Files.newBufferedWriter(
                     result,
                     StandardCharsets.UTF_8)) {
                mapper.writeValue(bw, output);
            }
        }

        @Override
        public Path getResultPath() {
            return result;
        }

        @Override
        public void acceptLogEntry(final ISimulationLogEntry entry) {
            if (entry instanceof SystemErrorLogEntry) {
                final SystemErrorLogEntry sele = (SystemErrorLogEntry) entry;
                errors.add(sele.getMessage());
            } else if (entry instanceof BatchInputEntry) {
                final BatchInputEntry bie = (BatchInputEntry) entry;
                inputs.putAll(bie.getColumns());
            } else {
                final Optional<BatchOutputEntry> boe =
                    BatchLogEntryConverter.convert(entry, null);
                if (boe.isPresent()) {
                    final Map<String, Object> row = new HashMap<>();
                    row.putAll(boe.get().getReducedRowKey());
                    row.putAll(boe.get().getColumns());
                    outputs.add(row);
                }
            }
        }
    }

    protected final ObjectMapper mapper;

    @Inject
    public StandaloneBatchReportEngine(final ObjectMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public IReportingSession startReportingSession() throws IOException {
        return new Session();
    }
}
