package uk.org.cse.nhm.simulation.measure.insulation;

import java.util.EnumSet;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IVentilationSystem;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;

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
	public void visitTransparentElement(double visibleLightTransmittivity,
			double solarGainTransmissivity, double horizontalOrientation,
			double verticalOrientation, OvershadingType overshading) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addWallInfiltration(double wallArea, double airChangeRate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFanInfiltration(int fans) {
		// TODO Auto-generated method stub

	}

	@Override
	public void addFloorInfiltration(double floorArea, double airChangeRate) {
		// TODO Auto-generated method stub

	}

	@Override
	public void visitFabricElement(AreaType type, double area, double uValue,
			double kValue) {
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

}
