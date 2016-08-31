package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.energycalculator.constants.EnergyCalculatorConstants;

/**
 * This is an internal transducer which determines the gain-load ratio and
 * produces Gains See
 * {@link #generate(IEnergyCalculatorHouseCase, IInternalParameters, ISpecificHeatLosses, IEnergyState)}
 * for details about what this does.
 * 
 * @author hinton
 * 
 */
class GainLoadRatioAdjuster implements IEnergyTransducer {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(GainLoadRatioAdjuster.class);

	@Override
	public ServiceType getServiceType() {
		return ServiceType.INTERNALS;
	}

	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.AfterGains;
	}

	@Override
	public String toString() {
		return "Gains -> Heat";
	}

	private double revisedGUF;
	private double[] demandTemperature;
	private double externalTemperature;
	private double areaWeightedMeanTemperature;

	/**
	 * This does the gain-to-load adjustment specified in BREDEM 2009. This
	 * seems to involve some calculation which makes an assumption about the
	 * number of degree-days in a month away from some threshold temperature,
	 * and using it to compute the "heating on factor" which down-weights the
	 * amount of time the heating is on in warmer months, but as is often the
	 * case this is not really explained in BREDEM 2009.
	 * <p>
	 * Once the factor has been computed this sets the heat demand into the
	 * {@link IEnergyState}, and then supplies some of that heat demand using
	 * the adjusted gains (as for some reason BREDEM 2009 specifies that
	 * internal gains should be scaled by the same amount as heat demand).
	 * 
	 * 
	 * @param house
	 * @param parameters
	 * @param losses
	 * @param state
	 * @oddity BREDEM 2009 specifies that a "heating on factor" is calculated to
	 *         account for the variation in how much heating systems are on in
	 *         warmer months. For some reason, this scaling factor is also
	 *         applied to internal gains, in spite of the fact that (for
	 *         example) solar gains in warmer months will actually be larger.
	 *         Anyway, this is not our assumption, but it is odd.
	 */
	@Override
	public void generate(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final ISpecificHeatLosses losses, final IEnergyState state) {
		final double totalGains = state.getExcessSupply(EnergyType.GainsUSEFUL_GAINS);
		
		/*
		BEISDOC
		NAME: Total Useful Gains
		DESCRIPTION: The total gains multiplied by the revised gains utilisation factor.
		TYPE: formula
		UNIT: W
		SAP: (95)
		BREDEM: 8I
		DEPS: gains-utilisation-factor-revised,total-gains
		ID: total-useful-gains
		CODSIEB
		*/
		final double revisedGains = totalGains * revisedGUF;

		/*
		BEISDOC
		NAME: Threshold temperature
		DESCRIPTION: The upper threshold temperature for BREDEM's degree days calculation.
		TYPE: formula
		UNIT: ℃
		BREDEM: 8D
		DEPS: zone-1-adjusted-demand-temperature,gains-utilisation-factor-threshold-difference
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
		DEPS: weather,total-useful-gains,specific-heat-loss
		ID: unheated-temperature
		CODSIEB
		*/
		final double unheatedTemperature = externalTemperature + totalGains / losses.getSpecificHeatLoss();
		
		
		final double thresholdDegreeDays = calculateThresholdDegreeDays(parameters.getConstants(), thresholdTemperature, unheatedTemperature);

		final double thresholdDegreeDaysPlus1 = calculateThresholdDegreeDays(parameters.getConstants(), thresholdTemperature + 1, unheatedTemperature);

		/*
		BEISDOC
		NAME: Heating on Factor
		DESCRIPTION: The fraction of the month which is heated. Calculated by subtracting the number of degree days at threshold -0.5 from those at threshold +0.5.
		TYPE: formula
		UNIT: Dimensionless
		BREDEM: 8H
		DEPS: threshold-degree-days
		ID: heating-on-factor
		CODSIEB
		*/
		final double heatingOnFactor = thresholdDegreeDaysPlus1 - thresholdDegreeDays;

		/*
		BEISDOC
		NAME: Heat Loss Rate for Mean Internal Temperature
		DESCRIPTION: The total heat loss from the building at the given mean internal temperature.
		TYPE: formula
		UNIT: W
		SAP: (97)
		BREDEM: 8I
		DEPS: specific-heat-loss,weather,mean-internal-temperature-adjusted
		ID: heat-loss-at-mean-internal-temperature
		CODSIEB
		*/
		final double heatLossRate = (areaWeightedMeanTemperature - externalTemperature) * losses.getSpecificHeatLoss();
		
		/*
		BEISDOC
		NAME: friendlyname
		DESCRIPTION: description
		TYPE: type
		UNIT: unit
		SAP: (98) 
		BREDEM: 8I
		DEPS: heating-on-factor,heat-loss-at-mean-internal-temperature
		NOTES: TODO implement the SAP version of this, for which the heatingOnFactor is replaced by 1/0 depend on whether it is a heating month.
		ID: heat-demand
		CODSIEB
		*/
		final double heatDemand = heatingOnFactor * heatLossRate;

		if (log.isDebugEnabled()) {
			log.debug("{} total gains, {} useful gains, {} heat on factor, {} demand, {} external temp", totalGains, revisedGains, heatingOnFactor, heatDemand,
					externalTemperature);
		}

		// this slightly convoluted increase in demand is intended so that other
		// parts of the calculation can use HEAT by INTERNALS
		// to find out the actual useful gains after adjustment.
		state.increaseDemand(EnergyType.DemandsHEAT, (heatDemand - revisedGains * heatingOnFactor) + revisedGains);
		state.increaseDemand(EnergyType.GainsUSEFUL_GAINS, totalGains);
		state.increaseSupply(EnergyType.DemandsHEAT, revisedGains);
		state.increaseSupply(EnergyType.HackMEAN_INTERNAL_TEMPERATURE, areaWeightedMeanTemperature);
	}

	public void setRevisedGUF(final double revisedGUF) {
		this.revisedGUF = revisedGUF;
	}

	public void setDemandTemperature(final double[] demandTemperature) {
		this.demandTemperature = demandTemperature;
	}

	public void setExternalTemperature(final double externalTemperature) {
		this.externalTemperature = externalTemperature;
	}

	public void setAreaWeightedMeanTemperature(final double areaWeightedMeanTemperature) {
		this.areaWeightedMeanTemperature = areaWeightedMeanTemperature;
	}

	@Override
	public int getPriority() {
		return 0;
	}

	protected double calculateThresholdDegreeDays(final IConstants constants, final double thresholdTemperature, final double unheatedTemperature) {
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
}