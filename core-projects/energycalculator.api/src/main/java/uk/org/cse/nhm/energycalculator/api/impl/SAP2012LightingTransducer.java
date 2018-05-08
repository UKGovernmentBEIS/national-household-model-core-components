package uk.org.cse.nhm.energycalculator.api.impl;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;

/**
 * <pre>
 * SAP2012LightingTransducer, clamps multiplier to SAP compliant values of either
 * {@link SAP2012LightingTransducer#CFL_EFFICIENCY} or {@link SAP2012LightingTransducer#INCANDESCENT_EFFICIENCY}
 *
 * If multiplier is less than or equal to CFL_EFFICIENCY then clamp to this, otherwise clamp to INCANDESCENT_EFFICIENCY.
 * <pre>
 * @author trickyBytes
 */
public class SAP2012LightingTransducer extends SimpleLightingTransducer {

    public static final double INCANDESCENT_EFFICIENCY = 6.8139; // watts
    public static final double CFL_EFFICIENCY = INCANDESCENT_EFFICIENCY / 2.0;

    /**
     * @param name
     * @param proportion
     * @param multiplier
     * @param splitRate
     */
    public SAP2012LightingTransducer(String name, double proportion, double multiplier, double[] splitRate) {
        super(name, proportion, multiplier, splitRate);
    }

    /**
     * @param house
     * @param parameters
     * @param losses
     * @param state
     * @see
     * uk.org.cse.nhm.energycalculator.api.impl.SimpleLightingTransducer#generate(uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase,
     * uk.org.cse.nhm.energycalculator.api.IInternalParameters,
     * uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses,
     * uk.org.cse.nhm.energycalculator.api.IEnergyState)
     */
    @Override
    public void generate(IEnergyCalculatorHouseCase house, IInternalParameters parameters, ISpecificHeatLosses losses,
            IEnergyState state) {
        double multiplier = getMultiplier();
        double clamped = 0;

        if (multiplier < INCANDESCENT_EFFICIENCY) {
            clamped = CFL_EFFICIENCY;
        } else {
            clamped = INCANDESCENT_EFFICIENCY;
        }

        setMultiplier(clamped);
        super.generate(house, parameters, losses, state);
    }
}
