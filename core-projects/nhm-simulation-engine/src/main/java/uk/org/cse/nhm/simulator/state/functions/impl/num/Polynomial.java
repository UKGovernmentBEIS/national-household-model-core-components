package uk.org.cse.nhm.simulator.state.functions.impl.num;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.joda.time.DateTime;

import com.google.common.collect.ImmutableSet;
import com.google.inject.assistedinject.Assisted;

import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

/**
 * A function which evaluates a "variable" function x, and then uses a sequence
 * of other functions to compute the coefficients of terms in a polynomial on x.
 *
 * @author hinton
 *
 */
public class Polynomial extends AbstractNamed implements IComponentsFunction<Double> {

    final IComponentsFunction<? extends Number> x;
    final Set<IDimension<?>> dependencies;
    final List<PolynomialTerm> terms;

    public static class PolynomialTerm {

        public final int degree;
        public final IComponentsFunction<? extends Number> coefficient;

        public PolynomialTerm(final int degree,
                final IComponentsFunction<? extends Number> coefficient) {
            this.degree = degree;
            this.coefficient = coefficient;
        }
    }

    /**
     * The sequence of functions on which this depends. The first function
     * computes the "x" value, and subsequent functions are used to compute the
     * coefficients of x^0, x^1, and so on.
     *
     * @param values
     */
    @Inject
    public Polynomial(@Assisted final IComponentsFunction<? extends Number> x, @Assisted final List<PolynomialTerm> terms) {
        this.x = x;
        this.terms = terms;

        final ImmutableSet.Builder<IDimension<?>> dims = ImmutableSet.builder();

        dims.addAll(x.getDependencies());

        for (final PolynomialTerm t : terms) {
            dims.addAll(t.coefficient.getDependencies());
        }

        dependencies = dims.build();
    }

    @Override
    public Double compute(final IComponentsScope scope, final ILets lets) {
        final double x1 = x.compute(scope, lets).doubleValue();

        double accumulator = 0d;

        for (final PolynomialTerm term : terms) {
            accumulator += (Math.pow(x1, term.degree) * term.coefficient.compute(scope, lets).doubleValue());
        }

        return accumulator;
    }

    @Override
    public Set<IDimension<?>> getDependencies() {
        return dependencies;
    }

    @Override
    public Set<DateTime> getChangeDates() {
        final ImmutableSet.Builder<DateTime> ds = ImmutableSet.builder();

        ds.addAll(x.getChangeDates());
        for (final PolynomialTerm t : terms) {
            ds.addAll(t.coefficient.getChangeDates());
        }

        return ds.build();
    }
}
