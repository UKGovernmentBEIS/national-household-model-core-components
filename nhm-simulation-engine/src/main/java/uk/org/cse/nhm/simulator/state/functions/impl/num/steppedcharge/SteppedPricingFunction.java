package uk.org.cse.nhm.simulator.state.functions.impl.num.steppedcharge;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;

import javax.annotation.Nullable;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSet.Builder;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class SteppedPricingFunction extends AbstractNamed implements IComponentsFunction<Double> {
	public static class Range {

		private final double upperBound;
		private final IComponentsFunction<Number> unitPrice;

		@AssistedInject
		public Range(
				@Assisted final double upperBound, 
				@Assisted final IComponentsFunction<Number> unitPrice) {
			this.upperBound = upperBound;
			this.unitPrice = unitPrice;
		}
		
		double compute(final double from, final double units, final IComponentsScope scope, final ILets lets) {
			if(units < from) {
				throw new IllegalArgumentException(String.format("Units %f were too low to fall into range starting at %f.", units, from));
			}
			
			return (Math.min(units, upperBound) - from) * unitPrice.compute(scope, lets).doubleValue();
		}
	}
	
	private final IComponentsFunction<Number> standingCharge;
	private final boolean alwaysApply;
	private final IComponentsFunction<Number> unitsFunction;
	private final SortedSet<Range> ranges;
	private final Set<IDimension<?>> dependencies;
	
	@AssistedInject
	public SteppedPricingFunction (
			@Assisted("standingCharge") @Nullable final IComponentsFunction<Number> standingCharge,
			@Assisted final boolean alwaysApply,
			@Assisted("unitsFunction") final IComponentsFunction<Number> unitsFunction,
			@Assisted final List<SteppedPricingFunction.Range> ranges
			) {
				this.standingCharge = standingCharge;
				this.alwaysApply = alwaysApply;
				this.unitsFunction = unitsFunction;
				this.ranges = new TreeSet<Range>(new Comparator<Range>(){
					@Override
					public int compare(final Range o1, final Range o2) {
						return Double.compare(o1.upperBound, o2.upperBound);
					}});
				
				this.ranges.addAll(ranges);
				
				dependencies = getDependencies(standingCharge, unitsFunction, ranges);
	}

	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		final Double units = unitsFunction.compute(scope, lets).doubleValue();
		if(units < 0.0) {
			throw new IllegalStateException(String.format("Units function %s produces a negative quantity of units %f. No behaviour defined for calculating the price of negative units.", unitsFunction, units));
		}
		
		if(units == 0.0) {
			if(alwaysApply) {
				return standingCharge.compute(scope, lets).doubleValue();
			} else {
				return 0.0;
			}
		} else {
			double result = standingCharge.compute(scope, lets).doubleValue();
			double previousRangeEnd = 0.0;
			for(final Range r : ranges) {
				result += r.compute(previousRangeEnd, units, scope, lets);
				
				if(r.upperBound > units) {
					return result;
				}
				
				previousRangeEnd = r.upperBound;
			}
			return result;
		}
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return dependencies;
	}
	
	private ImmutableSet<IDimension<?>> getDependencies(final IComponentsFunction<Number> standingCharge, final IComponentsFunction<Number> unitsFunction, final List<SteppedPricingFunction.Range> ranges) {
		final Builder<IDimension<?>> dependencyBuilder = ImmutableSet.<IDimension<?>>builder();
		
		dependencyBuilder.addAll(unitsFunction.getDependencies());
		
		if(standingCharge != null) {
			dependencyBuilder.addAll(standingCharge.getDependencies());
		}
		
		for(final Range r : ranges) {
			dependencyBuilder.addAll(r.unitPrice.getDependencies());
		}
		
		return dependencyBuilder.build();
	}


	@Override
	public Set<DateTime> getChangeDates() {
		final Builder<DateTime> changeDates = ImmutableSet.<DateTime>builder();
		
		changeDates.addAll(unitsFunction.getChangeDates());
		
		if(standingCharge != null) {
			changeDates.addAll(standingCharge.getChangeDates());
		}
		
		for(final Range r : ranges) {
			changeDates.addAll(r.unitPrice.getChangeDates());
		}
		
		return changeDates.build();
	}
}
