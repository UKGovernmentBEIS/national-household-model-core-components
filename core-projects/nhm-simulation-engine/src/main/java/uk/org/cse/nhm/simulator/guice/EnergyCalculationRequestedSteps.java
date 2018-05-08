package uk.org.cse.nhm.simulator.guice;

import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;

import java.util.EnumSet;
import java.util.Set;

public class EnergyCalculationRequestedSteps {

    Set<EnergyCalculationStep> requestedSteps = EnumSet.noneOf(EnergyCalculationStep.class);
    // Not thread safe.
    boolean closed = false;

    public void request(EnergyCalculationStep step) {
        if (closed) {
            throw new RuntimeException("Tried to add extra steps to EnergyCalculationRequestedSteps, but it has already been used in an energy calcualtion.");
        }
        requestedSteps.add(step);
    }

    public Set<EnergyCalculationStep> getRequestedSteps() {
        if (!closed) {
            closed = true;
        }
        /**
         * We could take a defensive copy here, but it shouldn't be needed,
         * because registering all the scenario elements should happen before
         * the energy calculator is ever run.
         */
        return requestedSteps;
    }
}
