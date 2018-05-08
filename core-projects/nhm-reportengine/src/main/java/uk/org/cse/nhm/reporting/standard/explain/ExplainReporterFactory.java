package uk.org.cse.nhm.reporting.standard.explain;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import org.apache.velocity.app.VelocityEngine;

import com.google.common.base.Optional;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.ExplainLogEntry;
import uk.org.cse.nhm.reporting.standard.GenericDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor.Type;
import uk.org.cse.nhm.reporting.standard.flat.MultiplexedReporter;
import uk.org.cse.nhm.reporting.standard.libraries.LibraryOutput;
import uk.org.cse.nhm.reporting.standard.resources.TemplatedResourceOutput;

public class ExplainReporterFactory implements IReporterFactory {

    private static final LibraryOutput Sankey = new LibraryOutput("explainSankey", LibraryOutput.D3Plugins, LibraryOutput.JQUERY);

    private final VelocityEngine templateEngine;

    @Inject
    public ExplainReporterFactory(final VelocityEngine velocity) {
        this.templateEngine = velocity;
    }

    @Override
    public IReporter startReporting(final IOutputStreamFactory factory) {
        return new ExplainReporter(factory, templateEngine);
    }

    static class ExplainReporter extends MultiplexedReporter<ExplainLogEntry> {

        private final VelocityEngine velocity;

        public ExplainReporter(final IOutputStreamFactory factory, final VelocityEngine velocity) {
            super(ExplainLogEntry.class, factory);
            this.velocity = velocity;
        }

        @Override
        protected IReporter createDelegate(final String name, final IOutputStreamFactory factory) {
            return new NamedExplainReporter(factory, name);
        }

        @Override
        public void close() throws IOException {
            super.close();

            final Set<String> childNames = getChildNames();

            if (childNames.isEmpty() == false) {
                final Map<String, Object> parameters = new HashMap<String, Object>();
                parameters.put("entries", childNames);

                final TemplatedResourceOutput output
                        = TemplatedResourceOutput.create()
                                .withTemplate(velocity, "explainSankey/explainSankeyReport.vm", parameters)
                                .withOutput("charts/explainSankey/explainSankeyReport.html")
                                .build();

                try (final OutputStream out = factory.createReportFile(output.getPath(),
                        Optional.<IReportDescriptor>of(GenericDescriptor.of(Type.Chart, Sankey)))) {
                    output.writeContent(out);
                }

            }
        }

        @Override
        protected String getName(final ExplainLogEntry entry) {
            return entry.getName();
        }

        static class NamedExplainReporter implements IReporter {

            private final String name;
            private final IOutputStreamFactory factory;
            private final List<ExplainLogEntry> entries = new ArrayList<>();

            public NamedExplainReporter(final IOutputStreamFactory factory, final String name) {
                this.name = name;
                this.factory = factory;
            }

            @Override
            public void close() throws IOException {
                // construct the actual output
                if (entries.isEmpty()) {
                    return;
                }

                {
                    final ExplainJSONOutput explainJSONOutput
                            = new ExplainJSONOutput(name, new TreeSet<ExplainLogEntry>(entries));

                    try (final OutputStream out = factory.createReportFile(
                            explainJSONOutput.getPath(),
                            Optional.<IReportDescriptor>absent()
                    )) {
                        explainJSONOutput.writeContent(out);
                    }
                }
                {
                    final CollapsedExplainJSONOutput collapsedExplainJSONOutput
                            = new CollapsedExplainJSONOutput(name, new TreeSet<ExplainLogEntry>(entries));

                    try (final OutputStream out = factory.createReportFile(
                            collapsedExplainJSONOutput.getPath(),
                            Optional.<IReportDescriptor>of(GenericDescriptor.of(Type.Data))
                    )) {
                        collapsedExplainJSONOutput.writeContent(out);
                    }
                }
            }

            @Override
            public Set<Class<? extends ISimulationLogEntry>> getEntryClasses() {
                return Collections.<Class<? extends ISimulationLogEntry>>singleton(ExplainLogEntry.class);
            }

            @Override
            public void handle(final ISimulationLogEntry entry) {
                if (entry instanceof ExplainLogEntry) {
                    entries.add((ExplainLogEntry) entry);
                }
            }

        }
    }
}
