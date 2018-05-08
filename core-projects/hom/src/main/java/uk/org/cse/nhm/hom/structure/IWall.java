package uk.org.cse.nhm.hom.structure;

import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.ThermalMassLevel;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.components.fabric.types.ElevationType;

/**
 * Represents a wall - part of the extrusion of this storey.
 *
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

    public Optional<ThermalMassLevel> getThermalMassLevel();

    public WallConstructionType getWallConstructionType();

    public double getLength();

    public Set<WallInsulationType> getWallInsulationTypes();

    public boolean hasWallInsulation(WallInsulationType type);

    double getWallInsulationThickness(WallInsulationType type);

    /**
     * @return The sum of the wall insulation thickness for the given types.
     */
    double getWallInsulationThickness(Set<WallInsulationType> types);
}
