package uk.org.cse.nhm.simulator.factories;

import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.trigger.exposure.impl.BernoulliSampler;
import uk.org.cse.nhm.simulator.trigger.exposure.impl.EverythingSampler;
import uk.org.cse.nhm.simulator.trigger.exposure.impl.FixedQuantityHouseInstanceGroupSampler;
import uk.org.cse.nhm.simulator.trigger.exposure.impl.ProportionateHouseInstanceGroupSampler;

public interface ISamplerFactory {

    public FixedQuantityHouseInstanceGroupSampler createCountingSampler(int count);

    public ProportionateHouseInstanceGroupSampler createProportionSampler(double proportion);

    public BernoulliSampler createBernoulliSampler(IComponentsFunction<Number> parameter);

    public EverythingSampler createEverythingSampler();
}
