package uk.org.cse.nhm.energycalculator.api;

public interface IEnergyCalculationResultWithSteps {
    IEnergyCalculationResult[] getResults();
    IEnergyCalculationSteps getSteps();
}
