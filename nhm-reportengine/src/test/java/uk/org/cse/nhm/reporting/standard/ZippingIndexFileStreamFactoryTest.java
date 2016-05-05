package uk.org.cse.nhm.reporting.standard;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.commons.io.IOUtils;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;

public class ZippingIndexFileStreamFactoryTest {
	@Test
	public void zipFileIsCreatedAsExpected() throws IOException {
		final ZippingIndexingFileStreamFactory factory = new ZippingIndexingFileStreamFactory(TemplatedResourceOutputTest.templateEngine);
		try (final OutputStream os = factory.createReportFile("Normal Report", Optional.<IReportDescriptor>absent())) {
			os.write("Hello world".getBytes(StandardCharsets.UTF_8));
		}
		try (final OutputStream os = factory.createReportFile("Report: bad filename", Optional.<IReportDescriptor>absent())) {
			os.write("Bad :(".getBytes(StandardCharsets.UTF_8));
		}
		
		factory.close();
		final Path zipFilePath = factory.getZipFilePath();
		
		final ZipFile zip = new ZipFile(zipFilePath.toFile());
		final Enumeration<? extends ZipEntry> entries = zip.entries();
		final HashSet<String> namesSeen = new HashSet<>();
		while (entries.hasMoreElements()) {
			final ZipEntry ze = entries.nextElement();
			namesSeen.add(ze.getName());
			if (ze.getName().equals("Normal Report")) {
				Assert.assertEquals("Hello world",
						IOUtils.toString(zip.getInputStream(ze), StandardCharsets.UTF_8)
				);
			} else if (ze.getName().equals("Report- bad filename")) {
				Assert.assertEquals("Bad :(",
						IOUtils.toString(zip.getInputStream(ze), StandardCharsets.UTF_8)
				);
			}
		}
		
		Assert.assertTrue(namesSeen.contains("Report- bad filename"));
		Assert.assertTrue(namesSeen.contains("Normal Report"));
		
		zip.close();
		Files.delete(zipFilePath);
	}
}
