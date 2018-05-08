package uk.org.cse.nhm.simulator.trigger.exposure.impl;

import java.util.Set;

import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.trigger.exposure.IDwellingGroupSampler;
import uk.org.cse.nhm.simulator.util.RandomSource;

public class EverythingSampler implements IDwellingGroupSampler {

    @AssistedInject
    public EverythingSampler() {
    }

    @Override
    public Set<IDwelling> sample(final RandomSource random, final Set<IDwelling> sampleFrom) {
        return sampleFrom;
    }

}
