package uk.org.cse.nhm.language.builder.action.measure;

import java.util.Set;

import javax.inject.Inject;

import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.action.measure.renewable.XSolarHotWaterMeasure;
import uk.org.cse.nhm.language.definition.action.measure.renewable.XSolarPhotovoltaicMeasure;
import uk.org.cse.nhm.simulation.measure.factory.IMeasureFactory;
import uk.org.cse.nhm.simulator.measure.sizing.ISizingFunction;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class RenewablesMeasureAdapter extends ReflectingAdapter {
	final IMeasureFactory factory;
	@Inject
	public RenewablesMeasureAdapter(final Set<IConverter> delegates, final IMeasureFactory factory, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.factory = factory;
	}
	
	@Adapt(XSolarHotWaterMeasure.class)
	public IComponentsAction buildSolarHotWaterMeasure(
			@Prop(XSolarHotWaterMeasure.P.area)           final IComponentsFunction<Number> installedArea,
			@Prop(XSolarHotWaterMeasure.P.cost)           final IComponentsFunction<Number> installationCost,
			@Prop(XSolarHotWaterMeasure.P.cylinderVolume) final IComponentsFunction<Number> cylinderVolume,
            @Prop(XSolarHotWaterMeasure.P.zle)            final IComponentsFunction<Number> zle,
            @Prop(XSolarHotWaterMeasure.P.lhlc)           final IComponentsFunction<Number> lhlc
			) {
		return factory.createSolarHotWaterMeasure(
				installedArea,
				installationCost,
				cylinderVolume,
                zle,
                lhlc);
	}
	
	@Adapt(XSolarPhotovoltaicMeasure.class)
	public IComponentsAction buildSolarPhotovoltaicMeasure(
			@Prop(XSolarPhotovoltaicMeasure.P.sizing) final ISizingFunction sizingFunction,
			@Prop(XSolarPhotovoltaicMeasure.P.capex) final IComponentsFunction<Number> capex,
			@Prop(XSolarPhotovoltaicMeasure.P.ownUseProportion) final IComponentsFunction<Number> ownUse
			) {
		return factory.createSolarPhotovoltaicMeasure(sizingFunction, capex, ownUse);
	}
}
