package uk.org.cse.nhm.simulator.obligations;

import java.util.Collection;

import org.joda.time.DateTime;

import com.google.common.base.Optional;

import uk.org.cse.nhm.simulator.scope.IComponentsAction;
import uk.org.cse.nhm.simulator.scope.IComponentsScope;
import uk.org.cse.nhm.simulator.state.IDwelling;
import uk.org.cse.nhm.simulator.transactions.IPayment;

/**
 * Represents an obligation a dwelling has to do some transactions in the future
 * 
 * @author hinton
 * @since 3.7.0
 */
public interface IObligation extends IComponentsAction {
	/**
	 * @param after
	 * @return the date of the next transaction, after the given date.
	 */
	public Optional<DateTime> getNextTransactionDate(final DateTime state);
	
	/**
	 * @param state
	 * @return the payments associated with the given state of affairs for a dwelling;
	 * 		 <p> note that it is the duty of the obligation to detect the case in which
	 * 			 the given date does not match the state's date, and if necessary mock up
	 *           a new state for any delegates which use the time dimension. you have been warned.
	 */
	public Collection<IPayment> generatePayments(final DateTime onDate, final IComponentsScope state);
	
	/**
	 * Invoked to tell the obligation to consider itself "real" and to schedule itself to act
	 * on the given dwelling
	 */
	public void handle(final IDwelling dwelling);

    /**
     * Invoked when a dwelling previously given to handle has been destroyed.
     */
    public void forget(final IDwelling dwelling);
}
