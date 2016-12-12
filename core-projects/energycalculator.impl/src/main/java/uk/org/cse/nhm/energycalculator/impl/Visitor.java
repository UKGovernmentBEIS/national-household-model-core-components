package uk.org.cse.nhm.energycalculator.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.common.base.Optional;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorParameters;
import uk.org.cse.nhm.energycalculator.api.IEnergyCalculatorVisitor;
import uk.org.cse.nhm.energycalculator.api.IEnergyTransducer;
import uk.org.cse.nhm.energycalculator.api.IHeatingSystem;
import uk.org.cse.nhm.energycalculator.api.IVentilationSystem;
import uk.org.cse.nhm.energycalculator.api.ThermalMassLevel;
import uk.org.cse.nhm.energycalculator.api.types.AreaType;
import uk.org.cse.nhm.energycalculator.api.types.EnergyType;
import uk.org.cse.nhm.energycalculator.api.types.FloorConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.FloorType;
import uk.org.cse.nhm.energycalculator.api.types.FrameType;
import uk.org.cse.nhm.energycalculator.api.types.GlazingType;
import uk.org.cse.nhm.energycalculator.api.types.OvershadingType;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.RoofType;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WindowInsulationType;
import uk.org.cse.nhm.energycalculator.impl.demands.LightingDemand09;
import uk.org.cse.nhm.energycalculator.impl.gains.SolarGainsSource;

/**
 * Helper class for the calculator, which implements the visitor interface and connects up default transducers.
 * <p>
 * It has various public fields, which is normally evil, but it should only be constructed by {@link EnergyCalculatorCalculator},
 * and only be used by same in a tightly controlled way. If you find yourself using it anywhere else, it is time to refactor it.
 * <p>
 * The main jobs it does are:
 *
 * <ol>
 * 	<li>Collecting all the {@link IEnergyTransducer}s into {@link #transducers}, and adding a {@link SolarGainsSource}, {@link GainLoadRatioAdjuster}, and {@link LightingDemand09}</li>
 * 	<li>Collecting all the {@link IHeatingSystem}s into {@link #heatingSystems}</li>
 *  <li>Collecting all the {@link IVentilationSystem}s into {@link #ventilationSystems}</li>
 *  <li>Accumulating the specific heat loss, external area and thermal mass into {@link #totalFabricHeatLoss}, {@link #totalExternalArea}, and {@link #totalThermalMass}</li>
 *  <li>Passing ventilation information to {@link #infiltration}, the {@link IStructuralInfiltrationAccumulator}</li>
 *  <li>Passing lighting information from the visitor to {@link #lightingDemand} and {@link #solarGains}</li>
 * </ol>
 *
 * @author hinton
 *
 */
abstract class Visitor implements IEnergyCalculatorVisitor {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Visitor.class);

	public final IStructuralInfiltrationAccumulator infiltration;

	public final List<IEnergyTransducer> transducers;
	public final List<IHeatingSystem> heatingSystems = new ArrayList<IHeatingSystem>();
	public final Map<IHeatingSystem, Double> proportions = new HashMap<>();
	public final List<IVentilationSystem> ventilationSystems = new ArrayList<IVentilationSystem>();

	public final double[][] areasByType = new double[2][AreaType.values().length];

	public double totalFabricHeatLoss, totalExternalArea;

	public double[] thermalMassAreas = new double[ThermalMassLevel.values().length];

	public final GainLoadRatioAdjuster glrAdjuster;

	private final LightingDemand09 lightingDemand;

	private final SolarGainsSource solarGains;

	private RoofConstructionType roofConstructionType;
	private Double roofInsulationThickness;

	private FloorConstructionType groundFloorConstructionType;
	private Double floorInsulationThickness;

	public static Visitor create(final IConstants constants, final IEnergyCalculatorParameters parameters, final int buildYear, final Country country, final List<IEnergyTransducer> defaultTransducers) {
		switch(parameters.getCalculatorType()) {
		case SAP2012:
			return new SAPVisitor(constants, parameters, buildYear, country, defaultTransducers);
		case BREDEM2012:
			return new BREDEMVisitor(constants, parameters, defaultTransducers);
		default:
			throw new UnsupportedOperationException("Unknown calculator type when choosing visitor to construct " + parameters.getCalculatorType());
		}
	}

	protected Visitor(final IConstants constants, final IEnergyCalculatorParameters parameters, final List<IEnergyTransducer> defaultTransducers) {
		this.solarGains = new SolarGainsSource(constants, EnergyType.GainsSOLAR_GAINS);
		this.glrAdjuster = new GainLoadRatioAdjuster();
		this.lightingDemand = new LightingDemand09(constants);
		this.transducers = new ArrayList<IEnergyTransducer>(defaultTransducers.size() + 15);
		this.transducers.addAll(defaultTransducers);
		this.infiltration = new StructuralInfiltrationAccumulator(constants);
		transducers.add(solarGains);
		transducers.add(glrAdjuster);
		transducers.add(lightingDemand);
	}

	@Override
	public void visitHeatingSystem(final IHeatingSystem system, final double proportion) {
		heatingSystems.add(system);
		proportions.put(system, proportion);
	}

	@Override
	public double heatSystemProportion(final IHeatingSystem system) {
		if (proportions.containsKey(system)) {
			return proportions.get(system);

		} else {
			throw new RuntimeException("No proportion found for system " + system.toString());
		}
	}

	@Override
	public void visitEnergyTransducer(final IEnergyTransducer transducer) {
		transducers.add(transducer);
	}

	@Override
	public void visitVentilationSystem(final IVentilationSystem ventilation) {
		ventilationSystems.add(ventilation);
	}

	@Override
	public void visitWall(
			final WallConstructionType constructionType,
			final double externalOrExternalInsulationThickness,
			final boolean hasCavityInsulation,
			final double area,
			final double uValue,
			final double thickness,
			final Optional<ThermalMassLevel> thermalMassLevel) {

		log.debug("VISIT Wall, {}, {}, {}, {}, {}", constructionType, area, uValue, thermalMassLevel);

		final AreaType areaType = constructionType.getWallType().getAreaType();

		if (thermalMassLevel.isPresent()) {
			thermalMassAreas[thermalMassLevel.get().ordinal()] += area;
		}

		visitArea(
				areaType,
				area,
				overrideWallUValue(
						uValue,
						constructionType,
						externalOrExternalInsulationThickness,
						hasCavityInsulation,
						thickness
					));
	}

	abstract protected double overrideWallUValue(final double uValue, final WallConstructionType constructionType, final double externalOrInternalInsulationThickness, final boolean hasCavityInsulation, final double thickness);

	@Override
	public void visitDoor(final double area, final double uValue) {
		log.debug("VISIT Door, {}, {}", area, uValue);

		visitArea(AreaType.Door, area, overrideDoorUValue(uValue));
	}

	protected abstract double overrideDoorUValue(double uValue);

	@Override
	public void setRoofType(final RoofConstructionType constructionType, final double insulationThickness) {
		this.roofConstructionType = constructionType;
		this.roofInsulationThickness = insulationThickness;
	}

	@Override
	public void visitCeiling(final RoofType type, final double area, final double uValue) {
		log.debug("VISIT {}, {}, {}, {}, {}", type, area, uValue, roofConstructionType, roofInsulationThickness);

		if (roofConstructionType == null || roofInsulationThickness == null) {
			throw new RuntimeException("setRoofType must be called before visitCeiling");
		}

		visitArea(
				type.getAreaType(),
				area,
				overrideRoofUValue(uValue, type, roofConstructionType, roofInsulationThickness));
	}

	protected abstract double overrideRoofUValue(double uValue, RoofType type, RoofConstructionType constructionType,
			double insulationThickness);

	@Override
	public void visitWindow(
			final double area,
			final double uValue,
			final FrameType frameType,
			final GlazingType glazingType,
			final WindowInsulationType insulationType
			) {
		log.debug("VISIT Window, {}, {}, {}, {}, {}", area, uValue, frameType, glazingType, insulationType);

		visitArea(
				AreaType.Glazing,
				area,
				overrideWindowUValue(uValue, frameType, glazingType, insulationType)
			);
	}

	protected abstract double overrideWindowUValue(final double uValue, final FrameType frameType, final GlazingType glazingType,
			final WindowInsulationType insulationType);

	@Override
	public void setFloorType(final FloorConstructionType groundFloorConstructionType, final double insulationThickness) {
		this.groundFloorConstructionType = groundFloorConstructionType;
		this.floorInsulationThickness = insulationThickness;
	}

	@Override
	public void visitFloor(final FloorType type, final boolean isGroundFloor, final double area, final double uValue, final double exposedPerimeter, final double wallThickness) {
		log.debug("VISIT {}, {}, {}, {}, {}", type, area, uValue, groundFloorConstructionType, floorInsulationThickness);

		if (isGroundFloor && (groundFloorConstructionType == null || floorInsulationThickness == null)) {
			throw new RuntimeException("setGroundFloorType must be called before calling visitFloor with isGroundFloor = true");
		}

		visitArea(
				type.getAreaType(),
				area,
				overrideFloorUValue(
						type,
						isGroundFloor,
						area,
						uValue,
						exposedPerimeter,
						wallThickness,
						groundFloorConstructionType,
						floorInsulationThickness));
	}

	protected abstract double overrideFloorUValue(
			final FloorType type,
			final boolean isGroundFloor,
			final double area,
			final double uValue,
			final double exposedPerimeter,
			final double wallThickness,
			final FloorConstructionType groundFloorConstructionType,
			final double groundFloorInsulationThickness);

	public void visitArea(final AreaType type, final double area, final double uValue) {
		assert !(Double.isNaN(uValue) || Double.isInfinite(uValue)) : "Infinite or NaN u-value";
		if (type.isExternal()) {
			totalExternalArea += area;
		}

		final double fabricLoss = area * uValue;

		totalFabricHeatLoss += fabricLoss;
		areasByType[0][type.ordinal()] += area;
		areasByType[1][type.ordinal()] += fabricLoss;
	}

	@Override
	public void addFanInfiltration(final int fans) {
		infiltration.addFanInfiltration(fans);
	}

	@Override
	public void addGroundFloorInfiltration(final FloorConstructionType floorType) {
		final double airChangeRate;
		switch(floorType) {
		case SuspendedTimberUnsealed:
			airChangeRate = 0.2;
			break;
		case SuspendedTimberSealed:
			airChangeRate = 0.1;
			break;
		case Solid:
		default:
			return;
		}
		infiltration.addFloorInfiltration(airChangeRate);
	}

	@Override
	public void addVentInfiltration(final int vents) {
		infiltration.addVentInfiltration(vents);
	}

	@Override
	public void addFlueInfiltration() {
		infiltration.addFlueInfiltration();
	}

	@Override
	public void addChimneyInfiltration() {
		infiltration.addChimneyInfiltration();
	}

	@Override
	public void addWallInfiltration(final double wallArea, final WallConstructionType wallType, final double airChangeRate) {
		infiltration.addWallInfiltration(
				wallArea,
				overrideAirChangeRate(
						wallType,
						airChangeRate
				)
		);
	}

	protected abstract double overrideAirChangeRate(final WallConstructionType wallType, final double airChangeRate);

	@Override
	public final void visitTransparentElement(
			final GlazingType glazingType,
			final WindowInsulationType insulationType,
			final double visibleLightTransmittivity,
			final double solarGainTransmissivity,
			final double area,
			final FrameType frameType,
			final double frameFactor,
			final double horizontalOrientation,
			final double verticalOrientation,
			final OvershadingType overshading
			) {

		final double usefulArea = overrideFrameFactor(frameType, frameFactor) * area;
		final double totalVisibleLightTransmittivity = overrideVisibleLightTransmittivity(glazingType, visibleLightTransmittivity) * usefulArea;
		final double totalSolarGainTransmissivity = overrideSolarGainTransmissivity(glazingType, insulationType, solarGainTransmissivity) * usefulArea;

		solarGains.addTransparentElement(
				totalVisibleLightTransmittivity,
				totalSolarGainTransmissivity,
				horizontalOrientation,
				verticalOrientation,
				overshading
			);

		lightingDemand.addTransparentElement(
				totalVisibleLightTransmittivity,
				totalSolarGainTransmissivity,
				horizontalOrientation,
				verticalOrientation,
				overshading
			);
	}

	protected abstract double overrideFrameFactor(final FrameType frameType, final double frameFactor);
	protected abstract double overrideVisibleLightTransmittivity(final GlazingType glazingType, final double visibleLightTransmittivity);
	protected abstract double overrideSolarGainTransmissivity(final GlazingType glazingType, final WindowInsulationType insulationType, final double solarGainTransmissivity);

    public double getBestThermalMassParameter() {
		/*
		BEISDOC
		NAME: Thermal Mass
		DESCRIPTION: Choose the thermal mass parameter based on which level has the largest wall area.
		TYPE: lookup
		UNIT: kJ/m^2.℃
		SAP: Table 1f
		BREDEM: 4A
		DEPS: thermal-mass-level,wall-thermal-mass-category
		ID: thermal-mass
		CODSIEB
		*/
		double highestArea = 0;
		ThermalMassLevel level = ThermalMassLevel.MEDIUM;
		for (int i = 0; i < thermalMassAreas.length; i++) {
			if (thermalMassAreas[i] > highestArea) {
				highestArea = thermalMassAreas[i];
				level = ThermalMassLevel.values()[i];
			}
		}

		return level.getThermalMassParameter();
	}

	@Override
	public String toString() {
		return this.getClass().getSimpleName() + " [totalSpecificHeatLoss=" + totalFabricHeatLoss + ", totalExternalArea=" + totalExternalArea + ", totalThermalMass="
				+ getBestThermalMassParameter() + "]";
	}
}
