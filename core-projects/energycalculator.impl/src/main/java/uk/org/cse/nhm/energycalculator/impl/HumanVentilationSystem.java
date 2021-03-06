package uk.org.cse.nhm.energycalculator.impl;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IVentilationSystem;
import uk.org.cse.nhm.energycalculator.api.StepRecorder;
import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;

/**
 * This is a special {@link IVentilationSystem} which implements BREDEM 7.2.3,
 * Occupant Ventilation.
 *
 * It modifies the house air change rate so that it exceeds 1.0 air changes per
 * hour.
 *
 * @author hinton
 *
 */
public class HumanVentilationSystem implements IVentilationSystem {

    public HumanVentilationSystem(final IConstants constants) {

    }

    @Override
    public double getAirChangeRate(final double houseAirChangeRate) {
        /*
		BEISDOC
		NAME: Natural Infiltration
		DESCRIPTION: The air change rate, modified to include natural ventilation
		TYPE: formula
		UNIT: ach/h
		SAP: (24d)
                SAP_COMPLIANT: Yes
		BREDEM: 3F
                BREDEM_COMPLIANT: Yes
		DEPS: adjusted-infiltration
		ID: natural-infiltration
		CODSIEB
         */
        final double result;
        if (houseAirChangeRate < 1.0) {
            result = 0.5 + (0.5 * houseAirChangeRate * houseAirChangeRate);
        } else {
            result = houseAirChangeRate;
        }
        StepRecorder.recordStep(EnergyCalculationStep.Ventilation_NaturalOrPositiveFromLoft, result);
        return result;
    }
}
