package uk.org.cse.nhm.simulator.hooks;

import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Set;

import com.google.common.base.Function;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;

public class RuntimeSampleDwellings extends AbstractNamed implements IDwellingSet {
	private static final Function<IDwelling, Double> WEIGHT = new Function<IDwelling, Double>() {
		@Override
		public Double apply(final IDwelling input) {
			return Double.valueOf(input.getWeight());
		}
	};
	private final IDwellingSet source;
	private final IComponentsFunction<Number> sampler;
	private final Number constantValue;
	
	@AssistedInject
	public RuntimeSampleDwellings(
			@Assisted final IComponentsFunction<Number> sampler,
			@Assisted final IDwellingSet source) {
		super();
		this.sampler = sampler;
		this.source = source;
		
		if (sampler instanceof ConstantComponentsFunction) {
			constantValue = ((ConstantComponentsFunction<Number>)sampler).getValue();
		} else {
			constantValue = null;
		}
	}

	@Override
	public Set<IDwelling> get(final IState state, final ILets lets) {
		final Number compute = constantValue == null ? sampler.compute(state.detachedScope(null), lets) : constantValue;
		final Set<IDwelling> source = this.source.get(state, lets);
		
		double totalWeight = 0;
		for (final IDwelling d : source) {
			totalWeight += d.getWeight();
		}
		
		final double amountToDraw;
		
		if (compute.doubleValue() <= 0) {
			return Collections.emptySet();
		} else if (compute.doubleValue() < 1) {
			amountToDraw = compute.doubleValue() * totalWeight;
		} else {
			amountToDraw = Math.max(1, compute.doubleValue());
		}

		if (amountToDraw >= totalWeight) return source;
		
		final LinkedHashSet<IDwelling> result = new LinkedHashSet<>();
		
		state.getRandom().chooseMany(source, amountToDraw, WEIGHT, result);
				
		return result;
	}
}
