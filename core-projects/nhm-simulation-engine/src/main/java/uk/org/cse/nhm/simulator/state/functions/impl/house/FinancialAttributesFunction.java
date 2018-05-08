package uk.org.cse.nhm.simulator.state.functions.impl.house;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.hom.money.FinancialAttributes;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.state.IComponents;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public abstract class FinancialAttributesFunction<T> extends AbstractNamed
        implements IComponentsFunction<T> {

    final IDimension<FinancialAttributes> bad;

    public FinancialAttributesFunction(IDimension<FinancialAttributes> bad) {
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

    protected FinancialAttributes getAttributes(final IComponents components) {
        return components.get(bad);
    }
}
