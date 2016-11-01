package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.Collections;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;

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
import uk.org.cse.nhm.hom.structure.StructureModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class AverageUValueFunction extends AbstractNamed implements IComponentsFunction<Double> {
	private final IDimension<StructureModel> structure;
	private final Set<AreaType> includedAreas;
	
	@Inject 
	public AverageUValueFunction(
			final IDimension<StructureModel> structure,
			@Assisted final Set<AreaType> includedAreas) {
		this.structure = structure;
		this.includedAreas = includedAreas;
	}

	private static class Visitor implements IEnergyCalculatorVisitor {
		private double totalU = 0;
		private double totalA = 0;
		private final Set<AreaType> includedAreas;
		public Visitor(final Set<AreaType> includedAreas) {
			this.includedAreas = includedAreas;
		}

		@Override
		public void visitHeatingSystem(final IHeatingSystem system, final double proportion) {}
		
		@Override
		public double heatSystemProportion(final IHeatingSystem system) { return 0.0; }

		@Override
		public void visitEnergyTransducer(final IEnergyTransducer transducer) {}

		@Override
		public void visitVentilationSystem(final IVentilationSystem ventilation) {}

		@Override
		public void addWallInfiltration(final double wallArea, final WallConstructionType wallConstructionType, final double airChangeRate) {}

		@Override
		public void addFanInfiltration(final int fans) {}

		@Override
		public void visitFabricElement(final AreaType type, final double area, final double uValue, final Optional<ThermalMassLevel> thermalMassLevel) {
			if (includedAreas.contains(type)) {
				totalA += area;
				totalU += uValue * area;
			}
		}
		
		public double getAverageUValue() {
			return totalU / totalA;
		}

		@Override
		public void addVentInfiltration(int vents) {}

		@Override
		public void addFlueInfiltration() {}

		@Override
		public void addChimneyInfiltration() {}

		@Override
		public double getTotalThermalMass() { return 0; }

		@Override
		public void visitTransparentElement(GlazingType glazingType, WindowInsulationType insulationType,
				double visibleLightTransmittivity, double solarGainTransmissivity, double area, FrameType frameType,
				double frameFactor, double horizontalOrientation, double verticalOrientation,
				OvershadingType overshading) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void addGroundFloorInfiltration(FloorConstructionType floorType) {
			// TODO Auto-generated method stub
			
		}
	}
	
	@Override
	public Double compute(final IComponentsScope scope, final ILets lets) {
		final StructureModel structure = scope.get(this.structure);
		final Visitor v = new Visitor(includedAreas);
		structure.accept(v);
		return v.getAverageUValue();
	}

	@Override
	public Set<IDimension<?>> getDependencies() {
		return ImmutableSet.<IDimension<?>>of(structure);
	}

	@Override
	public Set<DateTime> getChangeDates() {
		return Collections.emptySet();
	}
}
