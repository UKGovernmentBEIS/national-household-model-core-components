package uk.org.cse.nhm.reporting.standard.flat;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry.Type;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IOutputStreamFactory;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReporter;

public abstract class SimpleTabularReporter<T extends ISimulationLogEntry>
	implements IReporter {
	private final Class<T> clazz;
	private final String name;
	private final TableType tableType;
	private final IOutputStreamFactory factory;
	private TSVWriter writer;
	
	private List<Field> fields = Collections.emptyList();
	private boolean started = false;
	private final Type headerType;

	public SimpleTabularReporter(
			final IOutputStreamFactory factory, 
			final Class<T> clazz,
			final TableType tableType,
			final String name,
			final Type headerType) {
		this.factory = factory;
		this.clazz = clazz;
		this.tableType = tableType;
		this.name = name;
		this.headerType = headerType;
	}
	
	public SimpleTabularReporter(
			final IOutputStreamFactory factory, 
			final Class<T> clazz,
			final TableType tableType,
			final String name) {
		this(factory, clazz, tableType, name, null);
	}

	@Override
	public Set<Class<? extends ISimulationLogEntry>> getEntryClasses() {
		if (tableType != null) {
			return ImmutableSet.<Class<? extends ISimulationLogEntry>>of(clazz, ReportHeaderLogEntry.class);
		} else {
			return ImmutableSet.<Class<? extends ISimulationLogEntry>>of(clazz);
		}
	}

	@Override
	public void handle(ISimulationLogEntry entry) {
		if (clazz.isInstance(entry)) {
			doHandle(clazz.cast(entry));
		} else if (entry instanceof ReportHeaderLogEntry) {
			if (((ReportHeaderLogEntry) entry).getType() == this.headerType) {
				maybeWriteHeaders();
			}
		}
	}
	
	protected abstract void doHandle(final T entry);

	protected void write(String... fields) {
		maybeWriteHeaders();
		writer.writeNext(fields);
	}
	
	public static class Field {
		public final String name;
		public final String description;
		public final String type;
		
		Field(String name, String description, String type) {
			super();
			this.name = name;
			this.description = description;
			this.type = type;
		}
		
		public static Field of(final String name, final String description, final String type) {
			return new Field(name, description, type);
		}
	}
	
	protected void maybeWriteHeaders() {
		if (started) return;
		started = true;
		writer = new TSVWriter(
				factory.createReportFile(
					String.format("%s/%s.tab",
							tableType.toString().toLowerCase(), 
							name),
				
				Optional.<IReportDescriptor>of(
						TabularDescriptor.of(
								getDescription(),
								fields
								)
						)));
		
		final String[] headers = new String[fields.size()];
		for (int i = 0; i<headers.length; i++) {
			headers[i] = fields.get(i).name;
		}
		writer.writeNext(headers);
	}
	
	protected void start(final List<Field> fields) {
		this.fields = fields;
	}

	protected abstract String getDescription();
	
	@Override
	public void close() throws IOException {
		if (writer != null) {
			writer.flush();
			writer.close();
		}
	}
}
