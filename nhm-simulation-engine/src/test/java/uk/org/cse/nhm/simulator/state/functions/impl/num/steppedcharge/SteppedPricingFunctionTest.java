package uk.org.cse.nhm.simulator.state.functions.impl.num.steppedcharge;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.steppedcharge.SteppedPricingFunction.Range;

public class SteppedPricingFunctionTest {

	private IComponentsScope scope;

	@Before
	public void setup() {
		scope = mock(IComponentsScope.class);
	}
	
	@Test
	public void shouldTakeDependenciesFromChildFunctions() {
		final IDimension<?> dep1 = mock(IDimension.class);
		final IDimension<?> dep2 = mock(IDimension.class);
		final IDimension<?> dep3 = mock(IDimension.class);
		
		final IComponentsFunction<Number> unitsFunction = func(0.0);
		when(unitsFunction.getDependencies()).thenReturn(Collections.<IDimension<?>>singleton(dep1));
		
		final IComponentsFunction<Number> standingCharge = func(0.0);
		when(standingCharge.getDependencies()).thenReturn(Collections.<IDimension<?>>singleton(dep2));
		
		final IComponentsFunction<Number> rangeFunc = func(0.0);
		when(rangeFunc.getDependencies()).thenReturn(Collections.<IDimension<?>>singleton(dep3));
		
		final SteppedPricingFunction steppedPricingFunction = new SteppedPricingFunction(standingCharge, false, unitsFunction, ImmutableList.of(new Range(0, rangeFunc)));
		
		Assert.assertEquals("Should return the dependencies from child functions.", ImmutableSet.of(dep1, dep2, dep3), steppedPricingFunction.getDependencies());
	}
	
	@Test
	public void shouldTakeChangeDatesFromChildFunctions() {
		final DateTime date1 = new DateTime(0);
		final DateTime date2 = new DateTime(1);
		final DateTime date3 = new DateTime(2);
		
		final IComponentsFunction<Number> unitsFunction = func(0.0);
		when(unitsFunction.getChangeDates()).thenReturn(Collections.singleton(date1));
		
		final IComponentsFunction<Number> standingCharge = func(0.0);
		when(standingCharge.getChangeDates()).thenReturn(Collections.singleton(date2));
		
		final IComponentsFunction<Number> rangeFunc = func(0.0);
		when(rangeFunc.getChangeDates()).thenReturn(Collections.singleton(date3));
		
		final SteppedPricingFunction steppedPricingFunction = new SteppedPricingFunction(standingCharge, false, unitsFunction, ImmutableList.of(new Range(0, rangeFunc)));
		
		Assert.assertEquals("Should return the dependencies from child functions.", ImmutableSet.of(date1, date2, date3), steppedPricingFunction.getChangeDates());
	}
	
	@Test(expected = IllegalStateException.class)
	public void negativeUnitsAreNotAllowed() {
		createSteppedPricingFunction(-1.0, 0.0, false, Collections.<Double, Double>emptyMap()).compute(scope, ILets.EMPTY);
	}
	
	@Test
	public void zeroUnitsBehaviourDependsOnAlwaysApplies() {
		SteppedPricingFunction func = createSteppedPricingFunction(0.0, 1.0, false, Collections.<Double, Double>emptyMap());
		Assert.assertEquals("Doesn't use standing charge if always apply is false and 0 units were used.", 0.0, func.compute(scope, ILets.EMPTY), 0.1);
		
		func = createSteppedPricingFunction(0.0, 1.0, true, Collections.<Double, Double>emptyMap());
		Assert.assertEquals("Uses standing charge if always apply is true and 0 units were used.", 1.0, func.compute(scope, ILets.EMPTY), 0.1);
	}
	
	@Test
	public void usesRangeFunctionsToCalculatePrice() {
		final double standingCharge = 10.0;
		final SteppedPricingFunction func = createSteppedPricingFunction(500.0, standingCharge, false, ImmutableMap.of(
				100.0, 1.0, // Fully used
				1000.0, 2.0, // Partially used
				10000.0, 3.0 // Should not be used
				));
		
		Assert.assertEquals("Should use the ranges to calculate the price based on the number of units.", standingCharge + (100.0 * 1.0) + (400.0 * 2.0), func.compute(scope, ILets.EMPTY), 0.1);
	}
	
	private SteppedPricingFunction createSteppedPricingFunction(final double units, final double standingCharge, final boolean alwaysApply, final Map<Double, Double> ranges) {
		final List<Range> rangeFunctions = makeRanges(ranges);
		return new SteppedPricingFunction(func(standingCharge), alwaysApply, func(units), rangeFunctions);
	}

	private List<Range> makeRanges(final Map<Double, Double> ranges) {
		final List<Range> rangeFunctions = new ArrayList<>();
		for(final Entry<Double, Double> range : ranges.entrySet()) {
			rangeFunctions.add(new Range(range.getKey(), func(range.getValue())));
		}
		return rangeFunctions;
	}
	
	@SuppressWarnings("unchecked")
	private IComponentsFunction<Number> func(final Double value) {
		final IComponentsFunction<Number> fun = mock(IComponentsFunction.class);
		when(fun.compute(any(IComponentsScope.class), any(ILets.class))).thenReturn(value);
		return fun;
	}
}
