package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public class TariffsTest {
	@Test(expected = IllegalArgumentException.class)
	public void invalidSetOfTariffsIsNotAdoptable() {
		final Tariffs t = Tariffs.free();
		final ITariff one = mock(ITariff.class);
		final ITariff two = mock(ITariff.class);
		
		when(one.getFuelTypes()).thenReturn(ImmutableSet.of(FuelType.MAINS_GAS, FuelType.BIOMASS_WOOD));
		when(two.getFuelTypes()).thenReturn(ImmutableSet.of(FuelType.PEAK_ELECTRICITY, FuelType.BIOMASS_WOOD));
		
		Assert.assertFalse("Both tariffs will try and do biomass wood, so no good",
				t.canAdoptTariffs(ImmutableSet.of(one, two)));
		
		t.adoptTariffs(ImmutableSet.of(one, two));
		
	}
	
	@Test(expected = IllegalArgumentException.class)
	public void validSetOfConflictingTariffsIsNotAdoptable() {
		final Tariffs t = Tariffs.free();
		final ITariff one = mock(ITariff.class);
		final ITariff two = mock(ITariff.class);
		
		when(one.getFuelTypes()).thenReturn(ImmutableSet.of(FuelType.MAINS_GAS, FuelType.BIOMASS_WOOD));
		
		t.adoptTariffs(ImmutableSet.of(one));
		
		when(two.getFuelTypes()).thenReturn(ImmutableSet.of(FuelType.PEAK_ELECTRICITY, FuelType.BIOMASS_WOOD));
		
		Assert.assertFalse("Existing tariff does biomass wood, so no good",
				t.canAdoptTariffs(ImmutableSet.of(two)));
		
		t.adoptTariffs(ImmutableSet.of(two));
	}
	
	@Test
	public void validSetOfNonConflictingTariffsIsAdoptable() {
		final Tariffs t = Tariffs.free();
		final ITariff one = mock(ITariff.class);
		final ITariff two = mock(ITariff.class);
		
		when(one.getFuelTypes()).thenReturn(ImmutableSet.of(FuelType.MAINS_GAS, FuelType.BIOMASS_WOOD));
		when(two.getFuelTypes()).thenReturn(ImmutableSet.of(FuelType.PEAK_ELECTRICITY, FuelType.OFF_PEAK_ELECTRICITY));
		
		t.adoptTariffs(ImmutableSet.of(one, two));
		
		Assert.assertSame(one, t.getTariff(FuelType.MAINS_GAS));
		Assert.assertSame(one, t.getTariff(FuelType.BIOMASS_WOOD));
		Assert.assertSame(two, t.getTariff(FuelType.PEAK_ELECTRICITY));
		Assert.assertSame(two, t.getTariff(FuelType.OFF_PEAK_ELECTRICITY));
		
		boolean a = false, b = false;
		
		for (final ITariff tf : t) {
			if (tf == one) {
				Assert.assertFalse(a);
				a = true;
			}
			if (tf == two) {
				Assert.assertFalse(b);
				b = true;
			}
		}
		
		Assert.assertTrue(a && b);
	}
	
	@Test
	public void copyMakesACopy() {
		final Tariffs t = Tariffs.free();
		final ITariff one = mock(ITariff.class);
		when(one.getFuelTypes()).thenReturn(ImmutableSet.of(FuelType.MAINS_GAS, FuelType.BIOMASS_WOOD));
		final ITariffs t2 = t.copy();
		
		t2.adoptTariffs(ImmutableSet.of(one));
		Assert.assertSame(one, t2.getTariff(FuelType.MAINS_GAS));
		
		Assert.assertNotSame(one, t.getTariff(FuelType.MAINS_GAS));

		t.adoptTariffs(ImmutableSet.of(one));
		Assert.assertSame(one, t.getTariff(FuelType.MAINS_GAS));
	}
}
