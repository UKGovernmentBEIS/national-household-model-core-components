package uk.org.cse.nhm.simulation.reporting.standard;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.MeasureCostLogEntry;
import uk.org.cse.nhm.logging.logentry.MeasureCostLogEntry.Stats;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry.Type;
import uk.org.cse.nhm.simulation.reporting.two.Accumulator;
import uk.org.cse.nhm.simulation.reporting.two.Accumulator.IAccumulation;
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

public class MeasureCostLogger implements ISimulationStepListener, IStateListener {

	private static final Set<StateChangeSourceType> irrelevantSourceTypes = EnumSet.of(
			StateChangeSourceType.INTERNAL,
			StateChangeSourceType.OBLIGATION,
			StateChangeSourceType.TIME
			);

	private final ILogEntryHandler loggingService;
	Map<Key, Costs> costsBySourceByTechByInstallationDate = new HashMap<>();
	private boolean hasChanges = false;

	@Inject
	public MeasureCostLogger(
			final ILogEntryHandler loggingService,
			final ISimulator simulator,
			final ICanonicalState state
			) {
				this.loggingService = loggingService;
				simulator.addSimulationStepListener(this);
				state.addStateListener(this);

				loggingService.acceptLogEntry(new ReportHeaderLogEntry(Type.MeasureCosts));
	}

	@Override
	public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
		if (irrelevantSourceTypes.contains(notification.getRootScope().getTag().getSourceType())) {
			return;
		}

		final DateTime installationDate = notification.getDate();

		for (final IDwelling modifiedDwelling : notification.getAllChangedDwellings()) {
			final Optional<IComponentsScope> componentsScope = notification.getRootScope().getComponentsScope(modifiedDwelling);

			if (componentsScope.isPresent()) {
				for (final ITechnologyInstallationDetails installDetails : componentsScope.get().getAllNotes(ITechnologyInstallationDetails.class)) {
					final Key k = new Key(installationDate, installDetails.getInstalledTechnology(), installDetails.getInstallationSource());

					if (!costsBySourceByTechByInstallationDate.containsKey(k)) {
						costsBySourceByTechByInstallationDate.put(k, new Costs(installDetails.getUnits()));
					}

					final Costs costs = costsBySourceByTechByInstallationDate.get(k);

					if (costs.getUnits() != installDetails.getUnits()) {
						throw new IncompatibleUnitsException(
								"Incompatible Units provided for technology " + installDetails.getInstalledTechnology().toString() +
								" from action " + installDetails.getInstallationSource().getIdentifier().getPath());
					}

					costs.update(
							installDetails.getOperationalCost(),
							installDetails.getInstallationCost(),
							installDetails.getInstalledQuantity(),
							modifiedDwelling.getWeight()
						);

					hasChanges = true;
				}
			}
		}
	}

	@Override
	public void simulationStepped(final DateTime dateOfStep, final DateTime nextDate, final boolean isFinalStep) throws NHMException {
		if (hasChanges) {
			for (final Entry<Key, Costs> entry : costsBySourceByTechByInstallationDate.entrySet()) {
				final Key key = entry.getKey();
				final Costs costs = entry.getValue();

				loggingService.acceptLogEntry(
					new MeasureCostLogEntry(
						key.getTechnology().toString(),
						key.getSourceAction().getIdentifier().getPath(),
						costs.getOpex().freeze(),
						costs.getCapex().freeze(),
						costs.getSize().freeze(),
						costs.getCount(),
						key.getInstallationDate(),
						costs.getUnits().toString())
				);
			}

			// Set everything up again.
			costsBySourceByTechByInstallationDate = new HashMap<>();
			hasChanges = false;
		}
	}

	static class Costs {
		private final StatsCalc opex = new StatsCalc();
		private final StatsCalc capex = new StatsCalc();
		private final StatsCalc size = new StatsCalc();
		private final Units units;
		private final IAccumulation count = new Accumulator.Count(false).start();

		public Costs(final Units units) {
			this.units = units;
		}

		public void update(final double operationalCost, final double installationCost, final double installedQuantity, final float weight) {
			opex.update(weight, operationalCost);
			capex.update(weight, installationCost);
			size.update(weight, installedQuantity);
			count.put(weight, true);
		}

		public StatsCalc getOpex() {
			return opex;
		}

		public StatsCalc getCapex() {
			return capex;
		}

		public StatsCalc getSize() {
			return size;
		}

		public Units getUnits() {
			return units;
		}

		public double getCount() {
			return count.get();
		}
	}

	static class StatsCalc {
			IAccumulation mean = new Accumulator.Mean(false).start();
			IAccumulation var = new Accumulator.Variance(false).start();
			IAccumulation max = new Accumulator.Max(false).start();
			IAccumulation min = new Accumulator.Min(false).start();
		    IAccumulation sum = new Accumulator.Sum(false).start();

		    List<IAccumulation> all = ImmutableList.of(
		    		mean,
		    		var,
		    		max,
		    		min,
		    		sum
	    		);

		public void update(final double weight, final double value) {
			for (final IAccumulation acc : all) {
				acc.put(weight, value);
			}
		}

		public Stats freeze() {
			return new MeasureCostLogEntry.Stats(
					mean.get(),
					var.get(),
					max.get(),
					min.get(),
					sum.get()
					);
		}
	}

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

	public static class IncompatibleUnitsException extends RuntimeException{
		private static final long serialVersionUID = 1152241204917974047L;
		public IncompatibleUnitsException(final String msg) {
			super(msg);
		}
	};
}
