package uk.org.cse.nhm.reporting.standard.flat;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import uk.org.cse.nhm.reporting.report.SizeRecordingReportOutput;

public abstract class TSVOutput extends SizeRecordingReportOutput {
	public static final String EXTENSION = ".tab";
	@Override
	public void doWriteContent(OutputStream outputStream) throws IOException {
		final TSVWriter tsvWriter = new TSVWriter(outputStream);
        try {
        	writeContent(tsvWriter);
        } finally {
        	tsvWriter.flush();
        }
	}

	protected abstract void writeContent(TSVWriter tsvWriter);

	@Override
	public String getTemplate() {
		return "table.vm";
	}

	@Override
	public Type getType() {
		return Type.Data;
	}

	@Override
	public final String getPath() {
		return getPathWithoutExtension() + EXTENSION;
	}
	
	protected abstract String getPathWithoutExtension();

    public abstract String getDescription();
    public abstract Map<String, String> getColumnsAndDescriptions();
}
