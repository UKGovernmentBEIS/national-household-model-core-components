package uk.org.cse.nhm.housepropertystore.build;

import java.util.Set;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;

import uk.org.cse.nhm.housepropertystore.guice.IHousePropertyFunctionFactory;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.function.bool.house.XHousePropertyIs;
import uk.org.cse.nhm.language.definition.function.house.XHouseProperty;
import uk.org.cse.nhm.language.definition.function.num.XHousePropertyNumber;
import uk.org.cse.nhm.simulator.impl.RequestedHouseProperties;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class HousePropertyAdapter extends ReflectingAdapter {
	private final IHousePropertyFunctionFactory factory;
	private final Provider<RequestedHouseProperties> requestedProperties;

	@Inject
	public HousePropertyAdapter(final Set<IConverter> delegates, final IHousePropertyFunctionFactory factory, final Provider<RequestedHouseProperties> requestedProperties, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.factory = factory;
		this.requestedProperties = requestedProperties;
	}
	
	@Adapt(XHousePropertyNumber.class)
	public IComponentsFunction<Number> buildHousePropertyNumber(
		@Prop(XHousePropertyNumber.P.name) final String name_) {
		final String name = name_.toLowerCase();
		
		requestedProperties.get().request(name);
		
		return factory.createNumber(name);
	}
	
	@Adapt(XHouseProperty.class)
	public IComponentsFunction<?> buildHousePropertyCategoryFunction(
		@Prop(XHouseProperty.P.name) final String name_) {
		final String name = name_.toLowerCase();
		
		requestedProperties.get().request(name);
		
		return factory.createCategory(name);
	}
	
	@Adapt(XHousePropertyIs.class)
	public IComponentsFunction<Boolean> buildHousePropertyFunction(
			@Prop(XHousePropertyIs.P.name) final String name_,
			@Prop(XHousePropertyIs.P.equalTo) final Optional<String> value,
			@Prop(XHousePropertyIs.P.above) final Optional<Double> lower,
			@Prop(XHousePropertyIs.P.below) final Optional<Double> higher
			) {
		final String name = name_.toLowerCase();
		
		requestedProperties.get().request(name);
		
		if (value.isPresent()) {
			final String valueComparison = value.get();
			Double valueAsDouble = null;
			final Double doubleComparison;
			try {
				valueAsDouble = Double.parseDouble(valueComparison);
			} catch (final NumberFormatException nfe) {
			}
			doubleComparison = valueAsDouble;
			return factory.createFunction(
					name, 
					new Predicate<String>() {
						@Override
						public boolean apply(@Nullable final String arg0) {
							if (arg0 == null) return false;
							if (valueComparison.equals(arg0)) {
								return true;
							} else if (doubleComparison != null) {
								try {
									final double v = Double.parseDouble(arg0);
									return v == doubleComparison;
								} catch (final NumberFormatException nfe) {
								}
							}
							return false;
						}
						
						@Override
						public String toString() {
							return "{} = " + valueComparison;
						}
					});
		} else if (lower.isPresent() || higher.isPresent()) {
			final double lowerBound = lower.or(Double.NEGATIVE_INFINITY);
			final double upperBound = higher.or(Double.POSITIVE_INFINITY);
			
			return factory.createFunction(
					name, 
					new Predicate<String>() {
						@Override
						public boolean apply(@Nullable final String arg0) {
							if (arg0 == null) return false;
							try {
								final double d = Double.parseDouble(arg0);
								
								return d > lowerBound && d < upperBound;
							} catch (final NumberFormatException nfe) {
								return false;
							}
						}
						
						@Override
						public String toString() {
							return String.format("%.2f < {} < %.2f", lowerBound, upperBound);
						}
					});
		} else {
			return IComponentsFunction.TRUE;
		}
	}
}
