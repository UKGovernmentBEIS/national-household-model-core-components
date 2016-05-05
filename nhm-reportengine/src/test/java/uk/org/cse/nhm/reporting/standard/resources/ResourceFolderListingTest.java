package uk.org.cse.nhm.reporting.standard.resources;

import java.util.Set;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.Iterables;

import uk.org.cse.nhm.reporting.standard.libraries.LibraryOutput;

public class ResourceFolderListingTest {
	@Test
	public void testListingKnownResourceFile() {
		String knownResource = "timeSeries/timeSeries.css";
		Set<String> resources = new ResourceFolderListing().getListing(knownResource);
		Assert.assertEquals("Listing of a known single file should return 1 results", 1, resources.size());
		Assert.assertEquals("Should have found the known resource", knownResource, Iterables.get(resources, 0));
	}
	
	@Test
	public void testListingResourceFileDeepInhierarchy() {
		String knownResource = LibraryOutput.RICKSHAW.getPath() + "/extensions/images/offset_pct.png";
		String library = LibraryOutput.RICKSHAW.getPath();
		
		Set<String> libraryFiles = new ResourceFolderListing().getListing(library);
		
		Assert.assertTrue("Library should output all its contents.", libraryFiles.contains(knownResource));
	}
}
