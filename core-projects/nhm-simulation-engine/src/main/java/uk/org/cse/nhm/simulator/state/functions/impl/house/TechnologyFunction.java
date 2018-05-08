package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.nhm.hom.emf.technologies.ITechnologyModel;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public abstract class TechnologyFunction<T> extends AbstractNamed implements IComponentsFunction<T> {

    final IDimension<ITechnologyModel> bad;

    public TechnologyFunction(IDimension<ITechnologyModel> bad) {
        this.bad = bad;
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.<IDimension<?>>singleton(bad);
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }

    protected ITechnologyModel getTechnologies(final IComponents components) {
        return components.get(bad);
    }
}
