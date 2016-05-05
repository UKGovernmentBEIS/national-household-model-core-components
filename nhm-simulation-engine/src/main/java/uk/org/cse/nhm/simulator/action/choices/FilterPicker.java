package uk.org.cse.nhm.simulator.action.choices;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope.IPicker;
import uk.org.cse.nhm.simulator.scope.PickOption;
import uk.org.cse.nhm.simulator.sequence.ISequenceSpecialAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.util.RandomSource;

public class FilterPicker extends LettingPicker {

	private final IComponentsFunction<Boolean> test;
	private final IPicker delegate;

	@AssistedInject
	public FilterPicker(
			@Assisted final IComponentsFunction<Boolean> test,
			@Assisted final IPicker delegate, 
			@Assisted final List<ISequenceSpecialAction> bindings) {
		super(bindings);
		this.test = test;
		this.delegate = delegate;
	}

	@Override
	protected PickOption doPick(final RandomSource random, final Set<PickOption> options) {
		final ImmutableSet.Builder<PickOption> reducedOptions = ImmutableSet.builder();
		
		for (final PickOption option : options) {
			if (test.compute(option.scope, option.lets)) {
				reducedOptions.add(option);
			}
		}
		
		return delegate.pick(random, reducedOptions.build());
	}
}
