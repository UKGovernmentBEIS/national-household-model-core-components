package uk.org.cse.nhm.simulator.guice;

import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

import uk.org.cse.nhm.simulator.state.dimensions.energy.EnergyCalculatorBridge;

public class ConfigurationModule extends AbstractModule {

    private final int cacheSize;

    public ConfigurationModule(final int cacheSize) {
        super();
        this.cacheSize = cacheSize;
    }

    @Override
    protected void configure() {
        bindConstant().annotatedWith(Names.named(EnergyCalculatorBridge.CACHE_SIZE)).to(cacheSize);
    }

}
