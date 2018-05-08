package uk.org.cse.nhm.simulator.guice;

import com.google.inject.AbstractModule;

public class EnergyCalculationStepModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(EnergyCalculationRequestedSteps.class).in(SimulationScoped.class);
    }
}
