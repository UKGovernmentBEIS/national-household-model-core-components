package uk.org.cse.nhm.reporting.standard.resources;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import uk.org.cse.nhm.reporting.report.IReportOutput;
import uk.org.cse.nhm.reporting.report.SizeRecordingReportOutput;

public class ResourceOutput extends SizeRecordingReportOutput {
	private String resourcePath;
	private String outputPath;

	public ResourceOutput(final String directory, final String name, final String extension) {
		this(String.format("%s/%s.%s", directory, name, extension));
	}
	
	public ResourceOutput(final String resourcePath) {
		this.resourcePath = resourcePath.replace('\\', '/');
		this.outputPath = this.resourcePath;
		if(this.resourcePath.startsWith("/")) {
			this.outputPath = this.outputPath.substring(1);
		} else {
			this.resourcePath = "/" + this.resourcePath;
		}
	}

	@Override
	public String getPath() {
		return IReportOutput.RESOURCES + outputPath;
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
	public void doWriteContent(final OutputStream outputStream) throws IOException {
		try (final InputStream resourceStream = getClass().getResourceAsStream(resourcePath)) {
			if (resourceStream == null) {
				throw new IOException("Unable to get stream for report resource " + resourcePath);
			}
			IOUtils.copy(resourceStream, outputStream);
		} catch (final NullPointerException npe) {
			// If the resource stream is from a JAR, it throws an error when you try to close it. 
		}
	}
}
