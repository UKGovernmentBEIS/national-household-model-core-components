package uk.org.cse.nhm.hom.components.fabric.types;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * FloorLocationType, as specified in CARS conversion document.
 * 
 * @author richardt
 * @version $Id: FloorLocationType.java 94 2010-09-30 15:39:21Z richardt
 * @since 0.0.1-SNAPSHOT
 */
public enum FloorLocationType {
    BASEMENT(0),
    GROUND(1),
    FIRST_FLOOR(2),
    SECOND_FLOOR(3),
    HIGHER_FLOOR(FloorLocationType.HIGHESTNORMALLEVEL),
    TOP_FLOOR(99),
    ROOM_IN_ROOF(100);

    private int level;
    public static final int HIGHESTNORMALLEVEL = 4;

    private FloorLocationType(final int level) {
        this.level = level;
    }

    /**
     * Return the level.
     * 
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * TODO.
     * 
     * @param currentLevel
     * @return
     * @since 0.0.1-SNAPSHOT
     */
    public static final FloorLocationType getNextLevel(FloorLocationType currentLevel) {
        final List<FloorLocationType> types = Arrays.asList(FloorLocationType.values());
        Collections.sort(types);

        if (currentLevel == null) {
            currentLevel = types.get(0);
        }

        if ((currentLevel.getLevel() + 1) > HIGHESTNORMALLEVEL) {
            return currentLevel;
        } else {
            return types.get(currentLevel.getLevel() + 1);
        }
    }

	public boolean isInContactWithGround() {
		return this == BASEMENT || this == GROUND;
	}
}
