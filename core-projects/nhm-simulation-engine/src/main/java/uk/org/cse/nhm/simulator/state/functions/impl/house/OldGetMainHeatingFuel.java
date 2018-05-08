package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;

public class OldGetMainHeatingFuel extends MainHeatingFuelFunction<FuelType> {

    @Inject
    public OldGetMainHeatingFuel(ITechnologyOperations operations, IDimension<ITechnologyModel> bad) {
        super(operations, bad);
    }

    @Override
    public FuelType compute(IComponentsScope scope, ILets lets) {
        return getMainHeatingFuel(scope);
    }
}
