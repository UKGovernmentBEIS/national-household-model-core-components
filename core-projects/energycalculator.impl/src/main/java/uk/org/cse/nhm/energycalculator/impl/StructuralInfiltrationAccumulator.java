package uk.org.cse.nhm.energycalculator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.constants.EnergyCalculatorConstants;

/**
 * An infiltration accumulator which matches SAP 2012 spec on ventilation rate, plus
 * BREDEM 7.2.2 on wind speeds.
 * 
 * The site exposure factor is now applied externally, because BREDEM 2009 changes that.
 *  
  @author hinton
 *
 */
public class StructuralInfiltrationAccumulator implements IStructuralInfiltrationAccumulator {
	private static final Logger log = LoggerFactory.getLogger(StructuralInfiltrationAccumulator.class);
	private final double STACK_EFFECT_PARAMETER;
	private final double DRAUGHT_LOBBY_VENTILATION;
	private final double WINDOW_INFILTRATION;
	private final double DRAUGHT_STRIPPED_FACTOR;
	/**
	 * The area of the largest wall yet seen, in M2
	 */
	private double maximumWallArea = 0;
	
	/**
	 * The air change rate associated with the largest wall, in ACH/hr
	 */
	private double wallAirChangeRate = 0;
		
	/**
	 * The infiltration from vents, in M3/hr
	 */
	private double ventInfiltration = 0;
	
	/**
	 * The infiltration from fans in M3/hr
	 */
	private double fanInfiltration = 0;
	
	/**
	 * The infiltration from floors in ACH/hr
	 */
	private double floorAirChangeRate = 0;

	
	public StructuralInfiltrationAccumulator(final IConstants constants) {
		STACK_EFFECT_PARAMETER = constants.get(EnergyCalculatorConstants.STACK_EFFECT_AIR_CHANGE_PARAMETER);
		DRAUGHT_LOBBY_VENTILATION = constants.get(EnergyCalculatorConstants.DRAUGHT_LOBBY_AIR_CHANGE_PARAMETER);
		WINDOW_INFILTRATION = constants.get(EnergyCalculatorConstants.WINDOW_INFILTRATION);
		DRAUGHT_STRIPPED_FACTOR = constants.get(EnergyCalculatorConstants.DRAUGHT_STRIPPED_FACTOR);
	}

	/**
	 * @assumption Infiltration rates are processed in the manner suggested by
	 *             SAP-09, as it has the clearest specification. Specifically,
	 *             for walls, take the IR of the largest wall, or max IR if
	 *             there are several largest walls.
	 */
	@Override
	public double getAirChangeRate(final IEnergyCalculatorHouseCase house, final IEnergyCalculatorParameters parameters) {
        final double volume = house.getHouseVolume();
		/**
		 * This is the number of air changes/hr due to fan infiltration;
		 */
		final double fanAirChanges = fanInfiltration == 0 ? 0 : fanInfiltration / volume;
		/**
		 * This is air changes/hr due to non-fan vents and flues
		 */
		final double ventAirChanges = ventInfiltration == 0 ? 0 : ventInfiltration / volume;
		
		final double windowAirChanges = WINDOW_INFILTRATION - DRAUGHT_STRIPPED_FACTOR * house.getDraughtStrippedProportion();
		
		if (log.isDebugEnabled()) {
			log.debug("Fan ventilation {} ach/hr", fanAirChanges);
			log.debug("Vent ventilation {} ach/hr", ventAirChanges);
			log.debug("Wall ventilation {} ach/hr", wallAirChangeRate);
			log.debug("Floor {} ach/hr", floorAirChangeRate);
			log.debug("Draught Lobby {} ach/hr", (house.hasDraughtLobby() ? 0 : DRAUGHT_LOBBY_VENTILATION));
			log.debug("Stack effect {} ach/hr", STACK_EFFECT_PARAMETER * (house.getNumberOfStoreys() - 1));
		}
		
		/**
		 * Unforced air changes is from everything that is not a fan
		 */
		final double unforcedAirChanges = 
				ventAirChanges +
				wallAirChangeRate +
				windowAirChanges +
				floorAirChangeRate +
				(house.hasDraughtLobby() ? 0 : DRAUGHT_LOBBY_VENTILATION) + // SAP specifies 0.05 for no draught lobby. We presume flats have a draught lobby.
				STACK_EFFECT_PARAMETER * (house.getNumberOfStoreys() - 1);
		
		return unforcedAirChanges  + fanAirChanges;
	}

	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.energycalculator.impl.IStructuralInfiltrationAccumulator#addWallInfiltration(double, double)
	 */
	@Override
	public void addWallInfiltration(final double wallArea, final double airChangeRate) {
		if (airChangeRate == 0) {
			if (log.isTraceEnabled()) log.trace("Ignoring zero-infiltration wall, assuming it is a party wall");
			return;
		}
		if (log.isTraceEnabled()) log.trace("Adding {} ach/hr of wall infiltration", airChangeRate);
		if (wallArea > maximumWallArea) {
			maximumWallArea = wallArea;
			wallAirChangeRate = airChangeRate;
		} else if (wallArea == maximumWallArea && airChangeRate > wallAirChangeRate) {
			wallAirChangeRate = airChangeRate;
		}
	}

	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.energycalculator.impl.IStructuralInfiltrationAccumulator#addVentInfiltration(double)
	 */
	@Override
	public void addVentInfiltration(final double infiltrationRate) {
		if (log.isTraceEnabled()) log.trace("Adding {} m3/hr of vent infiltration", infiltrationRate);
		ventInfiltration += infiltrationRate;
	}

	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.energycalculator.impl.IStructuralInfiltrationAccumulator#addFloorInfiltration(double, double)
	 */
	@Override
	public void addFloorInfiltration(double floorArea, double airChangeRate) {
		if (log.isTraceEnabled()) log.trace("Adding {} ach/hr of floor infiltration", airChangeRate);
		floorAirChangeRate += airChangeRate;
	}

	/* (non-Javadoc)
	 * @see uk.org.cse.nhm.energycalculator.impl.IStructuralInfiltrationAccumulator#addFanInfiltration(double)
	 */
	@Override
	public void addFanInfiltration(double infiltrationRate) {
		if (log.isTraceEnabled()) log.trace("Adding {} m3/hr of fan infiltration", infiltrationRate);
		fanInfiltration += infiltrationRate;
	}
}
