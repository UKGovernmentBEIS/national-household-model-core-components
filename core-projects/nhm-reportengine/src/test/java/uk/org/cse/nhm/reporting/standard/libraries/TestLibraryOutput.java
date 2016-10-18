package uk.org.cse.nhm.reporting.standard.libraries;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

public class TestLibraryOutput {

	@Test
	public void testResolvesDependencies() {
		Assert.assertEquals(ImmutableSet.of(LibraryOutput.D3, LibraryOutput.JQUERY), LibraryOutput.RICKSHAW.getDependencies());
		Assert.assertEquals(ImmutableSet.of(), LibraryOutput.D3.getDependencies());
		Assert.assertEquals(ImmutableSet.of(LibraryOutput.D3), LibraryOutput.D3Plugins.getDependencies());
		Assert.assertEquals(ImmutableSet.of(), LibraryOutput.JQUERY.getDependencies());
	}
}
