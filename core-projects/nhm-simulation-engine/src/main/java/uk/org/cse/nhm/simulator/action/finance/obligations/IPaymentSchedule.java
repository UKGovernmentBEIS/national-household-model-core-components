package uk.org.cse.nhm.simulator.action.finance.obligations;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

import uk.org.cse.nhm.simulator.let.ILets;
import uk.org.cse.nhm.simulator.state.ICanonicalState;
import uk.org.cse.nhm.simulator.state.IDwelling;

public interface IPaymentSchedule {

    Optional<DateTime> getNextTransactionDate(DateTime currentDate);

    boolean transactionStillValid(DateTime date);

    public interface IFactory {

        IPaymentSchedule getSchedule(ICanonicalState state, DateTime currentDate, ILets lets, IDwelling dwelling);
    }
}
