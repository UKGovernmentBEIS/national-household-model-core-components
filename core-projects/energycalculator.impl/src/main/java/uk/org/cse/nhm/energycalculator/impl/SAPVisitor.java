package uk.org.cse.nhm.energycalculator.impl;

import java.util.List;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;

public class SAPVisitor extends Visitor {
	final double steelOrTimberFrameInfiltration = 0.25;
	final double otherWallInfiltration = 0.35;
	
	private final Country country;
	private final Band ageBand;

	protected SAPVisitor(
			final IConstants constants,
			final IEnergyCalculatorParameters parameters,
			final int buildYear,
			final Country country,
			final List<IEnergyTransducer> defaultTransducers
			) {
		super(constants, parameters, defaultTransducers);
		ageBand = SAPAgeBandValue.fromYear(buildYear, country).getName();
		this.country = country;
	}

	@Override
	protected double overrideAirChangeRate(WallConstructionType wallType, double airChangeRate) {
		if (wallType == WallConstructionType.TimberFrame || wallType == WallConstructionType.MetalFrame) {
			return steelOrTimberFrameInfiltration;
		} else {
			return otherWallInfiltration;
		}
	}

	@Override
	protected double overrideFrameFactor(FrameType frameType, double frameFactor) {
		/*
		BEISDOC
		NAME: Frame Factor
		DESCRIPTION: The proportion of a door or window which is glazed.
		TYPE: Lookup
		UNIT: Dimensionless
		SAP: Table 6c
		BREDEM: Table 2 
		DEPS: frame-type
		SET: action.reset-glazing, measure.install-glazing
		STOCK: Imputation schema (windows)
		NOTES: In SAP 2012 mode, values for the frame-factor set in the scenario will be overridden by the SAP table lookup.
		ID: frame-factor
		CODSIEB
		*/
		switch(frameType) {
		case Metal:
			return 0.8;
		case uPVC:
		case Wood:
			return 0.7;
		default:
			throw new IllegalArgumentException("Unknown frame type while computing frame factor " + frameType);
		}
	}

	@Override
	protected double overrideVisibleLightTransmittivity(GlazingType glazingType,
			double visibleLightTransmittivity) {
		/*
		BEISDOC
		NAME: Soalr Light Transmittivity
		DESCRIPTION: Visible light transmittance factor of the glazing at normal incidence
		TYPE: Lookup
		UNIT: Dimensionless
		SAP: Table 6b (light transmittance column)
		BREDEM: Table 1
		DEPS: glazing-type
		SET: action.reset-glazing, measure.install-glazing
		STOCK: Imputation schema (windows)
		NOTES: In SAP 2012 mode, values for the visible light transmissivity set in the scenario will be overridden by the SAP table lookup.
		ID: light-transmittance-factor
		CODSIEB
		*/
		switch (glazingType) {
		case Single:
			return 0.9;
		case Secondary:
		case Double:
			return 0.8;
		case Triple:
			return 0.7;
		default:
			throw new IllegalArgumentException("Unknown glazing type while computing light transmittance factor " + glazingType);
		}
	}

	@Override
	protected double overrideSolarGainTransmissivity(GlazingType glazingType, WindowInsulationType insulationType,
			double solarGainTransmissivity) {
		/*
		BEISDOC
		NAME: Solar Gain Transmissivity
		DESCRIPTION: Solar energy transmittance factor of the glazing at normal incidence.
		TYPE: Lookup
		UNIT: Dimensionless
		SAP: Table 6b (solar energy column)
		BREDEM: Table 24
		DEPS: glazing-type,glazing-insulation-type
		SET: action.reset-glazing, measure.install-glazing
		STOCK: Imputation schema (windows)
		NOTES: In SAP 2012 mode, values for the gains transmissivity set in the scenario will be overridden by the SAP table lookup.
		ID: solar-gain-transmissivity 
		CODSIEB
		*/
		switch (glazingType) {
		case Single:
			return 0.85;
		case Secondary:
			return 0.76;
		case Double:
			switch (insulationType) {
			case Air:
				return 0.76;
			case LowEHardCoat:
				return 0.72;
			case LowESoftCoat:
				return 0.63;
			default:
				throw new IllegalArgumentException("Unknown window insulation type while computing solar gains tranmissivity " + insulationType);	
			}
			
		case Triple:
			switch (insulationType) {
			case Air:
				return 0.68;
			case LowEHardCoat:
				return 0.64;
			case LowESoftCoat:
				return 0.57;
			default:
				throw new IllegalArgumentException("Unknown window insulation type while computing solar gains tranmissivity " + insulationType);	
			}
			
		default:
			throw new IllegalArgumentException("Unknown glazing type while computing solar gains tranmissivity " + glazingType);
		}	
	}

	@Override
	protected double overrideWallUValue(WallConstructionType constructionType, final double externalOrInternalInsulationThickness, final boolean hasCavityInsulation, double uValue) {
		return SAPUValues.Walls.get(
				country,
				constructionType,
				externalOrInternalInsulationThickness,
				hasCavityInsulation,
				ageBand
			);
	}

	@Override
	protected double overrideDoorUValue(double uValue) {
		return SAPUValues.Doors.getOutside(ageBand, country);
	}
}
