package uk.org.cse.nhm.reporting.standard;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Set;

import javax.inject.Inject;
import javax.inject.Provider;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.reporting.IReportEngine;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;

/**
 * This reporting service is an entry point for things that want to create
 * reporting sessions.
 *
 * A reporting session can be connected to an event queue or directly to a
 * simulator, to create some reports.
 *
 * @author hinton
 *
 */
public class StandardReportEngine implements IReportEngine {

    private final Set<IReporterFactory> reporterFactories;
    private final Provider<IZippingFileStreamFactory> streamFactoryProvider;

    @Inject
    public StandardReportEngine(final Set<IReporterFactory> reporterFactories,
            final Provider<IZippingFileStreamFactory> streamFactoryProvider) {
        this.reporterFactories = reporterFactories;
        this.streamFactoryProvider = streamFactoryProvider;
    }

    @Override
    public IReportingSession startReportingSession() {
        final IZippingFileStreamFactory streamFactory = streamFactoryProvider.get();

        final ImmutableList.Builder<IReporter> reporters = ImmutableList.builder();
        for (final IReporterFactory factory : reporterFactories) {
            final IReporter reporter = factory.startReporting(streamFactory);
            reporters.add(reporter);
        }

        return new Session(streamFactory, reporters.build());
    }

    private static class Session implements IReportingSession {

        private static final Logger log = LoggerFactory.getLogger(StandardReportEngine.class);
        private final IZippingFileStreamFactory streamFactory;
        private final ImmutableList<IReporter> build;
        long counter = 0;
        final DateTime creationDate;
        boolean closed = false;

        public Session(final IZippingFileStreamFactory streamFactory, final ImmutableList<IReporter> build) {
            this.streamFactory = streamFactory;
            this.build = build;
            this.creationDate = new DateTime();
        }

        @Override
        public void close() throws IOException {
            if (closed) {
                return;
            }
            try {
                log.debug("closing session; reported on {} log entries", counter);
                for (final IReporter r : build) {
                    try {
                        r.close();
                    } catch (final Throwable th) {
                        log.error("While closing {}", r, th);
                        th.printStackTrace();
                    }
                }
                try {
                    streamFactory.setStartAndEndDates(creationDate, new DateTime());
                    streamFactory.close();
                } catch (final Throwable th) {
                    log.error("While finishing report", th);
                    th.printStackTrace();
                    if (th instanceof IOException) {
                        throw (IOException) th;
                    }
                }
                log.debug("closed");
            } finally {
                closed = true;
            }
        }

        @Override
        public Path getResultPath() {
            return streamFactory.getZipFilePath();
        }

        @Override
        public void acceptLogEntry(final ISimulationLogEntry entry) {
            counter++;
            for (final IReporter r : build) {
                for (final Class<? extends ISimulationLogEntry> e : r.getEntryClasses()) {
                    if (e.isInstance(entry)) {
                        try {
                            r.handle(entry);
                        } catch (final Throwable th) {
                            log.error("Error when passing {} to {}", entry, r, th);
                        }
                    }
                }
            }
        }
    }
}
