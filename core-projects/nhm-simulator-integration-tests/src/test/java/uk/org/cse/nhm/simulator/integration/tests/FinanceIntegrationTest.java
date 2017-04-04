package uk.org.cse.nhm.simulator.integration.tests;

import java.io.IOException;
import java.util.Collections;
import java.util.LinkedList;
import java.util.Queue;

import org.junit.Assert;
import org.junit.Test;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterables;

import uk.org.cse.commons.scopes.IScope;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.language.builder.action.measure.wetheating.WetHeatingMeasure;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.logging.logentry.AggregateLogEntry;
import uk.org.cse.nhm.logging.logentry.TransactionLogEntry;
import uk.org.cse.nhm.simulation.measure.boilers.StandardBoilerMeasure;
import uk.org.cse.nhm.simulator.integration.tests.guice.IFunctionAssertion;
import uk.org.cse.nhm.simulator.integration.tests.guice.IntegrationTestOutput;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.obligations.impl.FixedInterestLoanObligation;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

public class FinanceIntegrationTest extends SimulatorIntegrationTest {
	@Test
	public void globalPayment() throws NHMException, InterruptedException {
		loggingService = new ILogEntryHandler(){

			int payments;
			
			@Override
			public void close() throws IOException {
				Assert.assertEquals("Should have been the right number of global payments.", 3, payments);
			}

			@Override
			public void acceptLogEntry(final ISimulationLogEntry entry) {
				if (entry instanceof TransactionLogEntry) {
					final TransactionLogEntry t = (TransactionLogEntry) entry;
					if (!t.getPayer().startsWith("dwelling")) {
						Assert.assertEquals("Should pay the right account.", "general-taxation", t.getPayer());
						Assert.assertEquals("Should deduct from the right account.", "government-policies", t.getPayee());
						Assert.assertEquals("Should contain the right tags.", "taxation", t.getTags());
						Assert.assertEquals("Should pay the right amount for payment #" + payments, 10000 * Math.pow(2, payments), t.getAmount(), 0.0);
						payments++;
					}
				}
			}
		};
		
		super.runSimulation(dataService, loadScenario("finance/globalPayment.s"), true, Collections.<Class<?>>emptySet());
	}
	
	@Test
	public void withObligation() throws NHMException, InterruptedException {
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
					Assert.assertEquals("Houses should have spent 3 payments * 1 = 3 money.", 2.0, ((AggregateLogEntry) entry).getColumns().get("average-obligated-spend"), 0.0);
					hit = true;
				}
			}
		};
		
		super.runSimulation(fewerHouseCases(dataService, 0.05), loadScenario("finance/withObligation.s"), true, Collections.<Class<?>>emptySet());
	}
	
	@Test
	public void fixedRateLoans() throws Exception {
		final XScenario scenario = loadScenario("finance/fixedrateloan.s");
		
		final IState state = runSimulation(fewerHouseCases(dataService, 0.03), scenario, true, Collections.<Class<?>>emptySet()).state;
		
		for (final IDwelling d : state.getDwellings()) {
			final IObligationHistory obligations = state.get(testExtension.obligationHistory, d);
			final DwellingTransactionHistory transactions = state.get(testExtension.transactionHistory, d);
			
			if (state.get(testExtension.basicAttributes, d).getRegionType() == RegionType.London) {
				Assert.assertTrue("The final obligation should be a loan obligation, if in london",
						obligations.reverseIterator().next() instanceof FixedInterestLoanObligation);
				
				Assert.assertEquals("House should be in deficit by the interest amount.", -50.0, transactions.getBalance(), ERROR_DELTA);
				
			} else {
				for (final IObligation ob : obligations) {
					Assert.assertFalse("Nobody outside london ought to have got a loan", ob instanceof FixedInterestLoanObligation);
				}
			}
		}
	}
	
	@Test
	public void predictObligations() throws NHMException, InterruptedException {
		runSimulation(
				fewerHouseCases(dataService, 0.05), 
				loadScenario("predictobligations.s"),
				true,
				Collections.<Class<?>>emptySet(), ImmutableMap.<String, IFunctionAssertion>of(
						"new-obligations",
						new IFunctionAssertion() {
							@Override
							public void evaluate(
									final String name, 
									final IntegrationTestOutput output,
									final IComponentsScope scope, 
									final ILets lets, final double value) {
								Assert.assertTrue(
										(value == 0 && scope.getAllNotes(IObligation.class).isEmpty()) ||
										value == 1000d);
							}
						}));
	}
	
	@Test
	public void testAdditionalCosts() throws NHMException, InterruptedException {
		final XScenario scenario = loadScenario("additionalCostsScenario.s");

		final CountingStateListener listener = new CountingStateListener() {

			@Override
			public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
				if (notification.getRootScope().getTag().getIdentifier().getName().startsWith("install standard boiler with hassle cost")) {
					incrementChanges();

					final IDwelling dwelling = Iterables.get(notification.getAllChangedDwellings(), 0);
					boolean boilerCapexFound = false;
					boolean wetHeatingCapexFound = false;
					boolean hassleCostFound = false;
					boolean installationCostFound = false;

					final Queue<IScope<? extends IStateChangeSource>> scopesToLookAt = new LinkedList<>();
					scopesToLookAt.add(notification.getRootScope().getComponentsScope(dwelling).get());
					
					while(!scopesToLookAt.isEmpty()) {
						final IScope<? extends IStateChangeSource> current = scopesToLookAt.poll();
						scopesToLookAt.addAll(current.getSubScopes());
						
						for(final ITransaction cost : current.getLocalNotes(ITransaction.class)) {
							if (cost.getTags().contains("hassle")) {
								Assert.assertEquals("Hassle cost should have been included based on the combined capex of the boiler and wet heating technologies.", 2001d, cost.getAmount(), ERROR_DELTA);
								hassleCostFound = true;
							} else if(cost.getTags().contains("installation")) {
								Assert.assertEquals("Installation cost should be taken from the defined number.", 1d, cost.getAmount(), ERROR_DELTA);
								installationCostFound = true;
							} else if (cost.getTags().contains(ITransaction.Tags.CAPEX)) {
								if (current.getTag() instanceof StandardBoilerMeasure) {
									Assert.assertEquals("Boiler capex should be taken from the inner measure.", 1d, cost.getAmount(), ERROR_DELTA);
									boilerCapexFound = true;
								} else if (current.getTag() instanceof WetHeatingMeasure) {
									Assert.assertEquals("Wet heating capex should be taken from the inner measure.", 2000d, cost.getAmount(), ERROR_DELTA);
									wetHeatingCapexFound = true;
								}
							}
						}
					}
					
					if(!boilerCapexFound) {
						Assert.fail("Boiler capex was missing.");	
					}
					if(!wetHeatingCapexFound) {
						Assert.fail("Central heating capex was missing.");	
					}
					if(!hassleCostFound) {
						Assert.fail("Boiler installation hassle cost was missing.");
					}
					if(!installationCostFound) {
						Assert.fail("Boiler installation cost was missing.");
					}
				}
			}
		};

		final String houseKnownToBeSuitableForStandardGasBoiler = "H0271307";
		runSimulation(restrictHouseCases(dataService, houseKnownToBeSuitableForStandardGasBoiler), scenario, true, Collections.<Class<?>>emptySet(), listener);

		Assert.assertEquals("The target should have been successfully applied.", 1, listener.getChangeCount());
	}
}
