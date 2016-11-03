package uk.org.cse.nhm.energycalculator.impl;

import java.util.List;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.FloorType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.RoofType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;

public class BREDEMVisitor extends Visitor {

	public BREDEMVisitor(final IConstants constants, final IEnergyCalculatorParameters parameters,
			final List<IEnergyTransducer> defaultTransducers) {
		super(constants, parameters, defaultTransducers);
	}

	@Override
	protected double overrideAirChangeRate(final WallConstructionType wallType, final double airChangeRate) {
		return airChangeRate;
	}

	@Override
	protected double overrideFrameFactor(final FrameType frameType, final double frameFactor) {
		return frameFactor;
	}

	@Override
	protected double overrideVisibleLightTransmittivity(final GlazingType glazingType, final double visibleLightTransmittivity) {
		return visibleLightTransmittivity;
	}

	@Override
	protected double overrideSolarGainTransmissivity(final GlazingType glazingType, final WindowInsulationType insulationType, final double solarGainTransmissivity) {
		return solarGainTransmissivity;
	}

	@Override
	protected double overrideWallUValue(final double uValue, final WallConstructionType constructionType,
			final double externalOrInternalInsulationThickness, final boolean hasCavityInsulation, final double thickness) {
		return uValue;
	}

	@Override
	protected double overrideDoorUValue(final double uValue) {
		return uValue;
	}

	@Override
	protected double overrideRoofUValue(final double uValue, final RoofType type, final RoofConstructionType constructionType,
			final double insulationThickness) {
		return uValue;
	}

	@Override
	protected double overrideWindowUValue(final double uValue, final FrameType frameType, final GlazingType glazingType,
			final WindowInsulationType insulationType) {
		return uValue;
	}

	@Override
	protected double overrideFloorUValue(final double uValue, final FloorType type, final boolean isGroundFloor, final double area,
			final double exposedPerimeter, final FloorConstructionType groundFloorConstructionType,
			final double groundFloorInsulationThickness, final double wallThickness) {
		return uValue;
	}
}
