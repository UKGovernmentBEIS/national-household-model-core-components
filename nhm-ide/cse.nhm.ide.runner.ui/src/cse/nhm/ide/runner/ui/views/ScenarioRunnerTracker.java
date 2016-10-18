package cse.nhm.ide.runner.ui.views;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Set;

import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import org.osgi.util.tracker.ServiceTracker;

import cse.nhm.ide.runner.api.IScenarioRun;
import cse.nhm.ide.runner.api.IScenarioRunner;
import cse.nhm.ide.runner.api.IScenarioRunnerListener;
import cse.nhm.ide.runner.ui.preferences.RunnerUIPreferences;

public class ScenarioRunnerTracker extends ServiceTracker<IScenarioRunner, IScenarioRunner> implements IScenarioRunnerListener {
    private final LinkedHashSet<IScenarioRunnerListener> listeners = new LinkedHashSet<>();
	
	private final Set<IScenarioRunner> runners = new HashSet<>();
	private final Set<IScenarioRun> allRuns = new LinkedHashSet<>();
	
	public ScenarioRunnerTracker(BundleContext context) {
		super(context, IScenarioRunner.class, null);
	}

    public List<IScenarioRunner> getRunners() {
        final IScenarioRunner[] arr = getServices(new IScenarioRunner[0]);
        final boolean hideLocal = RunnerUIPreferences.isLocalRunnerHidden();

        if (hideLocal && arr.length > 1) {
            final List<IScenarioRunner> result = new ArrayList<IScenarioRunner>(arr.length);
            for (final IScenarioRunner runner : arr) {
                if (runner.getName().equals("local")) continue;
                result.add(runner);
            }
            return result;
        } else {
            return Arrays.asList(getServices(new IScenarioRunner[0]));
        }
    }

	@Override
	public void close() {
		for (final IScenarioRunner r : runners)
			r.removeListener(this);
		runners.clear();
		super.close();
	}

	@Override
	public IScenarioRunner addingService(ServiceReference<IScenarioRunner> reference) {
		final IScenarioRunner runner = super.addingService(reference);
		runner.addListener(this);
		runners.add(runner);
		
		scenarioRunnerUpdated(runner, 
				Collections.<IScenarioRun>emptySet(), Collections.<IScenarioRun>emptySet(),
				runner.getScenarioRuns());
		
		return runner;
	}

	@Override
	public void removedService(ServiceReference<IScenarioRunner> reference, final IScenarioRunner service) {
		service.removeListener(this);
		runners.remove(service);
		
		service.removeListener(this);
		scenarioRunnerUpdated(service, service.getScenarioRuns(), 
				Collections.<IScenarioRun>emptySet(), Collections.<IScenarioRun>emptySet());

		super.removedService(reference, service);
	}

	@Override
	public void scenarioRunnerUpdated(
			final IScenarioRunner runner, 
			final Collection<? extends IScenarioRun> removed,
			final Collection<? extends IScenarioRun> changed, 
			final Collection<? extends IScenarioRun> added) {
		allRuns.removeAll(removed);
		allRuns.addAll(added);
		for (final IScenarioRunnerListener l : listeners.toArray(new IScenarioRunnerListener[0])) {
			try {
				l.scenarioRunnerUpdated(runner, removed, changed, added);
			} catch (Throwable th) {
				th.printStackTrace();
			}
		}
	}
	
	public void addListener(final IScenarioRunnerListener listener) {
		listeners.add(listener);
	}

	public void removeListener(final IScenarioRunnerListener listener) {
		listeners.remove(listener);
	}

	public Set<IScenarioRun> getRuns() {
		return allRuns;
	}
}
