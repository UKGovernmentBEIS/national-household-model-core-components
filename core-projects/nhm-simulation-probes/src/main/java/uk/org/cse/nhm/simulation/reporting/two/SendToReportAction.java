package uk.org.cse.nhm.simulation.reporting.two;

import java.util.List;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.sequence.ISequenceScopeAction;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class SendToReportAction extends AbstractNamed implements IComponentsAction, ISequenceScopeAction {
	private final List<UnifiedReport> reports;
	
	public SendToReportAction(final List<UnifiedReport> reports) {
		super();
		this.reports = reports;
	}

	@Override
	public StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public boolean apply(ISettableComponentsScope scope, ILets lets) throws NHMException {
		final String key = getIdentifier().getName();
		for (final UnifiedReport report : reports) report.before(key, scope, lets);
		return true;
	}

	@Override
	public boolean isSuitable(IComponentsScope scope, ILets lets) {
		return true;
	}

	@Override
	public boolean isAlwaysSuitable() {
		return true;
	}
}
