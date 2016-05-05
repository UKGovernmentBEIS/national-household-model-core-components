package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchSolarPV extends AbstractNamed implements IComponentsFunction<Boolean> {
	private final IDimension<ITechnologyModel> techDimension;

	@Inject
	public MatchSolarPV(final IDimension<ITechnologyModel> techDimension) {
		this.techDimension = techDimension;
    }

	@Override
	public Boolean compute(final IComponentsScope scope, final ILets lets) {
		return scope.get(techDimension).getSolarPhotovoltaic() != null;
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
