package uk.org.cse.nhm.language.builder.action.measure;

import java.util.List;
import java.util.Set;

import com.google.common.base.Optional;
import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableSet;
import com.google.inject.Inject;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.hom.emf.technologies.HeatingSystemControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterControlType;
import uk.org.cse.nhm.hom.emf.technologies.StorageHeaterType;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.builder.function.MapEnum;
import uk.org.cse.nhm.language.definition.action.measure.heating.XBoilerMeasure;
import uk.org.cse.nhm.language.definition.action.measure.heating.XBreakBoilerMeasure;
import uk.org.cse.nhm.language.definition.action.measure.heating.XCombiBoilerMeasure;
import uk.org.cse.nhm.language.definition.action.measure.heating.XDistrictHeatingMeasure;
import uk.org.cse.nhm.language.definition.action.measure.heating.XEfficiencyAction;
import uk.org.cse.nhm.language.definition.action.measure.heating.XHeatPumpMeasure;
import uk.org.cse.nhm.language.definition.action.measure.heating.XHeatPumpMeasure.XHeatPumpType;
import uk.org.cse.nhm.language.definition.action.measure.heating.XHeatingControlMeasure;
import uk.org.cse.nhm.language.definition.action.measure.heating.XHeatingMeasure;
import uk.org.cse.nhm.language.definition.action.measure.heating.XHotWaterTankThermostat;
import uk.org.cse.nhm.language.definition.action.measure.heating.XInstallHotWaterCylinderInsulation;
import uk.org.cse.nhm.language.definition.action.measure.heating.XRoomHeaterMeasure;
import uk.org.cse.nhm.language.definition.action.measure.heating.XStandardBoilerMeasure;
import uk.org.cse.nhm.language.definition.action.measure.heating.XStorageHeaterMeasure;
import uk.org.cse.nhm.language.definition.action.measure.heating.XStorageHeaterMeasure.XStorageHeaterType;
import uk.org.cse.nhm.language.definition.action.measure.heating.XWarmAirMeasure;
import uk.org.cse.nhm.language.definition.action.measure.heating.XWetHeatingMeasure;
import uk.org.cse.nhm.language.definition.action.scaling.XScalingMeasure;
import uk.org.cse.nhm.language.definition.action.scaling.heating.XHeatingResponsivenessScaling;
import uk.org.cse.nhm.language.definition.action.scaling.heating.XHeatingSystem;
import uk.org.cse.nhm.language.definition.action.scaling.heating.XSpaceHeatingSystem;
import uk.org.cse.nhm.language.definition.enums.XChangeDirection;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.function.num.XEfficiencyMeasurement;
import uk.org.cse.nhm.simulation.measure.factory.IMeasureFactory;
import uk.org.cse.nhm.simulation.measure.heatpumps.AbstractHeatPumpMeasure.Hybrid;
import uk.org.cse.nhm.simulator.factories.IDefaultFunctionFactory;
import uk.org.cse.nhm.simulator.factories.IObjectFunctionFactory;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.num.HeatingEfficiencyFunction;

public class HeatingMeasureAdapter extends ReflectingAdapter {
    private final IMeasureFactory factory;
    private final BoundedFunctions wrap;
    private final IDefaultFunctionFactory functions;
	private IObjectFunctionFactory functionFactory;
    
    @Inject
    public HeatingMeasureAdapter(final Set<IConverter> delegates, final IDefaultFunctionFactory functions,
    							 final IObjectFunctionFactory functionFactory,
                                 final IMeasureFactory factory, final Set<IAdapterInterceptor> interceptors,
                                 final BoundedFunctions wrap) {
        super(delegates, interceptors);
        this.functions = functions;
		this.functionFactory = functionFactory;
        this.factory = factory;
        this.wrap = wrap;
    }
    
    @Adapt(XStandardBoilerMeasure.class)
    public IComponentsAction buildStandardBoilerMeasure(
            final Name identifier,
            @Prop(XBoilerMeasure.P.fuel) final XFuelType fuel,
            @Prop(XStandardBoilerMeasure.P.cylinderVolume) final IComponentsFunction<Number> cylVol,
            @Prop(XStandardBoilerMeasure.P.cylinderInsulationThickness) final double cylIns,
            @Prop(XStandardBoilerMeasure.P.requiredExteriorArea) final double exteriorArea,
            @Prop(XStandardBoilerMeasure.P.requiredFloorArea) final double interiorArea,
            @Prop(XBoilerMeasure.P.WINTER_EFFICIENCY) final IComponentsFunction<Number> winterEfficiency,
            @Prop(XBoilerMeasure.P.SUMMER_EFFICIENCY) final IComponentsFunction<Number> summerEfficiency,
            @Prop(XHeatingMeasure.P.SIZING) final Optional<ISizingFunction> sizingFunction,
            @Prop(XHeatingMeasure.P.CAPEX) final Optional<IComponentsFunction<Number>> capexFunction,
            @Prop(XHeatingMeasure.P.OPEX) final Optional<IComponentsFunction<Number>> opexFunction,
            @Prop(XWetHeatingMeasure.P.WET_HEATING_CAPEX) final Optional<IComponentsFunction<Number>> wetHeatingCapex
            ) {
        // the question here is, how do we get hold of the right technology?
        // presumably we have multiple factory methods, which invoke constructors
        // that are annotated with different technology identifiers?

        // or inject multiple technologies into the standard boiler constructor
        // and put the logic inside the constructor. that seems more sensible

        return factory.createStandardBoilerMeasure(
            MapEnum.fuel(fuel),
            wrap.sizing(identifier, sizingFunction),
            wrap.capex(identifier, capexFunction),
            wrap.opex(identifier, opexFunction),
            wrap.systemCapex(identifier, wetHeatingCapex),
            wrap.winterEfficiency(identifier, winterEfficiency),
            wrap.summerEfficiency(identifier, summerEfficiency),
            wrap.tank(identifier, cylVol),
            cylIns,
            interiorArea,
            exteriorArea);
    }

    @Adapt(XCombiBoilerMeasure.class)
    public IComponentsAction buildCombiBoilerMeasure(
            final Name identifier,
            @Prop(XBoilerMeasure.P.fuel) final XFuelType fuel,
            @Prop(XCombiBoilerMeasure.P.storageVolume) final IComponentsFunction<Number> storageVolume,
            @Prop(XBoilerMeasure.P.WINTER_EFFICIENCY) final IComponentsFunction<Number> winterEfficiency,
            @Prop(XBoilerMeasure.P.SUMMER_EFFICIENCY) final IComponentsFunction<Number> summerEfficiency,
            @Prop(XHeatingMeasure.P.SIZING) final Optional<ISizingFunction> sizingFunction,
            @Prop(XHeatingMeasure.P.CAPEX) final Optional<IComponentsFunction<Number>> capexFunction,
            @Prop(XHeatingMeasure.P.OPEX) final Optional<IComponentsFunction<Number>> opexFunction,
            @Prop(XWetHeatingMeasure.P.WET_HEATING_CAPEX) final Optional<IComponentsFunction<Number>> whCapexFunction
            ) {
        return factory.createCombiBoilerMeasure(
                MapEnum.fuel(fuel),
                wrap.sizing(identifier, sizingFunction),
                wrap.capex(identifier, capexFunction),
                wrap.opex(identifier, opexFunction),
                wrap.systemCapex(identifier, whCapexFunction),
                wrap.winterEfficiency(identifier, winterEfficiency),
                wrap.summerEfficiency(identifier, summerEfficiency),
                functions.createWarningFunction(identifier,
                                                "store volume",
                                                storageVolume,
                                                0,
                                                1000,
                                                true, true)
                );
    }

    @Adapt(XHeatPumpMeasure.class)
    public IComponentsAction buildHeatPumpMeasure(
            final Name identifier,
            @Prop(XHeatPumpMeasure.P.type) final XHeatPumpType type,
            @Prop(XHeatPumpMeasure.P.cop) final IComponentsFunction<Number> cop,
            @Prop(XHeatPumpMeasure.P.cylinderVolume) final IComponentsFunction<Number> cylinderVolume,
            @Prop(XHeatPumpMeasure.P.cylinderInsulation) final double cylinderInsulation,
            @Prop(XHeatingMeasure.P.SIZING) final Optional<ISizingFunction> sizingFunction,
            @Prop(XHeatingMeasure.P.CAPEX) final Optional<IComponentsFunction<Number>> capexFunction,
            @Prop(XWetHeatingMeasure.P.WET_HEATING_CAPEX) final Optional<IComponentsFunction<Number>> whCapexFunction,
            @Prop(XHeatingMeasure.P.OPEX) final Optional<IComponentsFunction<Number>> opexFunction,
            @Prop(XHeatPumpMeasure.P.fuel) final XFuelType mainFuel,
        	@Prop(XHeatPumpMeasure.P.hybridFuel) final Optional<XFuelType> hybridFuel,
    		@Prop(XHeatPumpMeasure.P.hybridEfficiency) final IComponentsFunction<Number> hybridEfficiency,
            @Prop(XHeatPumpMeasure.P.requiredExteriorArea) final double requiredExteriorArea,
    		@Prop(XHeatPumpMeasure.P.hybridRatio) final List<Double> hybridRatios) {
    	Preconditions.checkNotNull(mainFuel, "Main fuel should never be null for a heat pump");
        final ISizingFunction size                    = wrap.sizing(identifier, sizingFunction);
        final IComponentsFunction<Number> capex       = wrap.capex(identifier, capexFunction);
        final IComponentsFunction<Number> opex        = wrap.opex(identifier, opexFunction);
        final IComponentsFunction<Number> copFunction = wrap.cop(identifier, cop);
        final IComponentsFunction<Number> tank        = wrap.tank(identifier, cylinderVolume);
        final IComponentsFunction<Number> systemCapex = wrap.systemCapex(identifier, whCapexFunction);
        
        final Optional<Hybrid> hybrid;
        
        if (hybridFuel.isPresent()) {
        	hybrid = Optional.of(new Hybrid(wrap.efficiency(identifier, hybridEfficiency), hybridRatios, MapEnum.fuel(hybridFuel.get())));
        } else {
        	hybrid = Optional.absent();
        }
        
        if (type == XHeatPumpType.AirSource) {
            return factory.createAirSourceHeatPumpMeasure(size, capex, opex, systemCapex, copFunction,
                                                          cylinderInsulation, tank,
                                                          MapEnum.fuel(mainFuel),
                                                          hybrid
            		);
        } else {
            return factory.createGroundSourceHeatPumpMeasure(size, capex, opex, systemCapex, copFunction,
                                                             cylinderInsulation, tank, requiredExteriorArea,
                                                             MapEnum.fuel(mainFuel),
                                                             hybrid);
        }
    }

    @Adapt(XDistrictHeatingMeasure.class)
    public IComponentsAction buildDistrictHeatingMeasure(
            final Name identifier,
            @Prop(XHeatingMeasure.P.SIZING) final Optional<ISizingFunction> sizingFunction,
            @Prop(XHeatingMeasure.P.CAPEX) final Optional<IComponentsFunction<Number>> capexFunction,
            @Prop(XHeatingMeasure.P.OPEX) final Optional<IComponentsFunction<Number>> opexFunction,
            @Prop(XWetHeatingMeasure.P.WET_HEATING_CAPEX) final Optional<IComponentsFunction<Number>> whCapexFunction,
            @Prop(XDistrictHeatingMeasure.P.cylinderInsulationThickness) final double insulation,
            @Prop(XDistrictHeatingMeasure.P.cylinderVolume) final IComponentsFunction<Number> volume,
            @Prop(XDistrictHeatingMeasure.P.efficiency) final IComponentsFunction<Number> efficiency,
            @Prop(XDistrictHeatingMeasure.P.chargingUsageBased) final boolean chargingUsageBased
            ) {
        return factory.createDistrictHeatingMeasure(wrap.sizing (identifier, sizingFunction),
                                                    wrap.capex(identifier, capexFunction),
                                                    wrap.opex(identifier, opexFunction),
                                                    wrap.systemCapex(identifier, whCapexFunction),
                                                    insulation,
                                                    wrap.tank(identifier, volume),
                                                    wrap.efficiency(identifier, efficiency),
                                                    chargingUsageBased
                                                    );
    }

    @Adapt(XBreakBoilerMeasure.class)
    public IComponentsAction buildRemoveBoilerMeasure() {
        return factory.createBreakBoilerMeasure();
    }

    @Adapt(XRoomHeaterMeasure.class)
    public IComponentsAction buildRoomHeaterMeasure(
            final Name identifier,
            @Prop(XRoomHeaterMeasure.P.SIZING) final Optional<ISizingFunction> sizingFunction,
            @Prop(XRoomHeaterMeasure.P.CAPEX) final Optional<IComponentsFunction<Number>> capexFunction,
            @Prop(XRoomHeaterMeasure.P.OPEX) final Optional<IComponentsFunction<Number>> opexFunction,
            @Prop(XRoomHeaterMeasure.P.FUEL) final XFuelType fuel,
            @Prop(XRoomHeaterMeasure.P.EFFICIENCY) final Optional<Double> efficiency,
            @Prop(XRoomHeaterMeasure.P.REPLACE_EXISTING) final boolean replaceExisting) {
        return factory.createRoomHeaterMeasure(wrap.sizing(identifier, sizingFunction),
                                               wrap.capex(identifier, capexFunction),
                                               wrap.opex(identifier, opexFunction),
                                               MapEnum.fuel(fuel),
                                               efficiency,
                                               replaceExisting);
    }

    @Adapt(XEfficiencyAction.class)
    public IComponentsAction buildChangeBoilerEfficiencyMeasure(
            @Prop(XBoilerMeasure.P.WINTER_EFFICIENCY) final IComponentsFunction<Number> winterEfficiency,
            @Prop(XBoilerMeasure.P.SUMMER_EFFICIENCY) final IComponentsFunction<Number> summerEfficiency,
            @Prop(XEfficiencyAction.P.DIRECTION) final XChangeDirection direction) {
    	
        return factory.createBoilerEfficiencyMeasure(
        		Optional.fromNullable(winterEfficiency), 
        		Optional.fromNullable(summerEfficiency), 
        		direction,
        		
        		winterEfficiency == null ? 
        				Optional.of(functionFactory.createHeatingEfficiency(XHeatingSystem.PrimarySpaceHeating, XEfficiencyMeasurement.Winter)) : 
        					Optional.<HeatingEfficiencyFunction>absent(),

        		summerEfficiency == null ?
        				Optional.of(functionFactory.createHeatingEfficiency(XHeatingSystem.PrimarySpaceHeating,  XEfficiencyMeasurement.Summer)) :
        					Optional.<HeatingEfficiencyFunction>absent()
			);
    }

    @Adapt(XStorageHeaterMeasure.class)
    public IComponentsAction buildStorageHeaterMeasure(
            final Name identifier,
            @Prop(XHeatingMeasure.P.CAPEX) final Optional<IComponentsFunction<Number>> capex,
            @Prop(XHeatingMeasure.P.OPEX) final Optional<IComponentsFunction<Number>> opex,
            @Prop(XHeatingMeasure.P.SIZING) final Optional<ISizingFunction> size,
            @Prop(XStorageHeaterMeasure.P.responsiveness) final Optional<IComponentsFunction<Number>> responsiveness,
            @Prop(XStorageHeaterMeasure.P.type) final XStorageHeaterType type
            ) {
        StorageHeaterType internalType;
        StorageHeaterControlType controlType = StorageHeaterControlType.AUTOMATIC_CHARGE_CONTROL;

        switch (type) {
            case OldLargeVolume:
                internalType = StorageHeaterType.OLD_LARGE_VOLUME;
                break;
            case ConvectorCelect:
                controlType = StorageHeaterControlType.CELECT_CHARGE_CONTROL;
            case Convector:
                internalType = StorageHeaterType.CONVECTOR;
                break;
            case FanCelect:
                controlType = StorageHeaterControlType.CELECT_CHARGE_CONTROL;
            case Fan:
                internalType = StorageHeaterType.FAN;
                break;
            case SlimlineCelect:
                controlType = StorageHeaterControlType.CELECT_CHARGE_CONTROL;
            case Slimline:
                internalType = StorageHeaterType.SLIMLINE;
                break;
            case Integrated:
                internalType = StorageHeaterType.INTEGRATED_DIRECT_ACTING;
                break;
            default:
                throw new IllegalArgumentException("Unknown storage heater type " + type);
        }

        return factory.createStorageHeaterMeasure(
                internalType,
                controlType,
                wrap.sizing(identifier, size),
                wrap.capex(identifier, capex),
                wrap.opex(identifier, opex),
                responsiveness);
    }

    @Adapt(XHeatingControlMeasure.class)
    public IComponentsAction buildHeatingControlMeasure(final Name identifier,
                                                        
            @Prop(XHeatingControlMeasure.P.capex) final IComponentsFunction<Number> capex,
            @Prop(XHeatingControlMeasure.P.type) final XHeatingControlMeasure.XHeatingControlType type) {

        HeatingSystemControlType internalType;

        switch (type) {
            case ApplianceThermostat:
                internalType = HeatingSystemControlType.APPLIANCE_THERMOSTAT;
                break;
            case BypassValve:
                internalType = HeatingSystemControlType.BYPASS;
                break;
            case DelayedStartThermostat:
                internalType = HeatingSystemControlType.DELAYED_START_THERMOSTAT;
                break;
            case Programmer:
                internalType = HeatingSystemControlType.PROGRAMMER;
                break;
            case RoomThermostat:
                internalType = HeatingSystemControlType.ROOM_THERMOSTAT;
                break;
            case ThermostaticRadiatorValve:
                internalType = HeatingSystemControlType.THERMOSTATIC_RADIATOR_VALVE;
                break;
            case TimeTemperatureZoneControl:
                internalType = HeatingSystemControlType.TIME_TEMPERATURE_ZONE_CONTROL;
                break;
            default:
                throw new IllegalArgumentException("Unknown heating system control type " + type);
        }

        return factory.createHeatingControlMeasure(internalType,
                                                   wrap.capex(identifier, capex)
                                                   );
    }

    @Adapt(XHeatingResponsivenessScaling.class)
    public IComponentsAction buildResponsivenessScalingAction(
            @Prop(XScalingMeasure.P.of) final List<XSpaceHeatingSystem> systems,
            @Prop(XScalingMeasure.P.scaling) final IComponentsFunction<Number> scaling) {
        return factory.createResponsivenessScalingAction(ImmutableSet.copyOf(systems), scaling);
    }

    @Adapt(XHotWaterTankThermostat.class)
    public IComponentsAction buildHotWaterTankThermostat(
                                                         final Name identifier,
            
            @Prop(XHotWaterTankThermostat.P.capex) final IComponentsFunction<Number> capex) {

        return factory.createHotWaterTankThermostat(wrap.capex(identifier, capex));
    }
    
    @Adapt(XInstallHotWaterCylinderInsulation.class)
    public IComponentsAction buildInstallHotWaterCylinderInsulation(final Name identifier,
            @Prop(XHotWaterTankThermostat.P.capex) final IComponentsFunction<Number> capex) {
        return factory.createInstallHotWateCylinderInsulation(wrap.capex(identifier, capex));
    }
    
    @Adapt(XWarmAirMeasure.class)
    public IComponentsAction buildWarmAirMeasure(
            final Name identifier,
            @Prop(XHeatingMeasure.P.SIZING) final Optional<ISizingFunction> sizingFunction,
            @Prop(XWarmAirMeasure.P.FUEL) final XFuelType fuel,
            @Prop(XHeatingMeasure.P.CAPEX) final Optional<IComponentsFunction<Number>> capexFunction,
            @Prop(XHeatingMeasure.P.OPEX) final Optional<IComponentsFunction<Number>> opexFunction,
            @Prop(XWarmAirMeasure.P.EFFICIENCY) final IComponentsFunction<Number> efficiency
            ){
        return factory.createWarmAirMeasure(
                MapEnum.fuel(fuel),
                wrap.sizing (identifier, sizingFunction),
                wrap.capex(identifier, capexFunction),
                wrap.opex (identifier, opexFunction),
                wrap.efficiency(identifier, efficiency));
    }
}
