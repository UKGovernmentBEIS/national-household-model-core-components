package uk.org.cse.nhm.energycalculator.api.types;

import uk.org.cse.nhm.energycalculator.api.types.AreaType;

import static uk.org.cse.nhm.energycalculator.api.types.AreaType.*;

/**
 * DoorType.
 *
 * @author richardt
 * @version $Id: DoorType.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public enum DoorType {
    Solid(DoorSolid),
    Glazed(DoorGlazed);

    private AreaType areaType;

    DoorType(AreaType areaType) {
        this.areaType = areaType;
    }

    public AreaType getAreaType() {
        return areaType;
    }
}
