package uk.org.cse.nhm.energycalculator.api;


/**
 * This is a energy calculator parameters usable within a run - the main purpose of this is
 * to provide the calculator's internal constants table to transducers not
 * contained in the calculator.
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
	 *         for bad heating systems.
	 */
	public double getTemperatureAdjustment();
	
	public ISeasonalParameters getClimate();
}
