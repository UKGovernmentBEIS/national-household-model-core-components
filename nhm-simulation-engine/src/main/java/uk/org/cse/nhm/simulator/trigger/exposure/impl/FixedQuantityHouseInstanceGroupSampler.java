package uk.org.cse.nhm.simulator.trigger.exposure.impl;

import java.util.Set;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.state.IDwelling;


/**
 * A sampler which just picks n elements uniformly at random from the group each time. It never samples the same element twice.
 * 
 * If it can't sample n elements it may sample fewer than n.
 * 
 * It is not efficient.
 * 
 * @author hinton
 *
 */
public class FixedQuantityHouseInstanceGroupSampler extends RandomHouseInstanceGroupSampler {
	private static final Logger log = LoggerFactory.getLogger(FixedQuantityHouseInstanceGroupSampler.class);
	
	private final int sampleSize;
	
	@Inject
	public FixedQuantityHouseInstanceGroupSampler(
			@Assisted final int sampleCount) {
		this.sampleSize = sampleCount;
		
		log.debug("Sampling {} house instances to represent {} houses", this.sampleSize, sampleCount);
	}
	
	@Override
	protected int getSampleSize(final Set<IDwelling> source) {
		return sampleSize;
	}


	@Override
	public String toString() {
		return String.format("sample %d", sampleSize);
	}
}
