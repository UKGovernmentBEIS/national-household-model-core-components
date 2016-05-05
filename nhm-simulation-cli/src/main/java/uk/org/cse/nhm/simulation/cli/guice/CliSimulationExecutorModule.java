package uk.org.cse.nhm.simulation.cli.guice;

import com.google.inject.PrivateModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

import uk.org.cse.nhm.ipc.api.scenario.IStockService;
import uk.org.cse.nhm.ipc.api.tasks.ISimulationExecutor;
import uk.org.cse.nhm.language.sexp.ScenarioParserFactory;
import uk.org.cse.nhm.simulation.cli.CliStockService;
import uk.org.cse.nhm.simulation.simulator.impl.SimulationExecutor;
import uk.org.cse.nhm.stock.io.StockJacksonModule;

/**
 * <p>News up a {@link CliStockService} using stockFileLocation
 * as the location of the JsonFile to create a stock, exposed as a {@link IStockService}.</p>
 * <p>News up a {@link ISimulationExecutor} and exposed this ready to run simulations against.</p>
 * 
 * @author richardt
 *
 */
public class CliSimulationExecutorModule extends PrivateModule {

	private final String stockFileLocation;
	
	public CliSimulationExecutorModule(final String stockFileLocation) {
		this.stockFileLocation = stockFileLocation;
	}
			
	@Override
	protected void configure() {
		install(new StockJacksonModule());
		
		bind(String.class).annotatedWith(Names.named(CliStockService.STOCKFILENAME)).toInstance(stockFileLocation);
		bind(IStockService.class).to(CliStockService.class).in(Scopes.SINGLETON);
		bind(ScenarioParserFactory.class).in(Scopes.SINGLETON);
		
		bindConstant().annotatedWith(Names.named(SimulationExecutor.MAXIMUM_CACHE_SIZE))
			.to(50000);
		bind(ISimulationExecutor.class).to(SimulationExecutor.class).in(Scopes.SINGLETON);
		
		expose(IStockService.class);
		expose(ISimulationExecutor.class);
	}
}
