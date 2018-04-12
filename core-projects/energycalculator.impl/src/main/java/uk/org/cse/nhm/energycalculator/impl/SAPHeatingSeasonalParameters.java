package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.IWeather;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;
import uk.org.cse.nhm.energycalculator.mode.SAPTables;

public class SAPHeatingSeasonalParameters extends SeasonalParameters {
	public SAPHeatingSeasonalParameters(MonthType month, IWeather weather, double latitude) {
		super(month, weather, latitude);
	}

	@Override
	public IHeatingSchedule getZone1HeatingSchedule() {
		return SAPTables.HeatingSchedule.weekdaySevenAndEightWeekendZeroAndEight;
	}

	@Override
	public IHeatingSchedule getZone2HeatingSchedule(Zone2ControlParameter control) {
		switch (control) {
		case One:
		case Two:
			return getZone1HeatingSchedule();
		case Three:
			return SAPTables.HeatingSchedule.nineAndEight;
		}
		throw new RuntimeException("Unknown control parameter " + control);
	}

	@Override
	public boolean isHeatingOn() {
		return month.between(MonthType.October, MonthType.May);
	}

	@Override
	public double getHeatingOnFactor(IInternalParameters parameters, ISpecificHeatLosses losses, double revisedGains,
			double[] demandTemperature) {
		return 1;
	}

}
