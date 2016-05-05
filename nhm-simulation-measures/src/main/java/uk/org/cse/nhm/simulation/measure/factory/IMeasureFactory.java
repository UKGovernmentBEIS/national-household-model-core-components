package uk.org.cse.nhm.simulation.measure.factory;

import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.components.fabric.types.FrameType;
import uk.org.cse.nhm.hom.components.fabric.types.GlazingType;
import uk.org.cse.nhm.hom.components.fabric.types.RoofConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;
import uk.org.cse.nhm.hom.components.fabric.types.WindowInsulationType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.IAdjuster;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.language.definition.action.scaling.heating.XSpaceHeatingSystem;
import uk.org.cse.nhm.language.definition.enums.XChangeDirection;
import uk.org.cse.nhm.simulation.measure.HeatingControlMeasure;
import uk.org.cse.nhm.simulation.measure.StorageHeaterMeasure;
import uk.org.cse.nhm.simulation.measure.adjustment.AdjustmentMeasure;
import uk.org.cse.nhm.simulation.measure.adjustment.ClearAdjustmentsMeasure;
import uk.org.cse.nhm.simulation.measure.boilers.BoilerEfficiencyMeasure;
import uk.org.cse.nhm.simulation.measure.boilers.BreakBoilerMeasure;
import uk.org.cse.nhm.simulation.measure.boilers.CombiBoilerMeasure;
import uk.org.cse.nhm.simulation.measure.boilers.DistrictHeatingMeasure;
import uk.org.cse.nhm.simulation.measure.boilers.StandardBoilerMeasure;
import uk.org.cse.nhm.simulation.measure.heatpumps.AbstractHeatPumpMeasure.Hybrid;
import uk.org.cse.nhm.simulation.measure.heatpumps.AirSourceHeatPumpMeasure;
import uk.org.cse.nhm.simulation.measure.heatpumps.GroundSourceHeatPumpMeasure;
import uk.org.cse.nhm.simulation.measure.hotwater.FitStorageTankThermostat;
import uk.org.cse.nhm.simulation.measure.hotwater.InstallHotWaterCylinderInsulation;
import uk.org.cse.nhm.simulation.measure.insulation.AddOrRemoveLoftAction;
import uk.org.cse.nhm.simulation.measure.insulation.DraughtProofingMeasure;
import uk.org.cse.nhm.simulation.measure.insulation.FloorInsulationMeasure;
import uk.org.cse.nhm.simulation.measure.insulation.GlazingMeasure;
import uk.org.cse.nhm.simulation.measure.insulation.ModifyFloorInsulationMeasure;
import uk.org.cse.nhm.simulation.measure.insulation.ModifyRoofInsulationMeasure;
import uk.org.cse.nhm.simulation.measure.insulation.ModifyWallInsulationMeasure;
import uk.org.cse.nhm.simulation.measure.insulation.RoofInsulationMeasure;
import uk.org.cse.nhm.simulation.measure.insulation.WallInsulationMeasure;
import uk.org.cse.nhm.simulation.measure.lighting.LowEnergyLightingMeasure;
import uk.org.cse.nhm.simulation.measure.otherspaceheating.WarmAirMeasure;
import uk.org.cse.nhm.simulation.measure.renewables.SolarHotWaterMeasure;
import uk.org.cse.nhm.simulation.measure.renewables.SolarPhotovoltaicMeasure;
import uk.org.cse.nhm.simulation.measure.roomheaters.RoomHeaterMeasure;
import uk.org.cse.nhm.simulation.measure.scaling.ResponsivenessScalingAction;
import uk.org.cse.nhm.simulation.measure.structure.AlterWallHeatLossMeasure;
import uk.org.cse.nhm.simulation.measure.structure.ModifyWallConstructionTypeMeasure;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.HeatingEfficiencyFunction;

public interface IMeasureFactory {
    public StandardBoilerMeasure createStandardBoilerMeasure(
            @Assisted final FuelType fuelType,
            @Assisted final ISizingFunction sizingFunction,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
            @Assisted("wetHeating") final IComponentsFunction<Number> wetHeatingCostFunction,
            @Assisted("winterEfficiency") final IComponentsFunction<Number> winterEfficiency,
            @Assisted("summerEfficiency") final IComponentsFunction<Number> summerEfficiency,
            @Assisted("cylinderVolume") final IComponentsFunction<Number> cylinderVolume,
            @Assisted("cylinderInsulation") final double cylinderInsulationThickness,
            @Assisted("floorArea") final double minimumFloorArea,
            @Assisted("externalSpace") final double minimumExternalSpace
            );

    public CombiBoilerMeasure createCombiBoilerMeasure(
            @Assisted final FuelType fuelType,
            @Assisted final ISizingFunction sizingFunction,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
            @Assisted("wetHeating") final IComponentsFunction<Number> wetHeatingCostFunction,
            @Assisted("winterEfficiency") final IComponentsFunction<Number> winterEfficiency,
            @Assisted("summerEfficiency") final IComponentsFunction<Number> summerEfficiency,
            @Assisted("storageVolume") final IComponentsFunction<Number> storageVolume
            );

    public GroundSourceHeatPumpMeasure createGroundSourceHeatPumpMeasure(
            @Assisted final ISizingFunction sizingFunction,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
            @Assisted("wetHeating") final IComponentsFunction<Number> wetHeatingCostFunction,
            @Assisted("efficiency") final IComponentsFunction<Number> efficiency,
            @Assisted("insulation") final double cylinderInsulationThickness,
            @Assisted("volume") final IComponentsFunction<Number> cylinderVolume,
            @Assisted("space") final double minimumSpace,
            @Assisted("fuel") final FuelType fuel,
            @Assisted final Optional<Hybrid> hybrid
            );

    public AirSourceHeatPumpMeasure createAirSourceHeatPumpMeasure(
            @Assisted final ISizingFunction sizingFunction,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
            @Assisted("wetHeating") final IComponentsFunction<Number> wetHeatingCostFunction,
            @Assisted("efficiency") final IComponentsFunction<Number> efficiency,
            @Assisted("insulation") final double cylinderInsulationThickness,
            @Assisted("volume") final IComponentsFunction<Number> cylinderVolume,
            @Assisted("fuel") final FuelType fuel,
            @Assisted final Optional<Hybrid> hybrid
            );

    public WallInsulationMeasure createWallInsulationMeasure(
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("thickness") final double insulationThickness,
            @Assisted("rvalue") final IComponentsFunction<Number> thermalResistancePerMil,
            @Assisted("uvalue") final Optional<IComponentsFunction<Number>> uValueOverride,
            @Assisted final Predicate<IWall> suitability,
            @Assisted final WallInsulationType insulationType
            );

    public RoofInsulationMeasure createRoofInsulationMeasure(
            @Assisted("thickness") final double insulationThickness,
            @Assisted("rvalue") final IComponentsFunction<Number> resistanceFunction,
            @Assisted("uvalue") final Optional<IComponentsFunction<Number>> uValueFunction,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted final Set<RoofConstructionType> suitableRoofConstructionTypes,
            @Assisted final boolean topup
            );

    public GlazingMeasure createGlazingInsulationMeasure(
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("uvalue") final double uValue,
            @Assisted("light") final double lightTransmittance,
            @Assisted("gains") final double gainsTransmittance,
            @Assisted("framefactor") final double frameFactor,
            @Assisted final FrameType frameType,
            @Assisted final GlazingType glazingType,
            @Assisted final WindowInsulationType windowInsulationType
            );

    public DraughtProofingMeasure createDraughtProofingMeasure(
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("proportion") final double proportion
            );

    public SolarHotWaterMeasure createSolarHotWaterMeasure(
            @Assisted("area")   final IComponentsFunction<Number> installedArea,
            @Assisted("cost")   final IComponentsFunction<Number> installationCost,
            @Assisted("volume") final IComponentsFunction<Number> cylinderVolume,
            @Assisted("zle")    final IComponentsFunction<Number> zle,
            @Assisted("lhlc")   final IComponentsFunction<Number> lhlc);

    public AlterWallHeatLossMeasure createUValueModifier(
            final double uValue);

    public DistrictHeatingMeasure createDistrictHeatingMeasure(
            @Assisted() final ISizingFunction sizingFunction,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
            @Assisted("wetHeating") final IComponentsFunction<Number> wetHeatingCostFunction,
            @Assisted("insulation") final double cylinderInsulation,
            @Assisted("volume") final IComponentsFunction<Number> cylinderVolume,
            @Assisted("efficiency") final IComponentsFunction<Number> efficiency);

    public BreakBoilerMeasure createBreakBoilerMeasure();

    public RoomHeaterMeasure createRoomHeaterMeasure(
            @Assisted final ISizingFunction sizingFunction,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("opex") final IComponentsFunction<Number> opexFunction,

            @Assisted final FuelType fuel,
            @Assisted final Optional<Double> efficiency,
            @Assisted("replaceExisting") final boolean replaceExisting);

    public BoilerEfficiencyMeasure createBoilerEfficiencyMeasure(
            @Assisted("winterEfficiency") final Optional<IComponentsFunction<Number>> winterEfficiency,
            @Assisted("summerEfficiency") final Optional<IComponentsFunction<Number>> summerEfficiency,
            @Assisted final XChangeDirection direction,
            @Assisted("getWinterEfficiency") final Optional<HeatingEfficiencyFunction> getWinterEfficiency,
            @Assisted("getSummerEfficiency") final Optional<HeatingEfficiencyFunction> getSummerEfficiency);

    public StorageHeaterMeasure createStorageHeaterMeasure(
            @Assisted final StorageHeaterType type,
            @Assisted final StorageHeaterControlType controlType,
            @Assisted final ISizingFunction sizingFunction,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
            @Assisted("responsiveness") final Optional<IComponentsFunction<Number>> responsivenessFunction);

    public HeatingControlMeasure createHeatingControlMeasure(
            @Assisted final HeatingSystemControlType controlType,
            @Assisted final IComponentsFunction<Number> capex);

    public ModifyWallConstructionTypeMeasure createWallConstructionTypeModifier(
            @Assisted final WallConstructionType wallType);

    public ModifyWallInsulationMeasure createModifyWallInsulationAction(
            @Assisted("insulationthickness") final IComponentsFunction<? extends Number> thickness);

    public ModifyFloorInsulationMeasure createModifyFloorInsulationAction(
            @Assisted("insulationthickness") final IComponentsFunction<? extends Number> thickness,
            @Assisted("uvalue") final Optional<IComponentsFunction<? extends Number>> uvalue);

    public ModifyRoofInsulationMeasure createModifyLoftInsulationAction(
            @Assisted("insulationthickness") final IComponentsFunction<? extends Number> thickness,
            @Assisted("uvalue") final Optional<IComponentsFunction<? extends Number>> uvalue);

    public FloorInsulationMeasure createFloorInsulationMeasure
            (
                    @Assisted("capex") final IComponentsFunction<Number> capex,
                    @Assisted("resistance") final Optional<IComponentsFunction<Number>> resistance,
                    @Assisted("uvalue") final Optional<IComponentsFunction<Number>> uValue,
                    @Assisted final double thickness,
                    @Assisted final boolean isSolidFloor
            );

    public ResponsivenessScalingAction createResponsivenessScalingAction(
            @Assisted final Set<XSpaceHeatingSystem> systems,
            @Assisted final IComponentsFunction<Number> scaling);

    public AddOrRemoveLoftAction createAddOrRemoveLoftAction(final boolean addLoft);

    public LowEnergyLightingMeasure createLowEnergyLightingMeasure(
            @Assisted("threshold") final double threshold,
            @Assisted("proportion") final double proportion,
            @Assisted final IComponentsFunction<Number> capex);

    public SolarPhotovoltaicMeasure createSolarPhotovoltaicMeasure(
            @Assisted("efficiency") final IComponentsFunction<Number> efficiency,
            @Assisted("roofCoverage") final IComponentsFunction<Number> roofCoverage,
            @Assisted("capex") final IComponentsFunction<Number> capex, 
            @Assisted("ownUse") final IComponentsFunction<Number> ownUse);

    public AdjustmentMeasure createAdjustmentMeasure(
            @Assisted final IAdjuster prototype,
            @Assisted final boolean add);

    public ClearAdjustmentsMeasure createClearAdjustmentsMeasure();

    public FitStorageTankThermostat createHotWaterTankThermostat(
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction);
    
    public InstallHotWaterCylinderInsulation createInstallHotWateCylinderInsulation(
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction);
    
    public WarmAirMeasure createWarmAirMeasure(
            @Assisted final FuelType fuelType,
            @Assisted final ISizingFunction sizingFunction,
            @Assisted("capex") final IComponentsFunction<Number> capitalCostFunction,
            @Assisted("opex") final IComponentsFunction<Number> operationalCostFunction,
            @Assisted("efficiency") final IComponentsFunction<Number> efficiency);
}
