package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.*;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;

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
	 * @oddity BREDEM 2012 specifies that a "heating on factor" is calculated to
	 *         account for the variation in how much heating systems are on in
	 *         warmer months. For some reason, this scaling factor is also
	 *         applied to internal gains, in spite of the fact that (for
	 *         example) solar gains in warmer months will actually be larger.
	 *         Anyway, this is not our assumption, but it is odd.
	 */
	@Override
	public void generate(final IEnergyCalculatorHouseCase house, final IInternalParameters parameters, final ISpecificHeatLosses losses, final IEnergyState state) {
		final double totalGains = state.getExcessSupply(EnergyType.GainsUSEFUL_GAINS);

		// now we use up these gains, so that we don't use them anywhere else
		// this box turns them into heat, using the "heating on factor"
		state.increaseDemand(EnergyType.GainsUSEFUL_GAINS, totalGains);

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
		StepRecorder.recordStep(EnergyCalculationStep.Gains_Useful, revisedGains);

		final double fractionOfMonthThatIsHeated = parameters.getClimate().getHeatingOnFactor(parameters, losses, revisedGains, demandTemperature);

		StepRecorder.recordStep(EnergyCalculationStep.ExternalTemperature, externalTemperature);

		/*
		BEISDOC
		NAME: Heat Loss Rate for Mean Internal Temperature
		DESCRIPTION: The total heat loss from the building at the given mean internal temperature.
		TYPE: formula
		UNIT: W
		SAP: (97)
		BREDEM: 8I
		DEPS: specific-heat-loss,external-temperature,mean-internal-temperature-adjusted
		ID: heat-loss-at-mean-internal-temperature
		CODSIEB
		*/
		final double heatLossRate =
				(areaWeightedMeanTemperature - externalTemperature) * losses.getSpecificHeatLoss();

		StepRecorder.recordStep(EnergyCalculationStep.HeatLossRate, heatLossRate);

		// TODO this may not be correct in BREDEM; it appears to say that the "Fraction of month that is heated"
		// modulates only between using all the gains, and using just the utilisation-factored gains.
		// It sounds odd because it's a double-utilisation factor (or a utilisation double-factor?).
		// the name sounds like a factor to determine the proportion of the month that the heating is on
		// but here the base demand is not multiplied by the fraction at all.

		final double heatDemandSatisfiedByGains = (1 - fractionOfMonthThatIsHeated + (fractionOfMonthThatIsHeated * revisedGUF)) * totalGains;

		/*
		BEISDOC
		NAME: Heat Demand
		DESCRIPTION: The heat demand for the month
		TYPE: formula
		UNIT: W
		SAP: (98)
		BREDEM: 8I
		DEPS: bredem-heating-on-factor,heat-loss-at-mean-internal-temperature
		ID: heat-demand
		CODSIEB
		*/
		final double totalHeatDemandAfterGains = Math.max(0, heatLossRate - heatDemandSatisfiedByGains);

		StepRecorder.recordStep(EnergyCalculationStep.SpaceHeating, totalHeatDemandAfterGains);

		// the net effect of these two lines is zero
		state.increaseDemand(EnergyType.DemandsHEAT, heatDemandSatisfiedByGains);
		state.increaseSupply(EnergyType.DemandsHEAT, heatDemandSatisfiedByGains);

		// this is the actual amount of heat left to be provided after this
		// function has finished
		state.increaseDemand(EnergyType.DemandsHEAT, totalHeatDemandAfterGains);

		// this is just information for elsewhere.
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
}