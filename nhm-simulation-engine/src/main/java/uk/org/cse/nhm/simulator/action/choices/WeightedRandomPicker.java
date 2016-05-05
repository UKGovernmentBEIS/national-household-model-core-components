package uk.org.cse.nhm.simulator.action.choices;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableList;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.scope.PickOption;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.util.RandomSource;

public class WeightedRandomPicker extends LettingPicker {
	private final IComponentsFunction<Number> weight;

	@AssistedInject
	protected WeightedRandomPicker(
			@Assisted final List<ISequenceSpecialAction> bindings,
			@Assisted final IComponentsFunction<Number> weight) {
		super(bindings);
		this.weight = weight;
	}

	@Override
	protected PickOption doPick(final RandomSource random, final Set<PickOption> options) {
		final ImmutableList<PickOption> optionsInOrder = ImmutableList.copyOf(options);
		final ImmutableList.Builder<Double> weights = ImmutableList.builder();
		for (final PickOption o : optionsInOrder) {
			weights.add(weight.compute(o.scope, o.lets).doubleValue());
		}
		return random.chooseOne(optionsInOrder, weights.build());
	}
}
