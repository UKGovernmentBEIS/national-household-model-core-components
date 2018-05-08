package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.collect.Iterables;
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

public class MatchTariff extends AbstractNamed implements IComponentsFunction<Boolean> {

    private final ITariff tariff;
    private final IDimension<ITariffs> tariffsDimension;
    private final FuelType tariffFuel;

    @AssistedInject
    public MatchTariff(@Assisted final ITariff tariff, final IDimension<ITariffs> tariffsDimension) {
        this.tariff = tariff;
        this.tariffsDimension = tariffsDimension;
        this.tariffFuel = Iterables.get(tariff.getFuelTypes(), 0);
    }

    @Override
    public Boolean compute(final IComponentsScope scope, final ILets lets) {
        return scope.get(tariffsDimension).getTariff(tariffFuel).equals(tariff);
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
