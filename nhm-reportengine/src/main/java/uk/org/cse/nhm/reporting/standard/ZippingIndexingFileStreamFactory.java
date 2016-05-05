package uk.org.cse.nhm.reporting.standard;

import java.io.Closeable;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.inject.Inject;

import org.apache.velocity.app.VelocityEngine;
import org.joda.time.DateTime;

import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.reporting.report.IReportOutput;
import uk.org.cse.nhm.reporting.report.ReportMetadata;
import uk.org.cse.nhm.reporting.standard.libraries.ILibrariesOutput;
import uk.org.cse.nhm.reporting.standard.resources.ResourceFolderListing;
import uk.org.cse.nhm.reporting.standard.resources.ResourceOutput;

/**
 * Extends {@link ZippingFileStreamFactory} to add the capability to template out the index,
 * add web gunge, and metadata file.
 */
public class ZippingIndexingFileStreamFactory extends ZippingFileStreamFactory implements Closeable {
	private final VelocityEngine velocityEngine;
	private final ResourceFolderListing resourceFolderListing = new ResourceFolderListing();

	private DateTime creationDate = new DateTime();

	private DateTime endDate = new DateTime();
	
	@Inject
	public ZippingIndexingFileStreamFactory(final VelocityEngine engine) {
		super();
		velocityEngine = engine;	
	}
	
	@Override
	protected void saveOutputs(ZipOutputStream zos, List<CompletedOutput> outputs) throws IOException {
		super.saveOutputs(zos, outputs);
		saveLibraries(zos, outputs);
		saveIndex(zos, outputs);
	}

	private void saveIndex(ZipOutputStream zos, List<CompletedOutput> outputs)
			throws IOException {
		final ReportIndexPage index = new ReportIndexPage(velocityEngine, outputs, creationDate, endDate);
		zos.putNextEntry(new ZipEntry(index.getPath()));
		index.writeContent(zos);
		zos.flush();
		zos.closeEntry();
		
		final ImmutableList<IReportOutput> resources = ImmutableList.<IReportOutput>of(
				new ResourceOutput("index.css"),
				new ResourceOutput("nhm-on-fire-small.png"),
				new MetadataOutput(new ReportMetadata(creationDate.toDate(), endDate.toDate()))
				);
		
		for(final IReportOutput indexResource : resources) {
			zos.putNextEntry(new ZipEntry(indexResource.getPath()));
			indexResource.writeContent(zos);	
			zos.flush();
			zos.closeEntry();
		}
	}
	
	protected void saveLibraries(ZipOutputStream zos, List<CompletedOutput> outputs) throws IOException {
		final Set<String> written = new HashSet<>();
		final Set<ILibrariesOutput> allWritten = new HashSet<>();
		for (final CompletedOutput co : outputs) {
			for (final ILibrariesOutput lo : co.getDependencyClosure()) {
				if (allWritten.contains(lo)) continue;
				allWritten.add(lo);
				for (final IReportOutput ro : lo.buildResourceFiles(resourceFolderListing)) {
					if (written.contains(ro.getPath())) continue;
					written.add(ro.getPath());
					
					zos.putNextEntry(new ZipEntry(ro.getPath()));
					ro.writeContent(zos);
					zos.flush();
					zos.closeEntry();
				}
			}
		}
	}
	
	@Override
	public void setStartAndEndDates(final DateTime creationDate, final DateTime endDate) {
		this.creationDate = creationDate;
		this.endDate = endDate;
	}
}
