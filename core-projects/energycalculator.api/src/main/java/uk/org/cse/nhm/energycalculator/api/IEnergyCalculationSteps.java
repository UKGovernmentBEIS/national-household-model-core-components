package uk.org.cse.nhm.energycalculator.api;

import uk.org.cse.nhm.energycalculator.api.types.steps.EnergyCalculationStep;

public interface IEnergyCalculationSteps {
    /**
     * @return an intermediate step in the energy calculation for a particular month
     */
    double readStepMonthly(EnergyCalculationStep step, int month);

    /**
     * @return an intermediate step in the energy calculation. If this step is recorded monthly: apply the appropriate aggregation function.
     */
    double readStepAnnual(EnergyCalculationStep step);
}
