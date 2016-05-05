package uk.org.cse.nhm.simulation.measure.boilers;

import java.util.List;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;

@SuppressWarnings("unchecked")
public class TestTechScope extends TestScope {
	private final ITechnologyModel tech;

	TestTechScope(final ITechnologyModel tech) {
		this.tech = tech;
	}
	
	@Override
	public <T> T get(final IDimension<T> dimension) {
		return (T) tech;
	}
	
	@Override
	public boolean apply(final IComponentsAction action, final ILets lets) throws NHMException {
		return action.apply(this, lets);
	}
	
	@Override
	public boolean applyInSequence(final List<IComponentsAction> actions, final ILets lets, final boolean requireSuccess) throws NHMException {
		for(final IComponentsAction a : actions) {
			if(!a.apply(this, lets) && requireSuccess) {
				return false;
			}
		}
		return true;
	}
	
	@Override
	public <T> void modify(final IDimension<T> dimension, final IModifier<T> operation) {
		operation.modify((T) tech);
	}
}
