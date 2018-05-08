package uk.org.cse.nhm.logging.logentry.components;

import java.util.Map;

import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;

/**
 * A pojo for storing emissions/fuel/service
 *
 * @author hinton
 *
 */
@AutoProperty
public class EmissionsLogComponent extends AbstractFuelServiceLogComponent {

    @JsonCreator
    public EmissionsLogComponent(@JsonProperty("values") Map<FuelType, Map<ServiceType, Double>> values) {
        super(values);
    }

    @JsonIgnore
    public double getEmissions() {
        return getTotalValue();
    }

    @JsonIgnore
    public double getEmissions(final ServiceType service) {
        return getValue(service);
    }

    @JsonIgnore
    public double getEmissions(final ServiceType service, final FuelType fule) {
        return getValue(service, fule);
    }

    @JsonIgnore
    public double getEmissions(final FuelType fuel) {
        return getValue(fuel);
    }
}
