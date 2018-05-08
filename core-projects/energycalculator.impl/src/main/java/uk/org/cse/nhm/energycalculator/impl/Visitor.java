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
import uk.org.cse.nhm.energycalculator.api.impl.SimpleLightingTransducer;
import uk.org.cse.nhm.energycalculator.api.types.*;
import uk.org.cse.nhm.energycalculator.api.types.RegionType.Country;
import uk.org.cse.nhm.energycalculator.api.types.SAPAgeBandValue.Band;
import uk.org.cse.nhm.energycalculator.impl.demands.LightingDemand09;
import uk.org.cse.nhm.energycalculator.impl.gains.SolarGainsSource;
import uk.org.cse.nhm.energycalculator.mode.EnergyCalculatorType;

/**
 * Helper class for the calculator, which implements the visitor interface and
 * connects up default transducers.
 * <p>
 * It has various public fields, which is normally evil, but it should only be
 * constructed by {@link EnergyCalculatorCalculator}, and only be used by same
 * in a tightly controlled way. If you find yourself using it anywhere else, it
 * is time to refactor it.
 * <p>
 * The main jobs it does are:
 *
 * <ol>
 * <li>Collecting all the {@link IEnergyTransducer}s into {@link #transducers},
 * and adding a {@link SolarGainsSource}, {@link GainLoadRatioAdjuster}, and
 * {@link LightingDemand09}</li>
 * <li>Collecting all the {@link IHeatingSystem}s into
 * {@link #heatingSystems}</li>
 * <li>Collecting all the {@link IVentilationSystem}s into
 * {@link #ventilationSystems}</li>
 * <li>Accumulating the specific heat loss, external area and thermal mass into
 * {@link #totalFabricHeatLoss}, {@link #totalExternalArea}, and
 * {@link #totalThermalMass}</li>
 * <li>Passing ventilation information to {@link #infiltration}, the
 * {@link IStructuralInfiltrationAccumulator}</li>
 * <li>Passing lighting information from the visitor to {@link #lightingDemand}
 * and {@link #solarGains}</li>
 * </ol>
 *
 * @author hinton
 *
 */
final class Visitor implements IEnergyCalculatorVisitor {

    private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(Visitor.class);

    public final IStructuralInfiltrationAccumulator infiltration;

    public final List<IEnergyTransducer> transducers;
    public final List<IHeatingSystem> heatingSystems = new ArrayList<IHeatingSystem>();
    public final Map<IHeatingSystem, Double> proportions = new HashMap<>();
    public final List<IVentilationSystem> ventilationSystems = new ArrayList<IVentilationSystem>();

    public final double[][] areasByType = new double[2][AreaType.values().length];

    /*
	BEISDOC
	NAME: Fabric Heat Loss
	DESCRIPTION: The u-value multiplied by the area for heat-loss areas.
	TYPE: formula
	UNIT: W/℃
	SAP: (33)
        SAP_COMPLIANT: Yes
	BREDEM: 3B
        BREDEM_COMPLIANT: Yes
	DEPS: window-heat-loss,floor-heat-loss,ceiling-heat-loss,wall-heat-loss,door-heat-loss
	ID: fabric-heat-loss
	CODSIEB
     */
    public double totalFabricHeatLoss, totalExternalArea;

    public double[] thermalMassAreas = new double[ThermalMassLevel.values().length];

    public final GainLoadRatioAdjuster glrAdjuster;

    private final LightingDemand09 lightingDemand;

    private final SolarGainsSource solarGains;

    private RoofConstructionType roofConstructionType;
    private Double roofInsulationThickness;

    private FloorConstructionType groundFloorConstructionType;
    private Double floorInsulationThickness;

    private final EnergyCalculatorType mode;
    private final Country country;
    private final Band band;

    private ThermalMassLevel bestThermalMassLevel;

    public static Visitor create(final IConstants constants, final IEnergyCalculatorParameters parameters, final int buildYear, final Country country, final List<IEnergyTransducer> defaultTransducers) {
        return new Visitor(constants, parameters, defaultTransducers, country, SAPAgeBandValue.fromYear(buildYear, country).getName());
    }

    protected Visitor(final IConstants constants, final IEnergyCalculatorParameters parameters, final List<IEnergyTransducer> defaultTransducers, final Country country, final Band band) {
        this.country = country;
        this.band = band;
        this.solarGains = new SolarGainsSource(constants, EnergyType.GainsSOLAR_GAINS);
        this.glrAdjuster = new GainLoadRatioAdjuster();
        this.lightingDemand = new LightingDemand09(constants);
        this.transducers = new ArrayList<IEnergyTransducer>(defaultTransducers.size() + 15);
        this.transducers.addAll(defaultTransducers);
        this.infiltration = new StructuralInfiltrationAccumulator(constants);
        this.mode = parameters.getCalculatorType();
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

        /*
		BEISDOC
		NAME: Wall Heat Loss
		DESCRIPTION: The area multiplied by the u-value for a section of wall.
		TYPE: formula
		UNIT: area m^2 * u-value W/m^2/℃ = W/℃
		SAP: (29a,32b,32c)
                SAP_COMPLIANT: Yes
		BREDEM: 3B
                BREDEM_COMPLIANT: Yes
		DEPS:
		GET: house.u-value
		SET: action.reset-walls
		STOCK: storeys.csv (polygon shape), elevations.csv(tenthspartywall), imputation schemas (walls)
		NOTES: In SAP 2012 mode, the u-value of the wall will be overridden by one of tables S6, S7 or S8.
		ID: wall-heat-loss
		CODSIEB
         */
        final AreaType areaType = constructionType.getWallType().getAreaType();

        if (thermalMassLevel.isPresent()) {
            thermalMassAreas[thermalMassLevel.get().ordinal()] += area;
        }

        visitArea(
                areaType,
                area,
                mode.uvalues.getWall(uValue, country, constructionType, externalOrExternalInsulationThickness, hasCavityInsulation, band, thickness));
    }

    @Override
    public void visitDoor(final DoorType doorType, final double area, final double uValue) {
        log.debug("VISIT Door, {}, {}", area, uValue);

        /*
		BEISDOC
		NAME: Door Heat loss
		DESCRIPTION: The area multiplied by the u-value for a door.
		TYPE: formula
		UNIT: area m^2 * u-value W/m^2/℃ = W/℃
		SAP: (26)
                SAP_COMPLIANT: Yes
		BREDEM: 3B
                BREDEM_COMPLIANT: Yes
		DEPS:
		GET: house.u-value
		SET: action.reset-doors
		STOCK: elevations.csv (doorframe, tenthsopening), imputation schema (doors)
		ID: door-heat-loss
		NOTES: Doors are distributed amongst walls based on the opening proportion for this elevation in the stock, as per the CHM method.
		NOTES: Some doors may be omitted if the total area of doors is greater than the area allowed by the openingProportion.
		CODSIEB
         */
        visitArea(doorType.getAreaType(), area, mode.uvalues.getOutsideDoor(uValue, band, country));
    }

    @Override
    public void setRoofType(final RoofConstructionType constructionType, final double insulationThickness) {
        this.roofConstructionType = constructionType;
        this.roofInsulationThickness = insulationThickness;
    }

    @Override
    public void visitCeiling(final RoofType type, final double area, final double uValue) {
        log.debug("VISIT {}, {}, {}, {}, {}", type, area, uValue, roofConstructionType, roofInsulationThickness);

        /*
		BEISDOC
		NAME: Ceiling Heat loss
		DESCRIPTION: The area multiplied by the u-value for a heat-loss ceiling
		TYPE: formula
		UNIT: area m^2 * u-value W/m^2/℃ = W/℃
		SAP: (30,32b)
                SAP_COMPLIANT: Yes
		BREDEM: 3B
                BREDEM_COMPLIANT: Yes
		DEPS:
		GET: house.u-value
		SET: action.reset-roofs
		STOCK: roofs.csv (all fields), imputation schema (roofs)
		ID: ceiling-heat-loss
		NOTES: Party ceiling's u-value is always 0.
		CODSIEB
         */
        if (roofConstructionType == null || roofInsulationThickness == null) {
            throw new RuntimeException("setRoofType must be called before visitCeiling");
        }

        visitArea(
                type.getAreaType(),
                area,
                mode.uvalues.getCeiling(uValue, type, roofConstructionType, roofInsulationThickness, country, band));
    }

    @Override
    public void visitWindow(
            final double area,
            final double uValue,
            final FrameType frameType,
            final GlazingType glazingType,
            final WindowInsulationType insulationType,
            final WindowGlazingAirGap airGap
    ) {
        log.debug("VISIT Window, {}, {}, {}, {}, {}", area, uValue, frameType, glazingType, insulationType);
        /*
		BEISDOC
		NAME: Window Heat Loss
		DESCRIPTION: The area and u-value for a glazed area.
		TYPE: formula
		UNIT: area m^2 * u-value W/m^2/℃ = W/℃
		SAP: (27,27a)
                SAP_COMPLIANT: Yes
		BREDEM: 3B
                BREDEM_COMPLIANT: Yes
		DEPS: glazing-area
		GET: house.u-value
		SET: measure.install-glazing,action.reset-glazing
		STOCK: elevations.csv (glazed doors, percentagedoubleglazed, singleglazedwindowframe), imputation schema (windows, doors)
		ID: window-heat-loss
		NOTES: When setting the u-value, ensure to include the curtain correction factor.
		CODSIEB
         */
        visitArea(
                frameType.getAreaType(),
                area,
                mode.uvalues.getWindow(uValue, frameType, glazingType, insulationType, airGap)
        );
    }

    @Override
    public void setFloorType(final FloorConstructionType groundFloorConstructionType, final double insulationThickness) {
        this.groundFloorConstructionType = groundFloorConstructionType;
        this.floorInsulationThickness = insulationThickness;
    }

    @Override
    public void visitFloor(final AreaType type, final double area, final double uValue, final double exposedPerimeter, final double wallThickness) {
        log.debug("VISIT {}, {}, {}, {}, {}", type, area, uValue, groundFloorConstructionType, floorInsulationThickness);

        /*
		BEISDOC
		NAME: Floor Heat Loss
		DESCRIPTION: The area multiplied by the u-value for a section of floor which contributes to heat loss.
		TYPE: formula
		UNIT: area m^2 * u-value W/m^2/℃ = W/℃
		SAP: 28b, 32a
                SAP_COMPLIANT: Yes
		BREDEM: 3B
                BREDEM_COMPLIANT: Yes
		DEPS:
		GET: house.u-value
		SET: action.reset-floors,action.set-floor-insulation
		STOCK: cases.csv (grndfloortype), storeys.csv (shape of polygons), imputation schema (floors)
		ID: floor-heat-loss
		NOTES: Party floor's u-value is always 0.
		CODSIEB
         */
        final boolean isGroundFloorOrBasement = type == AreaType.BasementFloor || type == AreaType.GroundFloor;

        if (isGroundFloorOrBasement && (groundFloorConstructionType == null || floorInsulationThickness == null)) {
            throw new RuntimeException("setGroundFloorType must be called before calling visitFloor with a ground or basement floor");
        }

        visitArea(
                type,
                area,
                mode.uvalues.getFloor(uValue,
                        type == AreaType.PartyFloor,
                        type == AreaType.BasementFloor || type == AreaType.GroundFloor,
                        area,
                        exposedPerimeter,
                        wallThickness,
                        groundFloorConstructionType,
                        floorInsulationThickness,
                        band,
                        country));
    }

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
        switch (floorType) {
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
                mode.uvalues.getWallAirChangeRate(airChangeRate, wallType)
        );
    }

    @Override
    public final void visitTransparentElement(
            final GlazingType glazingType,
            final WindowInsulationType insulationType,
            final double _visibleLightTransmittivity,
            final double _solarGainTransmissivity,
            final double area,
            final FrameType frameType,
            final double _frameFactor,
            final double horizontalOrientation,
            final double verticalOrientation,
            final OvershadingType overshading
    ) {

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
        final double frameFactor = mode.uvalues.getFrameFactor(_frameFactor, frameType);

        final double usefulArea = frameFactor * area;

        /*
		BEISDOC
		NAME: Visible light effective transmission area
		DESCRIPTION: The effective visible light transmission area
		TYPE: formula
		UNIT: m2
		SAP: (74-82)
                SAP_COMPLIANT: Yes
		BREDEM: 5A
                BREDEM_COMPLIANT: Yes
		DEPS: light-transmittance-factor,frame-factor
		ID: visible-light-effective-transmission-area
		CODSIEB
         */
        final double totalVisibleLightTransmittivity
                = mode.uvalues.getVisibleLightTransmissivity(_visibleLightTransmittivity, glazingType) * usefulArea;

        /*
		BEISDOC
		NAME: Solar gains effective transmission area
		DESCRIPTION: The effective solar gains transmission area
		TYPE: formula
		UNIT: m2
		SAP: (74-82)
                SAP_COMPLIANT: Yes
		BREDEM: 5A
                BREDEM_COMPLIANT: Yes
		DEPS: solar-gain-transmissivity,frame-factor
		ID: solar-gains-effective-transmission-area
		CODSIEB
         */
        final double totalSolarGainTransmissivity = mode.uvalues.getSolarGainTransmissivity(_solarGainTransmissivity, glazingType, insulationType) * usefulArea;

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

    public double getBestThermalMassParameter() {
        /*
		BEISDOC
		NAME: Thermal Mass
		DESCRIPTION: Choose the thermal mass parameter based on which level has the largest wall area.
		TYPE: lookup
		UNIT: kJ/m^2.℃
		SAP: Table 1f
                SAP_COMPLIANT: Yes
		BREDEM: 4A
                BREDEM_COMPLIANT: Yes
		DEPS: thermal-mass-level,wall-thermal-mass-category
		ID: thermal-mass
		CODSIEB
         */
        if (bestThermalMassLevel == null) {
            double highestArea = 0;
            ThermalMassLevel level = ThermalMassLevel.MEDIUM;
            for (int i = 0; i < thermalMassAreas.length; i++) {
                if (thermalMassAreas[i] > highestArea) {
                    highestArea = thermalMassAreas[i];
                    level = ThermalMassLevel.values()[i];
                }
            }
            bestThermalMassLevel = level;
        }

        return bestThermalMassLevel.getThermalMassParameter();
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName() + " [totalSpecificHeatLoss=" + totalFabricHeatLoss + ", totalExternalArea=" + totalExternalArea + ", totalThermalMass="
                + getBestThermalMassParameter() + "]";
    }

    @Override
    public void visitLight(String name, double proportion, LightType lightType, double[] splitRate) {
        transducers.add(new SimpleLightingTransducer(lightType.name(), proportion,
                mode.lighting.getMultiplier(lightType), splitRate));
    }

}
