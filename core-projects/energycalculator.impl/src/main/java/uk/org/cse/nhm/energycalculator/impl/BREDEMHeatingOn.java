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
                SAP_COMPLIANT: N/A - not used
		BREDEM: 8D
                BRDEM_COMPLIANT: Yes
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
                SAP_COMPLIANT: N/A - not used
		BREDEM: 8E
                BREDEM_COMPLIANT: Yes
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
		DESCRIPTION: The fraction of gains which should be multiplied by the gains utilisation factor (the remaining gains are used unmodified). Calculated by subtracting the number of degree days at threshold -0.5 from those at threshold +0.5.
		TYPE: formula
		UNIT: Dimensionless
                SAP_COMPLAINT: N/A - not used
		BREDEM: 8H
                BREDEM_COMPLIANT: Yes
		DEPS: threshold-degree-days
		NOTES: Despite the name, this value does not directly affect whether or not the heating is on.
		NOTES: Also known as the fraction of month heated.
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
                SAP_COMPLIANT: N/A - not used
		BREDEM: 8F, 8G
                BREDEM_COMPLIANT: Yes
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
