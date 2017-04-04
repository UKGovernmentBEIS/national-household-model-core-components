package uk.org.cse.nhm.simulator.trigger.exposure.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import javax.annotation.Nullable;
import javax.inject.Inject;

import com.google.common.base.Function;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.trigger.exposure.IDwellingGroupSampler;
import uk.org.cse.nhm.simulator.util.RandomSource;

/**
 * Randomly samples a subset of IDwellings from the given HouseInstanceGroup.
 * 
 * Maintains the list of candidates for selection listener method.
 * 
 * Allows the same dwelling to be selected in multiple consecutive sample sets.
 * 
 * NOTE: 	Similar to RandomHouseInstanceGroupSampler however sample size is variable and exposure rate must be applied to each IDwelling
 * 			individually. 
 * 
 * TODO:	To prevent a dwelling appearing in multiple samples, a list of nonCandidates should be maintained as in RandomHouseInstanceGroupSampler.
 * 			This could be added as a class which extends this one "DecisionMakingFrequencySamplerDistinctImpl".
 * 
 * TODO:	Random member variable could be set to a class extending Random with a particular distribution from which to sample 
 * 
 * 			e.g. class NormalRandom extends Random { public NormalRandom(Integer seed, Double mean, Double sd) {...} }
 * 
 * @author tomw
 *
 */
public class BernoulliSampler implements IDwellingGroupSampler {
	// The function to apply to a given IDwelling to determine it's probability for exposure
	private final IComponentsFunction<Number> erf;
	
	private final ICanonicalState state;

	@Inject
	public BernoulliSampler(
			@Assisted final IComponentsFunction<Number> erf, 
			final ICanonicalState state) {
		this.erf = erf;
		this.state = state;
	}

	@Override
	public Set<IDwelling> sample(final RandomSource random, final Set<IDwelling> candidates) {
		final int numCandidates = candidates.size();
		final LinkedHashSet<IDwelling> sample = new LinkedHashSet<IDwelling>(numCandidates);
		
		final Function<IDwelling, Double> pfunc = new Function<IDwelling, Double>() {
			@Override
			public Double apply(@Nullable final IDwelling input) {
				return erf.compute(state.detachedScope(input), ILets.EMPTY).doubleValue();
			}
		};
		
        for (final IDwelling d : candidates) {
            final double w = pfunc.apply(d);
            if (w > random.nextDouble()) {
                sample.add(d);
            }
        }
		
		return sample;
	}
}

