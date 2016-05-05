package uk.org.cse.nhm.simulator.trigger.exposure.impl;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.util.RandomSource;

public class ProportionateSamplerTest {

	private ProportionateHouseInstanceGroupSampler sampler;
	private Set<IDwelling> someDwellings;
	private Set<IDwelling> noDwellings;

	@Before
	public void setUp() {

		someDwellings = new HashSet<IDwelling>();
		for (int i = 0; i < 8; i++) {
			final IDwelling mock = mock(IDwelling.class);
			when(mock.getWeight()).thenReturn(1f);
			someDwellings.add(mock);
		}
		noDwellings = ImmutableSet.<IDwelling> of();
	}

	@Test
	public void sampleEmptyGroupShouldReturnEmpty() {
		sampler = new ProportionateHouseInstanceGroupSampler(1d);

		Assert.assertEquals(noDwellings, sampler.sample(new RandomSource(0), noDwellings));
	}
	
	@Ignore
	@Test
	public void sampleWithProportionZeroShouldReturnEmpty() {
		sampler = new ProportionateHouseInstanceGroupSampler(0d);

		Assert.assertEquals(noDwellings, sampler.sample(new RandomSource(0),  someDwellings));
	}

	@Test
	public void sampleWithProportionOneShouldReturnWholeGroup() {		
		sampler = new ProportionateHouseInstanceGroupSampler(1d);

		Assert.assertEquals(someDwellings, sampler.sample(new RandomSource(0), someDwellings));
	}
}
