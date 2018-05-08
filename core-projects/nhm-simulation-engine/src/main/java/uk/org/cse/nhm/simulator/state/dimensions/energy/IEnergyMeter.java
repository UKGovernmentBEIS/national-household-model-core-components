package uk.org.cse.nhm.simulator.state.dimensions.energy;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;

/**
 *
 * A house's energy meter tracks the energy used by a house over time. This is
 * the integral of the {@link PowerDimension}'s value
 *
 * @since 3.8.0
 *
 */
public interface IEnergyMeter {

    /**
     * @param fuel
     * @return the accumulated energy use by the given type of fuel
     */
    public double getEnergyUseByFuel(final FuelType fuel);
}
