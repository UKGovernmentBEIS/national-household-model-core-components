package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;

import java.util.*;

public class StepRecorder{
    private static ThreadLocal<Steps> currentSteps = new ThreadLocal<>();

    public static void recordStep(EnergyCalculationStep step, double value) {
        Steps s = currentSteps.get();
        if (s == null) return;
        s.recordStep(step, value);
    }

    public static class Steps implements AutoCloseable, IEnergyCalculationSteps  {
        private final Set<EnergyCalculationStep> requiredSteps = EnumSet.noneOf(EnergyCalculationStep.class);
        private final Map<EnergyCalculationStep, List<Double>> values = new EnumMap<EnergyCalculationStep, List<Double>>(EnergyCalculationStep.class);

        public Steps(Collection<EnergyCalculationStep> record) { requiredSteps.addAll(record); }

        @Override
        public void close() {
            currentSteps.remove();
            verifyRecording();
        }

        private void verifyRecording() {
            for (EnergyCalculationStep step : requiredSteps) {
                if (values.containsKey(step)) {
                    List<Double> stepValues = values.get(step);
                    if (step.isMonthly()) {
                        if (stepValues.size() != 12) {
                            throw new RuntimeException(
                                    "Monthly step " + step + " should have had 12 entries, but had " + stepValues
                            );
                        }
                    } else {
                        if (stepValues.size() != 1) {
                            throw new RuntimeException(
                                    "Annual step " + step + " should have had 1 entry, but had " + stepValues
                            );
                        }
                    }

                } else if (!step.hasDefault()) {
                    throw new RuntimeException("Energy calculator failed to record requested step " + step);
                }
            }
        }

        void recordStep(EnergyCalculationStep step, double value) {
            if (requiredSteps.contains(step)) {
                if (!values.containsKey(step)) {
                    values.put(step, new ArrayList<Double>(step.isMonthly() ? 12 : 1));
                }
                values.get(step).add(value);
            }
        }

        @Override
        public double readStepAnnual(EnergyCalculationStep step) {
            return step.convertAnnual(readStep(step));
        }

        @Override
        public double readStepMonthly(EnergyCalculationStep step, int month) {
            if(!step.isMonthly()) {
                throw new IllegalArgumentException("Asked for a monthly value for annual step " + step);
            }
            return step.convertMonthly(readStep(step), month);
        }

        private List<Double> readStep(EnergyCalculationStep step) {
            if (requiredSteps.contains(step)) {
                if (values.containsKey(step)) {
                    return values.get(step);
                } else if (step.hasDefault()) {
                    return step.getDefault();
                } else {
                    throw new IllegalArgumentException("StepRecorderer was asked for a value from a step which was never recorded: " + step);
                }
            } else {
                throw new IllegalArgumentException("StepRecorderer was asked for a value from a step it wasn't asked to store " + step);
            }
        }
    }

    public static Steps record(Collection<EnergyCalculationStep> record) {
        if (record.isEmpty()) {
            final Steps noSteps = new Steps(record) {
                @Override
                void recordStep(EnergyCalculationStep step, double value) {
                    // Noop
                }
            };
            currentSteps.set(null);
            return noSteps;

        } else {
            final Steps out = new Steps(record);
            currentSteps.set(out);
            return out;
        }
    }
}
