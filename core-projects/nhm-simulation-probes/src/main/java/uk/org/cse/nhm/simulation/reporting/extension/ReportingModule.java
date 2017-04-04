package uk.org.cse.nhm.simulation.reporting.extension;

import com.google.inject.AbstractModule;
import com.google.inject.Key;
import com.google.inject.PrivateModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;

import uk.org.cse.nhm.language.adapt.IAdapter;
import uk.org.cse.nhm.language.builder.guice.ReflectingAdapterModule;
import uk.org.cse.nhm.simulation.reporting.probes.IProbeCollector;
import uk.org.cse.nhm.simulation.reporting.probes.ProbeCollector;
import uk.org.cse.nhm.simulation.reporting.standard.EnergyAggregator;
import uk.org.cse.nhm.simulation.reporting.standard.InstallationDetailsLogger;
import uk.org.cse.nhm.simulation.reporting.standard.MeasureCostLogger;
import uk.org.cse.nhm.simulation.reporting.standard.StockSizeAggregator;
import uk.org.cse.nhm.simulation.reporting.state.BasicAttributesFlattener;
import uk.org.cse.nhm.simulation.reporting.state.EmissionsFlattener;
import uk.org.cse.nhm.simulation.reporting.state.EnergyFlattener;
import uk.org.cse.nhm.simulation.reporting.state.IComponentFlattener;
import uk.org.cse.nhm.simulation.reporting.state.StateChangeLogger;
import uk.org.cse.nhm.simulator.guice.SimulationScoped;

/**
 * The probe module. This depends on having {@link ISimulationLogger} bound, but doesn't bind it.
 * 
 * Please make sure you either bind one globally or use a SimulationProperty to inject it when
 * you run the simulator.
 * 
 * @author hinton
 *
 */
public class ReportingModule extends AbstractModule {
    private <T> Key<IAdapter> adaptFactory(Class<T> clazz) {
        final ReflectingAdapterModule<T> m = new ReflectingAdapterModule<>(clazz);
        install(m);
        return m.getBinding();
    }
    
	@Override
	protected void configure() {
        final Multibinder<IAdapter>  adapters = Multibinder.newSetBinder(binder(), IAdapter.class);
		adapters.addBinding().to(ReportingAdapter.class);
		adapters.addBinding().to(AggregatesAdapter.class);
		adapters.addBinding().to(adaptFactory(IReportingFactory.class));
        
		install(new PrivateModule() {
			@Override
			protected void configure() {
				bind(IProbeCollector.class).to(ProbeCollector.class).in(SimulationScoped.class);
				
				install(new FactoryModuleBuilder().build(IReportingFactory.class));
				expose(IReportingFactory.class);
			}
		});
		
		final Multibinder<IComponentFlattener> flatteners = Multibinder.newSetBinder(binder(), IComponentFlattener.class);

		flatteners.addBinding().to(BasicAttributesFlattener.class).in(SimulationScoped.class);
		flatteners.addBinding().to(EnergyFlattener.class).in(SimulationScoped.class);
		flatteners.addBinding().to(EmissionsFlattener.class).in(SimulationScoped.class);
		
		bind(StateChangeLogger.class).in(SimulationScoped.class);
		
		bind(InstallationDetailsLogger.class).in(SimulationScoped.class);
		bind(StockSizeAggregator.class).in(SimulationScoped.class);
		bind(EnergyAggregator.class).in(SimulationScoped.class);
		bind(MeasureCostLogger.class).in(SimulationScoped.class);
	}
}
