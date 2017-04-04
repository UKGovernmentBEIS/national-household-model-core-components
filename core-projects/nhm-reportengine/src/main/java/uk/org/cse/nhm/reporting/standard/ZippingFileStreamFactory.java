package uk.org.cse.nhm.reporting.standard;

import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import org.apache.commons.io.IOUtils;
import org.joda.time.DateTime;

abstract public class ZippingFileStreamFactory extends TemporaryFileStreamFactory implements Closeable, IZippingFileStreamFactory {
	boolean alreadyClosed = false;
	private Path outputPath;
	
	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.reporting.standard.IZippingFileStreamFactory#close()
	 */
	@Override
	public void close() throws IOException {
		try {
			if (alreadyClosed) return;
			alreadyClosed = true;
			final Path outputPath = createOutputPath();
			setOutputPath(outputPath);
			final OutputStream fos = Files.newOutputStream(outputPath);
			final ZipOutputStream zos = new ZipOutputStream(fos);
			try {
				zos.setLevel(9);
				zos.setMethod(ZipOutputStream.DEFLATED);
				saveOutputs(zos, filter(super.getOutputs()));
			} finally {
				zos.flush();
				zos.close();
			}
		} finally {
			super.close();
		}
	}
	
	protected abstract List<CompletedOutput> filter(final List<CompletedOutput> outputs);

	private void setOutputPath(Path outputPath) {
		this.outputPath = outputPath;
	}

	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.reporting.standard.IZippingFileStreamFactory#getZipFilePath()
	 */
	@Override
	public Path getZipFilePath() throws IllegalStateException {
		if (outputPath == null) throw new IllegalStateException("No output file exists yet");
		return outputPath;
	}
	
	protected void saveOutputs(final ZipOutputStream zos, final List<CompletedOutput> outputs) throws IOException {
		for (final CompletedOutput output : outputs) {
			final ZipEntry ze = new ZipEntry(output.name);
			zos.putNextEntry(ze);
			try (final InputStream in  = Files.newInputStream(output.path)) {
				IOUtils.copy(in, zos);
			}
			zos.flush();
			zos.closeEntry();
		}
	}
	
	protected Path createOutputPath() throws IOException {
		return Files.createTempFile("nhm-report", "zip");
	}
	
	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.reporting.standard.IZippingFileStreamFactory#setStartAndEndDates(org.joda.time.DateTime, org.joda.time.DateTime)
	 */
	@Override
	public void setStartAndEndDates(final DateTime creationDate, final DateTime endDate) {
	}
}
