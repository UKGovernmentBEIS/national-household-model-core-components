package uk.org.cse.nhm.energycalculator.impl;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.DailyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.impl.InsolationPlaneUtil;
import uk.org.cse.nhm.energycalculator.api.impl.Weather;
import uk.org.cse.nhm.energycalculator.api.impl.WeeklyHeatingSchedule;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.energycalculator.api.types.Zone2ControlParameter;
import uk.org.cse.nhm.energycalculator.api.types.ZoneType;

public class SAPSeasonalParameters extends SeasonalParameters {

	// These names come from SAP 2012 Table 9.
	// I have no idea why they are called this.
	private static final IHeatingSchedule sevenAndEight = DailyHeatingSchedule.fromHours(7, 9, 16, 23);
	private static final IHeatingSchedule zeroAndEight = DailyHeatingSchedule.fromHours(7, 23);
	private static final IHeatingSchedule nineAndEight = DailyHeatingSchedule.fromHours(7, 9, 18, 23);
	private static final IHeatingSchedule off = DailyHeatingSchedule.fromHours();

	private static final IHeatingSchedule weekdaySevenAndEightWeekendZeroAndEight = new WeeklyHeatingSchedule(
			sevenAndEight, zeroAndEight);

	public SAPSeasonalParameters(final MonthType month) {
		super(month);
	}

	@Override
	public double getExternalTemperature() {
		return Weather.SAP12.getExternalTemperature(month);
	}

	@Override
	public double getSiteWindSpeed() {
		return Weather.SAP12.getWindSpeed(month);
	}

	@Override
	public double getSolarFlux(final double angleFromHorizontal, final double angleFromNorth) {
		return Weather.SAP12.getHorizontalSolarFlux(month) * InsolationPlaneUtil.getSolarFluxMultiplier(
				getSolarDeclination(), RegionType.UK_AVERAGE_LATITUDE_RADIANS, angleFromHorizontal, angleFromNorth);
	}

	@Override
	public IHeatingSchedule getHeatingSchedule(final ZoneType zone,
			final Optional<Zone2ControlParameter> zone2ControlParameter) {
		if (isHeatingOn()) {
			switch (zone) {
			case ZONE1:
				return weekdaySevenAndEightWeekendZeroAndEight;
			case ZONE2:
				if (zone2ControlParameter.isPresent()) {
					switch (zone2ControlParameter.get()) {
					case One:
					case Two:
						return weekdaySevenAndEightWeekendZeroAndEight;
					case Three:
						return nineAndEight;
					default:
						throw new IllegalArgumentException(
								"Unknown zone 2 control parameter (see SAP 2012 Table 9 and Table 4e) "
										+ zone2ControlParameter.get());
					}
				} else {
					throw new IllegalArgumentException(
							"Must specify the zone 2 control parameter when looking up the zone 2 heating schedule in SAP 2012 mode.");
				}
			default:
				throw new IllegalArgumentException(
						"Unknown heating zone while calculating SAP heating schedule " + zone);
			}
		} else {
			return off;
		}
	}

	@Override
	public boolean isHeatingOn() {
		// January to May, then October to December
		return month.between(MonthType.October, MonthType.May);
	}

	@Override
	public boolean isCoolingOn() {
		return month.between(MonthType.June, MonthType.August);
	}

	@Override
	public double getHeatingOnFactor(final IInternalParameters parameters, final ISpecificHeatLosses losses,
			final double revisedGains, final double[] demandTemperature) {
		/*
		 * BEISDOC NAME: SAP Heating on Factor DESCRIPTION: 1 if this is a
		 * heating month (October to May), otherwise 0. TYPE: formula UNIT:
		 * Dimensionless SAP: (98 - exclusion of columns which should not be
		 * filled in) ID: sap-heating-on-factor CODSIEB
		 */
		return isHeatingOn() ? 1 : 0;
	}
}
