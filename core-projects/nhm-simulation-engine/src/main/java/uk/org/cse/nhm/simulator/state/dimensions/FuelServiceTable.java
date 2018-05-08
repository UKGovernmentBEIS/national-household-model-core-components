package uk.org.cse.nhm.simulator.state.dimensions;

import java.util.Arrays;

import com.google.common.collect.Interner;
import com.google.common.collect.Interners;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;

/**
 * Lots of parts of the model hold tables of number by fuel by service. This is
 * a class which holds that kind of table, and tries to be memory and time
 * efficient about it. It is intended to be immutable.
 */
public class FuelServiceTable {

    /**
     * The main table; first dimension is on fueltype.ordinal, second is on
     * servicetype.ordinal. If there is no usage by a certain fueltype, the row
     * will be null, rather than all zeroes
     */
    private final float[][] byFuelByService;

    /**
     * A precomputed hashcode.
     */
    private final int hash;

    /* These two tables are caches, which are filled out as needed if anyone asks. */
    private float[] byFuel;
    private float[] byService;

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder {

        /**
         * To reduce memory use, we deduplicate these tables.
         */
        private static final Interner<FuelServiceTable> cache
                = Interners.newWeakInterner();

        private final float[][] byFuelByService;

        protected Builder() {
            byFuelByService = new float[FuelType.values().length][];
        }

        public void add(final FuelType ft, final ServiceType st, final double value) {
            if (value == 0) {
                return;
            }
            if (byFuelByService[ft.ordinal()] == null) {
                byFuelByService[ft.ordinal()] = new float[ServiceType.values().length];
            }
            byFuelByService[ft.ordinal()][st.ordinal()] += value;
        }

        public FuelServiceTable build() {
            return cache.intern(new FuelServiceTable(byFuelByService));
        }
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public boolean equals(final Object other) {
        if (other == this) {
            return true;
        }
        if (other instanceof FuelServiceTable) {
            final float[][] otherValues = ((FuelServiceTable) other).byFuelByService;
            for (int i = 0; i < FuelType.values().length; i++) {
                if (!Arrays.equals(byFuelByService[i], otherValues[i])) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private FuelServiceTable(final float[][] byFuelByService) {
        this.byFuelByService = byFuelByService;

        // precompute hash, as we will definitely want it.
        final int prime = 31;
        int result = 1;
        for (int i = 0; i < this.byFuelByService.length; ++i) {
            result = prime * result + Arrays.hashCode(this.byFuelByService[i]);
        }

        this.hash = result;
    }

    public float get(final FuelType ft) {
        if (byFuelByService[ft.ordinal()] == null) {
            return 0;
        }
        if (byFuel == null) {
            byFuel = new float[FuelType.values().length];
            for (int fi = 0; fi < byFuelByService.length; fi++) {
                if (byFuelByService[fi] != null) {
                    for (final double d : byFuelByService[fi]) {
                        byFuel[fi] += d;
                    }
                }
            }
        }
        return byFuel[ft.ordinal()];
    }

    public float get(final ServiceType st) {
        if (byService == null) {
            byService = new float[ServiceType.values().length];
            for (int fi = 0; fi < byFuelByService.length; fi++) { // for each fuel
                if (byFuelByService[fi] == null) {
                    continue;
                }
                for (int si = 0; si < byFuelByService[fi].length; si++) {
                    byService[si] += byFuelByService[fi][si];
                }
            }
        }
        return byService[st.ordinal()];
    }

    public float get(final FuelType ft, final ServiceType st) {
        final float[] byFuel = byFuelByService[ft.ordinal()];
        if (byFuel == null) {
            return 0f;
        }
        return byFuel[st.ordinal()];
    }
}
