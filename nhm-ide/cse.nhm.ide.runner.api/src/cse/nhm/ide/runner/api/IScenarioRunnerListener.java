package cse.nhm.ide.runner.api;

import java.util.Collection;

public interface IScenarioRunnerListener {
	public void scenarioRunnerUpdated(
			final IScenarioRunner runner,
			final Collection<? extends IScenarioRun> removed,
			final Collection<? extends IScenarioRun> changed,
			final Collection<? extends IScenarioRun> added
			);
}
