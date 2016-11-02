package uk.org.cse.nhm.energycalculator.impl;

import java.util.List;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;

public class BREDEMVisitor extends Visitor {

	public BREDEMVisitor(IConstants constants, IEnergyCalculatorParameters parameters,
			List<IEnergyTransducer> defaultTransducers) {
		super(constants, parameters, defaultTransducers);
	}

	@Override
	protected double overrideAirChangeRate(WallConstructionType wallType, double airChangeRate) {
		return airChangeRate;
	}

	@Override
	protected double overrideFrameFactor(FrameType frameType, double frameFactor) {
		return frameFactor;
	}

	@Override
	protected double overrideVisibleLightTransmittivity(GlazingType glazingType, double visibleLightTransmittivity) {
		return visibleLightTransmittivity;
	}

	@Override
	protected double overrideSolarGainTransmissivity(GlazingType glazingType, WindowInsulationType insulationType, double solarGainTransmissivity) {
		return solarGainTransmissivity;
	}
	
	@Override
	protected double overrideWallUValue(WallConstructionType constructionType,
			double externalOrInternalInsulationThickness, boolean hasCavityInsulation, double uValue) {
		return uValue;
	}
}
