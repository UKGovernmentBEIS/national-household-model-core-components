package uk.org.cse.nhm.simulator.state.functions.impl.house;

import javax.inject.Inject;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.hom.emf.util.ITechnologyOperations;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchMainHeatingFuel extends MainHeatingFuelFunction<Boolean> implements IComponentsFunction<Boolean> {

    private final FuelType fuel;

    @Inject
    public MatchMainHeatingFuel(final ITechnologyOperations operations, final IDimension<ITechnologyModel> bad, @Assisted final FuelType fuel) {
        super(operations, bad);
        this.fuel = fuel;
    }

    @Override
    public Boolean compute(final IComponentsScope scope, final ILets lets) {
        return fuel.equals(getMainHeatingFuel(scope));
    }
}
