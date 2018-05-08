package uk.org.cse.nhm.simulator.sequence;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.joda.time.DateTime;

import com.google.common.base.Joiner;
import com.google.common.base.Optional;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;
import com.google.inject.assistedinject.AssistedInject;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.language.definition.sequence.XScope;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.IProfilingStack;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IHypotheticalComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IBranch.IModifier;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.state.components.IFlags;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

public abstract class ChangeValue extends AbstractNamed implements IComponentsAction, ISequenceSpecialAction {

    private final List<IComponentsAction> hypotheses;
    private final List<Variable> variables;
    private final List<IComponentsFunction<Number>> values;
    private final Set<String> variablesRequiringSettableScope;
    private final IProfilingStack profiler;
    protected final IDimension<IFlags> flagsDimension;

    protected ChangeValue(final IDimension<IFlags> flagsDimension,
            final IProfilingStack profiler,
            final List<Variable> variables,
            final List<IComponentsFunction<Number>> values,
            final List<IComponentsAction> hypotheses) {
        this.flagsDimension = flagsDimension;
        this.profiler = profiler;
        this.variables = ImmutableList.copyOf(variables);
        this.values = values.size() == 1
                ? Collections.nCopies(variables.size(), values.get(0))
                : ImmutableList.copyOf(values);
        this.hypotheses = ImmutableList.copyOf(hypotheses);
        final ImmutableSet.Builder<String> setVars = ImmutableSet.builder();
        for (final Variable v : variables) {
            if (v.scope != XScope.Event) {
                setVars.add(v.name);
            }
        }
        this.variablesRequiringSettableScope = setVars.build();
    }

    public static class Setter extends ChangeValue {

        @AssistedInject
        public Setter(final IDimension<IFlags> flagsDimension,
                final IProfilingStack profiler,
                @Assisted final List<Variable> variables,
                @Assisted final List<IComponentsFunction<Number>> values,
                @Assisted final List<IComponentsAction> hypotheses) {
            super(flagsDimension, profiler, variables, values, hypotheses);
        }

        @Override
        protected Number getNewValue(final IProfilingStack profiler,
                final IComponentsScope scope,
                final ILets lets,
                final IComponentsFunction<Number> fn,
                final Variable variable) {
            return fn.compute(scope, lets);
        }
    }

    public static class Increaser extends ChangeValue {

        @AssistedInject
        public Increaser(final IDimension<IFlags> flagsDimension,
                final IProfilingStack profiler,
                @Assisted final List<Variable> variables,
                @Assisted final List<IComponentsFunction<Number>> values,
                @Assisted final List<IComponentsAction> hypotheses) {
            super(flagsDimension, profiler, variables, values, hypotheses);
        }

        @Override
        protected Number getNewValue(final IProfilingStack profiler,
                final IComponentsScope scope,
                final ILets lets,
                final IComponentsFunction<Number> fn,
                final Variable variable) {
            return fn.compute(scope, lets).doubleValue()
                    + checkPresent(scope, variable.name, variable.get(scope, flagsDimension)).doubleValue();
        }
    }

    public static class Decreaser extends ChangeValue {

        @AssistedInject
        public Decreaser(final IDimension<IFlags> flagsDimension,
                final IProfilingStack profiler,
                @Assisted final List<Variable> variables,
                @Assisted final List<IComponentsFunction<Number>> values,
                @Assisted final List<IComponentsAction> hypotheses) {
            super(flagsDimension, profiler, variables, values, hypotheses);
        }

        @Override
        protected Number getNewValue(final IProfilingStack profiler,
                final IComponentsScope scope,
                final ILets lets,
                final IComponentsFunction<Number> fn,
                final Variable variable) {
            return checkPresent(scope, variable.name, variable.get(scope, flagsDimension)).doubleValue()
                    - fn.compute(scope, lets).doubleValue();
        }
    }

    public static class Variable {

        private final XScope scope;
        public final String name;
        private final Optional<Double> defaultValue;

        public Variable(final String name, final XScope scope, final Optional<Double> defaultValue) {
            this.scope = scope;
            this.name = name;
            this.defaultValue = defaultValue;
        }

        @SuppressWarnings({"unchecked", "rawtypes"})
        public Optional<Number> get(final IComponentsScope scope, final IDimension<IFlags> flags) {
            switch (this.scope) {
                case Simulation:
                    return scope.getGlobalVariable(name, Number.class).or(defaultValue);
                case Event:
                    return (Optional) scope.getYielded(name).or(defaultValue);
                case House:
                    return (Optional) scope.get(flags).getRegister(name).or(defaultValue);
                default:
                    return Optional.<Number>absent();
            }
        }

        public void set(final IComponentsScope scope, final IDimension<IFlags> flags, final Number value) {
            switch (this.scope) {
                case Simulation:
                    ((ISettableComponentsScope) scope).setGlobalVariable(name, value);
                    break;
                case Event:
                    scope.yield(name, value.doubleValue());
                    break;
                case House:
                    ((ISettableComponentsScope) scope).modify(flags, new IModifier<IFlags>() {
                        @Override
                        public boolean modify(final IFlags modifiable) {
                            modifiable.setRegister(name, value.doubleValue());
                            return true;
                        }
                    });
                    break;
            }
        }

        public void copy(final IComponentsScope in, final IComponentsScope out, final IDimension<IFlags> flags) {
            // TODO could set flags en masse
            set(out, flags, get(in, flags).get());
        }
    }

    @Override
    public StateChangeSourceType getSourceType() {
        return StateChangeSourceType.ACTION;
    }

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets) throws NHMException {
        reallyApply(scope, lets);
        return true;
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        return true;
    }

    @Override
    public boolean isAlwaysSuitable() {
        return true;
    }

    @Override
    public ILets reallyApply(final ISettableComponentsScope scope, final ILets lets) {
        updateValues(scope, lets);
        return lets;
    }

    @Override
    public ILets reallyApply(final IComponentsScope scope, final ILets lets) {
        updateValues(scope, lets);
        return lets;
    }

    private Set<DateTime> changeDates;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Iterable<? extends DateTime> getChangeDates() {
        if (changeDates == null) {
            final ImmutableSet.Builder<DateTime> ds = ImmutableSet.builder();
            for (final IComponentsFunction cf : values) {
                ds.addAll(cf.getChangeDates());
            }
            changeDates = ds.build();
        }
        return changeDates;
    }

    private Set<IDimension<?>> dependencies;

    @SuppressWarnings({"rawtypes", "unchecked"})
    @Override
    public Iterable<? extends IDimension<?>> getDependencies() {
        if (dependencies == null) {
            final ImmutableSet.Builder<IDimension<?>> ds = ImmutableSet.builder();
            for (final IComponentsFunction cf : values) {
                ds.addAll(cf.getDependencies());
            }
            ds.add(flagsDimension);
            dependencies = ds.build();
        }
        return dependencies;
    }

    @Override
    public boolean needsIsolation() {
        return false;
    }

    private void updateValues(final IComponentsScope scope, final ILets lets) {
        checkLegality(scope);
        if (hypotheses.isEmpty()) {
            // just change the scope, one step at a time
            computeValues(scope, lets);
        } else {
            // do the change in a hypothetical, and then modify the scope to reflect the
            // effect
            final IHypotheticalComponentsScope hypothesis = scope.createHypothesis();
            for (final IComponentsAction ac : hypotheses) {
                hypothesis.apply(ac, lets);
            }
            computeValues(hypothesis, lets);
            for (final Variable v : variables) {
                v.copy(hypothesis, scope, flagsDimension);
            }
        }
    }

    private void computeValues(final IComponentsScope scope, final ILets lets) {
        final Iterator<Variable> variableIterator = variables.iterator();
        final Iterator<IComponentsFunction<Number>> valueIterator = values.iterator();
        while (variableIterator.hasNext() && valueIterator.hasNext()) {
            final Variable variable = variableIterator.next();
            final IComponentsFunction<Number> value = valueIterator.next();
            variable.set(scope, flagsDimension, getNewValue(profiler, scope, lets, value, variable));
        }
    }

    protected abstract Number getNewValue(final IProfilingStack profiler,
            final IComponentsScope scope,
            final ILets lets,
            final IComponentsFunction<Number> value,
            final Variable variable);

    protected final Number checkPresent(final IComponentsScope scope, final String name, final Optional<Number> number) {
        if (!number.isPresent()) {
            // die
            throw profiler.die(String.format("%s has no default value and has not been set yet, so cannot be increased or decreased", name), this, scope);
        } else {
            return number.get();
        }
    }

    private void checkLegality(final IComponentsScope scope) {
        if (variablesRequiringSettableScope.isEmpty()) {
            return;
        }
        if (scope instanceof ISettableComponentsScope) {
            return;
        }
        throw profiler.die(String.format("%s are not event scoped, and so cannot be modified here",
                Joiner.on(", ").join(variablesRequiringSettableScope)),
                this, scope);
    }
}
