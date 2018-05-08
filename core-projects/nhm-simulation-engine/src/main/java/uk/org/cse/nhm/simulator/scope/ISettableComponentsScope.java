package uk.org.cse.nhm.simulator.scope;

import java.util.List;
import java.util.Set;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.obligations.IObligation;
import uk.org.cse.nhm.simulator.state.IBranch;
import uk.org.cse.nhm.simulator.state.ISettableComponents;
import uk.org.cse.nhm.simulator.transactions.IPayment;
import uk.org.cse.nhm.simulator.util.RandomSource;

/**
 * A scope for performing operations on an individual house.
 *
 * @since 3.7.0
 */
public interface ISettableComponentsScope extends IComponentsScope, ISettableComponents {

    boolean apply(final IComponentsAction action, final ILets lets) throws NHMException;

    boolean apply(final Set<IComponentsAction> actions, final ILets lets, final IPicker picker) throws NHMException;

    boolean applyInSequence(final List<IComponentsAction> actions, final ILets lets, final boolean requireSuccess) throws NHMException;

    public interface IPicker {

        PickOption pick(final RandomSource randomSource, final Set<PickOption> options);
    }

    void addObligation(final IObligation obligation);

    void addTransaction(final IPayment payment);

    <T> void setGlobalVariable(final String string, final T value);

    @Override
    public IBranch getState();
}
