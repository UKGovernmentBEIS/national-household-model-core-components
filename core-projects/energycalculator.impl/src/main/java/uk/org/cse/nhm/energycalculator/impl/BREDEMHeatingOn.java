package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.constants.EnergyCalculatorConstants;

/*
 * Under BREDEM 2012, for what proportion of the month should the space heating or space cooling systems be on.
 */
public class BREDEMHeatingOn {

	public static double getHeatingOnFactor(final IInternalParameters parameters, final ISpecificHeatLosses losses, final double revisedGains, final double[] demandTemperature, final double externalTemperature) {
		/*
		BEISDOC
		NAME: Threshold temperature
		DESCRIPTION: The upper threshold temperature for BREDEM's degree days calculation.
		TYPE: formula
		UNIT: ℃
		BREDEM: 8D
		DEPS: zone-1-demand-temperature,gains-utilisation-factor-threshold-difference
		ID: threshold-temperature
		CODSIEB
		*/
		final double thresholdTemperature = demandTemperature[0] - parameters.getConstants().get(EnergyCalculatorConstants.GAINS_UTILISATION_FACTOR_THRESHOLD_DIFFERENCE);

		/*
		BEISDOC
		NAME: Unheated temperature
		DESCRIPTION: The lower threshold temperature for BREDEM's degree days calculation.
		TYPE: formala
		UNIT: ℃
		BREDEM: 8E
		DEPS: external-temperature,total-useful-gains,specific-heat-loss
		ID: unheated-temperature
		CODSIEB
		*/
		final double unheatedTemperature = externalTemperature + revisedGains / losses.getSpecificHeatLoss();

		final double thresholdDegreeDays = calculateThresholdDegreeDays(parameters.getConstants(), thresholdTemperature, unheatedTemperature);

		final double thresholdDegreeDaysPlus1 = calculateThresholdDegreeDays(parameters.getConstants(), thresholdTemperature + 1, unheatedTemperature);

		/*
		BEISDOC
		NAME: BREDEM Heating on Factor
		DESCRIPTION: The fraction of the month which is heated. Calculated by subtracting the number of degree days at threshold -0.5 from those at threshold +0.5.
		TYPE: formula
		UNIT: Dimensionless
		BREDEM: 8H
		DEPS: threshold-degree-days
		ID: bredem-heating-on-factor
		CODSIEB
		*/
		return thresholdDegreeDaysPlus1 - thresholdDegreeDays;
	}

	protected static double calculateThresholdDegreeDays(final IConstants constants, final double thresholdTemperature, final double unheatedTemperature) {
		final double factor = constants.get(EnergyCalculatorConstants.THRESHOLD_DEGREE_DAYS_VALUE);

		/*
		BEISDOC
		NAME: Threshold degree days
		DESCRIPTION: The number of degree days at threshold temp +-0.5 ℃.
		TYPE: formula
		UNIT: Degree Days
		BREDEM: 8F, 8G
		DEPS: threshold-degree-days-value,threshold-temperature,unheated-temperature
		ID: threshold-degree-days
		CODSIEB
		*/
		return (thresholdTemperature == unheatedTemperature) ? (1 / factor) : (thresholdTemperature - unheatedTemperature)
				/ (1 - Math.exp(-factor * (thresholdTemperature - unheatedTemperature)));
	}

	public static double getCoolingOnFactor() {
		throw new UnsupportedOperationException("Cooling not yet implemented in the NHM energy calculator.");
	}
}
