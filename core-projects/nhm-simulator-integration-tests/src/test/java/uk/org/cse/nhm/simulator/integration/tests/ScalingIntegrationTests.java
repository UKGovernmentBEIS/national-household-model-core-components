package uk.org.cse.nhm.simulator.integration.tests;

import java.io.IOException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;

public class ScalingIntegrationTests extends SimulatorIntegrationTest {

    @Test
    public void testScalingResponsiveness() throws NHMException, InterruptedException {
        loggingService = increasingMeanLogEntryHandler();

        runSimulation(fewerHouseCases(dataService, 0.01), loadScenario("scaling/scalingResponsiveness.s"), true, Collections.<Class<?>>emptySet());
    }

    private ILogEntryHandler increasingMeanLogEntryHandler() {
        return new ILogEntryHandler() {

            double lastValue = 0.0;

            @Override
            public void close() throws IOException {
                Assert.assertTrue("Should have received a report during the scenario.", lastValue > 0.0);
            }

            @Override
            public void acceptLogEntry(final ISimulationLogEntry entry) {
                if (entry instanceof AggregateLogEntry) {
                    final AggregateLogEntry report = (AggregateLogEntry) entry;
                    final Double mean = report.getColumns().get("mean");

                    Assert.assertTrue("mean value should increase each time we see a change.", mean > lastValue);
                    lastValue = mean;
                }
            }
        };
    }

}
