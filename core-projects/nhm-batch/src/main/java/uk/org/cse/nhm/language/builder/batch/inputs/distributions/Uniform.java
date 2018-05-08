package uk.org.cse.nhm.language.builder.batch.inputs.distributions;

import java.util.Random;

public class Uniform extends Distribution {

    private final Double start;
    private final double range;

    public Uniform(Random random, String placeholder, Double start, Double end) {
        super(random, placeholder);
        this.start = start;
        this.range = end - start;
    }

    @Override
    protected Double nextRandom(Random random) {
        return (random.nextDouble() * range) + start;
    }
}
