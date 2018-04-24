package uk.org.cse.nhm.language.builder.action.measure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import uk.org.cse.nhm.energycalculator.api.types.LightType;
import uk.org.cse.nhm.hom.emf.technologies.AdjusterType;
import uk.org.cse.nhm.hom.emf.technologies.IAdjuster;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologiesFactory;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.builder.function.MapEnum;
import uk.org.cse.nhm.language.definition.action.measure.adjust.XAddAdjustmentAction;
import uk.org.cse.nhm.language.definition.action.measure.adjust.XAdjustment;
import uk.org.cse.nhm.language.definition.action.measure.adjust.XClearAdjustmentsAction;
import uk.org.cse.nhm.language.definition.action.measure.adjust.XRemoveAdjustmentAction;
import uk.org.cse.nhm.language.definition.action.measure.adjust.XSetAdjustmentTerms;
import uk.org.cse.nhm.language.definition.action.measure.adjust.XSetAdjustmentTerms.XAdjustmentType;
import uk.org.cse.nhm.language.definition.action.measure.lighting.XLightingMeasure;
import uk.org.cse.nhm.language.definition.action.measure.lighting.XLightingProportionsMeasure;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.enums.XLightType;
import uk.org.cse.nhm.simulation.measure.adjustment.SetAdjustmentTermsMeasure;
import uk.org.cse.nhm.simulation.measure.factory.IMeasureFactory;
import uk.org.cse.nhm.simulation.measure.lighting.LightingProportionMeasure;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class LightingAndApplianceMeasureAdapter extends ReflectingAdapter {
	private final IMeasureFactory factory;

	@Inject
	public LightingAndApplianceMeasureAdapter(final Set<IConverter> delegates, final IMeasureFactory factory, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.factory = factory;
	}
	
	@Adapt(XLightingMeasure.class)
	public IComponentsAction buildLowEnergyLightingMeasure(
		@Prop(XLightingMeasure.P.from) final List<XLightType> from,
		@Prop(XLightingMeasure.P.to) final XLightType to,
		@Prop(XLightingMeasure.P.capex) final IComponentsFunction<Number> capex) {
		final List<LightType> mappedTypes = new ArrayList<>();
		for (final XLightType type : from) mappedTypes.add(MapEnum.lightType(type));
		return factory.createLowEnergyLightingMeasure(mappedTypes, MapEnum.lightType(to), capex);
	}
	
	@Adapt(XLightingProportionsMeasure.class)
    public LightingProportionMeasure createLightingProportionMeasure(
    		@Prop(XLightingProportionsMeasure.P.proportionOfCfl) final IComponentsFunction<Number> proportionOfCfl,
			@Prop(XLightingProportionsMeasure.P.proportionOfIcandescent) final IComponentsFunction<Number> proportionOfIcandescent,
			@Prop(XLightingProportionsMeasure.P.proportionOfHAL) final IComponentsFunction<Number> propotionOfHAL,
			@Prop(XLightingProportionsMeasure.P.proportionOfLED) final IComponentsFunction<Number> proportionOfLED,
			@Prop(XLightingProportionsMeasure.P.proportionOfLVHAL) final IComponentsFunction<Number> proportionOfLVHAl,
			@Prop(XLightingProportionsMeasure.P.proportionOfAPlusPlus) final IComponentsFunction<Number> proportionOfAPlusPlus
    		){
				
		return factory.createLightingProportionMeasure(proportionOfCfl, proportionOfIcandescent, propotionOfHAL, proportionOfLED, proportionOfLVHAl, proportionOfAPlusPlus);
	}
	
	@Adapt(XSetAdjustmentTerms.class)
	public SetAdjustmentTermsMeasure buildSetAdjustmentTermsAction(
	        @Prop(XSetAdjustmentTerms.P.adjustmentType) final XAdjustmentType adjustmentType,
	        @Prop(XSetAdjustmentTerms.P.constantTerm) final IComponentsFunction<Number> constantTerm,
	        @Prop(XSetAdjustmentTerms.P.linearFactor) final IComponentsFunction<Number> linearFactor
            ){

	    AdjusterType adjusterType = null;
	    switch (adjustmentType) {
            case Appliances:
                adjusterType = AdjusterType.APPLIANCE;
                break;
            case Cooking:
                adjusterType = AdjusterType.COOKER;
                break;
	    }	    
	    
	    return factory.createSetAdjustmentTermsMeasure(adjusterType,constantTerm, linearFactor);
	}
	
	@Adapt(XAddAdjustmentAction.class)
	public IComponentsAction buildAddAdjustmentAction(
			@Prop(XAddAdjustmentAction.P.adjustment) final IAdjuster prototype
			) {
		return factory.createAdjustmentMeasure(prototype, true);
	}
	
	@Adapt(XRemoveAdjustmentAction.class)
	public IComponentsAction buildRemoveAdjustmentAction(
			@Prop(XAddAdjustmentAction.P.adjustment) final IAdjuster prototype
			) {
		return factory.createAdjustmentMeasure(prototype, false);
	}
	
	@Adapt(XClearAdjustmentsAction.class)
	public IComponentsAction buildClearAdjustmentsAction(
			) {
		return factory.createClearAdjustmentsMeasure();
	}
	
	private final double KWH_PER_MONTH_TO_WATTS = 1.3689;
	
	@Adapt(XAdjustment.class)
	public IAdjuster buildAdjustment(
			final XAdjustment adjustment
			) {
		final IAdjuster adjuster = ITechnologiesFactory.eINSTANCE.createAdjuster();
		adjuster.setName(adjustment.getName());
		adjuster.setGains(adjustment.getMissedGains() * KWH_PER_MONTH_TO_WATTS);
		
		final Iterator<XFuelType> fti = adjustment.getFuelTypes().iterator();
		final Iterator<Double> vi = adjustment.getValues().iterator();
		while (fti.hasNext() && vi.hasNext()) {
			adjuster.getFuelTypes().add(MapEnum.fuel(fti.next()));
			adjuster.getDeltas().add(vi.next() * KWH_PER_MONTH_TO_WATTS);
		}
		return adjuster;
	}
	
	@Adapt(XAdjustment.class)
	public Initializable ignoreDeclaration() {
		return Initializable.NOP;
	}
}
