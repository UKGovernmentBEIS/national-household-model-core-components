package uk.org.cse.nhm.reporting.standard;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.Set;

import javax.inject.Inject;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.SurveyCaseLogEntry;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor.Type;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;

public class StockInputReporter implements IReporter {
	private final IOutputStreamFactory factory;
	private final ObjectMapper mapper;
	
	private Writer writer;
	
	public static class Factory implements IReporterFactory {
		private ObjectMapper mapper;

		@Inject
		public Factory(final ObjectMapper mapper) {
			this.mapper = mapper;
		}

		@Override
		public IReporter startReporting(IOutputStreamFactory factory) {
			return new StockInputReporter(factory, mapper);
		}
	}
	
	public StockInputReporter(IOutputStreamFactory factory, final ObjectMapper mapper) {
		this.factory = factory;
		this.mapper = mapper;
	}

	@Override
	public void close() throws IOException {
		if (writer != null) {
			try {
				writer.close();
			} finally {
				writer = null;
			}
		}
	}

	@Override
	public Set<Class<? extends ISimulationLogEntry>> getEntryClasses() {
		return ImmutableSet.<Class<? extends ISimulationLogEntry>>of(SurveyCaseLogEntry.class);
	}

	@Override
	public void handle(ISimulationLogEntry entry) {
		if (entry instanceof SurveyCaseLogEntry) {
			try {
				final Writer out = getWriter();
				final SurveyCase surveyCase = ((SurveyCaseLogEntry)entry).getSurveyCase();
				out.write(mapper.writeValueAsString(surveyCase));
				out.write('\n');
			} catch (final IOException e) {
				throw new RuntimeException("Exception creating stock report output", e);
			}
		}
	}

	private Writer getWriter() {
		if (writer == null) {
			writer = new BufferedWriter(new OutputStreamWriter(
					factory.createReportFile("stock/stock.json", 
							Optional.<IReportDescriptor>of(
									GenericDescriptor.of(Type.Input)
									))));
		}
		return writer;
	}

}
