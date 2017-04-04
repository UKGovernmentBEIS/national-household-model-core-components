package cse.nhm.ide.runner.api;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;
import java.util.HashSet;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IProject;
import org.eclipse.core.runtime.IPath;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.SubProgressMonitor;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableMap;

import cse.nhm.ide.ui.WorkspaceFS;
import cse.nhm.ide.ui.builder.NHMNature;
import cse.nhm.ide.ui.models.ServiceTrackingModel;
import uk.org.cse.nhm.bundle.api.IRunInformation;

public abstract class ScenarioRunner implements IScenarioRunner {
	private final LinkedHashSet<IScenarioRunnerListener> listeners = new LinkedHashSet<>();

	@Override
	public void addListener(final IScenarioRunnerListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeListener(final IScenarioRunnerListener listener) {
		listeners.remove(listener);
	}

	@Override
	public synchronized ScenarioSubmission submit(final String name,
                                                  final IFile fileHandle,
                                                  final IProgressMonitor monitor) {
		final IProject project = fileHandle.getProject();
		monitor.beginTask("Preparing to run scenario", 100);
		if (project.exists()) {
			// find an NHM, construct a job, store the data in the path, enqueue
			final Optional<NHMNature> nature_ = NHMNature.of(project);
			if (nature_.isPresent()) {
				final NHMNature nature = nature_.get();

				final ServiceTrackingModel model = nature.getModel();

				if (!model.isAvailable()) {
					throw new RuntimeException("Model version " + model.getTargetVersion() +
							" is not installed, and so cannot be used to run this scenario");
				}

				final IRunInformation<IPath> information = model.<IPath>getRunInformation(WorkspaceFS.INSTANCE,
						fileHandle.getFullPath());

				final ImmutableMap.Builder<String, String> stockHashMapping = ImmutableMap.builder();
				final ImmutableMap.Builder<String, Path> stockPathMapping = ImmutableMap.builder();

				monitor.worked(10);

				String hash;
				try {
					hash = HashUtility.hashRun(model.version(), information, stockHashMapping, stockPathMapping);
				} catch (final IOException e) {
					throw new RuntimeException("Unable to hash " + fileHandle.getName() + ": " + e.getMessage(), e);
				}

				monitor.worked(10);

                // find out whether job already exists
                final Optional<IScenarioRun> existingJob = doGet(hash);
                if (existingJob.isPresent()) {
                    monitor.worked(80);
                    return new ScenarioSubmission(name, existingJob.get(), true);
                } else {
                    final IScenarioRun newJob = doSubmit(hash,
                                                         name,
                                                         model.version(),
                                                         stockHashMapping.build(),
                                                         stockPathMapping.build(),
                                                         information.snapshots(),
                                                         information.isBatch(),
                                                         new SubProgressMonitor(monitor, 80));

                    added(newJob);

                    return new ScenarioSubmission(name, newJob, false);
                }
			} else {
				throw new IllegalArgumentException("Project " + project + " does not have an NHM version associated with it.");
			}
		} else {
			throw new IllegalArgumentException(fileHandle + " is not in a project");
		}
	}

    protected abstract Optional<IScenarioRun> doGet(final String hash);

	protected abstract IScenarioRun doSubmit(
			final String hash,
			final String name,
			final String version,
			final Map<String, String> stockHashes,
			final Map<String, Path> stockFiles,
			final Iterable<String> snapshotText,
			final boolean isBatch,
			final IProgressMonitor monitor
			);

	protected void updated(final Collection<? extends IScenarioRun> removed, final Collection<? extends IScenarioRun> changed, final Collection<? extends IScenarioRun> added) {
		for (final IScenarioRunnerListener l : listeners.toArray(new IScenarioRunnerListener[0])) {
			try {
				l.scenarioRunnerUpdated(this, removed, changed, added);
			} catch (final Throwable th) {
				th.printStackTrace();
			}
		}
	}

	public void removed(final IScenarioRun... remoteRun) {
		updated(Arrays.asList(remoteRun),
				Collections.<IScenarioRun>emptySet(),
				Collections.<IScenarioRun>emptySet());
	}

	public void added(final IScenarioRun... remoteRun) {
		updated(Collections.<IScenarioRun>emptySet(),
				Collections.<IScenarioRun>emptySet(),
				Arrays.asList(remoteRun));
	}

	public void changed(final IScenarioRun... remoteRun) {
		updated(Collections.<IScenarioRun>emptySet(),
				Arrays.asList(remoteRun),
				Collections.<IScenarioRun>emptySet());
    }

    @Override
    public void refresh() {

    }
}
