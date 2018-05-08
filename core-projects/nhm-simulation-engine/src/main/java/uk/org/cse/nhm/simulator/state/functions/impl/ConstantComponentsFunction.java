package uk.org.cse.nhm.simulator.state.functions.impl;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ConstantComponentsFunction<T> extends AbstractNamed implements IComponentsFunction<T> {

    private final T value;

    public ConstantComponentsFunction(final Name name, final T value) {
        setIdentifier(name);
        this.value = value;
    }

    @Override
    public T compute(IComponentsScope scope, ILets lets) {
        return value;
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return Collections.emptySet();
    }

    @Override
    public Set<DateTime> getChangeDates() {
        return Collections.emptySet();
    }

    public static <T> IComponentsFunction<T> of(final Name name, T price) {
        return new ConstantComponentsFunction<T>(name, price);
    }

    public T getValue() {
        return value;
    }
}
