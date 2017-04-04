package uk.org.cse.nhm.simulator.trigger.exposure.impl;

import java.util.LinkedHashSet;
import java.util.Set;

import com.google.common.base.Function;

import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.trigger.exposure.IDwellingGroupSampler;
import uk.org.cse.nhm.simulator.util.RandomSource;

/**
 * A sampler which uniformly randomly draws from its group.
 * @author hinton
 *
 */
public abstract class RandomHouseInstanceGroupSampler implements IDwellingGroupSampler {
	private static final Function<IDwelling, Double> WEIGHT = new Function<IDwelling, Double>() {
		@Override
		public Double apply(final IDwelling input) {
			return Double.valueOf(input.getWeight());
		}
	};
	
	protected RandomHouseInstanceGroupSampler() {
	}

	@Override
	public Set<IDwelling> sample(final RandomSource random, final Set<IDwelling> sampleFrom) {
		final int count = getSampleSize(sampleFrom);
		
		final LinkedHashSet<IDwelling> sample = new LinkedHashSet<IDwelling>(count);

        random.chooseMany(sampleFrom, count, WEIGHT, sample);
		
		return sample;
	}

	protected abstract int getSampleSize(final Set<IDwelling> source);
}
