package uk.org.cse.nhm.reporting.errors;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.reporting.standard.GenericDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;

abstract class AbsErrorReporter<T> implements IReporter {
	protected static final Logger log = LoggerFactory.getLogger(AbsErrorReporter.class);
	
	private final IOutputStreamFactory streamFactory;
	private final String reportName;
	private final Class<? extends ISimulationLogEntry> entryClass;
	
	private Writer writer;
	
	public AbsErrorReporter(final IOutputStreamFactory streamFactory, final String reportName, final Class<? extends ISimulationLogEntry> entryClass) {
		this.streamFactory = streamFactory;
		this.reportName = reportName;
		this.entryClass = entryClass;
	}

	@Override
	public final void close() throws IOException {
		if(writer != null) {
			writer.close();
		}
	}

	@Override
	public final Set<Class<? extends ISimulationLogEntry>> getEntryClasses() {
		return ImmutableSet.<Class<? extends ISimulationLogEntry>>of(entryClass);
	}

	@SuppressWarnings("unchecked")
	@Override
	public final void handle(final ISimulationLogEntry entry) {
		if(writer == null) {
			final OutputStream output = streamFactory.createReportFile(reportName, Optional.<IReportDescriptor>of(GenericDescriptor.of(IReportDescriptor.Type.Problems)));
			writer = new BufferedWriter(new OutputStreamWriter(output));
		}
		
		if (entryClass.isInstance(entry)) {
			try {
				writer.write(output((T) entry));
			} catch (final IOException e) {
				log.error("Error writing the " + reportName + " log " + e.getMessage());
			}
		}
	}

	protected abstract String output(T entry);
}
