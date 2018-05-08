package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.hom.emf.technologies.FuelType;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariff;
import uk.org.cse.nhm.simulator.state.dimensions.fuel.cost.ITariffs;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class GetTariff extends AbstractNamed implements IComponentsFunction<ITariff> {

    private final FuelType fuelType;
    private final IDimension<ITariffs> tariffsDimension;

    @AssistedInject
    public GetTariff(@Assisted FuelType fuelType, IDimension<ITariffs> tariffsDimension) {
        this.fuelType = fuelType;
        this.tariffsDimension = tariffsDimension;
    }

    @Override
    public ITariff compute(IComponentsScope scope, ILets lets) {
        return scope.get(tariffsDimension).getTariff(fuelType);
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.<IDimension<?>>singleton(tariffsDimension);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }
}
