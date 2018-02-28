package uk.org.cse.nhm.energycalculator.api;

/**
 * The general interface for a energy calculator
 *
 * @author hinton
 *
 */
public interface IEnergyCalculator {
	/**
	 * Evaluate the condition of the given house case with the supplied parameters
	 *
	 * @param houseCase
	 * @param externalParameters
	 * @return an {@link IEnergyCalculationResultWithSteps} for the house
	 */
	public abstract IEnergyCalculationResultWithSteps evaluate(IEnergyCalculatorHouseCase houseCase, IEnergyCalculatorParameters externalParameters, final ISeasonalParameters[] climate);
}