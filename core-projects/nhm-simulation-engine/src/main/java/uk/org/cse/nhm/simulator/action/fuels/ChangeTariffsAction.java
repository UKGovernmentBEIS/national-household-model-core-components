package uk.org.cse.nhm.simulator.action.fuels;

import java.util.List;
import java.util.Set;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariff;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariffs;

public class ChangeTariffsAction extends AbstractNamed implements IComponentsAction, IModifier<ITariffs> {
	private final Set<ITariff> tariffs;
	private final IDimension<ITariffs> tariffsDimension;

	@AssistedInject
	public ChangeTariffsAction(
			@Assisted final List<ITariff> tariffs, final IDimension<ITariffs> tariffsDimension) {
		this.tariffs = ImmutableSet.copyOf(tariffs);
		this.tariffsDimension = tariffsDimension;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
		try {
			scope.modify(tariffsDimension, this);
			return true;
		} catch (final IllegalArgumentException e) {
			return false;
		}
	}

	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		return scope.createHypothesis().apply(this, ILets.EMPTY);
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return false;
	}

	@Override
	public boolean modify(final ITariffs modifiable) {
		modifiable.adoptTariffs(tariffs);
		return true;
	}
}
