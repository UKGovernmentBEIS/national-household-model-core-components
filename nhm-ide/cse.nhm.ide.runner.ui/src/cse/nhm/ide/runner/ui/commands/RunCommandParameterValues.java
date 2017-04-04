package cse.nhm.ide.runner.ui.commands;

import java.util.HashMap;
import java.util.Map;

import org.eclipse.core.commands.IParameterValues;

import cse.nhm.ide.runner.api.IScenarioRunner;
import cse.nhm.ide.runner.ui.RunnerUIPlugin;

public class RunCommandParameterValues implements IParameterValues {
	@SuppressWarnings("rawtypes")
	@Override
	public Map getParameterValues() {
		final Map<String, String> result = new HashMap<>();
		
		for (final IScenarioRunner runner : RunnerUIPlugin.getDefault().tracker.getRunners()) {
			result.put(runner.getName(), runner.getName());
		}
		
		return result;
	}
}
