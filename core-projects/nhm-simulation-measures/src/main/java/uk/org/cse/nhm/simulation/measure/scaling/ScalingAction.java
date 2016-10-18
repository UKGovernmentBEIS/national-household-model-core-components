package uk.org.cse.nhm.simulation.measure.scaling;

import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.errors.WarningLogEntry;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

abstract class ScalingAction extends AbstractNamed implements IComponentsAction {

	private final ILogEntryHandler loggingService;
	private final IComponentsFunction<Number> scalingFunction;

	protected ScalingAction(
			final ILogEntryHandler loggingService,
			final IComponentsFunction<Number> scalingFunction) {
		this.loggingService = loggingService;
		this.scalingFunction = scalingFunction;
	}
	
	@Override
	public final StateChangeSourceType getSourceType() {
		return StateChangeSourceType.ACTION;
	}

	@Override
	public final boolean apply(final ISettableComponentsScope scope, final ILets lets)
			throws NHMException {
		
		final Double scaling = scalingFunction.compute(scope, lets).doubleValue();
		
		doApply(scaling, scope);
		return true;
	}

	protected abstract void doApply(final double scaling, final ISettableComponentsScope scope) throws NHMException;

	@Override
	public final boolean isSuitable(final IComponentsScope scope, final ILets lets) {
		return true;
	}
	
	@Override
	public boolean isAlwaysSuitable() {
		return true;
	}
	
	protected final String name() {
		return getIdentifier().getName();
	}
	
	protected final void warn(final String warning, final IComponentsAction action, final int dwellingId, final double existing, final double invalid, final double fallback) {
		loggingService.acceptLogEntry(new WarningLogEntry(warning, ImmutableMap.of(
			"action", action.getIdentifier().getName(),
			"dwelling", Integer.toString(dwellingId),
			"existing", Double.toString(existing),
			"invalid", Double.toString(invalid),
			"fallback", Double.toString(fallback)
				)));
	}
}
