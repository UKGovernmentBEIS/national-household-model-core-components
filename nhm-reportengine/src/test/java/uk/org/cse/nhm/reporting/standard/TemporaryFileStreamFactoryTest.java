package uk.org.cse.nhm.reporting.standard;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.base.Optional;

import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor;
import uk.org.cse.nhm.reporting.standard.IReporterFactory.IReportDescriptor.Type;
import uk.org.cse.nhm.reporting.standard.TemporaryFileStreamFactory.CompletedOutput;

/**
 * TODO should this be an integration test?
 *
 */
public class TemporaryFileStreamFactoryTest {
	TemporaryFileStreamFactory factory;
	
	@Before
	public void setup() {
		factory = new TemporaryFileStreamFactory();
	}
	
	@After
	public void destroy() {
		if (factory != null) {
			try {
				factory.close();
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
			factory = null;
		}
	}
	
	@Test
	public void createsOutputStreamWhichWorks() throws IOException {
		final OutputStream output = factory.createReportFile("test", Optional.<IReportDescriptor>absent());
		
		final byte[] bs = new byte[] {1,2,3,4};
		
		output.write(bs);
		
		output.close();
		
		final List<CompletedOutput> outputs = factory.getOutputs();
		
		Assert.assertEquals("There should be one output", 1, outputs.size());
		
		final CompletedOutput out = outputs.get(0);
		
		final byte[] bytes = IOUtils.toByteArray(Files.newInputStream(out.path));
		
		Assert.assertTrue("the bytes we wrote have come out", Arrays.equals(bs, bytes));
	}
	
	@Test(expected=IOException.class)
	public void failsIfWeAskForOutputsWithoutClosingAStream() throws IOException {
		final OutputStream output = factory.createReportFile("test", Optional.<IReportDescriptor>absent());
		
		final byte[] bs = new byte[] {1,2,3,4};
		
		output.write(bs);
		
		factory.getOutputs();
	}
	
	@Test
	public void destroysTemporaryFilesAndNamesThingsCorrectly() throws IOException {
		final Path temporaryDirectory = factory.getTemporaryDirectory();
		
		final OutputStream output = factory.createReportFile("test", Optional.<IReportDescriptor>absent());
		output.close();
		final CompletedOutput marker = factory.getOutputs().get(0);
		Assert.assertTrue("Before we do anything the file should exist", Files.exists(marker.path));
		
		Assert.assertTrue("The report output should be inside the temporary directory", 
				marker.path.startsWith(temporaryDirectory));
		
		Assert.assertEquals("The file is named as we asked", "test", marker.getPath().getFileName().toString());
		
		// pre-emptively do the cleanup
		destroy();
		Assert.assertFalse("After closing the factory, it should have destroyed all its temporary files", 
				Files.exists(marker.path));
		
		Assert.assertFalse("Also, the temporary directory should have been unlinked",
				Files.exists(temporaryDirectory));
	}
	
	@Test
	public void storesReportDescriptorsIntoCompletedOutput() throws IOException {
		final OutputStream output = 
				factory.createReportFile("test", Optional.<IReportDescriptor>of(
						GenericDescriptor.of(Type.Chart)
				));
		output.close();
		
		final CompletedOutput out = factory.getOutputs().get(0);
		
		Assert.assertNotNull("Descriptor is present", out.getDescriptor());
	}
	
	@Test
	public void outputsContainByteCountWrittenToStream() throws IOException {
		final OutputStream output = factory.createReportFile("test", Optional.<IReportDescriptor>absent());
		
		final byte[] bs = new byte[] {1,2,3,4};
		
		output.write(bs);
		
		output.close();
		
		final CompletedOutput marker = factory.getOutputs().get(0);
	
		Assert.assertEquals("We wrote 4 bytes", 4, marker.getSize());
	}
}
