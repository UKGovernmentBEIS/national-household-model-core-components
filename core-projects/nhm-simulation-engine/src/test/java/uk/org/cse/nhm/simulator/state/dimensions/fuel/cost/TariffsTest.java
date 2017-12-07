package uk.org.cse.nhm.simulator.state.dimensions.fuel.cost;

import com.google.common.collect.ImmutableSet;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;

import static org.mockito.Mockito.*;

public class TariffsTest {
    private IExtraCharge chargeOne;
    private IExtraCharge chargeTwo;
    private IExtraCharge chargeThree;
    private ISettableComponentsScope scope;
    private ILets lets;

    @Before
    public void setup() {
        chargeOne = mock(IExtraCharge.class);
        chargeTwo = mock(IExtraCharge.class);
        chargeThree = mock(IExtraCharge.class);

        when(chargeOne.getFuel()).thenReturn(com.google.common.base.Optional.absent());
        when(chargeTwo.getFuel()).thenReturn(com.google.common.base.Optional.absent());
        when(chargeThree.getFuel()).thenReturn(com.google.common.base.Optional.absent());

        scope = mock(ISettableComponentsScope.class);
        lets = mock(ILets.class);
    }

    @Test
    public void addTwoExtraCharges() {
        ITariffs t = Tariffs.free().copy();

        t.addExtraCharge(chargeOne);
        t.addExtraCharge(chargeTwo);
        t.computeCharges(scope, lets);

        verify(chargeOne, times(1)).apply(scope, lets);
        verify(chargeTwo, times(1)).apply(scope, lets);
    }

    @Test
    public void dependencyOrderedExtraCharges() {
        when(chargeOne.getDependencies()).thenReturn(ImmutableSet.of(chargeTwo));
        when(chargeTwo.getDependencies()).thenReturn(ImmutableSet.of(chargeThree));
        when(chargeThree.getDependencies()).thenReturn(ImmutableSet.of());

        ITariffs t = tariffWithCharges(chargeOne, chargeTwo, chargeThree);

        InOrder inOrder = inOrder(chargeThree, chargeTwo, chargeOne);

        t.computeCharges(scope, lets);

        inOrder.verify(chargeThree, times(1)).apply(scope, lets);
        inOrder.verify(chargeTwo, times(1)).apply(scope, lets);
        inOrder.verify(chargeOne, times(1)).apply(scope, lets);
    }

    public static ITariffs tariffWithCharges(IExtraCharge...charges) {
        ITariffs t = Tariffs.free().copy();

        for (IExtraCharge c : charges) {
            t.addExtraCharge(c);
        }

        return t;
    }

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
