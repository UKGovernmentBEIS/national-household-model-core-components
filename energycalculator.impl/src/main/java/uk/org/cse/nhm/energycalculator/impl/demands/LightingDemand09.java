package uk.org.cse.nhm.energycalculator.impl.demands;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyState;
import uk.org.cse.nhm.energycalculator.api.IInternalParameters;
import uk.org.cse.nhm.energycalculator.api.ISpecificHeatLosses;
import uk.org.cse.nhm.energycalculator.api.impl.Sink;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.constants.LightingConstants09;

/**
 * The CHM takes account of the external lighting in its lighting demand calculation..
 * <p>
 * This could alternatively be an extra light source, which satisfies whatever fraction
 * of demand is satisfied by daylighting; depends on whether you think of it as demand for
 * <em>additional</em> light, or total demand for light
 * <p>
 * ((C655 * (floor area * num people)^C656) * IE) * magic factor
 * @author hinton
 *
 */
public class LightingDemand09 extends Sink {
	private static final Logger log = LoggerFactory.getLogger(LightingDemand09.class);
	
	private final double LIGHT_DEMAND_EXPONENT;

	private final double DAYLIGHT_PARAMETER_MAXIMUM;
	
	private final double DLP_C, DLP_B, DLP_A;
	
	private final double[] OVERSHADING_FACTORS;
	
	private double totalLightFromWindows = 0;

	private final double MONTHLY_ADJUSTMENT_A;

	private final double MONTHLY_ADJUSTMENT_B;

	private final double MONTHLY_ADJUSTMENT_C;
	
	public LightingDemand09(final IConstants constants) {
		super(EnergyType.DemandsVISIBLE_LIGHT);
		
		LIGHT_DEMAND_EXPONENT = constants.get(LightingConstants09.LIGHT_DEMAND_EXPONENT);
		
		DAYLIGHT_PARAMETER_MAXIMUM = constants.get(LightingConstants09.DAYLIGHT_PARAMETER_MAXIMUM);
		
		DLP_A = constants.get(LightingConstants09.DAYLIGHT_PARAMETER_2_COEFFICIENT);
		DLP_B = constants.get(LightingConstants09.DAYLIGHT_PARAMETER_1_COEFFICIENT);
		DLP_C = constants.get(LightingConstants09.DAYLIGHT_PARAMETER_0_COEFFICIENT);
		
		MONTHLY_ADJUSTMENT_A = constants.get(LightingConstants09.ADJUSTMENT_FACTOR_TERMS, 0);

		MONTHLY_ADJUSTMENT_B = constants.get(LightingConstants09.ADJUSTMENT_FACTOR_TERMS, 1);
		
		MONTHLY_ADJUSTMENT_C = constants.get(LightingConstants09.ADJUSTMENT_FACTOR_TERMS, 2);
		
		OVERSHADING_FACTORS = constants.get(LightingConstants09.OVERSHADING_ACCESS_FACTORS, double[].class);
	}

	@Override
	public int getPriority() {
		return 0;
	}

	@Override
	protected double getDemand(
			final IEnergyCalculatorHouseCase house,
			final IInternalParameters parameters, 
			final ISpecificHeatLosses losses,
			final IEnergyState state) {
		log.debug("Total light from windows: {}", totalLightFromWindows);
		final double floorAreaPeople = house.getFloorArea() * parameters.getNumberOfOccupants();
		final double demandWithoutAdjustment = 
				Math.pow(floorAreaPeople, LIGHT_DEMAND_EXPONENT);

		final double daylightParameter = Math.min(DAYLIGHT_PARAMETER_MAXIMUM, totalLightFromWindows / house.getFloorArea());
		final double daylightSavingCoefficient =
						DLP_A * Math.pow(daylightParameter, 2) +
						DLP_B * daylightParameter + 
						DLP_C;
		
		final double monthlyAdjustment = MONTHLY_ADJUSTMENT_A + MONTHLY_ADJUSTMENT_B * 
				Math.cos(2 * Math.PI * (parameters.getClimate().getMonthOfYear() - MONTHLY_ADJUSTMENT_C) / 12);
		
		log.debug("Raw demand: {}, Daylight parameter: {}, savings factor: {}, month factor: {}", demandWithoutAdjustment, daylightParameter, daylightSavingCoefficient, monthlyAdjustment);
		
		return demandWithoutAdjustment * daylightSavingCoefficient * monthlyAdjustment;
	}
	
	@Override
	public String toString() {
		return "Lighting";
	}

	public void addTransparentElement(final double visibleLightTransmittivity,
			final double solarGainTransmissivity, final double horizontalOrientation,
			final double verticalOrientation, final OvershadingType overshading) {
		totalLightFromWindows += 0.9 * visibleLightTransmittivity * OVERSHADING_FACTORS[overshading.ordinal()];
	}
}
