package uk.org.cse.nhm.simulation.measure.insulation;

import java.util.EnumSet;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IVentilationSystem;
import uk.org.cse.nhm.energycalculator.api.ThermalMassLevel;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.DoorType;
import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.RoofType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WindowGlazingAirGap;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;

public class AreaAccumulator implements IEnergyCalculatorVisitor {

	private final EnumSet<AreaType> areaTypes;
	private double totalArea = 0;

	public AreaAccumulator(final EnumSet<AreaType> areaTypes) {
		this.areaTypes = areaTypes;
	}

	@Override
	public void visitHeatingSystem(final IHeatingSystem system, final double proportion) {
		// Noop

	}

	@Override
	public double heatSystemProportion(final IHeatingSystem system) {
		// Noop
		return 0.0;
	}

	@Override
	public void visitEnergyTransducer(final IEnergyTransducer transducer) {
		// Noop

	}

	@Override
	public void visitVentilationSystem(final IVentilationSystem ventilation) {
		// Noop

	}

	@Override
	public void addWallInfiltration(final double wallArea, final WallConstructionType wallConstructionType,final double airChangeRate) {
		// Noop

	}

	@Override
	public void addFanInfiltration(final int fans) {
		// Noop

	}

	public double getTotalArea() {
		return totalArea;
	}

	@Override
	public void addVentInfiltration(final int vents) {
		// Noop

	}

	@Override
	public void addFlueInfiltration() {
		// Noop

	}

	@Override
	public void addChimneyInfiltration() {
		// Noop

	}

	@Override
	public void visitTransparentElement(final GlazingType glazingType, final WindowInsulationType insulationType,
			final double visibleLightTransmittivity, final double solarGainTransmissivity, final double area, final FrameType frameType,
			final double frameFactor, final double horizontalOrientation, final double verticalOrientation, final OvershadingType overshading) {
		// Noop

	}

	@Override
	public void addGroundFloorInfiltration(final FloorConstructionType floorType) {
		// Noop
	}

	@Override
	public void visitWall(final WallConstructionType constructionType,
			final double externalOrExternalInsulationThickness, final boolean hasCavityInsulation, final double area, final double uValue, final double thickness,
			final Optional<ThermalMassLevel> thermalMassLevel) {

		if(this.areaTypes.contains(constructionType.getWallType().getAreaType())) {
			this.totalArea += area;
		}
	}

	@Override
	public void visitDoor(final DoorType doorType, final double area, final double uValue) {
		if (this.areaTypes.contains(doorType.getAreaType())) {
			this.totalArea += area;
		}
	}

	@Override
	public void setRoofType(final RoofConstructionType constructionType, final double insulationThickness) {
		// Noop
	}

	@Override
	public void visitCeiling(final RoofType type, final double area, final double uValue) {
		if (this.areaTypes.contains(type.getAreaType())) {
			this.totalArea += area;
		}
	}

	@Override
	public void visitWindow(final double area, final double uValue, final FrameType frameType, final GlazingType glazingType,
			final WindowInsulationType insulationType, final WindowGlazingAirGap airGap) {
		if (this.areaTypes.contains(frameType.getAreaType())) {
			this.totalArea += area;
		}
	}

	@Override
	public void setFloorType(final FloorConstructionType groundFloorConstructionType, final double insulationThickness) {
		// Noop
	}

	@Override
	public void visitFloor(final AreaType type, final double area, final double uValue, final double exposedPerimeter, final double wallThickness) {
		if (this.areaTypes.contains(type)) {
			this.totalArea += area;
		}
	}

    /**
     * @param name
     * @param proportion
     * @param efficiency
     * @param splitRate
     * @see uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor#visitLight(java.lang.String, double, double, double[])
     */
    @Override
    public void visitLight(String name, double proportion, double efficiency, double[] splitRate) {
        // Noop
    }
}
