package uk.org.cse.nhm.reporting.standard;

import java.io.IOException;
import java.io.OutputStream;

import uk.org.cse.nhm.reporting.report.IReportOutput;
import uk.org.cse.nhm.reporting.report.ReportMetadata;

public class MetadataOutput implements IReportOutput {
	private final ReportMetadata properties;
	
	MetadataOutput(final ReportMetadata properties) {
		super();
		this.properties = properties;
	}

	@Override
	public String getPath() {
		return ReportMetadata.REPORT_ENTRY_NAME;
	}

	@Override
	public void writeContent(final OutputStream outputStream) throws IOException {
		properties.write(outputStream);
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
	public String getHumanReadableSize() {
		return "0";
	}

}
