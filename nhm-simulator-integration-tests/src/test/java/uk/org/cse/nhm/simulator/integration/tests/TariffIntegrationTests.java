package uk.org.cse.nhm.simulator.integration.tests;

import java.io.IOException;
import java.util.Collections;

import org.junit.Assert;
import org.junit.Test;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;

public class TariffIntegrationTests extends SimulatorIntegrationTest {
	@Test
	public void tariffChanges() throws NHMException, InterruptedException {
		runSimulation(fewerHouseCases(dataService, 0.05), loadScenario("tariffs/tariffChanges.s"), true, Collections.<Class<?>>emptySet());
	}
	
	@Test
	public void tariffIncurrsCosts() throws NHMException, InterruptedException {
		final IState finishedState = runSimulation(fewerHouseCases(dataService, 0.05), loadScenario("tariffs/tariffCosts.s"), true, Collections.<Class<?>>emptySet()).state;

		for (final IDwelling d : finishedState.getDwellings()) {
			final IFlags flags = finishedState.get(testExtension.flags, d);
			final DwellingTransactionHistory transactions = finishedState.get(testExtension.transactionHistory, d);
			if(flags.testFlag("tariff-increases-over-time")) {
				
				Assert.assertEquals("Tariff should add up to 1 + 2 + 3 + 4 + 5.", -15.0, transactions.getBalance(), 0.0);
			} else {
				Assert.assertEquals("Tariff should add up to 5 * 1.", -5.0, transactions.getBalance(), 0.0);
			}
		}
	}
	
	@Test
	public void tariffCostsBasedOnEnergyUse() throws NHMException, InterruptedException {
		//IState finishedState = runSimulation(fewerHouseCases(dataService, 0.05), loadScenario("tariffs/tariffEnergyCosts.xml")).getState();
		final IState finishedState = runSimulation(restrictHouseCases(dataService, "H0012401"), loadScenario("tariffs/tariffEnergyCosts.s"), true, Collections.<Class<?>>emptySet()).state;
		
		// This test combines steps and efficiency changes, which isn't very helpful. Can we separate them?
		
		for (final IDwelling d : finishedState.getDwellings()) {
			final IFlags flags = finishedState.get(testExtension.flags, d);
			final DwellingTransactionHistory history = finishedState.get(testExtension.transactionHistory, d);
			
			if(flags.testFlag("inefficient boiler")) {
				Assert.assertTrue(String.format("Inefficient boiler should result in large fuel bill (was %s).", -history.getBalance()), history.getBalance() < -10000.0);
			}
			
			if(flags.testFlag("efficient boiler")) {
				Assert.assertTrue("Efficient boiler should result in smaller fuel bill.", history.getBalance() > -1000.0);
			} 
		}
	}
	
	@Test
	public void extraChargeIsPaid() throws NHMException, InterruptedException {
			loggingService = new ILogEntryHandler() {
			
			int count = 0;
			
			@Override
			public void close() throws IOException {
				if (count != 2) {
					Assert.fail("Should have received 2 report log entries.");
				}
			}
			
			@Override
			public void acceptLogEntry(final ISimulationLogEntry entry) {
				if (entry instanceof AggregateLogEntry) {
					final AggregateLogEntry aggregate = (AggregateLogEntry) entry;
					
					final boolean onGas = aggregate.getReducedRowKey().get("on-gas").equals("true");
					
					if (onGas) {
						Assert.assertEquals("Houses on the gas grid should not have the discount).", 0.0, aggregate.getColumns().get("average-energy-spend"), 0.0);
					} else {
						Assert.assertEquals("Houses off the gas grid should have the discount.", -135.0, aggregate.getColumns().get("average-energy-spend"), 0.0);
					}
					
					count++;
				}
			}
		};
		
		runSimulation(dataService, loadScenario("tariffs/extraCharges.s"), true, Collections.<Class<?>>emptySet());
	}
	
	@Test
	public void extraChargesAreOrdered() throws NHMException, InterruptedException {
		loggingService = new ILogEntryHandler() {
			
			boolean hit = false;
			
			@Override
			public void close() throws IOException {
				if (!hit) {
					Assert.fail("Should have received a report log entry.");
				}
			}
			
			@Override
			public void acceptLogEntry(final ISimulationLogEntry entry) {
				if (entry instanceof AggregateLogEntry) {
					Assert.assertEquals("Spend should equal one fuel payment of 1000 + 10% + 899 = 1999.", 1999, ((AggregateLogEntry) entry).getColumns().get("average-energy-spend"), 0.0);
					hit = true;
				}
			}
		};
		
		runSimulation(restrictHouseCases(dataService, "H0012401"), loadScenario("tariffs/orderedExtraCharges.s"), true, Collections.<Class<?>>emptySet());
	}
}
