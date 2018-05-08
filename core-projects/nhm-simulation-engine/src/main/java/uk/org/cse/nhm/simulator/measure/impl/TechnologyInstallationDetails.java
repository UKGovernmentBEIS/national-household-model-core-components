package uk.org.cse.nhm.simulator.measure.impl;

import org.pojomatic.Pojomatic;
import org.pojomatic.annotations.AutoProperty;

import uk.org.cse.nhm.simulator.measure.ITechnologyInstallationDetails;
import uk.org.cse.nhm.simulator.measure.TechnologyType;
import uk.org.cse.nhm.simulator.measure.Units;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;

@AutoProperty
public class TechnologyInstallationDetails implements ITechnologyInstallationDetails {

    private final IComponentsAction installationSource;
    private final TechnologyType installedTechnology;
    private final double installedQuantity;
    private final double installationCost;
    private final double operationalCost;
    private final Units units;

    public TechnologyInstallationDetails(final IComponentsAction installationSource, final TechnologyType installedTechnology, final double installedQuantity, final Units units, final double cost, final double opex) {
        super();
        this.installationSource = installationSource;
        this.installedTechnology = installedTechnology;
        this.installedQuantity = installedQuantity;
        this.units = units;
        this.installationCost = cost;
        this.operationalCost = opex;
    }

    @Override
    public IComponentsAction getInstallationSource() {
        return installationSource;
    }

    @Override
    public TechnologyType getInstalledTechnology() {
        return installedTechnology;
    }

    @Override
    public double getInstalledQuantity() {
        return installedQuantity;
    }

    @Override
    public Units getUnits() {
        return units;
    }

    @Override
    public double getInstallationCost() {
        return installationCost;
    }

    @Override
    public double getOperationalCost() {
        return operationalCost;
    }

    @Override
    public String toString() {
        return Pojomatic.toString(this);
    }
}
