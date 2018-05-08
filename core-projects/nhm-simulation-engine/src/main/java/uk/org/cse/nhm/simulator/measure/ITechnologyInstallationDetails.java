package uk.org.cse.nhm.simulator.measure;

import uk.org.cse.nhm.simulator.scope.IComponentsAction;

public interface ITechnologyInstallationDetails {

    public IComponentsAction getInstallationSource();

    public TechnologyType getInstalledTechnology();

    public double getInstalledQuantity();

    public Units getUnits();

    public double getInstallationCost();

    public double getOperationalCost();
}
