package uk.org.cse.nhm.ipc.api.tasks;

import java.io.Closeable;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;

/**
 * Runs a simulation or group of simulations;
 * this may include doing batch expansion, and generates a report at the end
 * 
 * @since 4.0.0
 */
public interface IBatchExecutor {
	public interface IBatchSession extends ITaskSession, Closeable {
		/**
		 * Tell our caller that a report has been finished
		 * @param urlToReport
		 * @throws IOException 
		 */
		public void handleCompletion(final String urlToReport) throws IOException;
		public void failed(final String message);
		
		/**
		 * Submit some simulations to run somewhere in the system. This is the "input" side of 
		 * {@link #register(ISimulationExecutor)}.
		 * @param count 
		 * 
		 * @param isBatch if this is true, the scenarios should be run as batches; it is an error to specify false
		 * 					if snapshots has many elements in it.
		 * @param snapshots something which provides some scenario snapshots to run
		 * @param logEntryHandler a thing to consume the outputs of all the various jobs.
		 * 
		 * @return a sequence of UUIDs, each one corresponding to the snapshot at the same position in the input iterator.
		 * @throws IOException
		 */
		public List<UUID> submitSimulations(
				int count, final boolean isBatch,
				final Iterable<IScenarioSnapshot> snapshots, 
				final ILogEntryHandler entryHandler);
	}
	
	public void runSimulationGroup(
			final String sourceID,
			final IBatchSession callback);
}
