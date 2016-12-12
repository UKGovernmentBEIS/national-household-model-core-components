package uk.org.cse.nhm.energycalculator.api.types;

import java.util.EnumSet;

/**
 * Describes the different types of insulation that a wall can have.
 * 
 * @author hinton
 *
 */
public enum WallInsulationType {
	External,
	Internal,
	FilledCavity;
	
	public static final EnumSet<WallInsulationType> InternalOrExternal = EnumSet.of(WallInsulationType.External, WallInsulationType.Internal);
}
