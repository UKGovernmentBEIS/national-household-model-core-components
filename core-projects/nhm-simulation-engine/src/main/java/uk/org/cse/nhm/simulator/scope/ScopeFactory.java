package uk.org.cse.nhm.simulator.scope;

import java.util.Map;

import javax.inject.Inject;

import uk.org.cse.commons.scopes.IScope;
import uk.org.cse.nhm.simulator.obligations.IObligationHistory;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.IDimension;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.IHypotheticalBranch;
import uk.org.cse.nhm.simulator.state.IState;
import uk.org.cse.nhm.simulator.state.IStateChangeSource;
import uk.org.cse.nhm.simulator.state.dimensions.time.ITimeDimension;
import uk.org.cse.nhm.simulator.transactions.DwellingTransactionHistory;

public class ScopeFactory {

    private final IDimension<IObligationHistory> obligationDimension;
    private final IDimension<DwellingTransactionHistory> transactionDimension;
    private final ITimeDimension timeDimension;

    @Inject
    public ScopeFactory(final IDimension<IObligationHistory> obligationDimension, final IDimension<DwellingTransactionHistory> transactionDimension, final ITimeDimension timeDimension) {
        this.obligationDimension = obligationDimension;
        this.transactionDimension = transactionDimension;
        this.timeDimension = timeDimension;
    }

    /**
     * @param action
     * @param branch
     * @param dwelling
     * @param yields - this argument will be shared with the resulting scope; no
     * defensive copy is taken, so think carefully
     * @return
     */
    public ComponentsScope createComponentsScope(final IScope<?> parentScope, final IStateChangeSource action, final IBranch branch, final IDwelling dwelling, final Map<String, Double> yields) {
        return new ComponentsScope(
                parentScope,
                transactionDimension,
                obligationDimension,
                timeDimension,
                this,
                action,
                branch,
                dwelling,
                yields);
    }

    public StateScope createStateScope(final IStateChangeSource tag, final IBranch branch) {
        return new StateScope(null, tag, branch, this);
    }

    public StateScope fromParentScope(final StateScope parent, final IStateChangeSource tag, final IBranch branch) {
        if (parent.getState() == branch) {
            return new StateScope(parent.parentScope, tag, branch, this);
        } else {
            return new StateScope(parent, tag, branch, this);
        }
    }

    public ImmutableComponentsScope createImmutableScope(final IState state, final IDwelling dwelling) {
        return new ImmutableComponentsScope(this, state, dwelling);
    }

    /**
     * @param action
     * @param branch
     * @param dwelling
     * @param yields - this argument will be shared with the resulting scope,
     * without a defensive copy
     * @return
     */
    public HypotheticalComponentsScope createHypotheticalScope(final IScope<?> parentScope, final IStateChangeSource action, final IHypotheticalBranch branch, final IDwelling dwelling, final Map<String, Double> yields) {
        return new HypotheticalComponentsScope(
                parentScope,
                transactionDimension,
                obligationDimension,
                timeDimension,
                this,
                action,
                branch,
                dwelling,
                yields);
    }
}
