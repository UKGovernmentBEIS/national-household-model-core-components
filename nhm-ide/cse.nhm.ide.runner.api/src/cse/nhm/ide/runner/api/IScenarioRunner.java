package cse.nhm.ide.runner.api;

import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.runtime.IProgressMonitor;

/**
 * The service interface for something which can run scenarios, and provide information about
 * existing and past jobs.
 */
public interface IScenarioRunner {
	public String getName();
	public String getDescription();
	public String getStatus();

	public ScenarioSubmission submit(final String name,
                                     final IFile fileHandle,
                                     final IProgressMonitor monitor);

	public List<IScenarioRun> getScenarioRuns();

	public void addListener(IScenarioRunnerListener listener);
    public void removeListener(IScenarioRunnerListener listener);

    public void refresh();
}
