package uk.org.cse.nhm.simulator.trigger.exposure.impl;

import java.util.Set;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.state.IDwelling;

public class ProportionateHouseInstanceGroupSampler extends RandomHouseInstanceGroupSampler {
	private final double sampleProportion;

	@Inject
	public ProportionateHouseInstanceGroupSampler(@Assisted final double sampleProportion) {
		this.sampleProportion = sampleProportion;
	}

	@Override
	protected int getSampleSize(final Set<IDwelling> source) {
		return (int) Math.round(totalWeight(source) * sampleProportion);
	}

	
	private double totalWeight(final Set<IDwelling> source) {
		double result = 0;
		for (final IDwelling d : source)result += d.getWeight();
		return result;
	}

	@Override
	public String toString() {
		return String.format("sample %.3f%%", sampleProportion);
	}
}
