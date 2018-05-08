package uk.org.cse.nhm.reporting.standard;

import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.velocity.app.VelocityEngine;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.sexp.Atom;
import com.larkery.jasb.sexp.Comment;
import com.larkery.jasb.sexp.Delim;
import com.larkery.jasb.sexp.ISExpressionVisitor;
import com.larkery.jasb.sexp.Invocation;
import com.larkery.jasb.sexp.Node;
import com.larkery.jasb.sexp.NodeBuilder;
import com.larkery.jasb.sexp.Seq;
import com.larkery.jasb.sexp.errors.IErrorHandler;
import com.larkery.jasb.sexp.parse.Cutout;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.ScenarioSnapshotLogEntry;
import uk.org.cse.nhm.macros.LookupTableMacro;
import uk.org.cse.nhm.reporting.report.IReportOutput;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor.Type;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;
import uk.org.cse.nhm.reporting.standard.resources.TemplatedResourceOutput;

public class LookupTablesReporter implements IReporter {

    public static class Factory implements IReporterFactory {

        final VelocityEngine templateEngine;

        @Inject
        public Factory(final VelocityEngine templateEngine) {
            this.templateEngine = templateEngine;
        }

        @Override
        public IReporter startReporting(final IOutputStreamFactory factory) {
            return new LookupTablesReporter(templateEngine, factory);
        }

    }

    private final IOutputStreamFactory factory;
    private final List<LookupTableForVelocity> tidied = new LinkedList<>();
    private final VelocityEngine templateEngine;

    LookupTablesReporter(final VelocityEngine templateEngine, final IOutputStreamFactory factory) {
        this.templateEngine = templateEngine;
        this.factory = factory;
    }

    @Override
    public void close() throws IOException {
        if (tidied.isEmpty() == false) {
            final TemplatedResourceOutput template
                    = TemplatedResourceOutput.create()
                            .withTemplate(templateEngine, "lookups/lookuptables.vm",
                                    new HashMap<>(ImmutableMap.<String, Object>of(
                                            "lookupTables", tidied
                                    ))
                            )
                            .withOutput(IReportOutput.LOOKUPS + "lookuptables.html")
                            .build();

            try (final OutputStream o = factory.createReportFile(template.getPath(),
                    Optional.<IReportDescriptor>of(
                            GenericDescriptor.of(Type.Input)
                    ))) {
                template.doWriteContent(o);
            }
        }
    }

    @Override
    public Set<Class<? extends ISimulationLogEntry>> getEntryClasses() {
        return ImmutableSet.<Class<? extends ISimulationLogEntry>>of(ScenarioSnapshotLogEntry.class);
    }

    @Override
    public void handle(final ISimulationLogEntry entry) {
        if (entry instanceof ScenarioSnapshotLogEntry) {
            final ScenarioSnapshotLogEntry ssle = (ScenarioSnapshotLogEntry) entry;

            final List<Node> tableNodes = new LinkedList<>();

            //TODO expand templates but not the lookuptable macro.
            ssle.getSnapshot().getUntemplated().accept(new Cutout<NodeBuilder>(ISExpressionVisitor.IGNORE) {
                @Override
                protected Optional<NodeBuilder> cut(final String head) {
                    if (head.equals(LookupTableMacro.LOOKUP_TABLE_NAME)) {
                        return Optional.of(NodeBuilder.create());
                    } else {
                        return Optional.absent();
                    }
                }

                @Override
                protected void paste(final NodeBuilder q) {
                    final Node n = q.getBestEffort();
                    tableNodes.add(n);
                    // but wait, we need to visit it
                    final Invocation i = Invocation.of(n, IErrorHandler.SLF4J);
                    if (i != null) {
                        if (i.arguments.containsKey("name")) {
                            atom("Lookup table " + i.arguments.get("name"));
                        } else {
                            atom("Lookup table at " + formatLocationOfInvocation(i));
                        }
                    } else {
                        atom("A malformed lookup table");
                    }
                }
            });

            // so now we have all the lookup-table elements, we need to parse each one into something useful
            for (final Node n : tableNodes) {
                // the extra true argument here causes the invocation to retain comments in the
                // remaining arguments list.
                final Invocation inv = Invocation.of(n, IErrorHandler.SLF4J, true);
                if (inv != null) {
                    final LookupTableForVelocity l = LookupTableForVelocity.fromInvocation(inv);
                    if (l != null) {
                        tidied.add(l);
                    }
                }
            }
        }
    }

    public static class LookupTableForVelocity {

        public final String name;
        public final String location;
        public final List<String> rowKeys;
        public final String columnKey;
        public final List<List<Cell>> header;
        public final List<List<Cell>> rows;
        public final String defaultValue;
        private int maxColumnIndex;

        /**
         * @param inv
         */
        LookupTableForVelocity(final Invocation inv) {
            if (inv.arguments.containsKey(LookupTableMacro.NAME_ARG)) {
                final Node nameNode = inv.arguments.get(LookupTableMacro.NAME_ARG);
                final String unescaped;
                if (nameNode instanceof Atom) {
                    unescaped = ((Atom) nameNode).getValue();
                } else {
                    unescaped = String.valueOf(nameNode);
                }
                this.name = StringEscapeUtils.escapeHtml(unescaped);
            } else {
                this.name = "Unnamed table";
            }

            this.location = formatLocationOfInvocation(inv);

            if (!inv.arguments.containsKey(LookupTableMacro.ROW_KEYS_ARG)) {
                throw new IllegalArgumentException();
            }

            final Node rowKeysNode = inv.arguments.get(LookupTableMacro.ROW_KEYS_ARG);

            final Seq rowKeysSeq;
            if (!(rowKeysNode instanceof Seq)) {
                rowKeysSeq = Seq.builder(rowKeysNode.getLocation(),
                        Delim.Bracket).add(rowKeysNode).build(rowKeysNode.getLocation());
            } else {
                rowKeysSeq = (Seq) rowKeysNode;
            }

            this.rowKeys = new LinkedList<>();
            if (rowKeysSeq.getDelimeter() == Delim.Bracket) {
                for (final Node n : rowKeysSeq.exceptComments()) {
                    rowKeys.add(StringEscapeUtils.escapeHtml(String.valueOf(n)));
                }
            } else {
                rowKeys.add(StringEscapeUtils.escapeHtml(String.valueOf(rowKeysSeq)));
            }

            if (inv.arguments.containsKey(LookupTableMacro.COLUMN_KEY_ARG)) {
                columnKey = StringEscapeUtils.escapeHtml(String.valueOf(inv.arguments.get(LookupTableMacro.COLUMN_KEY_ARG)));
            } else {
                columnKey = null;
            }

            if (inv.arguments.containsKey(LookupTableMacro.DEFAULT_ARG)) {
                defaultValue = StringEscapeUtils.escapeHtml(String.valueOf(inv.arguments.get(LookupTableMacro.DEFAULT_ARG)));
            } else {
                defaultValue = null;
            }

            this.maxColumnIndex = 0;

            header = new LinkedList<>();
            rows = new LinkedList<>();

            List<List<Cell>> out = header;
            final StringBuffer commentBuffer = new StringBuffer();
            for (final Node n : inv.remainder) {
                if (n instanceof Seq) {
                    if (commentBuffer.length() > 0) {
                        out.add(ImmutableList.of(new Cell("This is a comment from the table input", commentBuffer.toString(), true)));
                        commentBuffer.setLength(0);
                    }
                    final LinkedList<Cell> currentRow = new LinkedList<>();
                    int columnIndex = 0;
                    for (final Node col : ((Seq) n).exceptComments()) {
                        if (out == header) {
                            currentRow.add(
                                    new Cell(
                                            columnIndex < rowKeys.size()
                                            ? "This is just the column name for row key " + rowKeys.get(columnIndex)
                                            : (columnKey == null ? "This column contains the value to use"
                                                    : "This column contains the value to use when " + columnKey
                                                    + " matches " + String.valueOf(col)),
                                            String.valueOf(col),
                                            false
                                    )
                            );
                        } else {
                            currentRow.add(
                                    new Cell(
                                            columnIndex < rowKeys.size()
                                            ? "This row can only match when " + rowKeys.get(columnIndex) + " matches " + String.valueOf(col)
                                            : "This is a value which the lookup table will produce when a house matches the row and column constraints.",
                                            String.valueOf(col),
                                            false
                                    )
                            );
                        }
                        columnIndex++;
                        this.maxColumnIndex = Math.max(this.maxColumnIndex, columnIndex);
                    }
                    out.add(currentRow);
                    out = rows;
                } else if (n instanceof Comment) {
                    // we want to glue multiple comments together
                    commentBuffer.append(((Comment) n).getText());
                    commentBuffer.append(" ");
                }
            }

            if (commentBuffer.length() > 0) {
                out.add(ImmutableList.of(new Cell("This is a comment from the table input", commentBuffer.toString(), true)));
                commentBuffer.setLength(0);
            }

            if (rows.isEmpty()) {
                throw new IllegalArgumentException();
            }
        }

        public String getName() {
            return name;
        }

        public String getLocation() {
            return location;
        }

        public List<String> getRowKeys() {
            return rowKeys;
        }

        public String getColumnKey() {
            return columnKey;
        }

        public List<List<Cell>> getHeader() {
            return header;
        }

        public List<List<Cell>> getRows() {
            return rows;
        }

        public int getNumberOfColumns() {
            return maxColumnIndex;
        }

        public String getDefaultValue() {
            return defaultValue;
        }

        public static LookupTableForVelocity fromInvocation(final Invocation inv) {
            // process inv into lookup table
            try {
                return new LookupTableForVelocity(inv);
            } catch (final IllegalArgumentException iae) {
                return null;
            }
        }

        public static class Cell {

            public final String title;
            public final String body;
            public final boolean comment;

            Cell(final String title, final String body, final boolean comment) {
                super();
                this.comment = comment;
                this.title = StringEscapeUtils.escapeHtml(title);
                this.body = StringEscapeUtils.escapeHtml(body);
            }

            public String getTitle() {
                return title;
            }

            public String getBody() {
                return body;
            }

            public boolean isComment() {
                return comment;
            }
        }
    }

    static String formatLocationOfInvocation(final Invocation inv) {
        return StringEscapeUtils.escapeHtml(inv.node.getLocation().toString());
    }
}
