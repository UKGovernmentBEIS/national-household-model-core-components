package uk.org.cse.nhm.energycalculator.api.types;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationResult;

/**
 * Used to accumulate the different kinds of heat loss area, for the energy calculator's {@link IEnergyCalculationResult}
 * @author hinton
 * @since 1.0.0
 */
public enum AreaType {
	ExternalWall,
	PartyWall,
	ExternalFloor,
	PartyFloor,
	ExternalCeiling,
	PartyCeiling,
	Glazing,
	Door, InternalWall;

	/**
	 * @since 1.0.0
	 * @return true if this is an external area
	 */
	public boolean isExternal() {
		switch (this) {
		case Door:
		case ExternalCeiling:
		case ExternalFloor:
		case ExternalWall:
		case Glazing:
			return true;
		case PartyCeiling:
		case PartyFloor:
		case PartyWall:
		case InternalWall:
		default:
			return false;
		}
	}
}
