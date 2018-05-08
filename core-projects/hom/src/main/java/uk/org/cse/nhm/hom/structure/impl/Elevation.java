package uk.org.cse.nhm.hom.structure.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.api.types.DoorType;
import uk.org.cse.nhm.hom.structure.Door;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.IElevation;

/**
 * Represents an elevation, including information about its glazing types, doors
 * and so on. May need tidying up
 *
 * @author hinton
 *
 */
@AutoProperty
public class Elevation implements IElevation {

    /**
     * This is the default vertical orientation of an elevation (it is assumed
     * to be at 90 degrees to the ground). TODO roof windows should be different
     * in some way.
     */
    /*
	BEISDOC
	NAME: Glazing angle
	DESCRIPTION: The vertical tilt (where horizontal is 0) of a glazed element.
	TYPE: value
	UNIT: Radians
	SAP: (U3), S13
        SAP_COMPLIANT: No, see note
	BREDEM: 2.4.1A
        BREDEM_COMPLIANT: Yes
	NOTES: Glazed elements are assumed to be vertical in SAP.
	NOTES: There is an exception to this for roof windows where tilt is known to be less than 70 degrees. This exception is not implemented in the NHM because there is no information about roof window tilt.
	ID: glazing-angle
	CODSIEB
     */
    private final static double ANGLE_FROM_HORIZONTAL = Math.PI / 2;

    /**
     * This is the direction the elevation is facing.
     */
    private double angleFromNorth;

    /**
     * Holds all the glazing details for this elevation
     */
    private final List<Glazing> glazings = new ArrayList<Glazing>();

    /**
     * Holds all the doors in this elevation.
     */
    private final List<Door> doors = new ArrayList<Door>();

    /*
	BEISDOC
	NAME: Elevation opening proportion
	DESCRIPTION: The proportion of the elevation's area which is windows and doors.
	TYPE: value
	UNIT: Unitless proportion
        SAP_COMPLIANT: N/A - value from stock
        BREDEM_COMPLIANT: N/A - value from stock
	STOCK: elevations.csv (tenthsopening)
	ID: opening-proportion
	CODSIEB
     */
    private double openingProportion;

    /*
	BEISDOC
	NAME: Overshading
	DESCRIPTION: The overshading level for an elevation.
	TYPE: value
	SAP: Input to Table 6d, Table H2
        SAP_COMPLIANT: N/A - no data
	BREDEM: Input to Table 18
        BREDEM_COMPLIANT: N/A - no data
	NOTES: Overshading is always assumed to be average in the NHM.
	ID: overshading
	CODSIEB
     */
    private final OvershadingType overshading = OvershadingType.AVERAGE;

    public Elevation copy() {
        final Elevation other = new Elevation();

        other.setAngleFromNorth(getAngleFromNorth());
        other.setOpeningProportion(getOpeningProportion());

        for (final Glazing g : glazings) {
            other.addGlazing(g.copy());
        }

        for (final Door d : doors) {
            other.addDoor(d.copy());
        }

        return other;
    }

    public void addDoor(final Door door) {
        doors.add(door);
    }

    public void addGlazing(final Glazing g) {
        glazings.add(g);
    }

    public double getAngleFromNorth() {
        return angleFromNorth;
    }

    public void setAngleFromNorth(final double angleFromNorth) {
        this.angleFromNorth = angleFromNorth;
    }

    /**
     * The total proportion of this elevation's area containing doors & windows
     *
     * @return
     */
    public double getOpeningProportion() {
        return openingProportion;
    }

    public void setOpeningProportion(final double openingProportion) {
        this.openingProportion = openingProportion;
    }

    /* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IElevation#visitGlazing(uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor, double)
     */
    @Override
    public double visitGlazing(final IEnergyCalculatorVisitor visitor, final double wallArea, final double doorArea) {
        double glazedArea = 0;

        for (final Glazing glazing : glazings) {
            /*
			BEISDOC
			NAME: Glazing area
			DESCRIPTION: The area of a particular type of glazing in an elevation
			TYPE: Formula
			UNIT: m^2
                        SAP_COMPLIANT: Yes
                        BREDEM_COMPLIANT: Yes
			DEPS: elevation-glazed-proportion, opening-proportion
			ID: glazing-area
			CODSIEB
             */
            final double glazingArea = (wallArea * openingProportion - doorArea) * glazing.getGlazedProportion();

            visitor.visitWindow(glazingArea, glazing.getuValue(), glazing.getFrameType(), glazing.getGlazingType(), glazing.getInsulationType(), glazing.getWindowGlazingAirGap());

            visitor.visitTransparentElement(
                    glazing.getGlazingType(),
                    glazing.getInsulationType(),
                    glazing.getLightTransmissionFactor(),
                    glazing.getGainsTransmissionFactor(),
                    glazingArea,
                    glazing.getFrameType(),
                    glazing.getFrameFactor(),
                    ANGLE_FROM_HORIZONTAL,
                    angleFromNorth,
                    overshading);

            glazedArea += glazingArea;
        }

        return glazedArea;
    }

    /**
     * A utility interface for keeping track of doors that have already been
     * visited in an elevation, without adding state information to the
     * elevation for that purpose
     *
     * @author hinton
     *
     */
    public interface IDoorVisitor {

        /**
         * Record that some area of a wall segment is available for doors, and
         * return how much of that area will be taken up by doors.
         *
         * Call this multiple times. The visitor will internally accumulate how
         * much area is available to it.
         *
         * @param area the available opening area on the wall segment
         * @return
         */
        public double offerPotentialDoorArea(final double area);

        /**
         * Call this last. It will add the doors, scaling them down as needed if
         * they took up too much area.
         */
        public void visitDoors(final IEnergyCalculatorVisitor visitor);
    }

    /* (non-Javadoc)
	 * @see uk.org.cse.nhm.hom.structure.IElevation#getDoorVisitor()
     */
    @Override
    @JsonIgnore
    public IDoorVisitor getDoorVisitor() {
        return new CHMDoorVisitor();
    }

    private class CHMDoorVisitor implements IDoorVisitor {

        private double totalDoorArea = 0;
        private double remainingDoorArea;

        public CHMDoorVisitor() {
            for (final Door d : doors) {
                totalDoorArea += d.getArea();
            }
            remainingDoorArea = totalDoorArea;
        }

        @Override
        public double offerPotentialDoorArea(final double wallArea) {
            if (remainingDoorArea == 0) {
                return 0;
            }

            final double potentialDoorArea = wallArea * openingProportion;
            final double actualDoorArea = Math.min(remainingDoorArea, potentialDoorArea);

            remainingDoorArea -= actualDoorArea;

            return actualDoorArea;
        }

        @Override
        public void visitDoors(final IEnergyCalculatorVisitor visitor) {
            final double doorScaling = totalDoorArea == 0 ? 1 : (totalDoorArea - remainingDoorArea) / totalDoorArea;

            for (final Door d : doors) {
                visitor.visitDoor(
                        d.getDoorType(),
                        d.getArea() * doorScaling,
                        d.getuValue()
                );

                if (d.getDoorType() == DoorType.Glazed) {
                    visitor.visitTransparentElement(
                            d.getGlazingType(),
                            d.getWindowInsulationType(),
                            d.getLightTransmissionFactor(),
                            d.getGainsTransmissionFactor(),
                            d.getArea() * doorScaling,
                            d.getFrameType(),
                            d.getFrameFactor(),
                            ANGLE_FROM_HORIZONTAL,
                            angleFromNorth,
                            overshading
                    );
                }
            }
        }
    }

    public void setGlazings(final List<Glazing> glazings) {
        this.glazings.clear();
        this.glazings.addAll(glazings);
    }

    public List<Glazing> getGlazings() {
        return Collections.unmodifiableList(glazings);
    }

    public List<Door> getDoors() {
        return Collections.unmodifiableList(doors);
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        long temp;
        temp = Double.doubleToLongBits(angleFromNorth);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result + ((doors == null) ? 0 : doors.hashCode());
        result = prime * result
                + ((glazings == null) ? 0 : glazings.hashCode());
        temp = Double.doubleToLongBits(openingProportion);
        result = prime * result + (int) (temp ^ (temp >>> 32));
        result = prime * result
                + ((overshading == null) ? 0 : overshading.hashCode());
        return result;
    }

    @Override
    public boolean equals(final Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Elevation other = (Elevation) obj;
        if (Double.doubleToLongBits(angleFromNorth) != Double
                .doubleToLongBits(other.angleFromNorth)) {
            return false;
        }
        if (doors == null) {
            if (other.doors != null) {
                return false;
            }
        } else if (!doors.equals(other.doors)) {
            return false;
        }
        if (glazings == null) {
            if (other.glazings != null) {
                return false;
            }
        } else if (!glazings.equals(other.glazings)) {
            return false;
        }
        if (Double.doubleToLongBits(openingProportion) != Double
                .doubleToLongBits(other.openingProportion)) {
            return false;
        }
        if (overshading != other.overshading) {
            return false;
        }
        return true;
    }
}
