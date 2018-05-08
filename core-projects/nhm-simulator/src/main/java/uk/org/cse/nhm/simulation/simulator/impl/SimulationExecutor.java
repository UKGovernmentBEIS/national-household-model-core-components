package uk.org.cse.nhm.simulation.simulator.impl;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.Callable;

import javax.inject.Inject;
import javax.inject.Named;
import javax.management.timer.Timer;
import javax.xml.parsers.ParserConfigurationException;

import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

import com.google.common.base.Optional;
import com.larkery.jasb.sexp.ISExpression;
import com.larkery.jasb.sexp.errors.IErrorHandler.IError;

import uk.org.cse.nhm.ipc.api.scenario.IStockService;
import uk.org.cse.nhm.ipc.api.tasks.ISimulationExecutor;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationSession;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.language.sexp.IScenarioParser;
import uk.org.cse.nhm.language.sexp.IScenarioParser.IResult;
import uk.org.cse.nhm.language.sexp.ScenarioParserFactory;
import uk.org.cse.nhm.logging.logentry.errors.SystemErrorLogEntry;
import uk.org.cse.nhm.simulation.simulator.SimulatorRun;
import uk.org.cse.nhm.simulator.StopRunningException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.ISimulator;

/**
 * Provides methods for executing scenarios submitted to the worker queue <br />
 * When started, this service will listen for messages from the appropriate task
 * queue and handle request for scenario execution.
 *
 * @since 3.7.0
 */
public class SimulationExecutor implements ISimulationExecutor {

    public static final String MAXIMUM_CACHE_SIZE = "MAXIMUM_CACHE_SIZE";
    private final Logger log = LoggerFactory.getLogger(this.getClass());
    private final IStockService surveyCaseDataService;

    private final IScenarioParser<XScenario> parser;

    private final SimulatorRun run;

    @Inject
    public SimulationExecutor(
            final IStockService surveyCaseDataService,
            final ScenarioParserFactory parserFactory,
            final @Named(MAXIMUM_CACHE_SIZE) int maximumCacheSize) {
        this.surveyCaseDataService = surveyCaseDataService;
        run = new SimulatorRun(maximumCacheSize);
        this.parser = parserFactory.buildDefaultScenarioParser();
    }

    /**
     * This method validates the given scenario and runs a simulation of that
     * scenario if it is valid.
     *
     * @throws IOException
     * @throws ParserConfigurationException
     * @throws SAXException
     *
     *
     * @see
     * uk.org.cse.nhm.ipc.api.tasks.ISimulationExecutionService#run(java.util.UUID,
     * uk.org.cse.nhm.ipc.api.tasks.sim.IScenario, ILets)
     */
    @Override
    public List<? extends IError> run(final UUID runID, final ISExpression scenario, final ISimulationSession session) throws SAXException, ParserConfigurationException, IOException {
        if (session.isCancelled()) {
            return Collections.emptyList();
        }

        final long beginning = System.currentTimeMillis();

        log.debug("run(" + runID + ", " + scenario + "," + session + ")");
        session.progress("Validating...", 0d, Optional.<DateTime>absent());

        final IResult<XScenario> parse = parser.parse(scenario);

        if (!parse.hasErrors() && parse.getOutput().isPresent()) {
            session.progress("Executing...", 0.1d, Optional.<DateTime>absent());
            final XScenario xscenario = parse.getOutput().get();
            if (session.isCancelled()) {
                return Collections.emptyList();
            }

            try (final ISimulator simulator = run.build(xscenario, runID.toString(),
                    surveyCaseDataService,
                    SimulatorRun.createStandardParameters(session))) {

                simulator.addStoppingCriterion(new Callable<Boolean>() {
                    public Boolean call() {
                        return session.isCancelled();
                    }
                });

                final double totalTime = xscenario.getEndDate().getMillis()
                        - xscenario.getStartDate().getMillis();
                final double startTime = xscenario.getStartDate().getMillis();

                do {
                    final long now = System.currentTimeMillis();

                    final double completion = (simulator.getCurrentDate().getMillis() - startTime) / totalTime;

                    final DateTime etc;
                    if (completion == 0) {
                        etc = new DateTime(
                                (long) (now + 1000l * (45 + totalTime / (52 * Timer.ONE_WEEK)))); //45 seconds + 1 second per sim year 
                    } else {
                        etc = new DateTime(
                                (long) (now + ((now - beginning) * ((1 - completion) / completion))));
                    }

                    session.progress(simulator.getCurrentDate().getYear() + "",
                            completion * 0.9 + 0.1,
                            Optional.of(etc));
                } while (!session.isCancelled() && simulator.step() > 0);
            } catch (final StopRunningException stop) {
                log.info("Run cancelled by exception", stop);
                session.progress("Cancelled run", 1, Optional.<DateTime>absent());
                return Collections.emptyList();
            }
        } else {
            session.acceptLogEntry(new SystemErrorLogEntry(parse.getErrors().toString(), "", runID));
        }

        return parse.getErrors();
    }
}
