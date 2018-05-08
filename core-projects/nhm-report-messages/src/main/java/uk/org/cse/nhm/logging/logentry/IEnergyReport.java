package uk.org.cse.nhm.logging.logentry;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;

public interface IEnergyReport {

    /**
     * @param service
     * @return the energy used by service between {@link #getDate()} and
     * {@link #getUntil()}, in kWh
     */
    double getEnergyUsedByService(final ServiceType service);

    /**
     * @param fuel
     * @return the amount of energy used as fuel, between {@link #getDate()} and
     * {@link #getUntil()}, in kWh
     */
    double getEnergyUsedByFuel(final FuelType fuel);
}
