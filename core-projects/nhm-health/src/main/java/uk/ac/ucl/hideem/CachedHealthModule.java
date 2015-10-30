package uk.ac.ucl.hideem;

import java.util.List;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.ExecutionException;

import com.google.common.base.Supplier;

import com.google.common.cache.LoadingCache;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;

/*
 * Wraps an IHealthModule in a LoadingCache
 */
public class CachedHealthModule implements IHealthModule {
    private static class K {
        public final Supplier<? extends HealthOutcome> supplier;
        public final double t1;
        public final double t2;
        public final double p1;
        public final double p2;
        public final double h1;
        public final double h2;
        public final BuiltForm.Type form;
        public final double floorArea;
        public final BuiltForm.Region region;
        public final int mainFloorLevel;
        public final boolean hasWorkingExtractorFans;
        public final boolean hasTrickleVents;
        public final boolean hasDoubleGlazing;
        public final boolean hadDoubleGlazing;
        public final List<Person> people;

        public K(final Supplier<? extends HealthOutcome> supplier,
                 final double t1,
                 final double t2,
                 final double p1,
                 final double p2,
                 final double h1,
                 final double h2,
                 final BuiltForm.Type form,
                 final double floorArea,
                 final BuiltForm.Region region,
                 final int mainFloorLevel,
                 final boolean hasWorkingExtractorFans,
                 final boolean hasTrickleVents,
                 final boolean hadDoubleGlazing,
                 final boolean hasDoubleGlazing,
                 final List<Person> people) {
            this.supplier = supplier;
            this.people = people;
            this.hadDoubleGlazing = hadDoubleGlazing;
            this.hasDoubleGlazing = hasDoubleGlazing;
            this.hasTrickleVents = hasTrickleVents;
            this.hasWorkingExtractorFans = hasWorkingExtractorFans;
            this.mainFloorLevel = mainFloorLevel;
            this.region = region;
            this.floorArea = floorArea;
            this.form = form;
            this.h2 = h2;
            this.h1 = h1;
            this.p2 = p2;
            this.p1 = p1;
            this.t2 = t2;
            this.t1 = t1;
        }
    }

    protected final IHealthModule delegate;
    private final LoadingCache<K, HealthOutcome> outcome =
        CacheBuilder.newBuilder()
        .maximumSize(5000)
        .expireAfterAccess(10, TimeUnit.MINUTES)
        .build(new CacheLoader<K, HealthOutcome>() {
                public HealthOutcome load(final K key) {
                    return delegate.effectOf(key.supplier,
                                             key.t1, key.t2,
                                             key.p1, key.p2,
                                             key.h1, key.h2,

                                             key.form,
                                             key.floorArea,
                                             key.region,
                                             key.mainFloorLevel,
                                             key.hasWorkingExtractorFans,
                                             key.hasTrickleVents,
                                             key.hadDoubleGlazing,
                                             key.hasDoubleGlazing,
                                             key.people);
                }
            })
        ;

    public CachedHealthModule(final IHealthModule delegate) {
        this.delegate = delegate;
    }

    public CachedHealthModule() {
        this(new HealthModule());
    }

    @Override
    public <T extends HealthOutcome> T effectOf(Supplier<T> supplier,
                                                double t1, double t2,
                                                double p1, double p2,
                                                double h1, double h2,

                                                BuiltForm.Type form,
                                                double floorArea,
                                                BuiltForm.Region region,
                                                int mainFloorLevel,
                                                boolean hasWorkingExtractorFans,
                                                boolean hasTrickleVents,
                                                boolean hasDoubleGlazing,
                                                boolean hadDoubleGlazing,
                                                List<Person> people) {
        try {
            return (T) outcome.get(new K(supplier,
                                         t1, t2, p1, p2, h1, h2,
                                         form, floorArea, region,
                                         mainFloorLevel, hasWorkingExtractorFans,
                                         hasTrickleVents, hasDoubleGlazing, hadDoubleGlazing,
                                         people));
        } catch (final ExecutionException ex) {
            throw new RuntimeException("Error in health calculation: " + ex.getMessage(), ex);
        }
    }

    @Override
    public double getInternalTemperature(double specificHeat,
                                         double efficiency) {
        return delegate.getInternalTemperature(specificHeat, efficiency);
    }

    @Override
    public double getRebateDeltaTemperature(double baseTemperature, double rebate) {
        return delegate.getRebateDeltaTemperature(baseTemperature, rebate);
    }
}
