package uk.org.cse.nhm.energycalculator.api.types;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculationResult;

/**
 * Used to accumulate the different kinds of heat loss area, for the energy
 * calculator's {@link IEnergyCalculationResult}
 *
 * @author hinton
 * @since 1.0.0
 */
public enum AreaType {
    ExternalWall,
    PartyWall,
    BasementFloor,
    GroundFloor,
    ExposedUpperFloor,
    PartyFloor,
    ExternalCeiling,
    PartyCeiling,
    GlazingWood,
    GlazingMetal,
    GlazingUPVC,
    DoorSolid,
    DoorGlazed,
    InternalWall;

    /**
     * @since 1.0.0
     * @return true if this is an external area
     */
    public boolean isExternal() {
        switch (this) {
            case DoorGlazed:
            case DoorSolid:
            case ExternalCeiling:
            case BasementFloor:
            case GroundFloor:
            case ExposedUpperFloor:
            case ExternalWall:
            case GlazingMetal:
            case GlazingUPVC:
            case GlazingWood:
                return true;
            case PartyCeiling:
            case PartyFloor:
            case PartyWall:
            case InternalWall:
                return false;
            default:
                throw new UnsupportedOperationException("Unknown if area type is external or not " + this);
        }
    }
}
