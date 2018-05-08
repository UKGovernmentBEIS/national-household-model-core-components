package uk.org.cse.nhm.logging.logentry;

import java.util.Map;

import org.joda.time.DateTime;
import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.ImmutableMap;

import uk.org.cse.nhm.energycalculator.api.types.ServiceType;
import uk.org.cse.nhm.hom.emf.technologies.FuelType;

/**
 * Stores national energy consumption by fuel type and by service type, for an
 * interval of time.
 *
 * Use {@link #getDate()} to get the start of the interval, and
 * {@link #getUntil()} to get the end,
 *
 * and {@link #getEnergyUsedByFuel(FuelType)} to get the total amount of the
 * given fuel used (kWh), and {@link #getEnergyUsedByService(ServiceType)} for
 * the same by service
 *
 * @author hinton
 *
 */
@AutoProperty
public class NationalEnergyLogEntry extends AbstractDatedLogEntry implements IEnergyReport {

    private final DateTime until;
    private final Map<ServiceType, Double> byService;
    private final Map<FuelType, Double> byFuel;

    @JsonCreator
    public NationalEnergyLogEntry(
            @JsonProperty("date") DateTime date,
            @JsonProperty("until") DateTime until,
            @JsonProperty("byService") Map<ServiceType, Double> byService,
            @JsonProperty("byFuel") Map<FuelType, Double> byFuel) {
        super(date);
        this.until = until;

        this.byService = ImmutableMap.copyOf(byService);
        this.byFuel = ImmutableMap.copyOf(byFuel);
    }

    public DateTime getUntil() {
        return until;
    }

    public Map<FuelType, Double> getByFuel() {
        return byFuel;
    }

    public Map<ServiceType, Double> getByService() {
        return byService;
    }

    @JsonIgnore
    public double getEnergyUsedByService(final ServiceType service) {
        return byService.containsKey(service) ? byService.get(service) : 0;
    }

    @JsonIgnore
    public double getEnergyUsedByFuel(final FuelType fuel) {
        return byFuel.containsKey(fuel) ? byFuel.get(fuel) : 0;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }

    @Override
    public boolean equals(Object obj) {
        return Pojomatic.equals(this, obj);
    }

    @Override
    public int hashCode() {
        return Pojomatic.hashCode(this);
    }
}
