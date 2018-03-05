package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;
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

	protected MonthType month;

	SeasonalParameters(final MonthType month) {
		this.month = month;
	}

	@Override
	public final int getMonthOfYear() {
		return month.ordinal() + 1;
	}

	@Override
	public final double getSolarDeclination() {
		return DECLINATION[month.ordinal()];
	}
}
