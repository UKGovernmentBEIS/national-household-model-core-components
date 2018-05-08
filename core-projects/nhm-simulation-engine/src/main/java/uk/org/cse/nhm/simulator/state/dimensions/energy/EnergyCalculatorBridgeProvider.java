package uk.org.cse.nhm.simulator.state.dimensions.energy;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Provider;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import uk.org.cse.nhm.energycalculator.api.IConstants;
import uk.org.cse.nhm.energycalculator.api.impl.DefaultConstants;
import uk.org.cse.nhm.energycalculator.impl.EnergyCalculatorCalculator;
import uk.org.cse.nhm.simulator.guice.EnergyCalculationRequestedSteps;
import uk.org.cse.nhm.simulator.guice.StateModule;

public class EnergyCalculatorBridgeProvider implements Provider<EnergyCalculatorBridge> {

    private final LoadingCache<IConstants, EnergyCalculatorBridge> cache;
    private final Provider<IConstants> constantsProvider;

    @Inject
    public EnergyCalculatorBridgeProvider(final Provider<IConstants> constantsProvider,
            final EnergyCalculationRequestedSteps requestedSteps,
            @Named(EnergyCalculatorBridge.CACHE_SIZE) final int cacheSize) {
        this.constantsProvider = constantsProvider;
        this.cache = CacheBuilder.newBuilder()
                .softValues()
                .recordStats()
                .maximumSize(3)
                .expireAfterAccess(2, TimeUnit.HOURS)
                .build(new CacheLoader<IConstants, EnergyCalculatorBridge>() {
                    @Override
                    public EnergyCalculatorBridge load(final IConstants key) throws Exception {
                        return new EnergyCalculatorBridge(new EnergyCalculatorCalculator(key), requestedSteps, cacheSize);
                    }
                });
    }

    @Override
    public EnergyCalculatorBridge get() {
        try {
            return getFromCache(constantsProvider.get());
        } catch (final ExecutionException e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

    public EnergyCalculatorBridge getFromCache(IConstants constants) throws ExecutionException {
        return this.cache.get(constants);
    }

    /**
     * This is here solely to allow {@link StateModule} to offer other things an
     * energy calculator bridge which a) comes from the same cache as above b)
     * definitely has defaultconstants
     *
     * Without this we cannot guarantee a) (although we could do b)
     *
     */
    public static class WithDefaultConstants implements Provider<EnergyCalculatorBridge> {

        private EnergyCalculatorBridgeProvider realProvider;

        @Inject
        public WithDefaultConstants(final EnergyCalculatorBridgeProvider realProvider) {
            this.realProvider = realProvider;
        }

        @Override
        public EnergyCalculatorBridge get() {
            try {
                return realProvider.getFromCache(DefaultConstants.INSTANCE);
            } catch (ExecutionException e) {
                throw new RuntimeException(e.getMessage(), e);
            }
        }

    }
}
