package uk.org.cse.nhm.simulator.obligations;

import java.util.Collection;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

import uk.org.cse.nhm.NHMException;
import uk.org.cse.nhm.simulator.AbstractNamed;
import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.scope.ISettableComponentsScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.state.StateChangeSourceType;
import uk.org.cse.nhm.simulator.transactions.IPayment;

public class TestObligation extends AbstractNamed implements IObligation {

    @Override
    public boolean apply(final ISettableComponentsScope scope, final ILets lets)
            throws NHMException {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean isSuitable(final IComponentsScope scope, final ILets lets) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public StateChangeSourceType getSourceType() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<DateTime> getNextTransactionDate(final DateTime state) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Collection<IPayment> generatePayments(final DateTime onDate,
            final IComponentsScope state) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean isAlwaysSuitable() {
        return false;
    }

    @Override
    public void handle(final IDwelling dwelling) {
        // TODO Auto-generated method stub

    }

    @Override
    public void forget(final IDwelling dwelling) {
    }
}
