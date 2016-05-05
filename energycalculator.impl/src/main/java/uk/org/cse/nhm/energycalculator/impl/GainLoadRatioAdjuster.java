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

		final double revisedGains = totalGains * revisedGUF;

		final double thresholdTemperature = demandTemperature[0] - parameters.getConstants().get(EnergyCalculatorConstants.GAINS_UTILISATION_FACTOR_THRESHOLD_DIFFERENCE);
		final double unheatedTemperature = externalTemperature + totalGains / losses.getSpecificHeatLoss();
		final double thresholdDegreeDays = calculateThresholdDegreeDays(parameters.getConstants(), thresholdTemperature, unheatedTemperature);

		final double thresholdDegreeDaysPlus1 = calculateThresholdDegreeDays(parameters.getConstants(), thresholdTemperature + 1, unheatedTemperature);

		final double heatingOnFactor = thresholdDegreeDaysPlus1 - thresholdDegreeDays;

		final double heatDemand = heatingOnFactor * ((areaWeightedMeanTemperature - externalTemperature) * losses.getSpecificHeatLoss());

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
		return (thresholdTemperature == unheatedTemperature) ? (1 / factor) : (thresholdTemperature - unheatedTemperature)
				/ (1 - Math.exp(-factor * (thresholdTemperature - unheatedTemperature)));
	}
}