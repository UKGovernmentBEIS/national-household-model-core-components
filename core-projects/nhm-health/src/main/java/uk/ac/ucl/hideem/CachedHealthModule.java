package uk.ac.ucl.hideem;

import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;

import com.google.common.base.Supplier;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;

import uk.org.cse.nhm.energycalculator.api.types.RegionType;
import uk.org.cse.nhm.hom.types.BuiltFormType;

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
        public final BuiltFormType form;
        public final double floorArea;
        public final RegionType region;
        public final int mainFloorLevel;
        public final boolean hadWorkingExtractorFans; // per finwhatever
        public final boolean hadTrickleVents;         // this is cooked up elsewhere
        public final boolean hasWorkingExtractorFans; // per finwhatever
        public final boolean hasTrickleVents;         // this is cooked up elsewhere
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
                 final BuiltFormType form,
                 final double floorArea,
                 final RegionType region,
                 final int mainFloorLevel,
                 final boolean hadWorkingExtractorFans, // per finwhatever
                 final boolean hadTrickleVents,         // this is cooked up elsewhere
                 final boolean hasWorkingExtractorFans, // per finwhatever
                 final boolean hasTrickleVents,
                 final boolean hadDoubleGlazing,
                 final boolean hasDoubleGlazing,
                 final List<Person> people) {
            this.supplier = supplier;
            this.people = people;
            this.hadDoubleGlazing = hadDoubleGlazing;
            this.hasDoubleGlazing = hasDoubleGlazing;
            this.hadTrickleVents = hadTrickleVents;
            this.hadWorkingExtractorFans = hadWorkingExtractorFans;
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
                @Override
				public HealthOutcome load(final K key) {
                    return delegate.effectOf(key.supplier,
                                             key.t1, key.t2,
                                             key.p1, key.p2,
                                             key.h1, key.h2,

                                             key.form,
                                             key.floorArea,
                                             key.region,
                                             key.mainFloorLevel,
                                             key.hadWorkingExtractorFans,
                                             key.hadTrickleVents,
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
    public <T extends HealthOutcome> T effectOf(final Supplier<T> supplier,
                                                final double t1, final double t2,
                                                final double p1, final double p2,
                                                final double h1, final double h2,

                                                final BuiltFormType form,
                                                final double floorArea,
                                                final RegionType region,
                                                final int mainFloorLevel,
                                                final boolean hadWorkingExtractorFans,
                                                final boolean hadTrickleVents,
                                                final boolean hasWorkingExtractorFans,
                                                final boolean hasTrickleVents,
                                                final boolean hasDoubleGlazing,
                                                final boolean hadDoubleGlazing,
                                                final List<Person> people) {
        try {
            return (T) outcome.get(new K(supplier,
                                         t1, t2, p1, p2, h1, h2,
                                         form, floorArea, region,
                                         mainFloorLevel, hadWorkingExtractorFans,
                                         hadTrickleVents,hasWorkingExtractorFans,
                                         hasTrickleVents, hasDoubleGlazing, hadDoubleGlazing,
                                         people));
        } catch (final ExecutionException ex) {
            throw new RuntimeException("Error in health calculation: " + ex.getMessage(), ex);
        }
    }

    @Override
    public double getInternalTemperature(final double specificHeat,
                                         final double efficiency) {
        return delegate.getInternalTemperature(specificHeat, efficiency);
    }

    @Override
    public double getRebateDeltaTemperature(final double baseTemperature, final double rebate) {
        return delegate.getRebateDeltaTemperature(baseTemperature, rebate);
    }
}
