package uk.org.cse.nhm.energycalculator.api.impl;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;

/**
 * A transducer which turns electricity into heat.
 *
 * @since 1.0.0
 * @author hinton
 *
 */
public abstract class ElectricHeatTransducer extends EnergyTransducer {

    private final double proportion;

    /**
     * Make an EHT which will provide the given proportion of total space heat,
     * at the given priority.
     *
     * @since 1.0.0
     * @param proportion
     * @param priority
     */
    public ElectricHeatTransducer(final double proportion, final int priority) {
        super(ServiceType.PRIMARY_SPACE_HEATING, priority);

        this.proportion = proportion;
    }

    @Override
    public void generate(final IEnergyCalculatorHouseCase house,
            final IInternalParameters parameters, final ISpecificHeatLosses losses,
            final IEnergyState state) {
        final double gen = state.getBoundedTotalHeatDemand(proportion);
        state.increaseSupply(EnergyType.DemandsHEAT, gen);
        state.increaseElectricityDemand(getHighRateFraction(house, parameters, losses, state), gen / getEfficiency());
    }

    protected double getEfficiency() {
        return 1;
    }

    protected abstract double getHighRateFraction(final IEnergyCalculatorHouseCase house,
            final IInternalParameters parameters, final ISpecificHeatLosses losses,
            final IEnergyState state);

    @Override
    public TransducerPhaseType getPhase() {
        return TransducerPhaseType.Heat;
    }
}
