package uk.org.cse.nhm.simulator.state.dimensions.behaviour;

import java.util.Set;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.WeeklyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;

@AutoProperty
public class HeatingBehaviour implements IHeatingBehaviour {
	public static final Set<MonthType> DEFAULT_HEATING_MONTHS = ImmutableSet.of(
			MonthType.January,
			MonthType.February,
			MonthType.March,
			MonthType.April,
			MonthType.May,
			MonthType.October,
			MonthType.November,
			MonthType.December
			);

	/*
	BEISDOC
	NAME: Default Heating Schedule
	DESCRIPTION: The default heating schedule under BREDEM 2012 mode
	TYPE: formula
	UNIT: N/A
	SAP_COMPLIANT: N/A - not used
	BREDEM: Table 27, but appears to have an error
	BREDEM_COMPLIANT: No - the first weekday period ends too soon
	NOTES: In BREDEM 2012 mode, the zone 1 and zone 2 heating schedule are always identical
	ID: bredem-heating-schedule
	CODSIEB
	*/
	public static final IHeatingBehaviour DEFAULT_BEHAVIOUR =
			new HeatingBehaviour(
				new WeeklyHeatingSchedule(
						DailyHeatingSchedule.fromHours(7, 8, 18, 23),
						DailyHeatingSchedule.fromHours(7, 23)
				),
				19d,
				3d,
				true,
				DEFAULT_HEATING_MONTHS,
				EnergyCalculatorType.BREDEM2012
			);

	private IHeatingSchedule heatingSchedule;
	private double livingAreaDemandTemperature;
	private double secondAreaDemandTemperatureOrDifference;
	private boolean secondTemperatureIsDifference;
	private Set<MonthType> heatingMonths;
	private EnergyCalculatorType calculatorType;

	public HeatingBehaviour(final IHeatingSchedule heatingSchedule,
			final double livingAreaDemandTemperature,
			final double secondTemperature,
			final boolean secondTemperatureIsDifference,
			final Set<MonthType> heatingMonths,
			final EnergyCalculatorType calculatorType) {
		this.heatingSchedule = heatingSchedule;
		this.livingAreaDemandTemperature = livingAreaDemandTemperature;
		this.secondAreaDemandTemperatureOrDifference = secondTemperature;
		this.secondTemperatureIsDifference = secondTemperatureIsDifference;
		this.heatingMonths = heatingMonths;
		this.calculatorType = calculatorType;
	}

	@Override
	public IHeatingSchedule getHeatingSchedule() {
		return heatingSchedule;
	}

	@Override
	public void setHeatingSchedule(final IHeatingSchedule heatingSchedule) {
		this.heatingSchedule = heatingSchedule;
	}

	@Override
	public double getLivingAreaDemandTemperature() {
		return livingAreaDemandTemperature;
	}

	@Override
	public void setLivingAreaDemandTemperature(final double livingAreaDemandTemperature) {
		this.livingAreaDemandTemperature = livingAreaDemandTemperature;
	}

	@Override
	public Optional<Double> getTemperatureDifference() {
		if (secondTemperatureIsDifference) {
			return Optional.of(secondAreaDemandTemperatureOrDifference);
		} else {
			return Optional.absent();
		}
	}

	@Override
	public void setTemperatureDifference(final double temperatureDifference) {
		secondTemperatureIsDifference = true;
		this.secondAreaDemandTemperatureOrDifference = temperatureDifference;
	}

	@Override
	public Optional<Double> getSecondAreaDemandTemperature() {
		if (secondTemperatureIsDifference) {
			return Optional.absent();
		} else {
			return Optional.of(secondAreaDemandTemperatureOrDifference);
		}
	}

	@Override
	public void setSecondAreaDemandTemperature(final double temperature) {
		secondTemperatureIsDifference = false;
		this.secondAreaDemandTemperatureOrDifference = temperature;
	}

	@Override
	public Set<MonthType> getHeatingMonths() {
		return heatingMonths;
	}

	@Override
	public void setHeatingMonths(final Set<MonthType> heatingMonths) {
		this.heatingMonths = heatingMonths;
	}

	@Override
	public IHeatingBehaviour withLivingAreaDemandTemperature(final double newLivingAreaTemp) {
		return new HeatingBehaviour(getHeatingSchedule(), newLivingAreaTemp,
				secondAreaDemandTemperatureOrDifference, secondTemperatureIsDifference, heatingMonths, calculatorType);
	}

	@Override
	public EnergyCalculatorType getEnergyCalculatorType() {
		return calculatorType;
	}

	@Override
	public void setEnergyCalculatorType(final EnergyCalculatorType calculatorType) {
		this.calculatorType = calculatorType;
	}

	@Override
	public IHeatingBehaviour withEnergyCalculatorType(final EnergyCalculatorType newCalculatorType) {
		return new HeatingBehaviour(
				heatingSchedule,
				livingAreaDemandTemperature,
				secondAreaDemandTemperatureOrDifference,
				secondTemperatureIsDifference,
				heatingMonths,
				newCalculatorType);
	}

	@Override
	public String toString() {
		return Pojomatic.toString(this);
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((calculatorType == null) ? 0 : calculatorType.hashCode());
		result = prime * result + ((heatingMonths == null) ? 0 : heatingMonths.hashCode());
		result = prime * result + ((heatingSchedule == null) ? 0 : heatingSchedule.hashCode());
		long temp;
		temp = Double.doubleToLongBits(livingAreaDemandTemperature);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		temp = Double.doubleToLongBits(secondAreaDemandTemperatureOrDifference);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + (secondTemperatureIsDifference ? 1231 : 1237);
		return result;
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final HeatingBehaviour other = (HeatingBehaviour) obj;
		if (calculatorType != other.calculatorType)
			return false;
		if (heatingMonths == null) {
			if (other.heatingMonths != null)
				return false;
		} else if (!heatingMonths.equals(other.heatingMonths))
			return false;
		if (heatingSchedule == null) {
			if (other.heatingSchedule != null)
				return false;
		} else if (!heatingSchedule.equals(other.heatingSchedule))
			return false;
		if (Double.doubleToLongBits(livingAreaDemandTemperature) != Double
				.doubleToLongBits(other.livingAreaDemandTemperature))
			return false;
		if (Double.doubleToLongBits(secondAreaDemandTemperatureOrDifference) != Double
				.doubleToLongBits(other.secondAreaDemandTemperatureOrDifference))
			return false;
		if (secondTemperatureIsDifference != other.secondTemperatureIsDifference)
			return false;
		return true;
	}

	@Override
	public IHeatingBehaviour copy() {
		return new HeatingBehaviour(heatingSchedule, livingAreaDemandTemperature, secondAreaDemandTemperatureOrDifference, secondTemperatureIsDifference, heatingMonths, calculatorType);
	}
}
