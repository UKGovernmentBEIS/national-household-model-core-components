package uk.org.cse.nhm.simulator.action;

import java.util.List;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class CaseAction extends AbstractNamed implements IComponentsAction {
	public static class Case {
		public final IComponentsFunction<Boolean> test;
		public final IComponentsAction action;
		public Case(final IComponentsFunction<Boolean> test, final IComponentsAction action) {
			super();
			this.test = test;
			this.action = action;
		}
	}
	
	private final List<Case> cases;
	private final IComponentsAction defaultAction;
	private final boolean alwaysSuitable;
	
	@AssistedInject
	protected CaseAction(
			@Assisted final List<Case> cases, 
			@Assisted final IComponentsAction defaultAction) {
		super();
		this.cases = cases;
		this.defaultAction = defaultAction;
		boolean alwaysSuitable = true;
		for (final Case c : cases) {
			if (!c.action.isAlwaysSuitable()) {
				alwaysSuitable = false;
				break;
			}
		}
		if (!defaultAction.isAlwaysSuitable()) {
			alwaysSuitable = false;
		}
		this.alwaysSuitable = alwaysSuitable;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
		return chooseAction(scope, lets).apply(scope, lets);
	}

	@Override
	public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		return chooseAction(scope, lets).isSuitable(scope, lets);
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return alwaysSuitable;
	}
	
	private IComponentsAction chooseAction(final IComponentsScope scope, final ILets lets) {
		for (final Case c : cases) {
			if (c.test.compute(scope, lets)) return c.action;
		}
		return defaultAction;
	}
}
