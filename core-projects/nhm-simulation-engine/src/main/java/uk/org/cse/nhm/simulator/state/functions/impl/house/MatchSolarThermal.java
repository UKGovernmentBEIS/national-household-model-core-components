package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.inject.Inject;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class MatchSolarThermal extends AbstractNamed implements IComponentsFunction<Boolean> {

    private final IDimension<ITechnologyModel> techDimension;

    @Inject
    public MatchSolarThermal(final IDimension<ITechnologyModel> techDimension) {
        this.techDimension = techDimension;
    }

    @Override
    public Boolean compute(IComponentsScope scope, ILets lets) {
        return scope.get(techDimension).getCentralWaterSystem() != null
                && scope.get(techDimension).getCentralWaterSystem().getSolarWaterHeater() != null;
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.<IDimension<?>>singleton(techDimension);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }
}
