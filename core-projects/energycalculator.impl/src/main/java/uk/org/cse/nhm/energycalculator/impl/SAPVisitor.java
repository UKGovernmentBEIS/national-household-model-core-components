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

	private static final double _check(final double d) {
		assert !(Double.isNaN(d) || Double.isInfinite(d)) : "SAP table returned NaN or infinite";
		return d;
	}

	@Override
	protected double overrideAirChangeRate(final WallConstructionType wallType, final double airChangeRate) {
		if (wallType == WallConstructionType.TimberFrame || wallType == WallConstructionType.MetalFrame) {
			return _check(steelOrTimberFrameInfiltration);
		} else {
			return _check(otherWallInfiltration);
		}
	}

	@Override
	protected double overrideFrameFactor(final FrameType frameType, final double frameFactor) {
		/*
		BEISDOC
		NAME: Frame Factor
		DESCRIPTION: The proportion of a door or window which is glazed.
		TYPE: Lookup
		UNIT: Dimensionless
		SAP: Table 6c
                SAP_COMPLIANT: SAP mode only
		BREDEM: Table 2
                BREDEM_COMPLIANT: N/A - value from stock
		DEPS: frame-type
		SET: action.reset-glazing, measure.install-glazing
		STOCK: Imputation schema (windows)
		NOTES: In SAP 2012 mode, values for the frame-factor set in the scenario will be overridden by the SAP table lookup.
		ID: frame-factor
		CODSIEB
		*/
		switch(frameType) {
		case Metal:
			return _check(0.8);
		case uPVC:
		case Wood:
			return _check(0.7);
		default:
			throw new IllegalArgumentException("Unknown frame type while computing frame factor " + frameType);
		}
	}

	@Override
	protected double overrideVisibleLightTransmittivity(final GlazingType glazingType,
			final double visibleLightTransmittivity) {
		/*
		BEISDOC
		NAME: Solar Light Transmittivity
		DESCRIPTION: Visible light transmittance factor of the glazing at normal incidence
		TYPE: Lookup
		UNIT: Dimensionless
		SAP: Table 6b (light transmittance column)
                SAP_COMPLIANT: SAP mode only
		BREDEM: Table 1
                BREDEM_COMPLAINT: N/A - value from stock
		DEPS: glazing-type
		SET: action.reset-glazing, measure.install-glazing
		STOCK: Imputation schema (windows)
		NOTES: In SAP 2012 mode, values for the visible light transmissivity set in the scenario will be overridden by the SAP table lookup.
		ID: light-transmittance-factor
		CODSIEB
		*/
		switch (glazingType) {
		case Single:
			return _check(0.9);
		case Secondary:
		case Double:
			return _check(0.8);
		case Triple:
			return _check(0.7);
		default:
			throw new IllegalArgumentException("Unknown glazing type while computing light transmittance factor " + glazingType);
		}
	}

	@Override
	protected double overrideSolarGainTransmissivity(final GlazingType glazingType, final WindowInsulationType insulationType,
			final double solarGainTransmissivity) {
		/*
		BEISDOC
		NAME: Solar Gain Transmissivity
		DESCRIPTION: Solar energy transmittance factor of the glazing at normal incidence.
		TYPE: Lookup
		UNIT: Dimensionless
		SAP: Table 6b (solar energy column)
                SAP_COMPLIANT: SAP mode only
		BREDEM: Table 24
                BREDEM_COMPLAINT: N/A - value from stock
		DEPS: glazing-type,glazing-insulation-type
		SET: action.reset-glazing, measure.install-glazing
		STOCK: Imputation schema (windows)
		NOTES: In SAP 2012 mode, values for the gains transmissivity set in the scenario will be overridden by the SAP table lookup.
		ID: solar-gain-transmissivity
		CODSIEB
		*/
		switch (glazingType) {
		case Single:
			return _check(0.85);
		case Secondary:
			return _check(0.76);
		case Double:
			switch (insulationType) {
			case Air:
				return _check(0.76);
			case LowEHardCoat:
				return _check(0.72);
			case LowESoftCoat:
				return _check(0.63);
			default:
				throw new IllegalArgumentException("Unknown window insulation type while computing solar gains tranmissivity " + insulationType);
			}

		case Triple:
			switch (insulationType) {
			case Air:
				return _check(0.68);
			case LowEHardCoat:
				return _check(0.64);
			case LowESoftCoat:
				return _check(0.57);
			default:
				throw new IllegalArgumentException("Unknown window insulation type while computing solar gains tranmissivity " + insulationType);
			}

		default:
			throw new IllegalArgumentException("Unknown glazing type while computing solar gains tranmissivity " + glazingType);
		}
	}

	@Override
	protected double overrideWallUValue(final double uValue, final WallConstructionType constructionType, final double externalOrInternalInsulationThickness, final boolean hasCavityInsulation, final double thickness) {
		return _check(SAPUValues.Walls.get(
				country,
				constructionType,
				externalOrInternalInsulationThickness,
				hasCavityInsulation,
				ageBand,
				thickness
			));
	}

	@Override
	protected double overrideDoorUValue(final double uValue) {
		return _check(SAPUValues.Doors.getOutside(ageBand, country));
	}

	@Override
	protected double overrideRoofUValue(final double uValue, final RoofType type, final RoofConstructionType constructionType,
			final double insulationThickness) {
		return _check(SAPUValues.Roofs.get(type, constructionType, insulationThickness, country, ageBand));
	}

	@Override
	protected double overrideWindowUValue(final double uValue, final FrameType frameType, final GlazingType glazingType,
			final WindowInsulationType insulationType) {
		return _check(SAPUValues.Windows.get(frameType, glazingType, insulationType));
	}

	@Override
	protected double overrideFloorUValue(
			final FloorType type,
			final boolean isGroundFloor,
			final double area,
			final double uValue,
			final double exposedPerimeter,
			final double wallThickness,
			final FloorConstructionType groundFloorConstructionType,
			final double groundFloorInsulationThickness
			) {

		return _check(SAPUValues.Floors.get(type, isGroundFloor, area, exposedPerimeter, wallThickness, groundFloorConstructionType, groundFloorInsulationThickness, ageBand, country));
	}
}
