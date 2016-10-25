package uk.org.cse.stockimport.hom.impl.steps;

import java.util.Set;

import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IVentilationSystem;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.hom.SurveyCase;
import uk.org.cse.stockimport.domain.IBasicDTO;
import uk.org.cse.stockimport.hom.ISurveyCaseBuildStep;
import uk.org.cse.stockimport.hom.impl.steps.imputation.DoorWranglingStep;
import uk.org.cse.stockimport.hom.impl.steps.imputation.MainImputationStep;
import uk.org.cse.stockimport.repository.IHouseCaseSources;

/**
 * @since 1.0
 */
public class InternalWallBuildStep implements ISurveyCaseBuildStep {
	final String IDENTIFIER = InternalWallBuildStep.class.getCanonicalName();
	
	/**
	 * A visitor which collects up all the external areas on the structure model for us.
	 * @author hinton
	 *
	 */
	private static class AreaVisitor  implements IEnergyCalculatorVisitor {
		private double externalArea = 0;

		@Override
		public void visitHeatingSystem(IHeatingSystem system, double proportion) {
			
		}
		
		@Override
		public double heatSystemProportion(IHeatingSystem system) {
			// We don't care about this method for this visitor.
			return 0.0;
		}

		@Override
		public void visitEnergyTransducer(IEnergyTransducer transducer) {
			
		}

		@Override
		public void visitVentilationSystem(IVentilationSystem ventilation) {
			
		}

		@Override
		public void visitTransparentElement(double visibleLightTransmittivity, double solarGainTransmissivity,
				double horizontalOrientation, double verticalOrientation, OvershadingType overshading) {
			
		}

		@Override
		public void addWallInfiltration(double wallArea, double airChangeRate) {
			
		}

		@Override
		public void addFanInfiltration(int fans) {
			
		}

		@Override
		public void addFloorInfiltration(double floorArea, double airChangeRate) {
			
		}

		@SuppressWarnings("incomplete-switch")
		@Override
		public void visitFabricElement(AreaType type, double area, double uValue, double kValue) {
			switch (type) {
			case Door:
			case ExternalWall:
			case Glazing:
			case PartyWall:
				externalArea += area;
				break;
			}
		}
		
		public double getExternalArea() {
			return externalArea;
		}

		@Override
		public void addVentInfiltration(int vents) {}

		@Override
		public void addFlueInfiltration() {}

		@Override
		public void addChimneyInfiltration() {}
	}
	
	@Override
	public String getIdentifier() {
		return IDENTIFIER;
	}

	@Override
	public Set<String> getDependencies() {
		return ImmutableSet.of(StoreyBuildStep.IDENTIFIER, ElevationBuildStep.IDENTIFIER, MainImputationStep.IDENTIFIER, DoorWranglingStep.IDENTIFIER);
	}

	@Override
    /**
     * @assumption Based on CHM: internal wall area is the same as facade area.
     */
	public void build(SurveyCase model, IHouseCaseSources<IBasicDTO> dtoProvider) {
		final AreaVisitor av = new AreaVisitor();
		model.getStructure().accept(av);
		model.getStructure().setInternalWallArea(av.getExternalArea());
	}

}
