package uk.org.cse.nhm.energycalculator.api.types;

import static uk.org.cse.nhm.energycalculator.api.types.AreaType.GlazingMetal;
import static uk.org.cse.nhm.energycalculator.api.types.AreaType.GlazingUPVC;
import static uk.org.cse.nhm.energycalculator.api.types.AreaType.GlazingWood;

/**
 * FrameType.
 *
 * @author richardt
 * @version $Id: FrameType.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public enum FrameType {
    Wood(GlazingWood),
    Metal(GlazingMetal),
    uPVC(GlazingUPVC);

    private AreaType areaType;

    FrameType(AreaType areaType) {
        this.areaType = areaType;
    }

    public AreaType getAreaType() {
        return areaType;
    }
}
