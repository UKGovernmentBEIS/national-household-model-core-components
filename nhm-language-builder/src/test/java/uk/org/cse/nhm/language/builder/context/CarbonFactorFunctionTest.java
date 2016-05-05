package uk.org.cse.nhm.language.builder.context;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.ImmutableMultimap;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.ICarbonFactors;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class CarbonFactorFunctionTest {
	
	@Test
	public void defaultValueIsSap09() {
		final CarbonFactorFunction cff = new CarbonFactorFunction(HashMultimap.
				<IComponentsFunction<Number>, FuelType>
				create());
		
		Assert.assertEquals("With no function, SAP09 comes out", ICarbonFactors.SAP09.getCarbonFactor(FuelType.MAINS_GAS), 
				cff.compute(null, ILets.EMPTY).getCarbonFactor(FuelType.MAINS_GAS), 0d);
	}
	
	@SuppressWarnings("unchecked")
	@Test
	public void functionsAreComposedCorrectly() {
		final IComponentsFunction<Number> f1 = mock(IComponentsFunction.class);
		final IComponentsFunction<Number> f2 = mock(IComponentsFunction.class);
		
		final IDimension<?> d1 = mock(IDimension.class);

		final IDimension<?> d2 = mock(IDimension.class);
		
		when(f1.compute(null, ILets.EMPTY)).thenReturn(2d);
		when(f2.compute(null, ILets.EMPTY)).thenReturn(3d);
		
		when(f1.getDependencies()).thenReturn(ImmutableSet.<IDimension<?>>of(d1));
		when(f2.getDependencies()).thenReturn(ImmutableSet.<IDimension<?>>of(d2));
		when(f1.getChangeDates()).thenReturn(ImmutableSet.of(new DateTime(0)));
		when(f2.getChangeDates()).thenReturn(ImmutableSet.of(new DateTime(1)));
		
		final CarbonFactorFunction cff = new CarbonFactorFunction(
				
				ImmutableMultimap
				.<IComponentsFunction<Number>, FuelType>
				builder().putAll(f1, ImmutableSet.of(FuelType.MAINS_GAS))
				.putAll(f2, ImmutableSet.of(FuelType.HOUSE_COAL, FuelType.PEAK_ELECTRICITY))
				.build()
				);
		
		final ICarbonFactors compute = cff.compute(null, ILets.EMPTY);
		
		Assert.assertEquals(2d, compute.getCarbonFactor(FuelType.MAINS_GAS), 0d);
		Assert.assertEquals(3d, compute.getCarbonFactor(FuelType.HOUSE_COAL), 0d);
		Assert.assertEquals(3d, compute.getCarbonFactor(FuelType.PEAK_ELECTRICITY), 0d);
		Assert.assertEquals( ICarbonFactors.SAP09.getCarbonFactor(FuelType.BIOMASS_WOOD), compute.getCarbonFactor(FuelType.BIOMASS_WOOD), 0d);
		
		
		Assert.assertEquals(cff.getDependencies(), ImmutableSet.of(d1, d2));
		Assert.assertEquals(cff.getChangeDates(), ImmutableSet.of(new DateTime(0), new DateTime(1)));
		
	}
}
