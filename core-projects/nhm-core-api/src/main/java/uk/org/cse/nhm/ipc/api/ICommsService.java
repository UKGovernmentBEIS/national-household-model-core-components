package uk.org.cse.nhm.ipc.api;

import java.io.Closeable;

import uk.org.cse.nhm.ipc.api.reporting.IReportService;
import uk.org.cse.nhm.ipc.api.scenario.IScenarioService;
import uk.org.cse.nhm.ipc.api.tasks.ITaskService;

import com.google.common.base.Optional;

/**
 * A handle onto the various services which allow communication between different NHM subsystems.
 * 
 * @since 3.7.0
 *
 */
public interface ICommsService extends Closeable {
	public Optional<ITaskService> 		getTaskService();
	
	public Optional<IReportService>		getReportService();
	
	public Optional<IScenarioService> 	getScenarioService();
}
