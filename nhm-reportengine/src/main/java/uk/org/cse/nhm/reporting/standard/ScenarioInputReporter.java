package uk.org.cse.nhm.reporting.standard;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.apache.commons.io.IOUtils;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.larkery.jasb.sexp.parse.DataSourceSnapshot;

import uk.org.cse.nhm.ipc.api.IncludeAddress;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.ScenarioSnapshotLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;

public class ScenarioInputReporter implements IReporter {
	private final IOutputStreamFactory factory;

	@Inject
	public ScenarioInputReporter(final IOutputStreamFactory factory) {
		this.factory = factory;
	}

	@Override
	public void close() throws IOException {
		
	}

	@Override
	public Set<Class<? extends ISimulationLogEntry>> getEntryClasses() {
		return ImmutableSet.<Class<? extends ISimulationLogEntry>>
			of(ScenarioSnapshotLogEntry.class);
	}

	@Override
	public void handle(final ISimulationLogEntry entry) {
		if (entry instanceof ScenarioSnapshotLogEntry) {
			final ScenarioSnapshotLogEntry s = (ScenarioSnapshotLogEntry) entry;
			final DataSourceSnapshot dss = s.getSnapshot().contents();
			
			for (final Map.Entry<String, String> e : dss.getContentsMap().entrySet()) {
				String filename;
				
				try {
					final URI uri = URI.create(e.getKey());
					final IncludeAddress ia = IncludeAddress.parse(uri);
					if (ia.isEmpty()) {
						filename = e.getKey();
					} else {
						filename = ia.makeFileName();
					}
				} catch (Exception ex) {
					filename = e.getKey();
				}
				
				try (final OutputStream os = factory.createReportFile(
						filename, 
						Optional.<IReportDescriptor>of(GenericDescriptor.of(IReportDescriptor.Type.Input)))) {
					IOUtils.write(e.getValue(), os, StandardCharsets.UTF_8);
				} catch (final IOException e1) {
					throw new RuntimeException("Exception creating scenario report output", e1);
				}
			}
		}
	}
}
