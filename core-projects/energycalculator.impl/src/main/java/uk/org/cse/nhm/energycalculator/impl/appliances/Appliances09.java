package uk.org.cse.nhm.energycalculator.impl.appliances;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.energycalculator.api.types.TransducerPhaseType;
import uk.org.cse.nhm.energycalculator.constants.ApplianceConstants09;

/**
 * The EnergyCalculator appliance model
 *
 * @author hinton
 *
 */
public class Appliances09 implements IEnergyTransducer {
	private static final Logger log = LoggerFactory.getLogger(Appliances09.class);
	private final double APPLIANCE_DEMAND_COEFFICIENT_SAP;
	private final double APPLIANCE_DEMAND_COEFFICIENT_BREDEM;
	private final double APPLIANCE_DEMAND_EXPONENT;
	private final double APPLIANCE_DEMAND_COSINE_COEFFICIENT;
	private final double APPLIANCE_DEMAND_COSINE_OFFSET;
	private final double[] APPLIANCE_HIGH_RATE_FRACTION;

	public Appliances09(final IConstants constants) {
		// SAP constants are not configurable.
		this.APPLIANCE_DEMAND_COEFFICIENT_SAP = constants.get(ApplianceConstants09.APPLIANCE_DEMAND_COEFFICIENT_SAP);
		this.APPLIANCE_DEMAND_COEFFICIENT_BREDEM = constants.get(ApplianceConstants09.APPLIANCE_DEMAND_COEFFICIENT_BREDEM);
		this.APPLIANCE_DEMAND_EXPONENT = constants.get(ApplianceConstants09.APPLIANCE_DEMAND_EXPONENT);
		this.APPLIANCE_DEMAND_COSINE_COEFFICIENT = constants.get(ApplianceConstants09.APPLIANCE_DEMAND_COSINE_COEFFICIENT);
		this.APPLIANCE_DEMAND_COSINE_OFFSET = constants.get(ApplianceConstants09.APPLIANCE_DEMAND_COSINE_OFFSET);
		this.APPLIANCE_HIGH_RATE_FRACTION = constants.get(ApplianceConstants09.APPLIANCE_HIGH_RATE_FRACTION, double[].class);
	}

	@Override
	public ServiceType getServiceType() {
		return ServiceType.APPLIANCES;
	}

	@Override
	public void generate(
			final IEnergyCalculatorHouseCase house,
			final IInternalParameters parameters,
			final ISpecificHeatLosses losses,
			final IEnergyState state) {

		/*
		BEISDOC
		NAME: Appliance Initial Demand
		DESCRIPTION: Electricity demand from household appliances, before adjustment.
		TYPE: formula
		UNIT: W
		SAP: (L10)
                SAP_COMPLIANT: Yes
		BREDEM: 1I
                BREDEM_COMPLIANT: Yes
		DEPS: appliance-demand-coefficient,appliance-demand-exponent,occupancy,dwelling-floor-area
		ID: appliance-initial-demand
		CODSIEB
		*/
		final double demand = getApplianceDemandCoefficient(parameters) *
				Math.pow(house.getFloorArea() * parameters.getNumberOfOccupants(),
						APPLIANCE_DEMAND_EXPONENT);

		if (log.isDebugEnabled()) log.debug("Ea = {} W", demand);

		/*
		BEISDOC
		NAME: Appliance Adjusted Demand
		DESCRIPTION: Electric demand from household appliances, adjusted for the month of the year.
		TYPE: formula
		UNIT: W
		SAP: (L11)
                SAP_COMPLIANT: Yes
		BREDEM: (1J)
                BREDEM_COMPLIANT: Yes
		DEPS: appliance-initial-demand,appliance-adjustement-cosine-coefficient,appliance-adjustment-cosine-offset
		ID: appliance-adjusted-demand
		CODSIEB
		*/
		final double monthlyAdjustment =
				1 + APPLIANCE_DEMAND_COSINE_COEFFICIENT *
				Math.cos(Math.PI * 2 * (parameters.getClimate().getMonthOfYear() - APPLIANCE_DEMAND_COSINE_OFFSET) / 12);
		final double adjustedDemand = monthlyAdjustment * demand;

		if (log.isDebugEnabled()) log.debug("Monthly adjustment = {} (adjusted = {})", monthlyAdjustment, adjustedDemand);

		state.increaseElectricityDemand(APPLIANCE_HIGH_RATE_FRACTION[parameters.getTarrifType().ordinal()], adjustedDemand);
		state.increaseSupply(
				EnergyType.GainsAPPLIANCE_GAINS,
				house.hasReducedInternalGains() ? (0.6 * adjustedDemand) : adjustedDemand
					);
	}

	private double getApplianceDemandCoefficient(final IInternalParameters parameters) {
		switch(parameters.getCalculatorType()) {
		case SAP2012:
			return APPLIANCE_DEMAND_COEFFICIENT_SAP;
		case BREDEM2012:
			return APPLIANCE_DEMAND_COEFFICIENT_BREDEM;
		default:
			throw new UnsupportedOperationException("Unknown energy calculator type " + parameters.getCalculatorType());
		}
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	public String toString() {
		return "Appliances";
	}

	@Override
	public TransducerPhaseType getPhase() {
		return TransducerPhaseType.BeforeEverything;
	}
}
