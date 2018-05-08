package uk.org.cse.nhm.language.builder.batch.inputs.distributions;

import java.util.Random;

public class Triangular extends Distribution {

    private final Double start;
    private final Double end;
    private final Double peak;
    private final double peakProportion;

    public Triangular(final Random random, final String placeholder, final Double a, final Double b, final Double peak) {
        super(random, placeholder);
        this.start = Math.min(a, b);
        this.end = Math.max(a, b);
        this.peak = peak;

        this.peakProportion = (peak - start) / (end - start);
    }

    @Override
    protected Double nextRandom(final Random random) {
        final double uniform = random.nextDouble();

        return uniform < peakProportion
                ? start + Math.sqrt(uniform * (end - start) * (peak - start))
                : end - Math.sqrt((1 - uniform) * (end - start) * (end - peak));
    }
}
