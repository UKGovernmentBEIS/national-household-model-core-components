package uk.org.cse.nhm.simulator.trigger.exposure;

import java.util.Set;

import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.util.RandomSource;

/**
 * A sampler is used to draw a subset from a house instance group for presentation to an action.
 * @author hinton
 *
 */
public interface IDwellingGroupSampler {
	/**
	 * Select a subset of the given set to be mangled in some way.
	 * @param sampleFrom TODO
	 * @param group
	 * @return subset of group.
	 */
	public Set<IDwelling> sample(final RandomSource random, Set<IDwelling> sampleFrom);

	public static IDwellingGroupSampler IDENTITY = new IDwellingGroupSampler() {

		@Override
		public Set<IDwelling> sample(final RandomSource random, Set<IDwelling> sampleFrom) {
			return sampleFrom;
		}
	};
}
