package uk.org.cse.nhm.language.builder.reset;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.common.collect.ImmutableSet;

import uk.org.cse.nhm.hom.components.fabric.types.DoorType;
import uk.org.cse.nhm.hom.components.fabric.types.FrameType;
import uk.org.cse.nhm.hom.components.fabric.types.GlazingType;
import uk.org.cse.nhm.hom.components.fabric.types.WallConstructionType;
import uk.org.cse.nhm.hom.components.fabric.types.WallInsulationType;
import uk.org.cse.nhm.hom.components.fabric.types.WindowInsulationType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.builder.function.MapEnum;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XWallInsulationMeasure.XWallInsulationType;
import uk.org.cse.nhm.language.definition.action.reset.XComputeFloorUValue;
import uk.org.cse.nhm.language.definition.action.reset.XFloorIsGroundFloor;
import uk.org.cse.nhm.language.definition.action.reset.XGetDoorType;
import uk.org.cse.nhm.language.definition.action.reset.XGetFrameType;
import uk.org.cse.nhm.language.definition.action.reset.XGetGlazingInsulationType;
import uk.org.cse.nhm.language.definition.action.reset.XGetGlazingType;
import uk.org.cse.nhm.language.definition.action.reset.XResetDoors;
import uk.org.cse.nhm.language.definition.action.reset.XResetFloors;
import uk.org.cse.nhm.language.definition.action.reset.XResetGlazing;
import uk.org.cse.nhm.language.definition.action.reset.XResetOpex;
import uk.org.cse.nhm.language.definition.action.reset.XResetRoofs;
import uk.org.cse.nhm.language.definition.action.reset.XResetWalls;
import uk.org.cse.nhm.language.definition.action.reset.XTechnologyFuel;
import uk.org.cse.nhm.language.definition.action.reset.XTechnologyType;
import uk.org.cse.nhm.language.definition.action.reset.XTechnologyType.XTechnologyTypeValue;
import uk.org.cse.nhm.language.definition.action.reset.XWallConstruction;
import uk.org.cse.nhm.language.definition.action.reset.XWallInfiltration;
import uk.org.cse.nhm.language.definition.action.reset.XWallInsulationThickness;
import uk.org.cse.nhm.language.definition.action.reset.XWallUValue;
import uk.org.cse.nhm.simulator.reset.IResetFactory;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ResetActionsAdapter extends ReflectingAdapter {
	private final IResetFactory factory;
	
	@Inject
	ResetActionsAdapter(
			final Set<IConverter> delegates,
			final Set<IAdapterInterceptor> interceptors,
			final IResetFactory factory) {
		super(delegates, interceptors);
		this.factory = factory;
	}

	/* Walls */
	
	@Adapt(XResetWalls.class)
	public IComponentsAction buildResetWalls(
			@Prop(XResetWalls.P.uvalue) final Optional<IComponentsFunction<Number>> uLookup,
			@Prop(XResetWalls.P.infiltration) final Optional<IComponentsFunction<Number>> infiltration,
			@Prop(XResetWalls.P.thickness) final Optional<IComponentsFunction<Number>> thickness
			) {
		return factory.createResetWallsAction(
				uLookup, 
				infiltration, 
				thickness);
	}

	@Adapt(XWallConstruction.class)
	public IComponentsFunction<WallConstructionType> buildWallConstructionType() {
		return factory.createWallConstructionFunction();
	}
	
	@Adapt(XWallInfiltration.class)
	public IComponentsFunction<? extends Number> buildWallInfiltration() {
		return factory.createWallInfiltrationFunction();
	}
	
	@Adapt(XWallInsulationThickness.class)
	public IComponentsFunction<? extends Number> buildWallInsulationThickness(
			@Prop(XWallInsulationThickness.P.types) final List<XWallInsulationType> insulations) {
		final Set<WallInsulationType> types = new HashSet<>();
		
		for (final XWallInsulationType wit : insulations) {
			types.add(MapEnum.wallInsulationType(wit));
		}
		
		if (types.isEmpty()) {
			types.addAll(EnumSet.allOf(WallInsulationType.class));
		}
		
		return factory.createWallInsulationThicknessFunction(ImmutableSet.copyOf(types));
	}
	
	@Adapt(XWallUValue.class)
	public IComponentsFunction<? extends Number> buildWallUValue() {
		return factory.createWallUValueFunction();
	}
	
	/* Windows */
	
	@Adapt(XResetGlazing.class)
	public IComponentsAction buildResetGlazings(
			@Prop(XResetGlazing.P.frameFactor) final Optional<IComponentsFunction<Number>> frameFactor,
			@Prop(XResetGlazing.P.gainsTransmittance) final Optional<IComponentsFunction<Number>> gainsTransmittance,
			@Prop(XResetGlazing.P.lightTransmittance) final Optional<IComponentsFunction<Number>> lightTransmittance,
			@Prop(XResetGlazing.P.uValue) final Optional<IComponentsFunction<Number>> uValue
			) {
		return factory.createResetGlazingsAction(
				frameFactor, 
				gainsTransmittance, 
				lightTransmittance, 
				uValue);
	}
	
	@Adapt(XGetGlazingInsulationType.class)
	public IComponentsFunction<WindowInsulationType> buildGlazingInsulationType() {
		return factory.createGlazingInsulationTypeFunction();
	}
	
	@Adapt(XGetGlazingType.class)
	public IComponentsFunction<GlazingType> buildGlazingType() {
		return factory.createGlazingTypeFunction();
	}
	
	@Adapt(XGetFrameType.class)
	public IComponentsFunction<FrameType> buildFrameType() {
		return factory.createGlazingFrameTypeFunction();
	}
	
	/* Doors */
	
	@Adapt(XResetDoors.class)
	public IComponentsAction buildResetDoors(
			@Prop(XResetDoors.P.rescale) final boolean rescale,
			@Prop(XResetDoors.P.area) final Optional<IComponentsFunction<Number>> area,
			@Prop(XResetDoors.P.uValue) final Optional<IComponentsFunction<Number>> uValue) {
		return factory.createResetDoorsAction(rescale, area, uValue);
	}
	
	@Adapt(XGetDoorType.class)
	public IComponentsFunction<DoorType> buildGetDoorType() {
		return factory.createDoorTypeFunction();
	}
	
	/* Floors */
	
	@Adapt(XResetFloors.class)
	public IComponentsAction buildResetFloors(
			@Prop(XResetFloors.P.uValue) final Optional<IComponentsFunction<Number>> uValue,
			@Prop(XResetFloors.P.infiltration) final Optional<IComponentsFunction<Number>> infiltration
			) {
		return factory.createResetFloorsAction(
				uValue,
				infiltration
				);
	}
	
	@Adapt(XComputeFloorUValue.class)
	public IComponentsFunction<? extends Number> buildComputeFloorUValue(final XComputeFloorUValue input) {
		return factory.createComputeFloorUValueFunction(
				input.getRsi(),
				input.getRse(),
				input.getSoilThermalConductivity(),
				input.getDeckThermalResistance(),
				input.getOpeningsPerMeterOfExposedPerimeter(),
				input.getHeightAboveGroundLevel(),
				input.getuValueOfWallsToUnderfloorSpace(),
				input.getAverageWindSpeedAt10m(),
				input.getWindShieldingFactor(),
				input.getFloorInsulationConductivity()
				);
	}
	
	@Adapt(XFloorIsGroundFloor.class)
	public IComponentsFunction<Boolean> buildFloorIsGroundFloor() {
		return factory.createFloorIsGroundFloorFunction();
	}
	
	/* Roofs */
	
	@Adapt(XResetRoofs.class)
	public IComponentsAction buildResetRoofAction(
			@Prop(XResetRoofs.P.uValue) final Optional<IComponentsFunction<Number>> uValue
			) {
		return factory.createResetRoofsAction(uValue);
	}
	
	/* Miscellaneous */
	
	@Adapt(XResetOpex.class)
	public IComponentsAction buildResetOpex(
			@Prop(XResetOpex.P.opex) final IComponentsFunction<Number> opex
			) {
		return factory.createResetOpexAction(opex);	
	}
	
	@Adapt(XTechnologyType.class)
	public IComponentsFunction<XTechnologyTypeValue> buildTechType() {
		return factory.createTechTypeFunction();
	}
	
	@Adapt(XTechnologyFuel.class)
	public IComponentsFunction<FuelType> buildTechFuelType() {
		return factory.createTechFuelFunction();
	}
}
