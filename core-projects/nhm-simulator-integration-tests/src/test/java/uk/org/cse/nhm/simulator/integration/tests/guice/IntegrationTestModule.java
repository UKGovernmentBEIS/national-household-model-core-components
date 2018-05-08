package uk.org.cse.nhm.simulator.integration.tests.guice;

import com.google.inject.AbstractModule;
import com.google.inject.assistedinject.FactoryModuleBuilder;
import com.google.inject.multibindings.Multibinder;

import uk.org.cse.nhm.language.adapt.IAdapter;
import uk.org.cse.nhm.simulator.guice.SimulationScoped;

public class IntegrationTestModule extends AbstractModule {

    @Override
    protected void configure() {
        // make our integration test outputs singleton per sim
        bind(IntegrationTestOutput.class).in(SimulationScoped.class);
        install(new FactoryModuleBuilder().build(ITestingFactory.class));
        // add our adapter
        Multibinder.newSetBinder(binder(), IAdapter.class).addBinding().to(IntegrationAdapter.class);
    }

}
