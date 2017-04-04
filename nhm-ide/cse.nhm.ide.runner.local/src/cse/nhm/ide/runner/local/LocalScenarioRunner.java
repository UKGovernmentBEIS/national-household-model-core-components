package cse.nhm.ide.runner.local;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;

import com.google.common.collect.ImmutableList;
import com.google.common.base.Optional;

import cse.nhm.ide.runner.api.IScenarioRun;
import cse.nhm.ide.runner.api.ScenarioRunner;

public class LocalScenarioRunner extends ScenarioRunner {
	// we will store all our run data in the state path.
	// each run gets an ID which is a hash of the inputs

	private final IPath statePath;

	private final BlockingDeque<LocalScenarioRun> jobsToRun = new LinkedBlockingDeque<>();

	String status = "Waiting";

	private Job job;

	private boolean stopped;

	public LocalScenarioRunner() {
		statePath = LocalRunnerPlugin.getPlugin().getStateLocation().append("jobs");
	}

	public void start() {
		stopped = false;

		job = new Job("Running locally queued scenarios") {
			@Override
			protected IStatus run(final IProgressMonitor monitor) {
				if (!monitor.isCanceled() && !stopped) {
					try {
						final LocalScenarioRun run = jobsToRun.take();
						LocalRunnerPlugin.logInformation("Started local run of %s [%s]", run.getID(), run.getName());
						monitor.beginTask("Running local scenario " + run.getName(), 100);
						run.run(monitor);
						LocalRunnerPlugin.logInformation("Completed local run of %s [%s]", run.getID(), run.getName());
					} catch (final InterruptedException e) {
					} catch (final Throwable th) {
						LocalRunnerPlugin.logException("Exception while running a local job", th);
					}
				}
				if (!monitor.isCanceled() && !jobsToRun.isEmpty()) schedule();
				return Status.OK_STATUS;
			}
		};
	}

	public void stop() {
		stopped = true;
		job.cancel();
	}

	@Override
	public String getName() {
		return "local";
	}

	@Override
	public String getDescription() {
		return "this computer";
	}

	@Override
	public String getStatus() {
		return status;
	}

    @Override
    protected Optional<IScenarioRun> doGet(final String hash) {
        final File f = statePath.toFile().toPath().resolve(LocalScenarioRun.RUNS).resolve(hash).toFile();
        if (f.exists() && f.isDirectory()) {
            return Optional.<IScenarioRun>of(LocalScenarioRun.existing(this, statePath.toFile().toPath(), hash));
        }
        return Optional.absent();
    }

	@Override
	protected IScenarioRun doSubmit(
			final String hash,
			final String name,
			final String version,
			final Map<String, String> stockHashes,
			final Map<String, Path> stockFiles,
			final Iterable<String> snapshots,
			final boolean isBatch,
			final IProgressMonitor monitor) {
		try {
			return LocalScenarioRun.of(
					this,
					statePath.toFile().toPath(),
					hash,
					name,
					version,
					stockHashes,
					stockFiles,
					snapshots,
					isBatch);
		} catch (final IOException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}

	@Override
	public List<IScenarioRun> getScenarioRuns() {
		final ImmutableList.Builder<IScenarioRun> output = ImmutableList.builder();
		final File f = statePath.toFile().toPath().resolve(LocalScenarioRun.RUNS).toFile();
		if (f.exists()) {
			for (final File d : f.listFiles()) {
				if (d.isDirectory()) {
					output.add(LocalScenarioRun.existing(this, statePath.toFile().toPath(), d.getName()));
				}
			}
		}
		return output.build();
	}

	void jobsUpdated(final Collection<LocalScenarioRun> removed, final Collection<LocalScenarioRun> changed, final Collection<LocalScenarioRun> added) {
		jobsToRun.addAll(added);
		super.updated(removed, changed, added);
		if (!stopped && !added.isEmpty()) {
			job.schedule();
		}
	}
}
