package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchBoiler extends AbstractNamed implements IComponentsFunction<Boolean> {
	private final IDimension<ITechnologyModel> techDimension;
	private final ITechnologyOperations operations;

	@Inject
	public MatchBoiler(final IDimension<ITechnologyModel> techDimension, final ITechnologyOperations operations) {
		this.techDimension = techDimension;
		this.operations = operations;
	}

	@Override
	public Boolean compute(final IComponentsScope scope, final ILets lets) {
		return operations.getBoiler(scope.get(techDimension)).isPresent();
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return Collections.<IDimension<?>> singleton(techDimension);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}
