package uk.org.cse.nhm.simulation.reporting.standard;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.inject.Inject;

import org.apache.commons.math3.stat.descriptive.SummaryStatistics;
import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Optional;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.MeasureCostLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry.Type;
import uk.org.cse.nhm.simulator.main.ISimulationStepListener;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.measure.ITechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;

/**
 * Logs information about measure installations such as source, OPEX, CAPEX,
 * size installed and installation date for reporting such as the measure
 * cost report.
 * 
 * @see uk.org.cse.nhm.reporting.builders.CSVMeasureCostsReportBuilder
 * @author tomw
 */
public class MeasureCostLogger implements ISimulationStepListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(MeasureCostLogger.class);
	private final ILogEntryHandler loggingService;
	Map<Key, Costs> costsBySourceByTechByInstallationDate = new HashMap<>();
	AtomicBoolean logOutOfDate = new AtomicBoolean(false);

	/**
	 * Thrown if attempt is made to combine two different Units in some way
	 * 
	 * @author tomw
	 *
	 */
	class IncompatibleUnitsException extends RuntimeException{
		private static final long serialVersionUID = 1152241204917974047L;
		public IncompatibleUnitsException(final String msg) {
			super(msg);
		}
	};
	
	@AutoProperty
	static class Key {
		private final DateTime installationDate;
		private final TechnologyType technology;
		private final IComponentsAction sourceAction;

		Key(final DateTime installationDate, final TechnologyType technology, final IComponentsAction sourceAction) {
			this.installationDate = installationDate;
			this.technology = technology;
			this.sourceAction = sourceAction;
		}
		
		@Override
		public boolean equals(final Object obj) {
			return Pojomatic.equals(this, obj);
		}
		
		@Override
		public int hashCode() {
			return Pojomatic.hashCode(this);
		}

		public DateTime getInstallationDate() {
			return installationDate;
		}

		public TechnologyType getTechnology() {
			return technology;
		}

		public IComponentsAction getSourceAction() {
			return sourceAction;
		}
	}
	
	class Costs {
		private final SummaryStatistics opex;
		private final SummaryStatistics capex;
		private final SummaryStatistics sizeInstalled;
		private final Units units;
		private final float quantum;

		/**
		 * Constructor to create a cost for a single installation instance
		 * 
		 * @param operationalCost
		 * @param installationCost
		 * @param installedQuantity
		 * @param units
		 * @param quantum 
		 */
		public Costs(final double operationalCost, final double installationCost,
				final double installedQuantity, final Units units, final float quantum) {
			
			this.quantum = quantum;
			this.opex = createSummary(operationalCost); 
			this.capex = createSummary(installationCost);
			this.sizeInstalled = createSummary(installedQuantity);
			this.units = units;
		}
		
		private SummaryStatistics createSummary(final double value) {
			final SummaryStatistics result = new SummaryStatistics();
			update(result, value);
			return result;
		}
		
		private void update(final SummaryStatistics existing, final double value) {
			for (int i = 0; i < quantum; i++) {
				existing.addValue(value);
			}
		}	

		public SummaryStatistics getOPEX() {
			return opex;
		}

		public Units getUnits() {
			return units;
		}

		public void update(final double operationalCost, final double installationCost,
				final double installedQuantity, final Units units) throws IncompatibleUnitsException {
			if(!this.units.equals(units)) {
				throw new IncompatibleUnitsException("Incompatible Units provided: [this.units: "+this.units+", units: "+units+"]");
			}
			
			update(this.opex, operationalCost);
			update(this.capex, installationCost);
			update(this.sizeInstalled, installedQuantity);
		}

		@Override
		public String toString() {
			return Pojomatic.toString(this);
		}

		public SummaryStatistics getCAPEX() {
			return capex;
		}

		public SummaryStatistics getSizeInstalled() {
			return sizeInstalled;
		}
	};

	/**
	 * Constructor for the logger 
	 * 
	 * @param loggingService
	 * @param simulator
	 * @param state
	 * @param executionID
	 */
	@Inject
	protected MeasureCostLogger(
			final ILogEntryHandler loggingService,
			final ISimulator simulator,
			final ICanonicalState state) {
		this.loggingService = loggingService;
		simulator.addSimulationStepListener(this);

		state.addStateListener(new IStateListener() {
			final Set<StateChangeSourceType> irrelevantSourceTypes = 
					EnumSet.of(StateChangeSourceType.INTERNAL, StateChangeSourceType.OBLIGATION, StateChangeSourceType.TIME);
			@Override
			public void stateChanged(final ICanonicalState state,
					final IStateChangeNotification notification) {
				
				if (irrelevantSourceTypes.contains(notification.getRootScope().getTag().getSourceType())) {
					return;
				}
				
				int installationCounter = 0;
				final int sizeBefore = costsBySourceByTechByInstallationDate.size();
				final DateTime installationDate = notification.getDate();
				LOGGER.debug("MeasureCostLogger is processing state changes for new installation date: ["+installationDate+"]...");

				// Get the technologies installed...
				for (final IDwelling modifiedDwelling : notification
						.getAllChangedDwellings()) {
					final Optional<IComponentsScope> componentsScope = notification.getRootScope().getComponentsScope(modifiedDwelling);
					
					if (componentsScope.isPresent()) {
						for (final ITechnologyInstallationDetails installDetails : componentsScope.get().getAllNotes(ITechnologyInstallationDetails.class)) {
							
							final Key k = new Key(installationDate, installDetails.getInstalledTechnology(), installDetails.getInstallationSource());
							if (costsBySourceByTechByInstallationDate.containsKey(k)) {
								costsBySourceByTechByInstallationDate.get(k).update(
									installDetails.getOperationalCost(),
									installDetails.getInstallationCost(),
									installDetails.getInstalledQuantity(),
									installDetails.getUnits()
										);
								
							} else {
								costsBySourceByTechByInstallationDate.put(k, new Costs(
										installDetails.getOperationalCost(), 
										installDetails.getInstallationCost(), 
										installDetails.getInstalledQuantity(), 
										installDetails.getUnits(),
										modifiedDwelling.getWeight()));
							}
							logOutOfDate.set(true);
							installationCounter++;
						}
					}
					
					
				}
				
				if(installationCounter>0)
					LOGGER.debug("MeasureCostLogger added "+installationCounter+" installations.");

				final int sizeAfter = costsBySourceByTechByInstallationDate.size();
				final int sizeDelta = sizeAfter-sizeBefore;
				LOGGER.debug("MeasureCostLogger size increased by "+sizeDelta+" to "+sizeAfter+" records.");
			}
		});

		LOGGER.debug("MeasureCostLogger is alive and listening.");
		
		loggingService.acceptLogEntry(new ReportHeaderLogEntry(Type.MeasureCosts));
	}

	/**
	 * Checks for new records and creates log entries for them, before resetting the internal record collection.
	 * 
	 * @param dateOfStep
	 * @param nextDate
	 * @param isFinalStep
	 * @throws NHMException
	 * @see uk.org.cse.nhm.simulator.main.ISimulationStepListener#simulationStepped(org.joda.time.DateTime,
	 *      org.joda.time.DateTime, boolean)
	 */
	@Override
	public void simulationStepped(final DateTime dateOfStep, final DateTime nextDate,
			final boolean isFinalStep) throws NHMException {
		LOGGER.debug("MeasureCostLogger is being stepped at [" + dateOfStep
				+ "]..");
		LOGGER.debug("MeasureCostLogger has "
				+ costsBySourceByTechByInstallationDate.size()
				+ " installation dates to log mods for...");
		if (logOutOfDate.get()
				&& !costsBySourceByTechByInstallationDate.isEmpty()) {
			createLogEntries(costsBySourceByTechByInstallationDate);
			costsBySourceByTechByInstallationDate.clear();
		}
		logOutOfDate.set(false);
	}

	/**
	 * Iterates through the aggregator tree creating log entries for each leaf record and 
	 * passing them to the logging service to be written asynchronously.
	 * 
	 * @param costsBySourceByTechByInstallationDate
	 * @throws NHMException
	 */
	private void createLogEntries(
			final Map<Key, Costs> costsBySourceByTechByInstallationDate)
			throws NHMException {
		LOGGER.debug("MeasureCostLogger is creating new log entries...");
		for (final Entry<Key, Costs> costsBySourceByTechWithDate : costsBySourceByTechByInstallationDate.entrySet()) {
			
			final Key key = costsBySourceByTechWithDate.getKey();
			
			final Costs costs = costsBySourceByTechWithDate.getValue();
			
			final MeasureCostLogEntry log = new MeasureCostLogEntry(
							key.getTechnology().toString(),
							key.getSourceAction().getIdentifier().getPath(),
							MeasureCostLogEntry.Stats.fromCommons(costs.getOPEX()),
							MeasureCostLogEntry.Stats.fromCommons(costs.getCAPEX()),
							MeasureCostLogEntry.Stats.fromCommons(costs.getSizeInstalled()),
							costs.getCAPEX().getN(),
							key.getInstallationDate(),
							costs.getUnits().toString());

			// Queue log entry for writing to log asynchronously
			loggingService.acceptLogEntry(log);
		}
	}
}
