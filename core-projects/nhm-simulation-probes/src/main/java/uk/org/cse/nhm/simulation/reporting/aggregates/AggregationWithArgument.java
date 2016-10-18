package uk.org.cse.nhm.simulation.reporting.aggregates;

import java.util.Set;

import com.google.common.base.Optional;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.functions.IComponentsFunction;

abstract class AggregationWithArgument extends AggregationFunction {
    private final IComponentsFunction<Number> argument;
	
	protected AggregationWithArgument(final IComponentsFunction<Number> argument) {
		this.argument = argument;
	}

    protected abstract double evaluate(final double[][] wvalues);
    
    public double evaluate(final IState state, final ILets lets, final Set<IDwelling> dwellings) {
        final double[][] map = new double[dwellings.size()][2];
        int i = 0;
        for (final IDwelling d : dwellings) {
            map[i][0] = argument.compute(state.detachedScope(d), lets).doubleValue();
            map[i][1] = d.getWeight();
            i++;
        }
        return evaluate(map);
    }

    public double evaluate(final IStateScope scope, final ILets lets, final Set<IDwelling> dwellings) {
        final double[][] map = new double[dwellings.size()][2];
        int i = 0;
        final IState state = scope.getState();
        for (final IDwelling d : dwellings) {
            final Optional<IComponentsScope> subscope = scope.getComponentsScope(d);
            if (subscope.isPresent()) {
                map[i][0] = argument.compute(subscope.get(), lets).doubleValue();
            } else {
                map[i][0] = argument.compute(state.detachedScope(d), lets).doubleValue();
            }
            map[i][1] = d.getWeight();
            i++;
        }
        return evaluate(map);
    }
}
