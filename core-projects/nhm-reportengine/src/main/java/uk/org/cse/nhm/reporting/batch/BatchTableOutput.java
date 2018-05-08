package uk.org.cse.nhm.reporting.batch;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.Table.Cell;

import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.flat.SimpleTabularReporter.Field;
import uk.org.cse.nhm.reporting.standard.flat.TSVWriter;
import uk.org.cse.nhm.reporting.standard.flat.TabularDescriptor;

class BatchTableOutput {

    private final IOutputStreamFactory factory;

    public BatchTableOutput(final IOutputStreamFactory factory) {
        this.factory = factory;
    }

    public final int write(final BatchTable table, final String filename, final String description) throws IOException {
        final List<Field> fields = buildFields(table);

        try (final TSVWriter writer = new TSVWriter(
                factory.createReportFile(filename,
                        Optional.<IReportDescriptor>of(
                                TabularDescriptor.of(description, fields)
                        ))
        )) {

            final String[] row = new String[fields.size()];
            int counter = 0;

            int i = 0;
            for (final Field f : fields) {
                row[i++] = f.name;
            }

            writer.writeNext(row);

            for (final Cell<UUID, Map<String, String>, Map<String, Double>> cell : table.getOutputValuesByUUIDAndGroup().cellSet()) {
                i = 0;

                final UUID runID = cell.getRowKey();

                final Map<String, String> inputs = table.getInputRows().get(runID);
                for (final String input : table.getInputColumns()) {
                    if (inputs.containsKey(input)) {
                        row[i++] = inputs.get(input);
                    } else {
                        row[i++] = "";
                    }
                }

                final Map<String, String> groups = cell.getColumnKey();
                for (final String group : table.getGroupColumns()) {
                    if (groups.containsKey(group)) {
                        row[i++] = groups.get(group);
                    } else {
                        row[i++] = "";
                    }
                }

                final Map<String, Double> outputsForRunAndGroup = cell.getValue();
                for (final String output : table.getOutputColumns()) {
                    if (outputsForRunAndGroup.containsKey(output)) {
                        row[i++] = String.format("%.4f", outputsForRunAndGroup.get(output));
                    } else {
                        row[i++] = "";
                    }
                }

                row[i++] = Boolean.toString(!table.getFailures().contains(runID));

                writer.writeNext(row);
                counter++;
            }

            return counter;
        }
    }

    final List<Field> buildFields(final BatchTable table) {
        final ImmutableList.Builder<Field> fields = ImmutableList.builder();

        for (final String in : table.getInputColumns()) {
            fields.add(Field.of(in, "a batch input", "String"));
        }

        for (final String group : table.getGroupColumns()) {
            fields.add(Field.of(group, "a group or date", "String"));
        }

        for (final String out : table.getOutputColumns()) {
            fields.add(Field.of(out, "a batch output", "Double"));
        }

        fields.add(Field.of("success", "whether a run succeeded", "Boolean"));

        return fields.build();
    }
}
