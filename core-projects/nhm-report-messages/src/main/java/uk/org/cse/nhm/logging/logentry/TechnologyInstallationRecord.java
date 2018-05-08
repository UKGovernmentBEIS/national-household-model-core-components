package uk.org.cse.nhm.logging.logentry;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * A small class to log the installation of a technology. Used in
 * {@link CaseWeightChange} to explain case weight changes.
 *
 * @author hinton
 *
 */
@AutoProperty
public class TechnologyInstallationRecord extends AbstractLogEntry {

    private final String technologyName;
    private final String measureName;
    private final double quantityInstalled;
    private final String units;
    private final double installationCost;

    @JsonCreator
    public TechnologyInstallationRecord(
            @JsonProperty("technologyName") String technologyName,
            @JsonProperty("measureName") String measureName,
            @JsonProperty("quantityInstalled") double quantityInstalled,
            @JsonProperty("units") String units,
            @JsonProperty("installationCost") double installationCost) {
        super();
        this.technologyName = technologyName;
        this.measureName = measureName;
        this.quantityInstalled = quantityInstalled;
        this.units = units;
        this.installationCost = installationCost;
    }

    public String getTechnologyName() {
        return technologyName;
    }

    public String getMeasureName() {
        return measureName;
    }

    public double getQuantityInstalled() {
        return quantityInstalled;
    }

    public String getUnits() {
        return units;
    }

    public double getInstallationCost() {
        return installationCost;
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
