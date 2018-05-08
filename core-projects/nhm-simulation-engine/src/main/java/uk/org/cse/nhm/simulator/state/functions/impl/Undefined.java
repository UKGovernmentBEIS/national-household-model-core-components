package uk.org.cse.nhm.simulator.state.functions.impl;

import java.util.Collections;
import java.util.Set;

import org.joda.time.DateTime;

import uk.org.cse.commons.names.Name;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public class Undefined {

    public static <T> IComponentsFunction<T> get(final IProfilingStack stack, final String error) {
        return new IComponentsFunction<T>() {
            @Override
            public Name getIdentifier() {
                return Name.of("Undefined");
            }

            @Override
            public T compute(final IComponentsScope scope, final ILets lets) {
                throw stack.die("Undefined value evaluated for " + error, null, scope);
            }

            @Override
            public Set<IDimension<?>> getDependencies() {
                return Collections.emptySet();
            }

            @Override
            public Set<DateTime> getChangeDates() {
                return Collections.emptySet();
            }
        };
    }
}
