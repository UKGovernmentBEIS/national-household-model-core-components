package uk.org.cse.nhm.reporting.standard.timeseries;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.velocity.app.VelocityEngine;

import com.google.common.base.Optional;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.reporting.report.IReportOutput;
import uk.org.cse.nhm.reporting.standard.GenericDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor.Type;
import uk.org.cse.nhm.reporting.standard.flat.MultiplexedReporter;
import uk.org.cse.nhm.reporting.standard.libraries.LibraryOutput;
import uk.org.cse.nhm.reporting.standard.resources.TemplatedResourceOutput;

public class TimeSeriesReporterFactory implements IReporterFactory {
	private final VelocityEngine templateEngine;

	private static final LibraryOutput TimeSeries = new LibraryOutput("timeSeries", LibraryOutput.RICKSHAW);
	
	@Inject
	public TimeSeriesReporterFactory(final VelocityEngine templateEngine) {
		this.templateEngine = templateEngine;
	}

	
	@Override
	public IReporter startReporting(final IOutputStreamFactory factory) {
		return new TimeSeriesReporter(factory, templateEngine);
	}

	
	static class TimeSeriesReporter extends MultiplexedReporter<AggregateLogEntry> {
		final VelocityEngine velocity;
		public TimeSeriesReporter(final IOutputStreamFactory factory, final VelocityEngine velocity) {
			super(AggregateLogEntry.class, factory);
			this.velocity = velocity;
		}

		@Override
		protected IReporter createDelegate(final String name, final IOutputStreamFactory factory) {
			return new NamedTimeSeriesReporter(factory, name, velocity);
		}

		@Override
		protected String getName(final AggregateLogEntry entry) {
			return entry.getName();
		}

		static class NamedTimeSeriesReporter implements IReporter {
			private final IOutputStreamFactory factory;
			private final String name;
			private final ArrayList<AggregateLogEntry> entries = new ArrayList<>();
			private final VelocityEngine velocity;

			public NamedTimeSeriesReporter(final IOutputStreamFactory factory,final String name, final VelocityEngine velocity) {
				this.factory = factory;
				this.name = name;
				this.velocity = velocity;
			}
			
			@Override
			public void close() throws IOException {
				final TimeSeriesBuilder jsonData = new TimeSeriesBuilder(name, entries);
				try (final OutputStream jsonOut = factory.createReportFile(jsonData.getPath(), 
						Optional.<IReportDescriptor>absent())) {
					jsonData.writeContent(jsonOut);
				}
				
				final Map<String, Object> parameters = new HashMap<String, Object>();
				parameters.put("report", name);
				final TemplatedResourceOutput page = 
						TemplatedResourceOutput.create()
							.withTemplate(velocity, "timeSeries/timeSeries.vm", parameters)
							.withOutput(IReportOutput.CHARTS + "timeSeries/" + name + ".html")
							.build();
				
				try (final OutputStream pageOutput = factory.createReportFile(page.getPath(), 
						Optional.<IReportDescriptor>of(GenericDescriptor.of(Type.Chart, TimeSeries)))) {
					page.writeContent(pageOutput);
				}
			}

			@Override
			public Set<Class<? extends ISimulationLogEntry>> getEntryClasses() {
				return Collections.<Class<? extends ISimulationLogEntry>>singleton(AggregateLogEntry.class);
			}

			@Override
			public void handle(final ISimulationLogEntry entry) {
				if (entry instanceof AggregateLogEntry) {
					entries.add((AggregateLogEntry) entry);
				}
			}
		}
	}
}
