package uk.org.cse.nhm.simulator.state.functions.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class ConditionComponentsFunction<T> extends AbstractNamed implements IComponentsFunction<T> {

    private final List<IComponentsFunction<Boolean>> conditions = new ArrayList<IComponentsFunction<Boolean>>();
    ;
	private final List<IComponentsFunction<T>> values = new ArrayList<IComponentsFunction<T>>();
    ;
	private final IComponentsFunction<T> defaultValue;
    private final Set<IDimension<?>> dependencies = new HashSet<IDimension<?>>();

    ;
	
	@Inject
    public ConditionComponentsFunction(
            @Assisted final List<IComponentsFunction<Boolean>> tests,
            @Assisted final List<IComponentsFunction<T>> values,
            @Assisted final IComponentsFunction<T> defaultValue) {
        this.defaultValue = defaultValue;
        for (int i = 0; i < tests.size(); i++) {
            addCondition(tests.get(i), values.get(i));
        }
    }

    public static class NumberCondition extends ConditionComponentsFunction<Number> {

        @Inject
        public NumberCondition(@Assisted final List<IComponentsFunction<Boolean>> tests,
                @Assisted final List<IComponentsFunction<Number>> values,
                @Assisted final IComponentsFunction<Number> defaultValue) {
            super(tests, values, defaultValue);
        }
    }

    private void addCondition(final IComponentsFunction<Boolean> condition, final IComponentsFunction<T> value) {
        conditions.add(condition);
        values.add(value);
        dependencies.addAll(condition.getDependencies());
        dependencies.addAll(value.getDependencies());
    }

    @Override
    public T compute(IComponentsScope scope, ILets lets) {
        final Iterator<IComponentsFunction<T>> valueFunctionIterator = values.iterator();
        for (final IComponentsFunction<Boolean> condition : conditions) {
            final IComponentsFunction<T> valueFunction = valueFunctionIterator.next();
            if (condition.compute(scope, lets)) {
                return valueFunction.compute(scope, lets);
            }
        }

        return defaultValue.compute(scope, lets);
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return dependencies;
    }

    @Override
    public Set<DateTime> getChangeDates() {
        final HashSet<DateTime> changes = new HashSet<DateTime>();

        for (final IComponentsFunction<Boolean> cond : conditions) {
            changes.addAll(cond.getChangeDates());
        }

        for (final IComponentsFunction<T> value : values) {
            changes.addAll(value.getChangeDates());
        }

        if (defaultValue != null) {
            changes.addAll(defaultValue.getChangeDates());
        }

        return changes;
    }
}
