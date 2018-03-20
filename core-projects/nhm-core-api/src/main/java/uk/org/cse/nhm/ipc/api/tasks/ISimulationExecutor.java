package uk.org.cse.nhm.ipc.api.tasks;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationSession;

/**
 * Callback interface for a simulator to interface; implementors should register themself with
 * their {@link ITaskService} to get presented with jobs.
 * 
 * Implementations are clients of the IPC API
 * 
 * @since 3.7.0
 *
 */
public interface ISimulationExecutor {

	/**
	 * Run a simulation with the given stock and scenario.
	 * 
	 * Talk to the session to record your progress or log messages.
	 * 
	 * <p>
	 * behind the scenes, this method is expected to be called by something
	 * which has done the following:
	 * <ol>
	 * <li>Received a request to run a sim job from the sim jobs queue</li>
	 * <li>Loaded the stock and scenario with the given UUID from the
	 * dataservice</li>
	 * <li>Constructed a new endpoint for log messages to go to</li>
	 * <li>Tasked another machine with reporting on the messages from that
	 * endpoint</li>
	 * <li>newed up the session argument to log to that endpoint</li>
	 * <li>finally invoked this method</li>
	 * </ol>
	 * </p>
	 * 
	 * @param runID
	 * 			  an id for the run
	 * @param scenarioSnapshot
	 *            scenario to run
	 * @param session
	 *            session to log to and provide status to
	 * @return problems (empty on success)
	 * @throws IOException 
	 * @throws ParserConfigurationException 
	 * @throws SAXException 
	 * @since 3.7.0
	 */
	List<? extends IError> run(final UUID runID, final ISExpression scenario, final ISimulationSession session) throws SAXException, ParserConfigurationException, IOException;
}
