package uk.org.cse.nhm.energycalculator.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorHouseCase;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.StepRecorder;
import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculationStep;
import uk.org.cse.nhm.energycalculator.constants.EnergyCalculatorConstants;

/**
 * An infiltration accumulator which matches SAP 2012 spec on ventilation rate, plus
 * BREDEM 7.2.2 on wind speeds.
 * <p>
 * The site exposure factor is now applied externally, because BREDEM 2009 changes that.
 *
 * @author hinton
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
     * The infiltration from floors in ACH/hr
     */
    private double floorAirChangeRate = 0;

    /**
     * The infiltration per open flue in m^3/hour
     */
    private final double openFlueVentilation;
    private double totalOpenFlueVentilation = 0;

    /**
     * The infiltration per chimney in m^3/hour
     */
    private final double chimneyVentilation;
    private double totalChimneyVentilation = 0;

    /**
     * The infiltration per intermittent fan in m^3/hour
     */
    private final double fanInfiltrationRate = 10;
    private double totalFanInfiltration = 0;

    /**
     * The infiltration per passive vent in m^3/hour
     */
    private final double ventInfiltrationRate = 10;
    private double totalVentInfiltration = 0;


    public StructuralInfiltrationAccumulator(final IConstants constants) {
        STACK_EFFECT_PARAMETER = constants.get(EnergyCalculatorConstants.STACK_EFFECT_AIR_CHANGE_PARAMETER);
        DRAUGHT_LOBBY_VENTILATION = constants.get(EnergyCalculatorConstants.DRAUGHT_LOBBY_AIR_CHANGE_PARAMETER);
        WINDOW_INFILTRATION = constants.get(EnergyCalculatorConstants.WINDOW_INFILTRATION);
        DRAUGHT_STRIPPED_FACTOR = constants.get(EnergyCalculatorConstants.DRAUGHT_STRIPPED_FACTOR);
        openFlueVentilation = constants.get(EnergyCalculatorConstants.OPEN_FLUE_VENTILATION_RATE);
        chimneyVentilation = constants.get(EnergyCalculatorConstants.CHIMNEY_VENTILATION_RATE);
    }

    /**
     * @assumption Infiltration rates are processed in the manner suggested by
     * SAP-09, as it has the clearest specification. Specifically,
     * for walls, take the IR of the largest wall, or max IR if
     * there are several largest walls.
     */
    @Override
    public double getAirChangeRate(final IEnergyCalculatorHouseCase house, final IEnergyCalculatorParameters parameters) {
        final double volume = house.getHouseVolume();

        StepRecorder.recordStep(EnergyCalculationStep.ChimneyVentilation, totalChimneyVentilation);
        StepRecorder.recordStep(EnergyCalculationStep.OpenFluesVentilation, totalOpenFlueVentilation);
        StepRecorder.recordStep(EnergyCalculationStep.IntermittentFansVentilation, totalFanInfiltration);
        StepRecorder.recordStep(EnergyCalculationStep.PassiveVentsVentilation, totalVentInfiltration);
        // Flueless gas fires not implemented
        StepRecorder.recordStep(EnergyCalculationStep.FluelessGasFiresVentilation, 0);

        /*
        BEISDOC
        NAME: Deliberate Infiltration
        DESCRIPTION: The total infiltration from passive vents, intermittent fans, open flues and chimneys
        TYPE: formula
        UNIT: m^3/hour
        SAP: (6a, 6b, 6a, 7b, 7c)
        BREDEM: Table 20
        STOCK: ventilation.csv
        NOTES: The values in the stock for chimneys are ignored. The counts of chimneys and open flues are derived from the heating system instead.
        NOTES: Flueless gas fires are not implemented in the NHM.
        ID: deliberate-infiltration
        CODSIEB
        */
        /**
         *  in M3/hr
         */
        final double deliberateInfiltration = totalChimneyVentilation + totalChimneyVentilation + totalFanInfiltration + totalVentInfiltration;

        /*
		BEISDOC
		ID: deliberate-air-changes
		NAME: Conversion of units for air changes from intermittent fans, passive vents, open flues and chimneys
		DESCRIPTION: Divide by house volume to get from m3/h to ach rate
		TYPE: formula
		UNIT: ach/h
		SAP: (8)
		BREDEM: 3C
		DEPS: deliberate-infiltration,dwelling-volume
		CODSIEB
		*/
        /**
         * This is air changes/hr due to intermittent fans, passive vents, open flues and chimneys.
         */
        final double deliberateAirChanges = deliberateInfiltration == 0 ? 0 : deliberateInfiltration / volume;

        StepRecorder.recordStep(EnergyCalculationStep.AirChanges_ChimneysFluesFansAndPSVs, deliberateAirChanges);

		/*
		BEISDOC
		NAME: Draught proofing effect on air change rate
		DESCRIPTION: Draught stripping reduces the constant air change rate from windows
		TYPE: formula
		UNIT: ach/h
		SAP: (15)
		BREDEM: Table 19
		DEPS: window-infiltration-constant,draught-stripped-factor-constant,draught-stripped-proportion
		STOCK: ventilation.csv (windowsanddoorsdraughtstrippedproportion)
		ID: window-infiltration
		CODSIEB
		*/
        final double windowAirChanges = WINDOW_INFILTRATION - (DRAUGHT_STRIPPED_FACTOR * house.getDraughtStrippedProportion());

        StepRecorder.recordStep(EnergyCalculationStep.ProportionWindowsDraughtProofed, house.getDraughtStrippedProportion());

        /*
        BEISDOC
        ID: stack-effect
        NAME: Stack effect
        DESCRIPTION: Each storey adds some air change per hour
        TYPE: formula
        UNIT: ach/h
        SAP: (9,10)
        BREDEM: Table 19
        DEPS:
        GET: house.number-of-storeys
        SET:
        STOCK: storeys.csv (number of rows for the house)
        CODSIEB
        */
        final double stackEffect = STACK_EFFECT_PARAMETER * (house.getNumberOfStoreys() - 1);
        StepRecorder.recordStep(EnergyCalculationStep.InfiltrationAdditionalStackEffect, stackEffect);

        StepRecorder.recordStep(EnergyCalculationStep.InfiltrationStructural, wallAirChangeRate);
        StepRecorder.recordStep(EnergyCalculationStep.InfiltrationGroundFloor, floorAirChangeRate);

        /*
        BEISDOC
        NAME: Include effect of draught lobby
        DESCRIPTION: Draught lobbies reduce some amount of air change rate
        TYPE: formula
        UNIT: ach/h
        SAP: (13)
        BREDEM: Table 19
        DEPS: draught-lobby-constant
        STOCK: cases.csv (hasdraftlobby)
        NOTES: SAP specifies 0.05 for no draught lobby. We presume flats have a draught lobby.
        ID: has-draught-lobby
        CODSIEB
        */
        final double absenceOfDraughtLobby = (house.hasDraughtLobby() ? 0 : DRAUGHT_LOBBY_VENTILATION);
        StepRecorder.recordStep(EnergyCalculationStep.InfiltrationAbsenceOfDraughtLobby, absenceOfDraughtLobby);

        StepRecorder.recordStep(EnergyCalculationStep.InfiltrationWindows, windowAirChanges);

        if (log.isDebugEnabled()) {
            log.debug("Deliberate ventilation {} ach/hr", deliberateAirChanges);
            log.debug("Wall ventilation {} ach/hr", wallAirChangeRate);
            log.debug("Floor {} ach/hr", floorAirChangeRate);
            log.debug("Draught Lobby {} ach/hr", absenceOfDraughtLobby);
            log.debug("Stack effect {} ach/hr", stackEffect);
        }

        /**
         * Unforced air changes is from everything that is not a fan
         */
        final double unforcedAirChanges =
                wallAirChangeRate +
                        windowAirChanges +
                        floorAirChangeRate +
                        absenceOfDraughtLobby +
                        stackEffect;
		
		/*
		BEISDOC
		NAME: Total infiltration rate
		DESCRIPTION: The sum of deliberate ventilation and passive infiltration. 
		TYPE: formula
		UNIT: ach/h
		SAP: (16,18)
		BREDEM: 3D
		DEPS: has-draught-lobby,stack-effect,window-infiltration,wall-air-changes,floor-infiltration,deliberate-air-changes
		ID: total-infiltration
		CODSIEB
		*/
		final double result = unforcedAirChanges + deliberateAirChanges;

		StepRecorder.recordStep(EnergyCalculationStep.InfiltrationRate_Initial, result);
        StepRecorder.recordStep(EnergyCalculationStep.InfiltrationRateMaybePressureTest, result); // Since we never use the pressure test.

        return result;
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
		
		/*
		BEISDOC
		ID: wall-air-changes
		NAME: Structural infiltration
		DESCRIPTION: Having a particular type of wall contributes some air changes to the total air change rate. The largest wall breaks ties
		TYPE: formula
		UNIT: ach/h
		SAP: (11)
		BREDEM: Table 19
		DEPS: external-wall-area
		NOTES: TODO Bredem specifies that this should be an area-weighted average.
		CODSIEB
		*/

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
    public void addVentInfiltration(final int vents) {
        final double infiltrationRate = vents * ventInfiltrationRate;

        if (log.isTraceEnabled())
            log.trace("Adding {} m3/hr of vent infiltration for {} passive vents", infiltrationRate, vents);
        totalVentInfiltration += infiltrationRate;
    }

    @Override
    public void addFlueInfiltration() {
        if (log.isTraceEnabled()) log.trace("Adding {} m3/hr of open flue infiltration", openFlueVentilation);
        totalOpenFlueVentilation += openFlueVentilation;

    }

    @Override
    public void addChimneyInfiltration() {
        if (log.isTraceEnabled()) log.trace("Adding {} m3/hr of chimney infiltration", chimneyVentilation);
        totalChimneyVentilation += chimneyVentilation;

    }

    /* (non-Javadoc)
     * @see uk.org.cse.nhm.energycalculator.impl.IStructuralInfiltrationAccumulator#addFloorInfiltration(double, double)
     */
    @Override
    public void addFloorInfiltration(double airChangeRate) {
        if (log.isTraceEnabled()) log.trace("Adding {} ach/hr of floor infiltration", airChangeRate);
		/*
		BEISDOC
		NAME: Floor Infiltration
		DESCRIPTION: Infiltration due to suspended timber floor
		TYPE: formula
		UNIT: m^3/h
		SAP: (12)
		BREDEM: Table 19
		SET: =action.reset-floors=
		NOTES: In SAP 2012 mode, this will always use the values from the SAP table, and will ignore any values from action.reset-floors.
		ID: floor-infiltration
		CODSIEB
		*/

        floorAirChangeRate += airChangeRate;
    }

    /* (non-Javadoc)
     * @see uk.org.cse.nhm.energycalculator.impl.IStructuralInfiltrationAccumulator#addFanInfiltration(double)
     */
    @Override
    public void addFanInfiltration(int fans) {
        final double infiltrationRate = fanInfiltrationRate * fans;

        if (log.isTraceEnabled()) log.trace("Adding {} m3/hr of fan infiltration from {} fans", infiltrationRate);
        totalFanInfiltration += infiltrationRate;
    }
}
