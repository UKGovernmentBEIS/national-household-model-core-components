package uk.org.cse.nhm.simulation.reporting.accounts;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableMap.Builder;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.GlobalAccountsLogEntry;
import uk.org.cse.nhm.simulator.main.ISimulationStepListener;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.state.ICanonicalState;

public class GlobalAccountsReport implements ISimulationStepListener {

    private final ICanonicalState state;
    private final ILogEntryHandler loggingService;

    @Inject
    public GlobalAccountsReport(
            ISimulator simulator,
            ICanonicalState state,
            ILogEntryHandler loggingService) {
        this.state = state;
        this.loggingService = loggingService;
        simulator.addSimulationStepListener(this);
    }

    @Override
    public void simulationStepped(DateTime dateOfStep, DateTime nextDate, boolean isFinalStep) throws NHMException {
        Builder<String, Double> accountBalances = ImmutableMap.builder();
        for (String account : state.getGlobals().getGlobalAccountNames()) {
            accountBalances.put(account, state.getGlobals().getGlobalAccount(account).getBalance());
        }

        loggingService.acceptLogEntry(new GlobalAccountsLogEntry(dateOfStep, accountBalances.build()));
    }
}
