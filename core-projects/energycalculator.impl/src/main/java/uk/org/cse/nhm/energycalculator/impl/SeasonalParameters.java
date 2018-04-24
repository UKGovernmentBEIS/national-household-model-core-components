package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
import uk.org.cse.nhm.energycalculator.api.IWeather;
import uk.org.cse.nhm.energycalculator.api.impl.InsolationPlaneUtil;
import uk.org.cse.nhm.energycalculator.api.types.MonthType;

public abstract class SeasonalParameters implements ISeasonalParameters {
	/*
	BEISDOC
	NAME: Declination
	DESCRIPTION: The declination of the sun
	TYPE: value
	UNIT: Radians
	SAP: Table U3 (bottom half)
        SAP_COMPLIANT: Yes
	BREDEM: Table 16
        BREDEM_COMPLIANT: Yes
	CONVERSION: From degress to radians
	ID: solar-declination
	CODSIEB
	*/
	public static final double[] DECLINATION = {
			-0.36128316, -0.22340214,
				-0.03141593, 0.17104227, 0.3281219, 0.40317106, 0.3700098,
				0.23911011, 0.05061455, -0.15184364, -0.32114058, -0.40142573
		};

	protected final MonthType month;
	private final IWeather weather;
	private final double latitude;
	
	public SeasonalParameters(MonthType month, IWeather weather, double latitude) {
		super();
		this.month = month;
		this.weather = weather;
		this.latitude = latitude;
	}

	@Override
	public final int getMonthOfYear() {
		return month.ordinal() + 1;
	}

	@Override
	public final double getSolarDeclination() {
		return DECLINATION[month.ordinal()];
	}

	@Override
	public double getExternalTemperature() {
		return weather.getExternalTemperature(month);
	}

	@Override
	public double getSiteWindSpeed() {
		return weather.getWindSpeed(month);
	}

	@Override
	public double getSolarFlux(double angleFromHorizontal, double angleFromNorth) {
		/*
		BEISDOC
		NAME: Effective solar flux
		DESCRIPTION:
		TYPE: formula
		UNIT: W
		SAP: (U1)
                SAP_COMPLIANT: Yes
		BREDEM: 2.4.1G
                BREDEM_COMPLIANT: Yes
		DEPS: insolation,solar-flux-adjustment
		ID: effective-solar-flux
		CODSIEB
		*/
        return weather.getHorizontalSolarFlux(month) * InsolationPlaneUtil.getSolarFluxMultiplier(getSolarDeclination(), latitude, angleFromHorizontal, angleFromNorth);
	}
}
