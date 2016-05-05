package uk.org.cse.nhm.hom.emf.technologies.boilers.impl.util;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.hom.constants.VentilationConstants;
import uk.org.cse.nhm.hom.emf.technologies.boilers.FlueType;

/**
 * Helper class for things with flues which handles ventilation communication with the calculator.
 * 
 * @author hinton
 *
 */
public class FlueVentilationHelper {
	public static void addInfiltration(final IEnergyCalculatorVisitor visitor, final FlueType flueType, final IConstants constants) {
		if (flueType == FlueType.OPEN_FLUE)
			visitor.addVentInfiltration(constants.get(VentilationConstants.OPEN_FLUE_VENTILATION_RATE));
		else if (flueType == FlueType.CHIMNEY)
			visitor.addVentInfiltration(constants.get(VentilationConstants.CHIMNEY_VENTILATION_RATE));
	}
}
