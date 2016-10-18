package uk.org.cse.nhm.simulation.reporting.aggregates;

import java.util.Set;

import javax.inject.Inject;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IStateScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IState;


public class Count extends AggregationFunction {
	@Inject
	public Count() {
	}
	
	@Override
	public double evaluate(final IState state, final ILets lets, final Set<IDwelling> dwellings) {
		return sw(dwellings);
	}

    @Override
	public double evaluate(final IStateScope state, final ILets lets, final Set<IDwelling> dwellings) {
        return sw(dwellings);
	}

    private static double sw(final Set<IDwelling> dwellings) {
        double total = 0;
		for (final IDwelling d : dwellings) total += d.getWeight();
		return total;
    }
}
