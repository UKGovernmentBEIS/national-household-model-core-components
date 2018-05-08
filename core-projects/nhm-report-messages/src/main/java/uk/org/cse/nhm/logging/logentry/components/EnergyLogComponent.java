package uk.org.cse.nhm.logging.logentry.components;

import java.util.Map;

import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.logging.logentry.IEnergyReport;

/**
 * A pojo for storing energy/fuel/service
 *
 * @author hinton
 *
 */
@AutoProperty
public class EnergyLogComponent extends AbstractFuelServiceLogComponent implements IEnergyReport {

    @JsonCreator
    public EnergyLogComponent(@JsonProperty("values") Map<FuelType, Map<ServiceType, Double>> values) {
        super(values);
    }

    @JsonIgnore
    public double getEnergyUsage() {
        return getTotalValue();
    }

    @JsonIgnore
    public double getEnergyUsedByService(final ServiceType service) {
        return getValue(service);
    }

    @JsonIgnore
    public double getEnergyUsage(final ServiceType service, final FuelType fuel) {
        return getValue(service, fuel);
    }

    @JsonIgnore
    public double getEnergyUsedByFuel(final FuelType fuel) {
        return getValue(fuel);
    }
}
