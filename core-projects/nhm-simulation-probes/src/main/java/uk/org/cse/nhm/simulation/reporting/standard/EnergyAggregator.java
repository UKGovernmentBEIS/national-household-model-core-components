package uk.org.cse.nhm.simulation.reporting.standard;

import java.util.Collection;
import java.util.EnumMap;
import java.util.Map;

import javax.inject.Inject;
import javax.management.timer.Timer;

import org.joda.time.DateTime;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.logging.logentry.NationalEnergyLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry;
import uk.org.cse.nhm.logging.logentry.ReportHeaderLogEntry.Type;
import uk.org.cse.nhm.simulator.main.ISimulationStepListener;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.dimensions.energy.IPowerTable;

/**
 * A report aggregator which sums up national energy usage for the purpose of
 * making a graph at the end.
 *
 * It is inefficient, but safe - rather than listening to the state for state
 * changes, it just computes the power usage at the end of each step and
 * integrates over the step.
 *
 * @author hinton
 *
 */
public class EnergyAggregator implements ISimulationStepListener {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(EnergyAggregator.class);
    /**
     * The current simulation state
     */
    private final IState state;
    private final ILogEntryHandler loggingService;
    private final IDimension<IPowerTable> energyDimension;

    @Inject
    public EnergyAggregator(
            final IDimension<IPowerTable> energyDimension,
            final ISimulator simulator, final IState state, final ILogEntryHandler loggingService) {
        this.energyDimension = energyDimension;
        this.state = state;
        this.loggingService = loggingService;

        simulator.addSimulationStepListener(this);

        loggingService.acceptLogEntry(new ReportHeaderLogEntry(Type.NationalPower));
    }

    @Override
    public void simulationStepped(final DateTime dateOfStep, final DateTime nextDate, final boolean isFinalStep) throws NHMException {
        // skip out zero length bits?
        if (dateOfStep.equals(nextDate)) {
            return;
        }

        /**
         * kWh/year by service type ordinal, then converted to kWh in interval
         */
        final double[] byService = new double[ServiceType.values().length];
        /**
         * kWh/year by fuel type ordinal, then converted to kWh in interval
         */
        final double[] byFuel = new double[FuelType.values().length];

        accumulatePower(state, state.getDwellings(), byService, byFuel);

        // determine duration in years of step
        convertToEnergy(dateOfStep, nextDate, byService);
        convertToEnergy(dateOfStep, nextDate, byFuel);

        // make into maps
        final Map<FuelType, Double> byFuelMap = new EnumMap<FuelType, Double>(FuelType.class);
        final Map<ServiceType, Double> byServiceMap = new EnumMap<ServiceType, Double>(ServiceType.class);

        for (final ServiceType st : ServiceType.values()) {
            byServiceMap.put(st, byService[st.ordinal()]);
        }

        for (final FuelType ft : FuelType.values()) {
            byFuelMap.put(ft, byFuel[ft.ordinal()]);
        }

        // now we have kWh used during step - we can log that.
        final NationalEnergyLogEntry entry = new NationalEnergyLogEntry(dateOfStep, nextDate,
                byServiceMap, byFuelMap);

        addEntryToLog(entry);
    }

    private void addEntryToLog(final NationalEnergyLogEntry entry) throws NHMException {
        log.debug("Adding energy log from {} to {}", entry.getDate(), entry.getUntil());
        loggingService.acceptLogEntry(entry);
    }

    /**
     * Multiply through the array of doubles by the scale factor;
     *
     * @param doubles
     * @param scaleFactor
     */
    private void scale(final double[] doubles, final double scaleFactor) {
        for (int i = 0; i < doubles.length; i++) {
            doubles[i] *= scaleFactor;
        }
    }

    /**
     * Takes the given array of power values, in energy/year, and converts to
     * energy by multiplying out the number of years between the two datetimes
     * given.
     *
     * @param startOfInterval
     * @param endOfInterval
     * @param power
     */
    private void convertToEnergy(final DateTime startOfInterval, final DateTime endOfInterval, final double[] power) {
        final long deltaMillis = Math.abs(endOfInterval.getMillis() - startOfInterval.getMillis());
        final double deltaYears = deltaMillis / (365d * Timer.ONE_DAY);

        // multiply through by number of years
        scale(power, deltaYears);
    }

    /**
     * Takes a collection of dwellings and accumulates their power by service
     * type and fuel type into the two given arrays, in kWh/year
     *
     * @param dwellings
     * @param byService
     * @param byFuel
     */
    protected void accumulatePower(final IState state, final Collection<? extends IDwelling> dwellings, final double[] byService, final double[] byFuel) {
        // accumulate kWh/year for types
        for (final IDwelling dwelling : dwellings) {
            final IPowerTable energy = state.get(energyDimension, dwelling);
            for (final ServiceType st : ServiceType.values()) {
                for (final FuelType ft : FuelType.values()) {
                    final double use = energy.getFuelUseByEnergyService(st, ft) * dwelling.getWeight();
                    byService[st.ordinal()] += use;
                    byFuel[ft.ordinal()] += use;
                }
            }
        }
    }
}
