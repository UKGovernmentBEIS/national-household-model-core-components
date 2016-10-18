package uk.org.cse.nhm.simulator.action.hypothetical;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class HypotheticalSetAction<T> extends HypotheticalAction {
	final IDimension<T> dimension;
	final T value;
	
	@AssistedInject
	public HypotheticalSetAction(
			final IDimension<T> dimension,
			@Assisted final T value) {
		this.dimension = dimension;
		this.value = value;
	}

	@Override
	protected boolean doApply(final IHypotheticalComponentsScope scope, final ILets lets) {
		scope.replace(dimension, new ReplacementValueShim<T>(dimension.index(), value));

		return true;
	}
}
