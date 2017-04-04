package uk.org.cse.nhm.reporting.errors;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;

import javax.inject.Inject;

import org.apache.velocity.app.VelocityEngine;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.LinkedHashMultimap;
import com.google.common.collect.SetMultimap;

import au.com.bytecode.opencsv.CSVWriter;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.reporting.report.IReportOutput;
import uk.org.cse.nhm.reporting.report.SizeRecordingReportOutput;
import uk.org.cse.nhm.reporting.standard.GenericDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor.Type;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;
import uk.org.cse.nhm.reporting.standard.jsonp.JSONPOutputUtility;
import uk.org.cse.nhm.reporting.standard.libraries.LibraryOutput;
import uk.org.cse.nhm.reporting.standard.resources.TemplatedResourceOutput;

public class WarningReporter implements IReporter {
	protected static final Logger log = LoggerFactory
			.getLogger(WarningReporter.class);
	
	private static final LibraryOutput Warnings = new LibraryOutput("warnings", LibraryOutput.D3);	

	private final IOutputStreamFactory streamFactory;

	private final SetMultimap<String, Map<String, String>> warnings = LinkedHashMultimap
			.create();

	private final VelocityEngine velocityEngine;

	@Inject
	public WarningReporter(final IOutputStreamFactory streamFactory, final VelocityEngine velocityEngine) {
		this.streamFactory = streamFactory;
		this.velocityEngine = velocityEngine;
	}
	
	@Override
	public void close() throws IOException {
		if (!warnings.isEmpty()) {
			final WarningsJSONOutput jsonp = new WarningsJSONOutput(warnings);
			
			try (final OutputStream out = streamFactory.createReportFile(
					jsonp.getPath(), 
					Optional.<IReportDescriptor>absent())) {
				jsonp.writeContent(out);
			}
			
			// generate a plain log; could produce a text-table
			
			try (final CSVWriter w = new CSVWriter(
					new OutputStreamWriter(streamFactory.createReportFile("warnings/warnings.tab",
					Optional.<IReportDescriptor>of(GenericDescriptor.of(Type.Problems))
							)
							,StandardCharsets.UTF_8
							),
							'\t',
							CSVWriter.NO_QUOTE_CHARACTER
					)) {
				final Set<String> allAttributes = new TreeSet<String>();
				for (final String warningType : warnings.keySet()) {
					for (final Map<String,String> warningAttributes : warnings.get(warningType)) {
						allAttributes.addAll(warningAttributes.keySet());
					}
				}
				final String[] row = new String[allAttributes.size() + 1];
				row[0] = "Warning";
				int i = 1;
				for (final String att : allAttributes) {
					row[i++] = att;
				}
				w.writeNext(row);
				for (final String warningType : warnings.keySet()) {
					row[0] = warningType;
					for (final Map<String, String> warningAttributes : warnings.get(warningType)) {
						i = 1;
						for (final String att : allAttributes) {
							if (warningAttributes.containsKey(att)) {
								row[i++] = warningAttributes.get(att); 
							} else {
								row[i++] = "";
							}
						}
						
                        w.writeNext(row);
					}
				}
				w.flush();
			}
			
			// generate the warnings page.
			
			final IReportOutput page = TemplatedResourceOutput.
					create()
					.withTemplate(velocityEngine, "warnings/warnings.vm", Collections.<String, Object>emptyMap())
					.withOutput("warnings/warnings.html")
					.build();
			
			try (final OutputStream pageOutput = streamFactory.createReportFile(page.getPath(), 
					Optional.<IReportDescriptor>of(GenericDescriptor.of(Type.Problems, Warnings)))) {
				page.writeContent(pageOutput);
			}
		}
	}

	@Override
	public Set<Class<? extends ISimulationLogEntry>> getEntryClasses() {
		return ImmutableSet
				.<Class<? extends ISimulationLogEntry>> of(WarningLogEntry.class);
	}

	@Override
	public void handle(final ISimulationLogEntry entry) {
		if (entry instanceof WarningLogEntry) {
			final WarningLogEntry warning = (WarningLogEntry) entry;
			warnings.put(warning.getWarning(), warning.getData());
		}
	}

	static class WarningsJSONOutput extends SizeRecordingReportOutput {
		private final List<WarningRow> rows = new ArrayList<>();

		public WarningsJSONOutput(final SetMultimap<String, Map<String, String>> warnings) {
			for(final String k : warnings.keySet()) {
				rows.add(new WarningRow(k, warnings.get(k)));
			}
		}
		
		@Override
		public String getPath() {
			return IReportOutput.DATA + "warnings.jsonp";
		}

		@Override
		public String getTemplate() {
			return IReportOutput.GENERIC_TEMPLATE;
		}

		@Override
		public Type getType() {
			return Type.Data;
		}

		@Override
		protected void doWriteContent(final OutputStream outputStream)
				throws IOException {
			JSONPOutputUtility.writeAsJSONP(this, rows, outputStream);
		}

	}
	
	static class WarningRow {
		public final String message;
		public final Set<Map<String, String>> data;

		public WarningRow(final String message, final Set<Map<String, String>> data) {
			this.message = message;
			this.data = data;
		}
	}

	public static class Factory implements IReporterFactory {
		private final VelocityEngine velocity;

		@Inject
		public Factory (final VelocityEngine velocity) {
			this.velocity = velocity;
		}
		
		@Override
		public IReporter startReporting(final IOutputStreamFactory factory) {
			return new WarningReporter(factory, velocity);
		}
	}
}
