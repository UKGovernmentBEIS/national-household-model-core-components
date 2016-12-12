package uk.org.cse.nhm.language.builder.guice;

import java.lang.annotation.Annotation;
import java.util.List;

import org.joda.time.DateTime;

import com.google.common.base.Function;
import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.Provider;
import com.google.inject.Scopes;
import com.google.inject.TypeLiteral;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.matcher.Matchers;
import com.google.inject.multibindings.Multibinder;
import com.google.inject.name.Named;

import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculatorType;
import uk.org.cse.nhm.ipc.api.scenario.IStockService;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.language.adapt.IAdapter;
import uk.org.cse.nhm.language.adapt.IAdapterInterceptor;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.builder.HookAdapter;
import uk.org.cse.nhm.language.builder.ISimulatorBuilder;
import uk.org.cse.nhm.language.builder.SimulatorBuilder;
import uk.org.cse.nhm.language.builder.TopLevelAdapter;
import uk.org.cse.nhm.language.builder.action.ActionAdapter;
import uk.org.cse.nhm.language.builder.action.AutoFlagInterceptor;
import uk.org.cse.nhm.language.builder.action.FinanceAdapter;
import uk.org.cse.nhm.language.builder.action.LetAndChoiceAdapter;
import uk.org.cse.nhm.language.builder.action.RegisterAdapter;
import uk.org.cse.nhm.language.builder.context.CalibrationAdapter;
import uk.org.cse.nhm.language.builder.context.FuelPropertyAdapter;
import uk.org.cse.nhm.language.builder.context.ParametersAdapter;
import uk.org.cse.nhm.language.builder.context.WeatherAdapter;
import uk.org.cse.nhm.language.builder.convert.ActionConverter;
import uk.org.cse.nhm.language.builder.convert.TestToSetConverter;
import uk.org.cse.nhm.language.builder.exposure.ExposureAdapter;
import uk.org.cse.nhm.language.builder.function.HouseTestFunctionAdapter;
import uk.org.cse.nhm.language.builder.function.HouseValueFunctionAdapter;
import uk.org.cse.nhm.language.builder.function.LogicFunctionAdapter;
import uk.org.cse.nhm.language.builder.function.NumberFunctionAdapter;
import uk.org.cse.nhm.language.builder.group.GroupAdapter;
import uk.org.cse.nhm.language.builder.profiler.IProfilingFactory;
import uk.org.cse.nhm.language.builder.profiler.ProfilingInterceptor;
import uk.org.cse.nhm.language.builder.profiler.ProfilingStack;
import uk.org.cse.nhm.language.builder.reset.ResetActionsAdapter;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.factories.IBooleanFunctionFactory;
import uk.org.cse.nhm.simulator.factories.IHouseValueFunctionFactory;
import uk.org.cse.nhm.simulator.factories.IObjectFunctionFactory;
import uk.org.cse.nhm.simulator.guice.Eager;
import uk.org.cse.nhm.simulator.guice.SimpleScope;
import uk.org.cse.nhm.simulator.guice.SimulationEngineModule;
import uk.org.cse.nhm.simulator.guice.SimulationScoped;
import uk.org.cse.nhm.simulator.impl.StockLogger;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * This sets up the main adapters and the guice simulation scope, and installs into itself also the 
 * core sim extension which gives us the various factories that we need.
 * 
 * @author hinton
 *
 */
public class BuilderModule extends AbstractModule {
	protected static final Class<? extends Annotation> SimulationScope = SimulationScoped.class;

    private <T> Key<IAdapter> adaptFactory(Class<T> clazz) {
        final ReflectingAdapterModule<T> m = new ReflectingAdapterModule<>(clazz);
        install(m);
        return m.getBinding();
    }

	@Override
	protected void configure() {
		bind(ISimulatorBuilder.class).to(SimulatorBuilder.class).in(Scopes.SINGLETON);
		
		final SimpleScope simulationScope = new SimpleScope();
		
		bind(SimpleScope.class).toInstance(simulationScope);
		bindScope(SimulationScoped.class, simulationScope);
		
		final InitializableListener listener = new InitializableListener();
		bind(InitializableListener.class).toInstance(listener);
		
		bindListener(
				Matchers.any(),
				listener);
		
		final Multibinder<IAdapter> adapters = Multibinder.newSetBinder(binder(), IAdapter.class);
		final Multibinder<IConverter> converters = Multibinder.newSetBinder(binder(), IConverter.class);
		@SuppressWarnings("unused")
		final Multibinder<Object> eager = Multibinder.newSetBinder(binder(), Object.class, Eager.class);
		
		adapters.addBinding().to(TopLevelAdapter.class);
		adapters.addBinding().to(ActionAdapter.class);
		adapters.addBinding().to(WeatherAdapter.class);
		adapters.addBinding().to(FuelPropertyAdapter.class);
		adapters.addBinding().to(ExposureAdapter.class);
		adapters.addBinding().to(GroupAdapter.class);
		adapters.addBinding().to(HouseTestFunctionAdapter.class);
		adapters.addBinding().to(LogicFunctionAdapter.class);
		adapters.addBinding().to(NumberFunctionAdapter.class);
		adapters.addBinding().to(HouseValueFunctionAdapter.class);
		adapters.addBinding().to(ParametersAdapter.class);
		adapters.addBinding().to(FinanceAdapter.class);
		adapters.addBinding().to(LetAndChoiceAdapter.class);
		adapters.addBinding().to(RegisterAdapter.class);
		adapters.addBinding().to(CalibrationAdapter.class);
		adapters.addBinding().to(ResetActionsAdapter.class);
        adapters.addBinding().to(HookAdapter.class);
        
        adapters.addBinding().to(adaptFactory(IHouseValueFunctionFactory.class));
        adapters.addBinding().to(adaptFactory(IObjectFunctionFactory.class));
        adapters.addBinding().to(adaptFactory(IBooleanFunctionFactory.class));

        // make sure there is only one of these
        bind(ProfilingInterceptor.class).in(Scopes.SINGLETON);
        bind(IProfilingStack.class).to(ProfilingStack.class).in(SimulationScope);
        
		final Multibinder<IAdapterInterceptor> interceptors = Multibinder.newSetBinder(binder(), IAdapterInterceptor.class);

        interceptors.addBinding().to(ProfilingInterceptor.class);
		interceptors.addBinding().to(AutoFlagInterceptor.class);

        install(new FactoryModuleBuilder().build(IProfilingFactory.class));
        
        converters.addBinding().to(ActionConverter.class);
        converters.addBinding().to(TestToSetConverter.class);
		
		install(new SimulationEngineModule());
		
		install(new PrivateModule() {
			@Override
			protected void configure() {
				
				addEmptyBinding(DateTime.class, SimulatorConfigurationConstants.StartDate);
				addEmptyBinding(DateTime.class, SimulatorConfigurationConstants.EndDate);
				addEmptyBinding(Integer.class, SimulatorConfigurationConstants.Granularity);
				
                addEmptyBinding(Key.get(new TypeLiteral<List<String>>(){},
                                        SimulatorConfigurationConstants.StockID));
				
				addEmptyBinding(Double.class, SimulatorConfigurationConstants.DemandTemperature);
				addEmptyBinding(Long.class, SimulatorConfigurationConstants.RandomSeed);
                addEmptyBinding(Integer.class, SimulatorConfigurationConstants.ProfilingDepth);
				
				addEmptyBinding(Key.get(new TypeLiteral<Function<Double, List<Double>>>() {}, SimulatorConfigurationConstants.Weighting));
                addEmptyBinding(Key.get(new TypeLiteral<IComponentsFunction<Number>>() {}, SimulatorConfigurationConstants.SurveyWeightFunction));
                
                addEmptyBinding(Key.get(EnergyCalculatorType.class, SimulatorConfigurationConstants.EnergyCalculatorType));
				
				bind(IStockService.class).toProvider(new Provider<IStockService>() {

					@Override
					public IStockService get() {
						throw new RuntimeException("Provider for survey case dataservice used outside of build scope");
					}
					
				}).in(SimulationScope);
				
				bind(ILogEntryHandler.class).toProvider(
						ErrorProvider.<ILogEntryHandler>named("Log Entry Handler")).in(SimulationScope);
				
				expose(ILogEntryHandler.class);
				
				bind(StockLogger.class).in(SimulationScope);
				expose(StockLogger.class);
				
				expose(IStockService.class);
			}
			
			private <T> void addEmptyBinding(final Class<T> clazz, final Named name) {
				addEmptyBinding(Key.get(clazz, name));
			}
			
			private <T> void addEmptyBinding(final Key<T> key) {
				bind(key).toProvider(
						ErrorProvider.<T>named(key.toString())
						).in(SimulationScope);
				expose(key);
			}
		});
	}
}
