package uk.org.cse.nhm.language.builder;

import static uk.org.cse.nhm.simulator.SimulatorConfigurationConstants.DemandTemperature;
import static uk.org.cse.nhm.simulator.SimulatorConfigurationConstants.EndDate;
import static uk.org.cse.nhm.simulator.SimulatorConfigurationConstants.Granularity;
import static uk.org.cse.nhm.simulator.SimulatorConfigurationConstants.ProfilingDepth;
import static uk.org.cse.nhm.simulator.SimulatorConfigurationConstants.RandomSeed;
import static uk.org.cse.nhm.simulator.SimulatorConfigurationConstants.StartDate;
import static uk.org.cse.nhm.simulator.SimulatorConfigurationConstants.StockID;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.base.Function;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.TypeLiteral;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.api.impl.OverrideConstants;
import uk.org.cse.nhm.ipc.api.scenario.IStockService;
import uk.org.cse.nhm.language.adapt.IAdapter;
import uk.org.cse.nhm.language.adapt.IAdaptingScope;
import uk.org.cse.nhm.language.adapt.IConverter;
import uk.org.cse.nhm.language.adapt.impl.AdaptingScope;
import uk.org.cse.nhm.language.builder.function.MapEnum;
import uk.org.cse.nhm.language.builder.guice.InitializableListener;
import uk.org.cse.nhm.language.builder.profiler.ProfilingInterceptor;
import uk.org.cse.nhm.language.definition.IScenarioElement;
import uk.org.cse.nhm.language.definition.XElement;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.language.definition.context.XEnergyConstantsContext;
import uk.org.cse.nhm.language.definition.context.XEnergyConstantsContext.XEnergyConstant;
import uk.org.cse.nhm.language.two.build.IBuilder;
import uk.org.cse.nhm.language.visit.IVisitor;
import uk.org.cse.nhm.language.visit.impl.AdapterInstaller;
import uk.org.cse.nhm.simulator.SimulatorConfigurationConstants;
import uk.org.cse.nhm.simulator.guice.Eager;
import uk.org.cse.nhm.simulator.guice.SimpleScope;
import uk.org.cse.nhm.simulator.guice.SimulationScoped;
import uk.org.cse.nhm.simulator.impl.StockCreator;
import uk.org.cse.nhm.simulator.main.ISimulator;
import uk.org.cse.nhm.simulator.main.Initializable;

public class SimulatorBuilder implements ISimulatorBuilder {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory
			.getLogger(SimulatorBuilder.class);
	/**
	 * This is the guice injection scope for things annotated with {@link SimulationScoped}.
	 * It ensures that within this thread, during a call to build, we only make one of each
	 * {@link SimulationScoped} thing.
	 * 
	 * It is unrelated to the {@link IAdaptingScope}, which is not to do with dependency injection
	 */
	private final SimpleScope simulationScope;
	
	/**
	 * Visitor which adds all the adapters in {@link #adapters} into all the things it visits
	 * 
	 * TODO prevent cycles and repeated adding
	 */
	private final IVisitor<XElement> adapterVisitor; 
	
	private final Set<IAdapter> adapters;
	private final InitializableListener initializableListener;
	private final Injector injector;
	private final Set<IConverter> converters;
	
	@Inject
	public SimulatorBuilder(final SimpleScope simulationScope, final Injector injector, 
			final InitializableListener initializableListener, final Set<IAdapter> adapters,
			final Set<IConverter> converters) {
		this.simulationScope = simulationScope;
		this.injector = injector;
		this.initializableListener = initializableListener;
		this.adapters = adapters;
		this.adapterVisitor = new AdapterInstaller<XElement>(adapters);
		this.converters = converters;
		log.debug("Builder constructed with {} adapters", adapters.size());
	}
	
	@Override
	public Set<Class<?>> getAdaptableClasses() {
		final HashSet<Class<?>> result = new HashSet<Class<?>>();
		for (final IAdapter adapter : adapters) {
			result.addAll(adapter.getAdaptableClasses());
		}
		return result;
	}
	
	static class BuildParams implements IBuilder {
		private final DateTime startDate;
		private final DateTime endDate;
		
		public BuildParams(final DateTime startDate, final DateTime endDate) {
			super();
			this.startDate = startDate;
			this.endDate = endDate;
		}

		@Override
		public DateTime getStartDate() {
			return startDate;
		}

		@Override
		public DateTime getEndDate() {
			return endDate;
		}
	}
	
	@Override
	public ISimulator build(final XScenario scenario, 
			final String executionID, 
			final IStockService dataService,
			final SimulationParameter<?>... additionalParameters) throws NHMException {
		// add all the adapters into the scenario
		scenario.accept(adapterVisitor);
		
		try {
			// tell guice that we have started building a simulator
			simulationScope.enter();

            injector.getInstance(ProfilingInterceptor.class).setEnabled(scenario.getProfile() > 0);
            
			simulationScope.seed(IStockService.class, dataService);
			
			configureGlobalVariables(scenario, executionID);
			configureEnergyCalculatorConstants(scenario);
			
			for (final SimulationParameter<?> p : additionalParameters) {
				addParameter(p);
			}
			
			final BuildParams params = new BuildParams(scenario.getStartDate(), scenario.getEndDate());
			
			// construct eager objects
			@SuppressWarnings("unused")
			final Set<Object> eager = injector.getInstance(Key.get(new TypeLiteral<Set<Object>>(){}, Eager.class));
			
			final AdaptingScope adaptingScope = new AdaptingScope(converters);
			adaptingScope.putGlobal("CURRENT_BUILDER", params);
			final ISimulator sim = scenario.adapt(ISimulator.class, adaptingScope);
		
			/*for (final Initializable i : simulationScope.getInitializables()) {
				i.initialize();
			}*/
			
			injector.getInstance(StockCreator.class);
			
			for (final Initializable i : initializableListener.getInitializables()) {
				log.debug("about to initialize {}", i);
				i.initialize();
			}
			
			initializableListener.clear();
			
			return sim;
		} catch (final Throwable th) {
			// shutdown the scope on this thread
			simulationScope.exit();
			initializableListener.clear();
			if (th instanceof RuntimeException) throw (RuntimeException) th;
			else throw new RuntimeException("Exception caught building simulator", th);
		}
	}

	private <T> void addParameter(final SimulationParameter<T> p) {
		simulationScope.seed(p.getKey(), p.getValue());
	}
	
    private void configureGlobalVariables(final XScenario scenario, final String executionID) {
        simulationScope.seed(Key.get(Long.class, RandomSeed), scenario.getSeed());
		simulationScope.seed(Key.get(DateTime.class, StartDate), scenario.getStartDate());
		simulationScope.seed(Key.get(DateTime.class, EndDate), scenario.getEndDate());
        simulationScope.seed(Key.get(Integer.class, Granularity), scenario.getGranularity());
        simulationScope.seed(new Key<List<String>>(StockID) {}, scenario.getStockID());

		simulationScope.seed(Key.get(Double.class, DemandTemperature), scenario.getDemandTemperature());
        simulationScope.seed(Key.get(Integer.class, ProfilingDepth), scenario.getProfile());
        
		simulationScope.seed(Key.get(
				new TypeLiteral<Function<Double, List<Double>>>() {}, SimulatorConfigurationConstants.Weighting
				), scenario.getWeighting().getFunction(scenario.getGranularity()));
	}
	
	private void configureEnergyCalculatorConstants(final XScenario scenario) {
		simulationScope.seed(Key.get(IConstants.class), buildConstants(scenario));
	}

	/**
	 * Takes the energy calculator parameters out of the scenario's parameters context,
	 * and creates an {@link OverrideConstants} with them, or just returns {@link DefaultConstants#INSTANCE}
	 * if there are no constants defined in the scenario
	 * 
	 * @param scenario
	 * @return {@link IConstants} for the scenario
	 */
	private IConstants buildConstants(final XScenario scenario) {
		for (final IScenarioElement<?> element : scenario.getContents()) {
			if (element instanceof XEnergyConstantsContext) {
				final Map<Enum<?>, Object> values = new HashMap<Enum<?>, Object>();
				final XEnergyConstantsContext constants = (XEnergyConstantsContext) element;
				for (final XEnergyConstant constant : constants .getConstants()) {
					values.put(
							MapEnum.energyConstant(constant.getConstant()), 
							getConstantValue(constant.getValue()));
				}
				return new OverrideConstants(DefaultConstants.INSTANCE, values);
			}
		}

		return DefaultConstants.INSTANCE;
	}

	/**
	 * Turn the given string into a double, or a double array, or throw a number format exception.
	 * 
	 * @param value
	 * @return
	 */
	private Object getConstantValue(final String value) {
		final String[] parts = value.split(",");
		if (parts.length == 1) {
			return Double.parseDouble(parts[0]);
		} else {
			final double[] ds = new double[parts.length];
			for (int i = 0; i<parts.length; i++) {
				ds[i] = Double.parseDouble(parts[i]);
			}
			return ds;
		}
	}
}
