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
	private final double APPLIANCE_DEMAND_COEFFICIENT;
	private final double APPLIANCE_DEMAND_EXPONENT;
	private final double APPLIANCE_DEMAND_COSINE_COEFFICIENT;
	private final double APPLIANCE_DEMAND_COSINE_OFFSET;
	private final double[] APPLIANCE_HIGH_RATE_FRACTION;

	public Appliances09(final IConstants constants) {
		this.APPLIANCE_DEMAND_COEFFICIENT = constants.get(ApplianceConstants09.APPLIANCE_DEMAND_COEFFICIENT);
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
		final double demand = APPLIANCE_DEMAND_COEFFICIENT * 		
				Math.pow(house.getFloorArea() * parameters.getNumberOfOccupants(),
						APPLIANCE_DEMAND_EXPONENT);
		if (log.isDebugEnabled()) log.debug("Ea = {} W", demand);
		final double monthlyAdjustment = 
				1 + APPLIANCE_DEMAND_COSINE_COEFFICIENT *
				Math.cos(Math.PI * 2 * (parameters.getClimate().getMonthOfYear() - APPLIANCE_DEMAND_COSINE_OFFSET) / 12);
		final double adjustedDemand = monthlyAdjustment * demand;
		if (log.isDebugEnabled()) log.debug("Monthly adjustment = {} (adjusted = {})", monthlyAdjustment, adjustedDemand);
		
		state.increaseElectricityDemand(APPLIANCE_HIGH_RATE_FRACTION[parameters.getTarrifType().ordinal()], adjustedDemand);
		state.increaseSupply(EnergyType.GainsAPPLIANCE_GAINS, adjustedDemand);
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
