package uk.org.cse.nhm.energycalculator.api;

import java.util.Map;

import uk.org.cse.nhm.energycalculator.api.types.AreaType;

public interface IEnergyCalculationResult {

    /**
     * @return the energy state, which details fuel use by energy service
     */
    public IEnergyState getEnergyState();

    /**
     * @return the heat loss details for the house
     */
    public ISpecificHeatLosses getHeatLosses();

    /**
     * @return a map from area type to the total area (m2) of that area type
     * encountered.
     */
    public Map<AreaType, Double> getHeatLossAreas();

    /**
     * @return a map from area type to the total heat loss for which that area
     * type is responsible.
     */
    public Map<AreaType, Double> getHeatLossByArea();
}
