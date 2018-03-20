package uk.org.cse.nhm.language.builder.action.measure;

import java.util.EnumSet;
import java.util.Set;

import javax.inject.Inject;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.energycalculator.api.types.RoofConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallConstructionType;
import uk.org.cse.nhm.energycalculator.api.types.WallInsulationType;
import uk.org.cse.nhm.hom.structure.IWall;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.builder.function.MapEnum;
import uk.org.cse.nhm.language.builder.function.MapWallTypes;
import uk.org.cse.nhm.language.definition.action.measure.adjust.XAdjustNumberOfAirChangeDevices;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XDraughtProofingMeasure;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XFloorInsulationMeasure;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XGlazingMeasure;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XInsulationMeasure;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XLoftInsulationMeasure;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XModifyFloorInsulationAction;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XModifyLoftInsulationAction;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XModifyUValueAction;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XModifyWallConstructionTypeAction;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XModifyWallInsulationAction;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XSetLoftAction;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XWallInsulationMeasure;
import uk.org.cse.nhm.language.definition.action.measure.insulation.XWallInsulationMeasure.XWallInsulationType;
import uk.org.cse.nhm.language.definition.enums.XFrameType;
import uk.org.cse.nhm.language.definition.enums.XGlazingType;
import uk.org.cse.nhm.language.definition.enums.XWallConstructionTypeRule;
import uk.org.cse.nhm.language.definition.enums.XWallInsulationRule;
import uk.org.cse.nhm.language.definition.enums.XWindowGlazingAirGap;
import uk.org.cse.nhm.language.definition.enums.XWindowInsulationType;
import uk.org.cse.nhm.simulation.measure.factory.IMeasureFactory;
import uk.org.cse.nhm.simulator.factories.IDefaultFunctionFactory;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class InsulationMeasureAdapter extends ReflectingAdapter {
	final IMeasureFactory factory;
	private final IDefaultFunctionFactory fns;
	
	@Inject
	public InsulationMeasureAdapter(final Set<IConverter> delegates, final IDefaultFunctionFactory fns, final IMeasureFactory factory, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.fns = fns;
		this.factory = factory;
	}
	
	@Adapt(XWallInsulationMeasure.class)
	public IComponentsAction buildWallInsulationMeasure(
			final Name id,
			@Prop(XWallInsulationMeasure.P.type) final XWallInsulationType type,
			@Prop(XWallInsulationMeasure.P.suitableConstruction) final Optional<XWallConstructionTypeRule> suitableConstruction,
			@Prop(XWallInsulationMeasure.P.suitableInsulation) final Optional<XWallInsulationRule> suitableInsulation,
			@Prop(XInsulationMeasure.P.resistanceFunction) final IComponentsFunction<Number> resistanceFunction,
			@Prop(XInsulationMeasure.P.uValueFunction) final Optional<IComponentsFunction<Number>> uValueFunction,
			@Prop(XInsulationMeasure.P.thickness) final double thickness,
			@Prop(XInsulationMeasure.P.capex) final Optional<IComponentsFunction<Number>> capex
			) {
		
		final WallInsulationType insulationType;
		final XWallConstructionTypeRule fallbackConstruction;
		final XWallInsulationRule fallbackInsulation;
		
		switch (type) {
		case Cavity:
			insulationType = WallInsulationType.FilledCavity;
			fallbackConstruction = XWallConstructionTypeRule.AnyCavity;
			fallbackInsulation = XWallInsulationRule.NoCavity;
			break;
		case External:
			insulationType = WallInsulationType.External;
			fallbackConstruction = XWallConstructionTypeRule.Any;
			fallbackInsulation = XWallInsulationRule.NoExternal;
			break;
		case Internal:
			insulationType = WallInsulationType.Internal;
			fallbackConstruction = XWallConstructionTypeRule.Any;
			fallbackInsulation = XWallInsulationRule.NoInternal;
			break;
		default:
			return null;
		}
		
		final Predicate<IWall> suitability = MapWallTypes.getPredicateMatching(
				suitableConstruction.or(fallbackConstruction),
				suitableInsulation.or(fallbackInsulation));
		
		return factory.createWallInsulationMeasure(
				capex.or(fns.createPricingFunction(id)),
				thickness, 
				resistanceFunction, 
				uValueFunction, 
				suitability, 
				insulationType);
	}
	
	@Adapt(XLoftInsulationMeasure.class)
	public IComponentsAction buildLoftInsulationMeasure(
			final Name id,
			@Prop(XLoftInsulationMeasure.P.topUp) final boolean topup,
			@Prop(XInsulationMeasure.P.thickness) final double thickness,
			@Prop(XInsulationMeasure.P.capex) final Optional<IComponentsFunction<Number>> capex,
			@Prop(XInsulationMeasure.P.uValueFunction) final Optional<IComponentsFunction<Number>> uValueFunction,
			@Prop(XInsulationMeasure.P.resistanceFunction) final IComponentsFunction<Number> resistanceFunction
			) {
		
		return factory.createRoofInsulationMeasure(
				thickness, 
				resistanceFunction, 
				uValueFunction, 
				capex.or(fns.createPricingFunction(id)), 
				EnumSet.allOf(RoofConstructionType.class), 
				topup);
	}
	
	@Adapt(XGlazingMeasure.class)
	public IComponentsAction createGlazingInsulationMeasure (
			final Name id,
			@Prop(XGlazingMeasure.P.capex) final Optional<IComponentsFunction<Number>> capitalCostFunction,
			@Prop(XGlazingMeasure.P.uValue) final double uValue,
			@Prop(XGlazingMeasure.P.lightTransmittance) final double lightTransmittance,
			@Prop(XGlazingMeasure.P.gainsTransmittance) final double gainsTransmittance,
			@Prop(XGlazingMeasure.P.frameFactor) final double frameFactor,
			@Prop(XGlazingMeasure.P.frameType) final XFrameType frameType,
			@Prop(XGlazingMeasure.P.glazingType) final XGlazingType glazingType,
			@Prop(XGlazingMeasure.P.insulationType) final XWindowInsulationType insulationType,
			@Prop(XGlazingMeasure.P.airGap) final XWindowGlazingAirGap airGap
	) {
		return factory.createGlazingInsulationMeasure(
					capitalCostFunction.or(fns.createPricingFunction(id)), 
					uValue, 
					lightTransmittance, 
					gainsTransmittance, 
					frameFactor, 
					MapEnum.frameType(frameType), 
					MapEnum.glazingType(glazingType),
					MapEnum.windowInsulationType(insulationType),
					MapEnum.windowGlazingAirGap(airGap));
	}
	
	@Adapt(XDraughtProofingMeasure.class)
	public IComponentsAction createDraughtProofingMeasure (
			final Name id,
			@Prop(XDraughtProofingMeasure.P.capex) final Optional<IComponentsFunction<Number>> capitalCostFunction,
			@Prop(XDraughtProofingMeasure.P.proportion) final double proportion
	) {
		return factory.createDraughtProofingMeasure(capitalCostFunction.or(fns.createPricingFunction(id)), proportion);
	}
	
	@Adapt(XModifyUValueAction.class)
	public IComponentsAction buildModifyUValueAction(
			@Prop(XModifyUValueAction.P.uValue) final double uValue
			) {
		return factory
				.createUValueModifier(uValue);
	}
	
	@Adapt(XModifyWallConstructionTypeAction.class)
	public IComponentsAction buildModifyWallConstructionTypeAction(
			@Prop(XModifyWallConstructionTypeAction.P.wallType) final WallConstructionType wallType
			) {
		return factory.createWallConstructionTypeModifier(wallType);
	}
	
	@Adapt(XModifyWallInsulationAction.class)
	public IComponentsAction buildModifyWallInsulationAction(
			@Prop(XModifyWallInsulationAction.P.insulationthickness) final IComponentsFunction<? extends Number> thickness) {
		return factory.createModifyWallInsulationAction(thickness);
	}
	
	@Adapt(XModifyFloorInsulationAction.class)
	public IComponentsAction buildModifyFloorInsulationAction(
			@Prop(XModifyFloorInsulationAction.P.insulationthickness) final IComponentsFunction<? extends Number> thickness,
			@Prop(XModifyFloorInsulationAction.P.uvalue) final Optional<IComponentsFunction<? extends Number>> uvalue
			) {
		return factory.createModifyFloorInsulationAction(thickness, uvalue);
	}
	
	@Adapt(XModifyLoftInsulationAction.class)
	public IComponentsAction buildModifyLoftInsulationAction(
			@Prop(XModifyLoftInsulationAction.P.insulationthickness) final IComponentsFunction<? extends Number> thickness,
			@Prop(XModifyLoftInsulationAction.P.uvalue) final Optional<IComponentsFunction<? extends Number>> uvalue
			) {
		return factory.createModifyLoftInsulationAction(thickness, uvalue);
	}

	@Adapt(XFloorInsulationMeasure.class)
	public IComponentsAction buildFloorInsulationMeasure
		(
			final Name identity,
			@Prop(XFloorInsulationMeasure.P.type) final XFloorInsulationMeasure.XFloorType floorType,
			@Prop(XInsulationMeasure.P.thickness) final double thickness,
			@Prop(XInsulationMeasure.P.capex) final Optional<IComponentsFunction<Number>> capex,
			@Prop(XInsulationMeasure.P.uValueFunction) final Optional<IComponentsFunction<Number>> uValueFunction,
			@Prop(XInsulationMeasure.P.resistanceFunction) final Optional<IComponentsFunction<Number>> resistanceFunction
		) {
		return factory.createFloorInsulationMeasure(capex.or(fns.createPricingFunction(identity)),
													resistanceFunction,
													uValueFunction,
													thickness,
													floorType == XFloorInsulationMeasure.XFloorType.Solid);
	}
	
	@Adapt(XSetLoftAction.class)
	public IComponentsAction buildSetLoftAction(final XSetLoftAction theAction) {
		return factory.createAddOrRemoveLoftAction(theAction.isAddLoft());
	}
	
	@Adapt(XAdjustNumberOfAirChangeDevices.class)
    public IComponentsAction buildAdjustNumberOfAirChangeDevicesMeasure(
            @Prop(XAdjustNumberOfAirChangeDevices.P.adjustment) final int adjustment,
            @Prop(XAdjustNumberOfAirChangeDevices.P.airChangeDevice) final XAdjustNumberOfAirChangeDevices.XAirChangeDevice device
            ) {
        return factory.createAdjustNumberOfAirChangeDevicesMeasure(adjustment,device);
    }
}
