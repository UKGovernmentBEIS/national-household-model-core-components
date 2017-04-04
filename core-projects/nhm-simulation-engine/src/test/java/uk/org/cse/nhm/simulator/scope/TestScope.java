package uk.org.cse.nhm.simulator.scope;

import java.util.Map;

import uk.org.cse.commons.scopes.IScope;

import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;

class TestScope extends ComponentsScope {
	public TestScope(
			final IDimension<DwellingTransactionHistory> dim, 
			final ITimeDimension time,
			final IStateChangeSource action,
			final IBranch branch, final IDwelling dwelling, final Map<String, Double> ys) {
        super(null, dim, null, time, null,  action, branch, dwelling, ys);
	}
	
	@Override
    protected ComponentsScope doCreateChild(
        final IScope<?> parentScope,
        final IStateChangeSource action, final IBranch branch, final Map<String, Double> y) {
		return new TestScope(this.transactionDimension, this.timeDimension, action, branch, dwelling, y);
	}

	public IBranch getBranch() {
		return branch;
	}
}
