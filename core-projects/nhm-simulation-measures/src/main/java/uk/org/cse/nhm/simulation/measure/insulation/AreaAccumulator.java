package uk.org.cse.nhm.simulation.measure.insulation;

import java.util.EnumSet;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IVentilationSystem;
import uk.org.cse.nhm.energycalculator.api.ThermalMassLevel;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;

public class AreaAccumulator implements IEnergyCalculatorVisitor {

	private EnumSet<AreaType> areaTypes;
	private double totalArea = 0;

	public AreaAccumulator(EnumSet<AreaType> areaTypes) {
		this.areaTypes = areaTypes;
	}

	@Override
	public void visitHeatingSystem(IHeatingSystem system, double proportion) {
		// TODO Auto-generated method stub

	}
	
	@Override
	public double heatSystemProportion(IHeatingSystem system) {
		// TODO Auto-generated method stub
		return 0.0;
	}

	@Override
	public void visitEnergyTransducer(IEnergyTransducer transducer) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitVentilationSystem(IVentilationSystem ventilation) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addWallInfiltration(double wallArea, final WallConstructionType wallConstructionType,double airChangeRate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFanInfiltration(int fans) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitFabricElement(AreaType type, double area, double uValue,
			Optional<ThermalMassLevel> thermalMassLevel) {
		if(this.areaTypes.contains(type)) {
			this.totalArea += area;
		}
	}

	public double getTotalArea() {
		return totalArea;
	}

	@Override
	public void addVentInfiltration(int vents) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addFlueInfiltration() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addChimneyInfiltration() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public double getTotalThermalMass() { return 0; }

	@Override
	public void visitTransparentElement(GlazingType glazingType, WindowInsulationType insulationType,
			double visibleLightTransmittivity, double solarGainTransmissivity, double area, FrameType frameType,
			double frameFactor, double horizontalOrientation, double verticalOrientation, OvershadingType overshading) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void addGroundFloorInfiltration(FloorConstructionType floorType) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void visitWall(WallConstructionType constructionType,
			double externalOrExternalInsulationThickness, boolean hasCavityInsulation, double area, double uValue,
			Optional<ThermalMassLevel> thermalMassLevel) {
		
		if(this.areaTypes.contains(constructionType.getWallType().getAreaType())) {
			this.totalArea += area;
		}
	}
}
