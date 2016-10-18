package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.ISeasonalParameters;

/*
 * Under SAP 2012, should the space heating or space cooling systems be on.
 */
public class SapHeatingOn {
	// Note that neither heating nor cooling is on during September.

	public boolean isHeatingOn(ISeasonalParameters season) {
		// January to May, then October to December
		return season.getMonthOfYear() <= 5 || season.getMonthOfYear() >= 10;
	}

	public boolean isCoolingOn(ISeasonalParameters season) {
		// June to August
		return season.getMonthOfYear() >= 6 && season.getMonthOfYear() <= 8;
	}

	/*
	 * A convenience method which turns our TRUE/FALSE into a 1/0.
	 * 
	 * This is useful because BREDEM uses a number between 0 and 1 (inclusive)
	 * to determine how much of the month the heating is on for, so we can fit
	 * into the same code.
	 */
	public double getHeatingOnFactor(ISeasonalParameters season) {
		/*
		BEISDOC
		NAME: SAP Heating on Factor
		DESCRIPTION: 1 if this is a heating month (October to May), otherwise 0.
		TYPE: formula
		UNIT: Dimensionless
		SAP: (98 - exclusion of columns which should not be filled in)
		ID: sap-heating-on-factor
		CODSIEB
		*/
		return isHeatingOn(season) ? 1 : 0;
	}
}
