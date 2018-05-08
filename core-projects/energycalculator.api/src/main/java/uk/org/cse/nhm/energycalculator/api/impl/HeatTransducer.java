package uk.org.cse.nhm.energycalculator.api.impl;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;

/**
 * A simple heat transducer, which suffices for use in almost all heating
 * systems.
 *
 * @author hinton
 *
 */
public class HeatTransducer implements IEnergyTransducer {

    private final EnergyType fuelType;
    private final double efficiency;

    private final double upperBound;
    private final boolean boundIsProportion;

    private final boolean isBounded;

    private final int priority;
    private final ServiceType service;

    public HeatTransducer(final EnergyType fuelType, final double efficiency, final double upperBound, final boolean boundIsProportion, final int priority, ServiceType service) {
        this.fuelType = fuelType;
        this.efficiency = efficiency;
        this.upperBound = upperBound;
        this.boundIsProportion = boundIsProportion;
        this.service = service;

        if (boundIsProportion) {
            isBounded = upperBound < 1.0;
        } else {
            isBounded = upperBound < Double.POSITIVE_INFINITY;
        }

        this.priority = priority;
    }

    public HeatTransducer(final EnergyType fuelType, final double efficiency, final ServiceType service) {
        this(fuelType, efficiency, 1.0, true, 0, service);
    }

    @Override
    public ServiceType getServiceType() {
        return service;
    }

    protected void consumed(final double quantity) {

    }

    private void generate(final IEnergyState state) {
        final double toGenerate;
        final EnergyType heatType = EnergyType.DemandsHEAT;

        if (isBounded) {
            if (boundIsProportion) {
                toGenerate = state.getBoundedTotalHeatDemand(upperBound);
            } else {
                toGenerate = Math.min(state.getUnsatisfiedDemand(heatType), upperBound);
            }
        } else {
            toGenerate = state.getUnsatisfiedDemand(heatType);
        }

        final double in = toGenerate / efficiency;

        state.increaseSupply(heatType, toGenerate);
        state.increaseDemand(fuelType, in);
        this.consumed(in);

    }

    @Override
    public void generate(
            final IEnergyCalculatorHouseCase house,
            final IInternalParameters parameters,
            final ISpecificHeatLosses losses,
            final IEnergyState state) {
        generate(state);
    }

    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public String toString() {
        return "Heating";
    }

    @Override
    public TransducerPhaseType getPhase() {
        return TransducerPhaseType.Heat;
    }
}
