package uk.org.cse.nhm.hom.structure.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.ThermalMassLevel;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.hom.components.fabric.types.DoorType;
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
	/*
	BEISDOC
	NAME: Glazing angle
	DESCRIPTION: The vertical tilt (where horizontal is 0) of a glazed element. 
	TYPE: value
	UNIT: Radians
	SAP: (U3), S13
	BREDEM: 2.4.1A
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
	BREDEM: Input to Table 18
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
			DESCRIPTION: The area of this type of glazing in this elevation 
			TYPE: Formula
			UNIT: m^2
			SAP: sap
			BREDEM: bredem
			DEPS: elevation-glazed-proportion, opening-proportion, wall-element, door-element
			ID: glazing-area
			CODSIEB
			*/
			final double glazingArea = (wallArea * openingProportion - doorArea) * glazing.getGlazedProportion();
			
			/*
			BEISDOC
			NAME: Glazed element
			DESCRIPTION: The area, u-value and k-value for a glazed area.
			TYPE: formula
			UNIT: area m^2, u-value W/m^2/℃, k-value kJ/m^2/℃
			SAP: (27,27a)
			BREDEM: 3B
			DEPS: glazing-area
			GET: house.u-value
			SET: measure.install-glazing,action.reset-glazing
			STOCK: elevations.csv (glazed doors, percentagedoubleglazed, singleglazedwindowframe), imputation schema (windows, doors)
			ID: glazed-element
			NOTES: Glazing's k-value (thermal mass) is always 0. When setting the u-value, ensure to include the curtain correction factor.
			CODSIEB
			*/
			visitor.visitFabricElement(AreaType.Glazing, glazingArea, glazing.getuValue(), Optional.<ThermalMassLevel>absent());
			
			visitor.visitTransparentElement(
					/*
					BEISDOC
					NAME: Effective light transmission area
					DESCRIPTION: Light transmittance multiplied by frame factor multiplied by area for a transparent element.
					TYPE: Formula
					UNIT: m^2
					SAP: 
					BREDEM: 
					DEPS: light-transmittance-factor, frame-factor, glazing-area
					ID: light-effective-transmission-area
					CODSIEB
					*/
					glazing.getLightTransmissionFactor() * glazing.getFrameFactor() * glazingArea,
					
					/*
					BEISDOC
					NAME: Effective solar gains transmission area
					DESCRIPTION: Solar gains transmittance multiplied by frame factor multiplied by area for a transparent element.
					TYPE: formula
					UNIT: m^2
					SAP: (74-82)
					BREDEM: 5A
					DEPS: gains-transmittance-factor, frame-factor, glazing-area
					ID: solar-gains-effective-transmission-area
					CODSIEB
					*/
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
		/*
		BEISDOC
		NAME: Door element
		DESCRIPTION: The area, u-value and k-value for a door.
		TYPE: formula
		UNIT: area m^2, u-value W/m^2/℃, k-value kJ/m^2/℃
		SAP: (26)
		BREDEM: 3B
		DEPS:
		GET: house.u-value
		SET: action.reset-doors
		STOCK: elevations.csv (doorframe, tenthsopening), imputation schema (doors)
		ID: door-element
		NOTES: Doors are distributed amongst walls based on the opening proportion for this elevation in the stock, as per the CHM method. 
		NOTES: Some doors may be omitted if the total area of doors is greater than the area allowed by this openingProportion.  
		CODSIEB
		*/
		private double totalDoorArea = 0;
		private double totalDoorHeatLossArea = 0;
		
		private final double meanDoorLightTransmissionPerArea;
		private final double meanDoorGainsTransmissionPerArea;
		
		private double remainingDoorArea;
		public CHMDoorVisitor() {
			double totalDoorLightTransmissionArea = 0;
			double totalDoorGainsTransmissionArea = 0;
			
			for (final Door d : doors) {
				totalDoorArea += d.getArea();
				totalDoorHeatLossArea += d.getArea() * d.getuValue();
				
				if (d.getDoorType() == DoorType.Glazed) {
					totalDoorLightTransmissionArea += d.getArea() * d.getFrameFactor() * d.getLightTransmissionFactor();
					totalDoorGainsTransmissionArea += d.getArea() * d.getFrameFactor() * d.getGainsTransmissionFactor();
				}
			}
			remainingDoorArea = totalDoorArea;
			
			meanDoorLightTransmissionPerArea = totalDoorLightTransmissionArea / totalDoorArea;
			meanDoorGainsTransmissionPerArea = totalDoorGainsTransmissionArea / totalDoorArea;
		}
		@Override
		public double visitDoors(final IEnergyCalculatorVisitor visitor, final double wallArea) {
			if (!hasMoreDoors()) return 0;
			final double openingArea = openingProportion * wallArea;
			final double doorArea = Math.min(openingArea, remainingDoorArea);
			
			visitor.visitFabricElement(AreaType.Door, 
					doorArea,
					(totalDoorHeatLossArea / totalDoorArea), 
					Optional.<ThermalMassLevel>absent());
			remainingDoorArea -= doorArea;
			
			visitor.visitTransparentElement(
					doorArea * meanDoorLightTransmissionPerArea,
					doorArea * meanDoorGainsTransmissionPerArea,
					ANGLE_FROM_HORIZONTAL, 
					angleFromNorth,
					overshading
					);

			
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
