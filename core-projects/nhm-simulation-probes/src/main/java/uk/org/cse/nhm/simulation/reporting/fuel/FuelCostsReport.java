package uk.org.cse.nhm.simulation.reporting.fuel;

import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;

import org.joda.time.DateTime;
import org.joda.time.Duration;
import org.joda.time.Seconds;

import com.google.common.base.Optional;

import uk.org.cse.commons.scopes.IScope;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.FuelCostsLogEntry;
import uk.org.cse.nhm.simulator.obligations.impl.TariffFuelAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeNotification;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.IStateListener;
import uk.org.cse.nhm.simulator.state.dimensions.energy.AnnualFuelObligation;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;
import uk.org.cse.nhm.simulator.transactions.ITransaction;

/**
 * Outputs fuel bills as rates per annum. Applies to state changes which are
 * caused by an {@link AnnualFuelObligation}. Only logs for a particular
 * dwelling if one of the fuel costs a different amount per annum than it did
 * before.
 * 
 * @since 3.8.0
 */
public class FuelCostsReport implements IStateListener {

	private static final double secondsInStandardYear = 365.25 * 86400; // standard Julian year

	private final ILogEntryHandler loggingService;

	private final IDimension<DwellingTransactionHistory> transactionsDimension;

	private final Map<Integer, Reading> readings = new LinkedHashMap<>();

	@Inject
	public FuelCostsReport(final IDimension<DwellingTransactionHistory> transactionsDimension, final ILogEntryHandler loggingService, final ICanonicalState state) {
		this.transactionsDimension = transactionsDimension;
		this.loggingService = loggingService;
		state.addStateListener(this);
	}

	@Override
	public void stateChanged(final ICanonicalState state, final IStateChangeNotification notification) {
		final DateTime changeDate = notification.getDate();

		final IStateScope changeScope = notification.getRootScope();

		for (final IDwelling d : notification.getCreatedDwellings()) {
			readings.put(d.getID(), new Reading(changeDate));
		}
		
		for (final IDwelling d : notification.getDestroyedDwellings()) {
			readings.remove(d.getID());
		}
		
		if (changeScope.getTag() instanceof AnnualFuelObligation) {
			for (final IDwelling d : notification.getChangedDwellings(transactionsDimension)) {
				final Optional<IComponentsScope> maybeComponentsScope = changeScope.getComponentsScope(d);

				if (maybeComponentsScope.isPresent()) {
					dwellingChanged(changeDate, d.getID(), maybeComponentsScope.get());
				} else {
					throw new IllegalStateException(String.format("State change notification claimed that transaction history was modified for dwelling %s, but no components scope could be found for that dwelling.", d));
				}
			}
		} else {
			/*
			 * We assume fuel bills are only evaluated inside
			 * AnnualFuelObligation.
			 */
		}
	}

	private void dwellingChanged(final DateTime changeDate, final int dwellingId, final IComponentsScope componentsScope) {
		if (!readings.containsKey(dwellingId)) {
			throw new DwellingNotKnownException(dwellingId);
		}

		final Optional<FuelCostsLogEntry> logEntry = readings.get(dwellingId).update(componentsScope, changeDate);
		if (logEntry.isPresent()) {
			log(logEntry.get());
		}
	}

	private void log(final FuelCostsLogEntry fuelCostsLogEntry) {
		loggingService.acceptLogEntry(fuelCostsLogEntry);
	}

	private static class Reading {
		private DateTime lastUpdate;
		private final Map<FuelType, Double> rates;

		Reading(final DateTime initialDate) {
			this.lastUpdate = initialDate;
			rates = new EnumMap<FuelType, Double>(FuelType.class);
			for (final FuelType fuel : FuelType.values()) {
				rates.put(fuel, 0.0);
			}
		}

		Optional<FuelCostsLogEntry> update(final IComponentsScope componentsScope, final DateTime now) {
			if (now.isBefore(lastUpdate)) {
				throw new IllegalArgumentException("Cannot update fuel costs with values from the past. Last update: " + lastUpdate + ", new update: " + now);
			}

			boolean changed = false;
			final Map<FuelType, List<ITransaction>> transactionsByFuel = findTransactionByFuelType(componentsScope);
			for (final FuelType fuel : FuelType.values()) {
				double cost = 0.0;
				if (transactionsByFuel.containsKey(fuel)) {
					for (final ITransaction transaction : transactionsByFuel.get(fuel)) {
						cost += transaction.getAmount();
					}
				}
				final double rate = asAnnualRate(cost, lastUpdate, now);

				if (changed(rate, rates.get(fuel))) {
					rates.put(fuel, rate);
					changed = true;
				}
			}

			lastUpdate = now;

			if (changed) {
				return Optional.of(new FuelCostsLogEntry(componentsScope.getDwellingID(), componentsScope.getDwelling().getWeight(), now, rates));
			} else {
				return Optional.absent();
			}
		}

		private static final double ERROR_DELTA = 0.0001;

		/*
		 * Ignore small floating point inaccuracies.
		 */
		private boolean changed(final Double a, final Double b) {
			return Math.abs(a - b) > ERROR_DELTA;
		}

		private double asAnnualRate(final double cost, final DateTime lastUpdate, final DateTime now) {
			if (now.isEqual(lastUpdate)) {
				if (cost != 0.0) {
					throw new IllegalArgumentException("No time has passed since the last meter reading, but energy has been used.");
				}
				return 0.0;
			} else {
				final Seconds secondsSinceLastUpdate = new Duration(lastUpdate, now).toStandardSeconds();
				return cost * secondsInStandardYear / secondsSinceLastUpdate.getSeconds();
			}
		}

		private Map<FuelType, List<ITransaction>> findTransactionByFuelType(final IScope<? extends IStateChangeSource> scope) {
			final Map<FuelType, List<ITransaction>> transactionsByFuel = new EnumMap<>(FuelType.class);
			for (final IScope<? extends IStateChangeSource> subScope : scope.getSubScopes()) {
				if (subScope.getTag() instanceof TariffFuelAction) {
					transactionsByFuel.put(((TariffFuelAction) subScope.getTag()).getFuelType(), subScope.getAllNotes(ITransaction.class));
				} else {
					transactionsByFuel.putAll(findTransactionByFuelType(subScope));
				}
			}
			return transactionsByFuel;
		}
	}

	@SuppressWarnings("serial")
	static class DwellingNotKnownException extends IllegalStateException {
		public DwellingNotKnownException(final int dwellingId) {
			super(String.format("Received a fuel bill notification for dwelling %d, but never saw it being constructed.", dwellingId));
		}
	}
}
