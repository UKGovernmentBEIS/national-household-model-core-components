package uk.org.cse.nhm.ipc.api.tasks;

import java.io.IOException;
import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.nhm.ipc.api.tasks.ITaskStatus.Phase;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;

/**
 * Provides a facility to handle tasks across the NHM system.
 * <br />
 * This provides the interface through which a client can submit tasks for the
 * NHM and get information on or manage those tasks.
 * <br />
 * This also provides the endpoint through which task runner/handler instances
 * can register themselves to handle/process tasks.
 * <br />
 * e.g. An instance of the simulation engine, running on a remote machine can
 * register itself as a worker available for processing simulation tasks through
 * this interface.
 *
 * @since 3.7.0
 *
 */
public interface ITaskService {

    /**
     * Submit the given simulation task for validation and execution
     *
     * This is the "input" side of {@link #register(IBatchExecutor)}
     *
     * @since 3.7.0
     * @param scenarioID id of a scenario (or a monte carlo scenario)
     * @return
     * @throws IOException
     */
    public Optional<String> submitSimulationTask(final String scenarioID, final String username) throws IOException;

    /**
     * Register an {@link IBatchExecutor}; this is the "output" side of
     * {@link #submitSimulationTask(String)}.
     *
     * An {@link IBatchExecutor} is expected to be able to take a scenario ID,
     * load it from a store somewhere, identify whether it is a batch or a
     * single job, and then simulate it, turn it into a report, and eventually
     * provide that report back to the task service for consolidation into a
     * report repository or similar.
     *
     * @param runner
     * @throws IOException
     */
    public void register(final IBatchExecutor runner) throws IOException;

    /**
     * This is the "output" side of
     * {@link #submitSimulations(boolean, Iterable, ILogEntryHandler)};
     *
     * An {@link ISimulationExecutor} needs to be able to take scenario
     * snapshots and generate log entries from running them.
     *
     * @param runner
     * @throws IOException
     */
    public void register(final ISimulationExecutor runner) throws IOException;

    /**
     * Cancel a task
     *
     * @param taskID
     */
    public void cancel(final String scenarioID);

    public interface IListener {

        public void taskStatusChanged(final ITaskStatus newStatus);
    }

    public void addListener(Optional<String> scenarioID, Optional<String> taskID, final Optional<Phase> phase, IListener listener);

    public void removeListener(final IListener listener);

    public Optional<ITaskStatus> getTaskStatus(String scenarioID);

    List<ITaskStatus> getTaskStatuses(Set<Phase> phases);
}
