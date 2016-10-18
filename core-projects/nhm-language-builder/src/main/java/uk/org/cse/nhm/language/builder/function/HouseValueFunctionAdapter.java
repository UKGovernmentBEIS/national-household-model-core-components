package uk.org.cse.nhm.language.builder.function;

import java.util.Set;

import javax.inject.Inject;

import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.enums.XFuelType;
import uk.org.cse.nhm.language.definition.fuel.XGetMethodOfPayment;
import uk.org.cse.nhm.language.definition.fuel.XMethodOfPayment;
import uk.org.cse.nhm.language.definition.function.house.XInsolation;
import uk.org.cse.nhm.simulator.factories.IHouseValueFunctionFactory;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * Adapts the things which actually get enum values out of houses.
 * @author hinton
 *
 */
public class HouseValueFunctionAdapter extends ReflectingAdapter {
	final IHouseValueFunctionFactory factory;
	@Inject
	public HouseValueFunctionAdapter(final Set<IConverter> delegates, final IHouseValueFunctionFactory factory, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.factory = factory;
	}

	@Adapt(XInsolation.class)
	public IComponentsFunction<Number> buildInsolation(@Prop(XInsolation.P.orientation) final double orientation, 
                                                       @Prop(XInsolation.P.inclination) final double inclination) {
		return factory.getGetInsolation(orientation, inclination);
	}
	
	@Adapt(XGetMethodOfPayment.class)
	public IComponentsFunction<XMethodOfPayment> buildHouseMethodOfPayment(
			@Prop(XGetMethodOfPayment.P.fuel) final XFuelType fuelType
			) {
		return factory.createMethodOfPayment(MapEnum.fuel(fuelType));
	}
}
