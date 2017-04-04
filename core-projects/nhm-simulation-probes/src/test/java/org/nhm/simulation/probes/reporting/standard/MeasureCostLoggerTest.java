package org.nhm.simulation.probes.reporting.standard;

import org.joda.time.DateTime;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.IOException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Set;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.ipc.api.tasks.sim.ISimulationLogEntry;
import uk.org.cse.nhm.logging.logentry.MeasureCostLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.simulation.reporting.standard.MeasureCostLogger;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.measure.ITechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

public class MeasureCostLoggerTest {

	private static final DateTime startDate = new DateTime();
	private static final DateTime secondDate = startDate.plusMonths(6);

	private TestLogEntryHandler loggingService;
	private ISimulator simulator;
	private ICanonicalState state;

	private IDwelling d1;
	private IComponentsScope scope1;
	private IDwelling d2;
	private IComponentsScope scope2;
	private Set<IDwelling> dwellings;

	private IComponentsAction action1;
	private IComponentsAction action2;

	private TestInstallationDetails installation1;

	private IStateChangeNotification notification;
	private IStateScope rootScope;
	private IStateChangeSource tag;

	private MeasureCostLogger logger;

	@Before
	public void setup() {
		loggingService = new TestLogEntryHandler();
		simulator = mock(ISimulator.class);
		state = mock(ICanonicalState.class);

		d1 = mock(IDwelling.class);
		scope1 = mock(IComponentsScope.class);
		d2 = mock(IDwelling.class);
		scope2 = mock(IComponentsScope.class);
		dwellings = ImmutableSet.of(d1, d2);

		notification = mock(IStateChangeNotification.class);
		rootScope = mock(IStateScope.class);
		tag = mock(IStateChangeSource.class);

		action1 = mock(IComponentsAction.class);
		action2 = mock(IComponentsAction.class);

		when(action1.getIdentifier()).thenReturn(Name.of("action1"));
		when(action2.getIdentifier()).thenReturn(Name.of("action2"));

		installation1 = new TestInstallationDetails(action1);

		// Set up state change source type
		when(notification.getRootScope()).thenReturn(rootScope);
		when(rootScope.getTag()).thenReturn(tag);


		// set up dwellings
		when(notification.getAllChangedDwellings()).thenReturn(dwellings);
		when(rootScope.getComponentsScope(d1)).thenReturn(Optional.of(scope1));
		when(rootScope.getComponentsScope(d2)).thenReturn(Optional.of(scope2));

		logger = new MeasureCostLogger(
				loggingService,
				simulator,
				state
			);
	}

	@Test
	public void listens() {
		verify(simulator, times(1)).addSimulationStepListener(Mockito.eq(logger));
		verify(state, times(1)).addStateListener(logger);
	}

	@Test
	public void header() {
		// This seems like a bit of weird design.
		// Why include this header at all if we don't send the fields with it?
		Assert.assertEquals("The header should have the right type.", ReportHeaderLogEntry.Type.MeasureCosts, loggingService.getHeader().getType());
	}

	@Test
	public void stepWithoutChangeLogsNothing() {
		logger.simulationStepped(startDate, secondDate, false);
		loggingService.assertZero();
	}

	@Test
	public void allowedSourceTypes() {
		final Set<StateChangeSourceType> validSources = EnumSet.of(
				StateChangeSourceType.ACTION,
				StateChangeSourceType.CREATION,
				StateChangeSourceType.TRIGGER
				);

		when(scope1.getAllNotes(ITechnologyInstallationDetails.class)).thenReturn(
				ImmutableList.<ITechnologyInstallationDetails>of(installation1));

		for (final StateChangeSourceType type : EnumSet.of(
				StateChangeSourceType.TIME,
				StateChangeSourceType.INTERNAL,
				StateChangeSourceType.OBLIGATION
				)) {
			when(tag.getSourceType()).thenReturn(type);
			logger.stateChanged(state, notification);
		}
		logger.simulationStepped(startDate, secondDate, false);

		Assert.assertEquals("There should be no entries for the disallowed state change source types.", 0, loggingService.getEntries().size());


		for (final StateChangeSourceType type : validSources) {
			when(tag.getSourceType()).thenReturn(type);
			logger.stateChanged(state, notification);
			logger.simulationStepped(startDate, secondDate, false);
			loggingService.assertCount("Allowed source type should have produced an entry.", 1);
			loggingService.clear();
		}
	}

	@Test
	public void entryPerTechnology() {
		when(scope1.getAllNotes(ITechnologyInstallationDetails.class)).thenReturn(
				ImmutableList.<ITechnologyInstallationDetails>of(
						installation1,
						// This duplicate should not create an extra row, but should add to the existing row.
						installation1,
						installation1.copy().setInstalledTechnology(TechnologyType.districtHeating())
						));
		when(tag.getSourceType()).thenReturn(StateChangeSourceType.ACTION);
		logger.stateChanged(state, notification);
		logger.simulationStepped(startDate, secondDate, false);
		loggingService.assertCount("Expected a log entry for each different technology.", 2);

		Assert.assertEquals("Each log entry should contain one of the two different technologies.",
				ImmutableSet.of(
						installation1.getInstalledTechnology().toString(),
						TechnologyType.districtHeating().toString()
					),
				ImmutableSet.of(
						loggingService.getEntries().get(0).getTechnology(),
						loggingService.getEntries().get(1).getTechnology()
					)
				);
	}

	@Test
	public void entryByMeasure() {
		when(scope1.getAllNotes(ITechnologyInstallationDetails.class)).thenReturn(
				ImmutableList.<ITechnologyInstallationDetails>of(
						installation1,
						installation1.copy().setInstallationSource(action2)
						));

		when(tag.getSourceType()).thenReturn(StateChangeSourceType.ACTION);
		logger.stateChanged(state, notification);
		logger.simulationStepped(startDate, secondDate, false);
		loggingService.assertCount("Expected a log entry for each different technology.", 2);

		Assert.assertEquals("Each log entry should contain one of the two different measures.",
				ImmutableSet.of(
						action1.getIdentifier().getPath(),
						action2.getIdentifier().getPath()
					),
				ImmutableSet.of(
						loggingService.getEntries().get(0).getSource(),
						loggingService.getEntries().get(1).getSource()
					)
				);
	}

	@Test(expected = MeasureCostLogger.IncompatibleUnitsException.class)
	public void rejectsIncompatibleUnits() {
		when(scope1.getAllNotes(ITechnologyInstallationDetails.class)).thenReturn(
				ImmutableList.<ITechnologyInstallationDetails>of(
						installation1,
						installation1.copy().setUnits(Units.SQUARE_METRES)
					));

		logger.stateChanged(state, notification);
	}

	@Test
	public void results() {
		when(d1.getWeight()).thenReturn(0.5f);

		when(scope1.getAllNotes(ITechnologyInstallationDetails.class)).thenReturn(
				ImmutableList.<ITechnologyInstallationDetails>of(
						installation1.copy()
						.setInstallationCost(1)
						.setOperationalCost(10)
						.setInstalledQuantity(100)
					));

		when(d2.getWeight()).thenReturn(1.5f);

		when(scope2.getAllNotes(ITechnologyInstallationDetails.class)).thenReturn(
				ImmutableList.<ITechnologyInstallationDetails>of(
						installation1.copy()
						.setInstallationCost(2)
						.setOperationalCost(20)
						.setInstalledQuantity(200)
					));

		logger.stateChanged(state, notification);
		logger.simulationStepped(startDate, secondDate, false);

		loggingService.assertCount("Expected a log entry encompassing both dwellings.", 1);

		final MeasureCostLogEntry entry = loggingService.getEntries().get(0);

		Assert.assertEquals("count", 2, entry.getCount(), 0.0);

		Assert.assertEquals("capex mean", ((0.5 * 1) + (1.5 * 2)) / 2, entry.getCapex().getMean(), 0.0);
		Assert.assertEquals("capex variance", 0.375, entry.getCapex().getVariance(), 0.0);
		Assert.assertEquals("capex max", 2, entry.getCapex().getMax(), 0.0);
		Assert.assertEquals("capex min", 1, entry.getCapex().getMin(), 0.0);
		Assert.assertEquals("capex sum", (0.5 * 1) + (1.5 * 2), entry.getCapex().getSum(), 0.0);

		Assert.assertEquals("opex mean", ((0.5 * 10) + (1.5 * 20)) / 2, entry.getOpex().getMean(), 0.0);
		Assert.assertEquals("opex variance", 37.5, entry.getOpex().getVariance(), 0.0);
		Assert.assertEquals("opex max", 20, entry.getOpex().getMax(), 0.0);
		Assert.assertEquals("opex min", 10, entry.getOpex().getMin(), 0.0);
		Assert.assertEquals("opex sum", (0.5 * 10) + (1.5 * 20), entry.getOpex().getSum(), 0.0);

		Assert.assertEquals("size mean", ((0.5 * 100) + (1.5 * 200)) / 2, entry.getSizeInstalled().getMean(), 0.0);
		Assert.assertEquals("size variance", 3750, entry.getSizeInstalled().getVariance(), 0.0);
		Assert.assertEquals("size max", 200, entry.getSizeInstalled().getMax(), 0.0);
		Assert.assertEquals("size min", 100, entry.getSizeInstalled().getMin(), 0.0);
		Assert.assertEquals("size sum", (0.5 * 100) + (1.5 * 200), entry.getSizeInstalled().getSum(), 0.0);
	}

	static class TestLogEntryHandler implements ILogEntryHandler {
		private final List<MeasureCostLogEntry> entries = new ArrayList<>();
		private ReportHeaderLogEntry header;

		@Override
		public void close() throws IOException {
			// Noop
		}

		public void clear() {
			entries.clear();
		}

		@Override
		public void acceptLogEntry(final ISimulationLogEntry entry) {
			if (entry instanceof ReportHeaderLogEntry) {
				if (header == null) {
					header = (ReportHeaderLogEntry) entry;

				} else {
					Assert.fail("Got two header log entries.");
				}

			} else if (entry instanceof MeasureCostLogEntry) {
				if (header == null) {
					Assert.fail("Got data, but haven't got a header log entry yet.");
				}
				entries.add((MeasureCostLogEntry) entry);
			} else {
				Assert.fail("Wrong log entry type " + entry.getClass());
			}
		}

		public List<MeasureCostLogEntry> getEntries() {
			return entries;
		}

		public ReportHeaderLogEntry getHeader() {
			return header;
		}

		public void assertZero() {
			assertCount("Expected no log entries", 0);
		}

		public void assertCount(final String message, final int expected) {
			Assert.assertEquals(message, expected, entries.size());
		}
	}

	/**
	 * A technology installation details which has:
	 * + Settable properties
	 * + Copy
	 * + Defaults
	 */
	static class TestInstallationDetails implements ITechnologyInstallationDetails {

		private IComponentsAction installationSource;
		private TechnologyType installedTechnology = TechnologyType.storageHeater();
		private Units units = Units.KILOWATTS;
		private double installedQuantity = 0;
		private double operationalCost = 0;
		private double installationCost = 0;

		public TestInstallationDetails(final IComponentsAction source) {
			this.installationSource = source;
		}

		public TestInstallationDetails copy() {
			final TestInstallationDetails copy = new TestInstallationDetails(installationSource);
			copy.setInstalledTechnology(installedTechnology);
			copy.setUnits(units);
			copy.setInstalledQuantity(installedQuantity);
			copy.setOperationalCost(operationalCost);
			copy.setInstallationCost(installationCost);
			return copy;
		}

		@Override
		public IComponentsAction getInstallationSource() {
			return installationSource;
		}

		public TestInstallationDetails setInstallationSource(final IComponentsAction source) {
			this.installationSource = source;
			return this;
		}

		@Override
		public TechnologyType getInstalledTechnology() {
			return installedTechnology;
		}

		public TestInstallationDetails setInstalledTechnology(final TechnologyType installedTechnology) {
			this.installedTechnology = installedTechnology;
			return this;
		}

		@Override
		public Units getUnits() {
			return units;
		}

		public TestInstallationDetails setUnits(final Units units) {
			this.units = units;
			return this;
		}

		@Override
		public double getInstalledQuantity() {
			return installedQuantity;
		}

		public TestInstallationDetails setInstalledQuantity(final double installedQuantity) {
			this.installedQuantity = installedQuantity;
			return this;
		}

		@Override
		public double getOperationalCost() {
			return operationalCost;
		}

		public TestInstallationDetails setOperationalCost(final double opex) {
			this.operationalCost = opex;
			return this;
		}

		@Override
		public double getInstallationCost() {
			return installationCost;
		}

		public TestInstallationDetails setInstallationCost(final double capex) {
			this.installationCost = capex;
			return this;
		}
	}
}
