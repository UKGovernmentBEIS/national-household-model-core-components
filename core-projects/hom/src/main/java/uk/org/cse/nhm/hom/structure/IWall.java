package uk.org.cse.nhm.hom.structure;

import java.util.Set;

import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;
import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;

/**
 * Represents a wall - part of the extrusion of this storey.
 * @author hinton
 *
 */
public interface IWall {
	public double getAirChangeRate();

	public ElevationType getElevationType();
	public boolean isPartyWall();
	
	public double getArea();
	public double getUValue();
	public double getThicknessWithInsulation();
	public double getThicknessWithoutInsulation();
	
	public WallConstructionType getWallConstructionType();

	public double getLength();

	public Set<WallInsulationType> getWallInsulationTypes();

	double getWallInsulationThickness(WallInsulationType type);
}
