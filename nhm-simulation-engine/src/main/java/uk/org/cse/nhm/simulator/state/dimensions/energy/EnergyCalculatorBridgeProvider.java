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
import uk.org.cse.nhm.energycalculator.impl.EnergyCalculatorCalculator;

public class EnergyCalculatorBridgeProvider implements Provider<EnergyCalculatorBridge> {
	private final LoadingCache<IConstants, EnergyCalculatorBridge> cache;
	private final Provider<IConstants> constantsProvider;

	@Inject
	public EnergyCalculatorBridgeProvider(final Provider<IConstants> constantsProvider, 
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
						return new EnergyCalculatorBridge(new EnergyCalculatorCalculator(key), cacheSize);
					}
				   });
	}

	@Override
	public EnergyCalculatorBridge get() {
		try {
			return this.cache.get(constantsProvider.get());
		} catch (final ExecutionException e) {
			throw new RuntimeException(e.getMessage(), e);
		}
	}
}
