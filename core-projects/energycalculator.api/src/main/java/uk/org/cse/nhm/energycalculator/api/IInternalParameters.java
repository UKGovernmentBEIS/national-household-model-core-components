package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;

/**
 * This is a energy calculator parameters usable within a run - the main purpose
 * of this is to provide the calculator's internal constants table to
 * transducers not contained in the calculator.
 *
 * @author hinton
 *
 */
public interface IInternalParameters extends IEnergyCalculatorParameters {

    /**
     * @return the {@link IConstants} table used in this run
     */
    public IConstants getConstants();

    /**
     * @return the amount the mean temperature should be adjusted, to account
     * for bad heating systems.
     */
    public double getTemperatureAdjustment();

    public ISeasonalParameters getClimate();

    /**
     * @return the zone 2 control parameter from SAP 2012 Table 9 or Table 4e.
     */
    public Zone2ControlParameter getZone2ControlParameter();
}
