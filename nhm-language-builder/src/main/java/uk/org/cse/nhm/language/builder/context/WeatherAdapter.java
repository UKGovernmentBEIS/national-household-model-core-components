package uk.org.cse.nhm.language.builder.context;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableList;
import com.google.common.primitives.Doubles;
import com.google.inject.Injector;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.Adapt;
import uk.org.cse.nhm.language.adapt.impl.Prop;
import uk.org.cse.nhm.language.adapt.impl.ReflectingAdapter;
import uk.org.cse.nhm.language.definition.context.XWeatherCase;
import uk.org.cse.nhm.language.definition.context.XWeatherCase.XWeatherWhen;
import uk.org.cse.nhm.language.definition.context.XWeatherConstant;
import uk.org.cse.nhm.language.definition.context.XWeatherContext;
import uk.org.cse.nhm.language.definition.context.XWeatherPerturbation;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.factories.IObjectFunctionFactory;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.main.Initializable;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.components.impl.Weather;
import uk.org.cse.nhm.simulator.state.dimensions.IFunctionDimension;
import uk.org.cse.nhm.simulator.state.dimensions.weather.IWeather;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;
import uk.org.cse.nhm.simulator.state.functions.impl.ConstantComponentsFunction;
import uk.org.cse.nhm.types.MonthType;

/**
 * This handles the {@link XWeatherContext} context parameter and its contents.
 * 
 * It has a small evil bit, where it sets a value into the weather function dimension.
 * 
 * 
 * @author hinton
 *
 */
public class WeatherAdapter extends ReflectingAdapter {
	private final Injector injector;
	private final IObjectFunctionFactory functionFactory;

	@Inject
	public WeatherAdapter(final Set<IConverter> delegates, 
			final Injector injector,
			final IObjectFunctionFactory functionFactory, final Set<IAdapterInterceptor> interceptors) {
		super(delegates, interceptors);
		this.injector = injector;
		this.functionFactory = functionFactory;
	}
	
	public static final class WeatherSetter implements Initializable  {
		private final IFunctionDimension<IWeather> weatherFunction;
		
		private IComponentsFunction<IWeather> value;
		
		@Inject
		public WeatherSetter(final IFunctionDimension<IWeather> weatherFunction) {
			this.weatherFunction = weatherFunction;
		}

		@Override
		public void initialize() throws NHMException {
			weatherFunction.setFunction(value);
		}

		public void setWeatherFunction(final IComponentsFunction<IWeather> weatherFunction) {
			this.value = weatherFunction;
		}
	}
	
	@Adapt(XWeatherContext.class)
	public Initializable buildWeatherContext(
			final Name name,
			@Prop(XWeatherContext.P.weather) final IComponentsFunction<IWeather> weather
			) {
		final WeatherSetter ws = injector.getInstance(WeatherSetter.class);
		ws.setWeatherFunction(weather);
		
		return ws;
	}
	
	@Adapt(XWeatherConstant.class)
	public ConstantComponentsFunction<IWeather> buildWeatherConstant(
			final Name name,
			@Prop(XWeatherConstant.P.temperature) final List<Double> temperature,
			@Prop(XWeatherConstant.P.windspeed) final List<Double> windspeed,
			@Prop(XWeatherConstant.P.insolation) final List<Double> insolation) {
		final IWeather weather = new Weather(
				Doubles.toArray(temperature),
				Doubles.toArray(insolation),
				Doubles.toArray(windspeed));
		return new ConstantComponentsFunction<IWeather>(name, weather);
	}
	
	@Adapt(XWeatherCase.class)
	public IComponentsFunction<IWeather> buildWeatherCase(
			@Prop(XWeatherCase.P.def) final IComponentsFunction<IWeather> defaultWeather,
			@Prop(XWeatherCase.P.cases) final List<WeatherAndTest> cases) {
		final ImmutableList.Builder<IComponentsFunction<Boolean>> tests = ImmutableList.builder();
		final ImmutableList.Builder<IComponentsFunction<IWeather>> weathers = ImmutableList.builder();
		
		for (final WeatherAndTest when : cases) {
			tests.add(when.test);
			weathers.add(when.weather);
		}
		
		return functionFactory.createWeatherCondition(tests.build(), weathers.build(), defaultWeather);
	}
	
	@Adapt(XWeatherCase.XWeatherWhen.class)
	public WeatherAndTest buildWeatherWhen(
			@Prop(XWeatherWhen.P.condition) final IComponentsFunction<Boolean> test,
			@Prop(XWeatherWhen.P.weather) final IComponentsFunction<IWeather> weather) {
		return new WeatherAndTest(weather, test);
	}
	
	static class WeatherAndTest {
		public final IComponentsFunction<IWeather> weather;
		public final IComponentsFunction<Boolean> test;
		
		public WeatherAndTest(final IComponentsFunction<IWeather> weather,
				final IComponentsFunction<Boolean> test) {
			this.weather = weather;
			if (test == null) {
				this.test = IComponentsFunction.TRUE;
			} else {
				this.test = test;
			}
		}
	}
	
	@Adapt(XWeatherPerturbation.class)
	public IComponentsFunction<IWeather> buildWeatherPerturbation(
			@Prop(XWeatherPerturbation.P.weather) final IComponentsFunction<IWeather> weather,
			@Prop(XWeatherPerturbation.P.offsetInsolation) final double offsetInsolation,
			@Prop(XWeatherPerturbation.P.offsetWindspeed) final double offsetWindSpeed,
			@Prop(XWeatherPerturbation.P.offsetTemperature) final double offsetTemperature,
			@Prop(XWeatherPerturbation.P.scaleInsolation) final double scaleInsolation,
			@Prop(XWeatherPerturbation.P.scaleWindspeed) final double scaleWindSpeed,
			@Prop(XWeatherPerturbation.P.scaleTemperature) final double scaleTemperature
			) {
		return new PerturbWeather(weather, offsetInsolation, offsetWindSpeed, offsetTemperature, scaleInsolation, scaleWindSpeed, scaleTemperature);
	}
	
	static class PerturbWeather extends AbstractNamed implements IComponentsFunction<IWeather> {
		private final IComponentsFunction<IWeather> weatherFunction;
		private final double offsetInsolation;
		private final double offsetTemperature;
		private final double offsetWindSpeed;
		private final double scaleInsolation;
		private final double scaleWindSpeed;
		private final double scaleTemperature;

		public PerturbWeather(
				final IComponentsFunction<IWeather> weatherFunction,
				final double offsetInsolation,
				final double offsetWindSpeed,
				final double offsetTemperature,
				final double scaleInsolation,
				final double scaleWindSpeed,
				final double scaleTemperature) {
					this.weatherFunction = weatherFunction;
					this.offsetInsolation = offsetInsolation;
					this.offsetWindSpeed = offsetWindSpeed;
					this.offsetTemperature = offsetTemperature;
					this.scaleInsolation = scaleInsolation;
					this.scaleWindSpeed = scaleWindSpeed;
					this.scaleTemperature = scaleTemperature;
		}

		@Override
		public IWeather compute(final IComponentsScope scope, final ILets lets) {
			final IWeather weather = weatherFunction.compute(scope, lets);
			return new IWeather() {

				@Override
				public double getExternalTemperature(final MonthType month) {
					return perturb(
							weather.getExternalTemperature(month),
							scaleTemperature,
							offsetTemperature
					);
				}

				@Override
				public double getHorizontalSolarFlux(final MonthType month) {
					return perturb(
							weather.getHorizontalSolarFlux(month),
							scaleInsolation,
							offsetInsolation
					);
				}

				@Override
				public double getWindSpeed(final MonthType month) {
					return perturb(
							weather.getWindSpeed(month),
							scaleWindSpeed,
							offsetWindSpeed
					);
				}
				
				private double perturb(final double original, final double scaling, final double offset) {
					return (original * (1 + scaling)) + offset;
				}
			};
		}

		@Override
		public Set<IDimension<?>> getDependencies() {
			return weatherFunction.getDependencies();
		}

		@Override
		public Set<DateTime> getChangeDates() {
			return weatherFunction.getChangeDates();
		}
	}
}
