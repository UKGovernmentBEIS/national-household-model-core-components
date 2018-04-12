package uk.org.cse.nhm.simulator.state.dimensions.behaviour;

import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;
import uk.org.cse.nhm.hom.ICopyable;

/**
 * Encapsulates a house's heating behaviour.
 *
 * @author hinton
 *
 */
public interface IHeatingBehaviour extends ICopyable<IHeatingBehaviour> {
	/**
	 * @return the heating schedule used by the house when heating is on.
	 */
	public IHeatingSchedule getHeatingSchedule();
	/**
	 * @return The demand temperature in the living area
	 */
	public double getLivingAreaDemandTemperature();
	/**
	 * This value does not mean the same thing as {@link #getLivingAreaDemandTemperature()} - {@link #getSecondAreaDemandTemperature()};
	 * one of the quirks of SAP is that you can either define the second area demand temperature OR you can define a difference and then
	 * it uses some process to work out the actual second area demand temperature to be used; this value is more like an aspiration, whereas
	 * {@link #getSecondAreaDemandTemperature()} actually forces the value.
	 *
	 * @return the temperature difference between living area and rest of dwelling, if present
	 */
	public Optional<Double> getTemperatureDifference();
	/**
	 * @return the rest-of-dwelling demand temperature, if set. See also {@link #getTemperatureDifference()}.
	 */
	public Optional<Double> getSecondAreaDemandTemperature();

	/**
	 * Construct a new heating behaviour like this one but with the given temperature
	 * @param d
	 * @return
	 */
	public IHeatingBehaviour withLivingAreaDemandTemperature(final double d);
	/**
	 * Set the heating schedule to be used when the heating is on.
	 * @param heatingSchedule
	 */
	void setHeatingSchedule(IHeatingSchedule heatingSchedule);
	/**
	 * Set the living area demand temperature
	 * @param livingAreaDemandTemperature
	 */
	void setLivingAreaDemandTemperature(double livingAreaDemandTemperature);
	/**
	 * Set the demand temperature in the rest of the dwelling; this implicitly unsets the temperature difference,
	 * as they are mutually exclusive.
	 * @param temperature
	 */
	void setSecondAreaDemandTemperature(double temperature);
	/**
	 * Set the desired temperature difference between living area and rest of dwelling; this implicitly unsets
	 * the demand temperature in rest of dwelling, as they are mutually exclusive.
	 * @param temperatureDifference
	 */
	void setTemperatureDifference(double temperatureDifference);

	/**
	 * @return The type of energy calculation to perform.
	 */
	EnergyCalculatorType getEnergyCalculatorType();

	/**
	 * @param calculatorType Set the type of energy calculation to perform.
	 */
	void setEnergyCalculatorType(EnergyCalculatorType calculatorType);

	/**
	 * Construct a new heating behaviour like this one but with the given temperature.
	 */
	public IHeatingBehaviour withEnergyCalculatorType(EnergyCalculatorType energyCalculatorType);

	/**
	 * Has no effect in SAP 2012 mode.
	 *
	 * @param heatingMonths the months during which the heating will be on
	 */
	public void setHeatingMonths(Set<MonthType> heatingMonths);

	public Set<MonthType> getHeatingMonths();

	@Override
	public int hashCode();

	@Override
	public boolean equals(Object obj);
}
