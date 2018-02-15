package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.EnergyCalculationStep;

import java.io.Closeable;
import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

public class StepRecorder {
    private static ThreadLocal<Steps> currentSteps = new ThreadLocal<>();

    public static void recordStep(EnergyCalculationStep step, double value) {
        Steps s = currentSteps.get();
        if (s == null) return;
        s.recordStep(step, value);
    }

    public static double readStep(EnergyCalculationStep step) {
        Steps s = currentSteps.get();
        if (s == null) return Double.NaN;
        return s.readStep(step);
    }

    public static class Steps implements AutoCloseable {
        private final Set<EnergyCalculationStep> requiredSteps = EnumSet.noneOf(EnergyCalculationStep.class);
        private final Map<EnergyCalculationStep, Double> values = new EnumMap<EnergyCalculationStep, Double>(EnergyCalculationStep.class);

        public Steps(EnumSet<EnergyCalculationStep> record) {
            requiredSteps.addAll(record);
        }

        @Override
        public void close() {
            currentSteps.remove();
        }

        void recordStep(EnergyCalculationStep step, double value) {
            if (requiredSteps.contains(step)) {
                if (values.containsKey(step)) {
                    throw new IllegalArgumentException("Energy Calculation Step Record was asked to set a step which has already been set " + step);
                } else {
                    values.put(step, value);
                }
            }
        }

        double readStep(EnergyCalculationStep step) {
            if (requiredSteps.contains(step)) {
                if (values.containsKey(step)) {
                    return values.get(step);
                } else {
                    if (step.hasDefault()) {
                        return step.getDefault();
                    } else {
                        // TODO: decide whether this should really be an error?
                        // It's useful for now, because we want to find out which ones don't get set.
                        throw new IllegalArgumentException("Energy Calculation Step Record was asked to get a step which was never set " + step);
                    }
                }
            } else {
                throw new IllegalArgumentException("Energy Calculation Step Record was asked for a value from a step it wasn't asked to store " + step);
            }
        }
    }

    public static Steps record(EnumSet<EnergyCalculationStep> record) {
        final Steps out = new Steps(record);
        currentSteps.set(out);
        return out;
    }
}
