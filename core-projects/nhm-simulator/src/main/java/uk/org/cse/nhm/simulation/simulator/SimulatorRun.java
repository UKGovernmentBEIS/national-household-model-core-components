package uk.org.cse.nhm.simulation.simulator;

import java.io.Reader;
import java.util.Arrays;

import com.google.common.collect.ImmutableList;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.housepropertystore.guice.HousePropertyModule;
import uk.org.cse.nhm.ipc.api.scenario.IStockService;
import uk.org.cse.nhm.ipc.api.tasks.report.ILogEntryHandler;
import uk.org.cse.nhm.language.builder.ISimulatorBuilder;
import uk.org.cse.nhm.language.builder.SimulationParameter;
import uk.org.cse.nhm.language.builder.SimulatorBuilder;
import uk.org.cse.nhm.language.builder.guice.BuilderModule;
import uk.org.cse.nhm.language.definition.XScenario;
import uk.org.cse.nhm.simulation.measure.guice.MeasuresModule;
import uk.org.cse.nhm.simulation.reporting.extension.ReportingModule;
import uk.org.cse.nhm.simulator.guice.ConfigurationModule;
import uk.org.cse.nhm.simulator.guice.EnergyCalculationStepModule;
import uk.org.cse.nhm.simulator.main.ISimulator;

/**
 * This is a useful class for those who want to start a simulator - it constructs a
 * {@link SimulatorBuilder} with a set of default modules that contain the normal
 * scenario elements of interest, and provides the {@link #createStandardParameters(LoggingService, HousePropertyStoreRepository, SimulationParameter...)}
 * method to illustrate the extra parameters to the {@link #build(XScenario, String, ISurveyCaseDataService, SimulationParameter...)} method
 * that the standard modules depend upon.
 *
 * It also provides {@link #parseScenario(Reader)}
 *
 * It also provides convenient access to the dependency injector (which is a bit hacky, but
 * useful in test cases), and the list of all known XML elements, for creating a JAX context,
 * generating documentation and so on.
 *
 * @author hinton
 *
 */
public class SimulatorRun {
	private final ISimulatorBuilder builder;

	private final Injector injector;

	/**
	 * This is a useful static method which makes a DI context with all the normal modules in,
	 * plus any extra modules (e.g. a debugging module) that you pass.
	 *
	 * This is the single source of truth about the language for most parts of the system, so
	 * if you make a new module, put it here.
	 * @param cacheSize
	 *
	 * @param extraModules
	 * @return An injector with standard modules + extra modules.
	 */
	public static Injector createStandardInjector(final int cacheSize, final Module... extraModules) {
		return Guice.createInjector(
				ImmutableList.<Module>builder()
						.add(new ConfigurationModule(cacheSize))
						.add(new BuilderModule())
						.add(new MeasuresModule())
						.add(new ReportingModule())
						.add(new HousePropertyModule())
						.add(new EnergyCalculationStepModule())
						.addAll(Arrays.asList(extraModules))
						.build());
	}

	/**
	 * The standard modules have certain standard parameters which are expected in
	 * {@link #build(XScenario, String, ISurveyCaseDataService, SimulationParameter...)}
	 * as the final argument. This method will create a parameter list with the required
	 * parameters in.
	 *
	 * It is a convenience, to make it easy to detect where parameters will need adding
	 * in the rest of the source - if you need to bind some other thing as a simulation scoped
	 * entity, because you have added a new module above which consumes something from
	 * outside, add it to here as well and the other places will break.
	 */
	public static SimulationParameter<?>[] createStandardParameters(
			final ILogEntryHandler loggingService,
			final SimulationParameter<?>... otherParameters
			) {
		// how many parameters are in the standard list - 2 at the moment
		final int K = 1;

		final SimulationParameter<?>[] result = new SimulationParameter<?>[K + otherParameters.length];

		result[0] = SimulationParameter.of(Key.get(ILogEntryHandler.class), loggingService);

		// pop in the extra ones
		for (int i = 0; i<otherParameters.length; i++) {
			result[i+K] = otherParameters[i];
		}

		return result;
	}

	public SimulatorRun(final int cacheSize, final Module ... extraModules) {
		this.injector = createStandardInjector(cacheSize, extraModules);
		this.builder = injector.getInstance(ISimulatorBuilder.class);
	}

	public ISimulator build(final XScenario scenario, final String executionID,
			final IStockService dataService, final SimulationParameter<?>... also) throws NHMException {
		return builder.build(scenario, executionID, dataService, also);
	}

	public <T> T getFromInjector(final Class<T> clazz) {
		return injector.getInstance(clazz);
	}
}
