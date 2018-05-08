package uk.org.cse.nhm.language.builder.batch.inputs.distributions;

import java.util.List;
import java.util.Random;

public class Discrete extends Distribution {

    private final List<WeightedChoice> weightedChoices;
    private final int totalWeights;

    public Discrete(Random random, String placeholder, List<WeightedChoice> weightedChoices) {
        super(random, placeholder);
        this.weightedChoices = weightedChoices;

        int totalWeights = 0;
        for (WeightedChoice choice : weightedChoices) {
            totalWeights += choice.weight;
        }
        this.totalWeights = totalWeights;

        if (this.totalWeights <= 0) {
            throw new IllegalArgumentException(String.format("Weights for a discrete distribution added up to %d. Must always be an integer greater than 0.", this.totalWeights));
        }
    }

    @Override
    protected Object nextRandom(Random random) {
        int uniform = random.nextInt(totalWeights) + 1;

        int accum = 0;
        for (WeightedChoice choice : weightedChoices) {
            accum += choice.weight;
            if (accum >= uniform) {
                return choice.value;
            }
        }

        throw new RuntimeException(String.format("Failed to draw from a discrete distribution because the uniformly drawn random %d was greater than the total weight of all the choices %d.", uniform, this.weightedChoices));
    }

    public static class WeightedChoice {

        private final Object value;
        private final int weight;

        public WeightedChoice(Object value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }
}
