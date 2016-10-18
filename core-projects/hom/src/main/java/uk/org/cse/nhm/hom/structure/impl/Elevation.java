package uk.org.cse.nhm.hom.structure.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.hom.structure.Door;
import uk.org.cse.nhm.hom.structure.Glazing;
import uk.org.cse.nhm.hom.structure.IElevation;

/**
 * Represents an elevation, including information about its glazing types, doors and so on.
 * May need tidying up
 * 
 * @author hinton
 *
 */
@AutoProperty
public class Elevation implements IElevation {
	/**
	 * This is the default vertical orientation of an elevation (it is assumed to be at 90 degrees to the ground).
	 * TODO roof windows should be different in some way.
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
	
	private double openingProportion;
	
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
			final double glazingArea = (wallArea * openingProportion - doorArea) * glazing.getGlazedProportion();
			
			visitor.visitFabricElement(AreaType.Glazing, glazingArea, glazing.getuValue(), 0);
			visitor.visitTransparentElement(
					glazing.getLightTransmissionFactor() * glazing.getFrameFactor() * glazingArea,
					glazing.getGainsTransmissionFactor() * glazing.getFrameFactor() * glazingArea, 
					ANGLE_FROM_HORIZONTAL, 
					angleFromNorth, 
					overshading);
			
			glazedArea += glazingArea;
		}
		
		return glazedArea;
	}
	
	/**
	 * A utility interface for keeping track of doors that have already been visited in an elevation,
	 * without adding state information to the elevation for that purpose
	 * @author hinton
	 *
	 */
	public interface IDoorVisitor {
		/**
		 * Add as many doors as possible into the given area, returning the total area of doors added
		 * @param visitor
		 * @param area
		 * @return
		 */
		public double visitDoors(final IEnergyCalculatorVisitor visitor, final double area);

		boolean hasMoreDoors();
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
		private double totalDoorHeatLossArea = 0;
		private double remainingDoorArea;
		public CHMDoorVisitor() {
			for (final Door d : doors) {
				totalDoorArea += d.getArea();
				totalDoorHeatLossArea += d.getArea() * d.getuValue();
			}
			remainingDoorArea = totalDoorArea;
		}
		@Override
		public double visitDoors(final IEnergyCalculatorVisitor visitor, final double wallArea) {
			if (!hasMoreDoors()) return 0;
			final double openingArea = openingProportion * wallArea;
			final double doorArea = Math.min(openingArea, remainingDoorArea);
			visitor.visitFabricElement(AreaType.Door, 
					doorArea,
					(totalDoorHeatLossArea / totalDoorArea), 0);
			remainingDoorArea -= doorArea;
			return doorArea;
		}
		
		@Override
		public boolean hasMoreDoors() {
			return remainingDoorArea > 0;
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
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		final Elevation other = (Elevation) obj;
		if (Double.doubleToLongBits(angleFromNorth) != Double
				.doubleToLongBits(other.angleFromNorth))
			return false;
		if (doors == null) {
			if (other.doors != null)
				return false;
		} else if (!doors.equals(other.doors))
			return false;
		if (glazings == null) {
			if (other.glazings != null)
				return false;
		} else if (!glazings.equals(other.glazings))
			return false;
		if (Double.doubleToLongBits(openingProportion) != Double
				.doubleToLongBits(other.openingProportion))
			return false;
		if (overshading != other.overshading)
			return false;
		return true;
	}
}
